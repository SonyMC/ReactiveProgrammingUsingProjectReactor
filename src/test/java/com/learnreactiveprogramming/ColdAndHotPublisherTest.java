package com.learnreactiveprogramming;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

import static com.learnreactiveprogramming.util.CommonUtil.delay;
import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

public class ColdAndHotPublisherTest {

    @Test
    public void coldPublisherTest() throws InterruptedException { // cold subscription which will emit al values for all subscribers

        var flux = Flux.range(1, 10);

        flux.subscribe(s -> System.out.println("Subscriber 1 : " + s)); //emits the value from beginning
        flux.subscribe(s -> System.out.println("Subscriber 2 : " + s));//emits the value from beginning
    }

    @Test
    public void hotPublisherTest() throws InterruptedException {

        Flux<Integer> stringFlux = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1));  // introduce a delay of 1 second for each element emitted by this flux

        // publish() is the easiest way to convert a cold stream to a hot one. This will convert the Flux to a ConnectableFlux.
        // ConnectableFlux will only emit values if there is at least one subscriber
        ConnectableFlux<Integer> connectableFlux = stringFlux.publish();
        // Connect to the hot stream
        connectableFlux.connect();

        // Introduce a delay of 3 seconds so that some events in the above flux will be missed by the subscribers
        Thread.sleep(3000);

        // Add a subscriber
        connectableFlux.subscribe(s -> System.out.println("Subscriber 1 : " + s));
        //delay(2000); // we are introducing a delay of 2 secs to ensure no other subscriber is invoked in same thread
        Thread.sleep(1000); // we are putting the thread to sleep for 1 second to ensure no other subscriber is invoked in same thread


        // Add a subscriber
        connectableFlux.subscribe(s -> System.out.println("Subscriber 2 : " + s)); // does not get the values from beginning
        Thread.sleep(10000); // we are putting the thread to sleep for 1 second to ensure no other subscriber is invoked in same thread

    }

    @Test
    public void hotPublisherTest_autoConnect() throws InterruptedException {

        Flux<Integer> stringFlux = Flux.range(1, 10)
                .doOnSubscribe(s -> {
                    System.out.println("Subscription started");
                })
                .delayElements(Duration.ofSeconds(1));  // introduce delay of 1 second

        // this "autoConnect" call needs to be connected to the publish method itself
        var hotSource = stringFlux.publish().autoConnect(2);  //autoConnect() will start emitting values only if at least two subscribers is available and returns a Flux<>

        // Subscriber 1
        var disposable = hotSource.subscribe(s -> System.out.println("Subscriber 1 : " + s));
        delay(2000);

        // Subscriber 2
        var disposable1 =  hotSource.subscribe(s -> System.out.println("Subscriber 2 : " + s)); // does not get the values from beginning
        System.out.println("Two subscribers connected");
        delay(2000);

        // cancel 1st subscriber
        disposable.dispose();
        // cancels 2nd subscriber
        disposable1.dispose();

        // 3rd subscribers
        hotSource.subscribe(s -> System.out.println("Subscriber 3 : " + s)); // does not get the values from beginning
        System.out.println("Three subscribers connected");
        Thread.sleep(10000);

    }

    @Test
    public void hotPublisherTest_refCount() throws InterruptedException {

        Flux<Integer> stringFlux = Flux.range(1, 10)
                .doOnSubscribe(s -> {
                    System.out.println("Subscription received");
                })
                .doOnCancel(() -> {
                    System.out.println("Received Cancel Signal");
                })
                .delayElements(Duration.ofSeconds(1));

        // this "refCount" call needs to be connected to the publish method itself
        // refCount: Connects to the upstream source when the given number of org.reactivestreams.Subscriber subscribes and disconnects when all Subscribers cancelled or the upstream source completed.
        var hotSource = stringFlux.publish().refCount(2);

        var disposable = hotSource.subscribe(s -> System.out.println("Subscriber 1 : " + s));
        delay(1000);
        var disposable1 = hotSource.subscribe(s -> System.out.println("Subscriber 2 : " + s)); // does not get the values from beginning
        System.out.println("Two subscribers connected");
        delay(2000);

        disposable.dispose();
        disposable1.dispose(); // this cancels the whole subscription

        //  This does not start the subscriber to emit the values,because of minimum of 2 subscribers needed.
        hotSource.subscribe(s -> System.out.println("Subscriber 3 : " + s));

        // Run by showing the above code and then enable the below code and run it.
        delay(2000);

        // By adding the fourth subscriber enables the minimum subscriber condition and it starts to emit the values
        hotSource.subscribe(s -> System.out.println("Subscriber 4: " + s));
        delay(10000);
    }

}
