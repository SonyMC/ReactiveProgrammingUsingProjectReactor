package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.exception.ReactorException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.learnreactiveprogramming.util.CommonUtil.delay;

@Slf4j
public class FluxAndMonoGeneratorService {

    static List<String> namesList = List.of("alex", "ben", "chloe");
    static List<String> namesList1 = List.of("adam", "jill", "jack");
    int stringLength =3;

    /*
     *       GENERIC functions
     *
     * */


    // Function to convert to Upper Case
    private String upperCase(String name) {
        delay(1000);
        return name.toUpperCase();
    }

    // Function to split the provided string into individual chars and return as a FLux
    public Flux<String> splitString(String name) {
        //split the provided string into an array
        var charArray= name.split(""); // split based on the supplied delimiter . Here we are interested in extracting all individual chars in the string
        return Flux.fromArray(charArray); // convert and return as Flux
    }

    // Introduce a time delay to split the provided string into individual chars and return as a FLux
    public Flux<String> splitString_withDelay(String name) {

        // get a random integer between 0 to 1000 which will eb used by the .delayElements call
        var delay = new Random().nextInt(1000);

        //var delay =1000;  // 1 second

        //split the provided string into an array
        var charArray= name.split(""); // split based on the supplied delimiter . Here we are interested in extracting all individual chars in the string
        return Flux.fromArray(charArray) // convert and return as Flux
                .delayElements(Duration.ofMillis(delay));        // duration by which to delay each Subscriber.onNext signal
    }


    // Function to split the provided string into individual chars and return as a Mono
    private Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");  // split string and store in char array
        var charList = List.of(charArray); // Create List from char array
        return Mono.just(charList);
    }

    // Return List of names with a delay of 1 sec
    public static List<String> names() {
        delay(1000);
        return List.of("alex", "ben", "chloe");
    }

    // Return a single name with a delay of 1 sec
    private String name() {
        delay(1000);
        return "alex";
    }


    public void sendEvents(FluxSink<String> sink) {
        {
            CompletableFuture.supplyAsync(() -> names()) // place the blocking call inside the create function
                    .thenAccept(names -> {
                        names.forEach(sink::next);
                        })
                    .thenRun(sink::complete);

        }
    }


    public void exception() {
        try {
            // code statements
        } catch (Exception e) {
            //log the exception
            throw e;
        }
    }

    /*
     *    FLUX
     *
     * */

    //METHOD - to demo Flux
    // Datasource 1 - Publisher
    //returns a Flux(reactive type that represents 0 to N elements ) of String
    public Flux<String> namesFlux(){
        //fromIterable() takes a collection
        //create and return a flux using the collection
        //return Flux.fromIterable(List.of("alex","ben","chloe"));  // though hard-coded here, the collection will usually be returned from a DB or service call
        return Flux.fromIterable(List.of("alex","ben","chloe"))
                .log();  // log each event between teh publisher and subscriber
    }

    // METHOD : to demo map operator on Flux reactive type
    public Flux<String> namesFlux_map() {
        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
                // **** longer version of lambda function
//                .map(s -> {
//                    return s.toUpperCase();
//                })
                // *** shorter version of lambda function
//              .map(s -> s.toUpperCase() )
//              .map((s -> s.toUpperCase() ))
                // **** method reference to String class's toUpperCase function
                .map(String::toUpperCase)
                .log();
    }


    // METHOD : to demo map operator & doOnNext on Flux reactive type
    public Flux<String> namesFlux_map_OnNext() {
        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                //.doOnNext accepts a Consumer(Functional Interface whose method is accept and which does not return anything
                .doOnNext(name -> {
                    System.out.println("Name is:"  + name);
                    name.toLowerCase();  // will have no impact as doOnNext() is a side Effect Operator
                })
                .log();
    }


    // METHOD : to demo map operator & doOnNext & doOnSubscribe on Flux reactive type
    public Flux<String> namesFlux_map_OnNext_OnSubscribe() {
        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                //doOnNext accepts a Consumer(Functional Interface whose method is accept and which does not return anything
                .doOnNext(name -> {
                    System.out.println("Name is:"  + name);
                    name.toLowerCase();  // will have no impact as doOnNext() is a side Effect Operator
                })
                //doOnSubscribe accepts a Consumer of type Subscription
                .doOnSubscribe( s->{
                    System.out.println("Subscription is :" + s);
                })
                .log();
    }

    // METHOD : to demo map operator & doOnNext and doOnSubscribe & doOnComplete on Flux reactive type
    public Flux<String> namesFlux_map_OnNext_OnSubscribe_onComplete() {
        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                //doOnNext accepts a Consumer(Functional Interface whose method is accept and which does not return anything
                .doOnNext(name -> {
                    System.out.println("Name is:"  + name);
                    name.toLowerCase();  // will have no impact as doOnNext() is a side Effect Operator
                })
                //doOnSubscribe accepts a Consumer of type Subscription. Will be called only once at the beginning
                .doOnSubscribe( s->{
                    System.out.println("Subscription is :" + s);
                })
                //doOnComplete accepts a Runnable Interface( no input and nothing is returned. Will be called only once at the end
                .doOnComplete( () -> {
                    System.out.println("Inside Complete Callback!!!");
                })
                .log();
    }


    // METHOD : to demo map operator & doOnNext and doOnSubscribe & doOnComplete & doFinally on Flux reactive type
    public Flux<String> namesFlux_map_OnNext_OnSubscribe_onComplete_doFinal() {
        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .map(String::toUpperCase)
                //doOnNext accepts a Consumer(Functional Interface whose method is accept and which does not return anything
                .doOnNext(name -> {
                    System.out.println("Name is:"  + name);
                    name.toLowerCase();  // will have no impact as doOnNext() is a side Effect Operator
                })
                //doOnSubscribe accepts a Consumer of type Subscription. Will be called only once at the beginning
                .doOnSubscribe( s->{
                    System.out.println("Subscription is :" + s);
                })
                //doOnComplete accepts a Runnable Interface( no input and nothing is returned. Will be called only once at the end
                .doOnComplete( () -> {
                    System.out.println("Inside Complete Callback!!!");
                })
                //doFinally accepts a Consumer
                .doFinally( signalType->{
                    System.out.println("Final Event > " + signalType);
                })
                .log();
    }

    // METHOD : to demo immutability on Flux reactive type
    // Note : Here since we are trying to assign the Reactive stream returned from the Flux to a variable and then apply the mapper, it will not transform the strings to upper cases due to immutability.
    public Flux<String> namesFlux_immutability() {
        // convert each flux element from lower to upper
        var namesFlux = Flux.fromIterable(List.of("alex", "ben", "chloe"));
        // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
        // **** method reference to String class's toUpperCase function
        namesFlux.map(String::toUpperCase)
                .log();
        return namesFlux;
    }


    // METHOD : to demo filter operator on Flux reactive type
    public Flux<String> namesFlux_mapAndFilter() {


        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
                // **** method reference to String class's toUpperCase function
                .map(String::toUpperCase)
                // *** filter the string whose length is greater than 3 ( i.e. allow only strings of length greater than 3)
                //.filter(s -> s.length() > 3)
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + "-" + s) //prefix string with length
                .log();
    }


    // METHOD : to demo flatMap operator on Flux reactive type
    public Flux<String> flux_flatMap() {


        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
                // **** method reference to String class's toUpperCase function
                .map(String::toUpperCase)
                // *** filter the string whose length is greater than 3 ( i.e. allow only strings of length greater than 3)
                //.filter(s -> s.length() > 3)
                .filter(s -> s.length() > stringLength)
                //return individual characters of the names viz. A,L,E,X,C,H,L,O,E
                .flatMap( s -> splitString(s))  // the splitString function returns a Flux of individual components
                .log();
    }

    /**
     * @param stringLength
     * @return AL, EX, CH, LO, E
     */
    public Flux<String> namesFlux_flatmap_sequential(int stringLength) {
        var namesList = List.of("alex", "ben", "chloe"); // a, l, e , x
        return Flux.fromIterable(namesList)
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitString)
                .log();
        //using "map" would give the return type as Flux<Flux<String>

    }


    // METHOD : to demo asynchronous operations on flatMap operator on Flux reactive type
    public Flux<String> flux_flatMap_async() {


        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
                // **** method reference to String class's toUpperCase function
                .map(String::toUpperCase)
                // *** filter the string whose length is greater than 3 ( i.e. allow only strings of length greater than 3)
                //.filter(s -> s.length() > 3)
                .filter(s -> s.length() > stringLength)
                //return individual characters of the names viz. A,L,E,X,C,H,L,O,E
                .flatMap( s -> splitString_withDelay(s))  // the splitString function returns a Flux of individual components with a delay
                .log();
    }


    // METHOD : to demo synchronous operations using concatMAp() operator on Flux reactive type
    public Flux<String> flux_concatMap() {


        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
                // **** method reference to String class's toUpperCase function
                .map(String::toUpperCase)
                // *** filter the string whose length is greater than 3 ( i.e. allow only strings of length greater than 3)
                //.filter(s -> s.length() > 3)
                .filter(s -> s.length() > stringLength)
                //return individual characters of the names viz. A,L,E,X,C,H,L,O,E
                .concatMap( s -> splitString_withDelay(s))  // the splitString function returns a Flux of individual components with a delay
                .log();
    }





    // METHOD : to demo transform operator on Flux reactive type
    // Accepts a Function Functional Interface
    public Flux<String> flux_transform() {


        // Function Functional Interfaces are used to extract a functionality and assign that functionality to a variable
        // Transform function accepts a Function Functional interface where both Input and Output are Flux
        Function<Flux<String>,Flux<String>> filtermap = name -> name.map(String::toUpperCase)  //**** method reference to String class's toUpperCase function
                .filter(s -> s.length() > stringLength)  // *** filter the string whose length is greater than 3 ( i.e. allow only strings of length greater than 3)
                .flatMap( s -> splitString(s))  //return individual characters of the names viz. A,L,E,X,C,H,L,O,E
                .defaultIfEmpty("default"); // specify a default value if no value is returned

        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filtermap)
                .log();
    }

    // METHOD : to demo transform operator with Default Switch on Flux reactive type
    // Accepts a Function Functional Interface
    public Flux<String> flux_transform_switchDefault() {

        int stringLength =6;

        // Function Functional Interfaces are used to extract a functionality and assign that functionality to a variable
        // Transform function accepts a Function Functional interface where both Input and Output are Flux
        Function<Flux<String>,Flux<String>> filtermap = name -> name.map(String::toUpperCase)  //**** method reference to String class's toUpperCase function
                .filter(s -> s.length() > stringLength)  // *** filter the string whose length is greater than 6 ( i.e. allow only strings of length greater than 6)
                .flatMap( s -> splitString(s))
                .defaultIfEmpty("default"); // specify a default value if no value is returned

        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filtermap)   // will return default value as no names have length greater than 6
                .log();
    }


    // METHOD : to demo transform operator with Empty Switch on Flux reactive type
    // Accepts a Function Functional Interface
    public Flux<String> flux_transform_switchifEmpty() {

        int stringLength =6;

        // Function Functional Interfaces are used to extract a functionality and assign that functionality to a variable
        // Transform function accepts a Function Functional interface where both Input and Output are Flux
        Function<Flux<String>,Flux<String>> filtermap = name -> name.map(String::toUpperCase)  //**** method reference to String class's toUpperCase function
                .filter(s -> s.length() > stringLength)  // *** filter the string whose length is greater than 3 ( i.e. allow only strings of length greater than 3)
                .flatMap( s -> splitString(s));  //return individual characters of the names viz. A,L,E,X,C,H,L,O,E


        // Create a Publisher Flux which can be used as input to the switchIf operator
        var defaultFlux = Flux.just("default")
                .transform(filtermap);   // reuse Function Functional Interface filtermap

        // convert each flux element from lower to upper
        return Flux.fromIterable(List.of("alex", "ben", "chloe"))
                .transform(filtermap)  //// will return default value as no names have length greater than 6
                .switchIfEmpty(defaultFlux)   // accepts only a Publisher
                .log();
    }



    // Create a Publisher Flux to explore concat()
    public Flux<String> explore_concat(){
        var abcFlux = Flux.just("A","B","C");  // create a flux for A,B,C
        var defFlux = Flux.just("D","E","F");  // create a flux fro D,E,F

        return Flux.concat(abcFlux,defFlux) // concat is a Static Method
                .log();
    }


    // Create a Publisher Flux to explore concatWith()
    public Flux<String> explore_concatWith(){
        var abcFlux = Flux.just("A","B","C");  // create a flux for A,B,C
        var defFlux = Flux.just("D","E","F");  // create a flux fro D,E,F

        return abcFlux.concatWith(defFlux)  // concatWith is an Instance Method
                .log();
    }

    // Create a Publisher Flux to explore merge()
    public Flux<String> explore_merge(){
        var abcFlux = Flux.just("A","B","C")  // create a flux for A,B,C
                .delayElements(Duration.ofMillis(100)); // the parallel processing wnt be evident unless we introduce a selay
        var defFlux = Flux.just("D","E","F")  // create a flux fro D,E,F
                .delayElements(Duration.ofMillis(125));
        return Flux.merge(abcFlux,defFlux) // merge is a Static Method
                .log();
    }


    // Create a Publisher Flux to explore mergeWith()
    public Flux<String> explore_mergetWith(){
        var abcFlux = Flux.just("A","B","C")  // create a flux for A,B,C
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D","E","F")  // create a flux fro D,E,F
                .delayElements(Duration.ofMillis(125));
        return abcFlux.mergeWith(defFlux)  // mergeWith is an Instance Method
                .log();
    }

    // Create a Publisher Flux to explore mergeSequential()
    public Flux<String> explore_mergeSequential(){
        var abcFlux = Flux.just("A","B","C")  // create a flux for A,B,C
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D","E","F")  // create a flux fro D,E,F
                .delayElements(Duration.ofMillis(125));
        return Flux.mergeSequential(abcFlux,defFlux) // mergeSequential is a Static Method
                .log();
    }

    // Create a Publisher Flux to explore zip()
    public Flux<String> explore_zip(){
        var abcFlux = Flux.just("A","B","C");  // create a flux for A,B,C

        var defFlux = Flux.just("D","E","F");  // create a flux for D,E,F

        return Flux.zip(abcFlux,defFlux,(first,second) -> first + second)     // zip is a Static Method, Result is going to be AD,BE,CF
                .log();
    }


    // Create a Publisher Flux to explore zip() for a tuple
    // handles a max. of 8 elements
    public Flux<String> explore_zip_tuple(){
        var abcFlux = Flux.just("A","B","C");  // create a flux for A,B,C

        var defFlux = Flux.just("D","E","F");  // create a flux for D,E,F

        var _123Flux = Flux.just("1","2","3");  // create a flux for 1,2,3

        var _456Flux = Flux.just("4","5","6");  // create a flux for 4,5,6


        return Flux.zip(abcFlux,defFlux,_123Flux,_456Flux)     // zip is a Static Method
                .map(Tuple4 -> Tuple4.getT1() + Tuple4.getT2() + Tuple4.getT3() + Tuple4.getT4())  // output will be AD14, BE25, CF36
                .log();
    }


    // Create a Publisher Flux to explore zipWith()
    public Flux<String> explore_zipWith(){
        var abcFlux = Flux.just("A","B","C");  // create a flux for A,B,C

        var defFlux = Flux.just("D","E","F");  // create a flux for D,E,F

        return abcFlux.zipWith(defFlux,(first,second) -> first + second)     // zipWith is an Instance Method, Result is going to be AD,BE,CF)
                .log();

    }


    // Create a Publisher Flux to explore generate()
      public Flux<Integer> explore_generate() {
        /*
        generate()
        Params:
        stateSupplier – called for each incoming Subscriber to provide the initial state for the generator bifunction
        generator – Consume the SynchronousSink provided per-subscriber by Reactor as well as the current state to generate a single signal on each pass and return a (new) state.
        Returns:a Flux
         */

        Flux<Integer> flux = Flux.generate(

                // Start state = 1 to 10 and output each event after multiplying by 2.
                () -> 1,   // No input -> Output is 1 (Supplier or initial value = 1)
                (state, sink) -> {  // bi-function, Two inputs : stat and Synchronous Sink

                    sink.next(state * 2);  ///multiply each event emitted by 2
                    // if the no. of events generated = 2 , then stop emission
                    if (state == 10) {
                        sink.complete();
                    }
                    return state + 1;  // increment the state by 1
                });

        return flux;
    }

    // Note -create():
    //     (1)  is asynchronous and multithreaded
    //     (2) Can generate OnNext, OnComplete and onError events using the FluxSink class
    //     (3) Multiple sessions in a single round is supported
    /*
    Params:
          emitter – Consume the FluxSink provided per-subscriber by Reactor to generate signals.
           Returns:
            a Flux
     */
    // FluxSink is a functional interface with methods next(),complete, error() etc.
    public Flux<String> explore_create() {

        return Flux.create(sink -> {  // sink is the representation of FluxSink in teh lambda expresssion
            //1. Start with this code
//
//            names()  // static function in this class
//           .forEach(sink::next);  //Emit a name element, generating an onNext signal.
//            sink.complete();
//        });


            //2. Finish with this code
            // Generate  asynchronous events by wrapping using CompletableFuture.supplyAsync
            CompletableFuture.supplyAsync(() -> names()) // place the blocking call inside the create function. Will release calling thread and have the whole execution in a different thread pool
                    .thenAccept(names -> {   // access the list
                 //       names.forEach(sink::next);   //Emit a name element, generating an onNext signal.
                        names.forEach((name) -> {
                            sink.next(name);   // switch to lambda to repeat event generation multiple times
                            sink.next(name);   // Note: multiple emission is only applicable for create ( i.e. wil not work for geenrate)
                        });
                    })
//                    .thenRun(sink::complete);
                    .thenRun(() -> sendEvents(sink)); // sendEvents is a function in this class which repeats the CompletableFuture.supplyAsync above
//                    .whenComplete((data, exception) -> {
//                        sink.error(exception);
                    });

//            sendEvents(sink);

  //      }, FluxSink.OverflowStrategy.BUFFER);
    }


    //The FluxSink exposed by this operator buffers in case of overflow. The buffer is discarded when the main sequence is cancelled.
    public Flux<String> explore_push() {

        Flux<String> flux = Flux.push(sink -> {
            CompletableFuture.supplyAsync(() -> names()) // place the blocking call inside the create function
                    .thenAccept(names -> {
                        names.forEach((s) -> {
                            sink.next(s);
                        });
                    })
                    .thenRun(() -> sendEvents(sink))
                    .whenComplete((data, exception) -> {
                        sink.error(exception);
                    });

            // sendEvents(sink);
        });
        return flux;
    }

    // handle is very close to generate , in teh sense that it uses a SynchronousSink and allows only one-by-one emissions
    // combination of map and filter operatio
    public Flux<String> explore_handle() {

        var namesList = List.of("alex", "ben", "chloe");
        return Flux.fromIterable(namesList)
                .handle((name, sink) -> {
                    if (name.length() > 3)
                        sink.next(name);
                });

    }

    public Flux<String> namesFlux_delay(int stringLength) {
        var namesList = List.of("alex", "ben", "chloe");

        return Flux.fromIterable(namesList)
                .delayElements(Duration.ofSeconds(1))
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + "-" + s);
    }


    public Flux<Integer> range(int max) {

        return Flux.range(0, max);
    }



    /*
     *    MONO
     *
     * */
    //METHOD - to demo Mono
    // Datasource 2 - Publisher
    //returns a mono (reactive type that represents 1 element) of String
    public Mono<String> namesMono(){
        // 'just' emits a mono with the specified item
        return Mono.just("achudhan")
                .log(); // log each event between teh publisher and subscriber
    }

    // METHOD : to demo map operator on mono reactive type
    public Mono<String> namesMono_map() {
        // convert Mono stream from lower to upper
        return Mono.just("achudhan")
                // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
                .map(String::toUpperCase)
                .log();
    }


    // METHOD : to demo immutability on Mono reactive type
    // Note : Here since we are trying to assign the Reactive stream returned from the Mono to a variable and then apply the mapper, it will not transform the strings to upper cases due to immutability.
    public Mono<String> namesMono_immutability() {
        // try to convert Mono stream from lower to upper
        var namesMono = Mono.just("achudhan");
        // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
        // **** method reference to String class's toUpperCase function
        namesMono.map(String::toUpperCase)
                .log();
        return namesMono;
    }


    // METHOD : to demo filter operator on Mono reactive type
    public Mono<String> namesMono_mapAndFilter() {
        // convert each flux element from lower to upper

        return Mono.just("achudhan")
                // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
                // **** method reference to String class's toUpperCase function
                .map(String::toUpperCase)
                // *** filter the string whose length is greater than 3 ( i.e. allow only strings of length greater than 3)
                // .filter(s -> s.length() > 3)
                .filter(s-> s.length() > stringLength)  //filter ;
                .map(s -> s.length() + "-" + s) //prefix string with length
                .log();
    }

    // METHOD : to demo flatMap operator on Mono reactive type
    public Mono<List<String>> namesMono_flatMap() {
        // convert each flux element from lower to upper

        return Mono.just("achudhan")
                // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
                // **** method reference to String class's toUpperCase function
                .map(String::toUpperCase)
                // *** filter the string whose length is greater than 3 ( i.e. allow only strings of length greater than 3)
                .filter(s-> s.length() > stringLength)  //filter ;
                .map(s -> s.length() + "-" + s) //prefix string with length
                .flatMap(this::splitStringMono) // flatMap() accepts as a parameter a functional interface. Method reference to function within this class which will return  Mono list of 8,-,A,C.H,U,D,H,A,N
                .log();
    }

    // METHOD : to demo flatMapMany operator on Mono reactive type which will return a Flux
    public Flux<String> namesMono_flatMapMany() {
        // convert each flux element from lower to upper

        return Mono.just("achudhan")
                // .map method accepts as a parameter the functional interface 'Function.java' which has an abstract apply method.
                // **** method reference to String class's toUpperCase function
                .map(String::toUpperCase)
                // *** filter the string whose length is greater than 3 ( i.e. allow only strings of length greater than 3)
                .filter(s-> s.length() > stringLength)  //filter ;
                .map(s -> s.length() + "-" + s) //prefix string with length
                .flatMapMany(this::splitString) // flatMap() accepts as a parameter a functional interface. Method reference to function within this class which will return  Flux ist of 8,-,A,C.H,U,D,H,A,N
                .log();
    }


    // Create a Publisher Mono for defaultIfEmpty()
    public Mono<String> namesMono_map_filter(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .defaultIfEmpty("default");

    }

    // Create a Publisher Mono for switchIfEmpty()
    public Mono<String> namesMono_map_filter_switchIfEmpty(int stringLength) {
        Mono<String> defaultMono = Mono.just("default");
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .switchIfEmpty(defaultMono);

    }


    // Create a Publisher Mono to explore concatWith()
    public Flux<String> explore_concatWithMono(){
        var aMono = Mono.just("A");  // create a mono for A
        var bMono = Mono.just("B");  // create a mono for B

        return aMono.concatWith(bMono)  // concatWith is an Instance emthod. Returns a Flux.
                .log();
    }

    // Create a Publisher Mono to explore mergeWith()
    public Flux<String> explore_mergeWithMono(){
        var aMono = Mono.just("A");         // create a mono for A
        var bMono = Mono.just("B") ;

        return aMono.mergeWith(bMono)  // concatWith is an Instance method. Returns a Flux.
                .log();
    }


    // Create a Publisher Mono to explore zip()
    public Flux<String> explore_zipMono(){
        var aMono = Mono.just("A");  // create a mono for A

        var bMono = Mono.just("B");  // create a mono for B


        return Flux.zip(aMono,bMono,(first,second) -> first + second )     // zip is a Static Method, Result is going to be AB
                .log();
    }



    // Create a Publisher Mono to explore zip() for a tuple
    // handles a max. of 8 elements
    public Flux<String> explore_zip_tuple_mono(){
        var aMono = Mono.just("A");  // create a mono for A

        var bMono = Mono.just("B");  // create a mono for B

        var cMono = Mono.just("C");  // create a mono for C


        return Flux.zip(aMono,bMono,cMono)     // zip is a Static Method
                .map(Tuple3 -> Tuple3.getT1() + Tuple3.getT2() + Tuple3.getT3())  // output willl be ABC
                .log();
    }


    // Create a Publisher Mono to explore zipWith()
    public Mono<String> explore_zipWithMono(){
        var aMono = Mono.just("A");  // create a mono for A

        var bMono =Mono.just("B");  // create a mono for B

        return aMono.zipWith(bMono)     // zipWith is an Instance Method. Result is going to be AB
                .map(Tuple2 -> Tuple2.getT1() + Tuple2.getT2())
                .log();

    }


    /**
    **************  ERROR HANDLING : Section for Exception Handling
    **/

    public Flux<String> exception_flux() {

        var flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))  // Note : D will not be published or subscribed as an error has occurred which will cancel the subscription between the publicher and subscriber
                .log();
        return flux;

    }


    /**
     * This provides a single fallback value
     *
     * @return
     */
    public Flux<String> explore_OnErrorReturn() {

        var flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new IllegalStateException("Exception Occurred")))
                .onErrorReturn("D") // default value
                .log();

            return flux;

    }

    /**
     * This provides a fallback value as a Reactive Stream
     *
     * @param e
     * @return
     */
    public Flux<String> explore_OnErrorResume(Exception e) {

        var recoveryFlux = Flux.just("D", "E", "F");

        var flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(e))
                .onErrorResume((exception) -> {
                    log.error("Exception is ", exception);
                    if (exception instanceof IllegalStateException)
                        return recoveryFlux;
                    else
                        return Flux.error(exception);
                });

        return flux;

    }

    /**
     * This helps to drop elements thats causing the issue and move on with the other elements
     *
     * @return
     */
    public Flux<String> explore_OnErrorContinue() {

        var flux = Flux.just("A", "B", "C")
                .map(name -> {
                    if (name.equals("B")) {
                        throw new IllegalStateException("Exception Occurred");
                    }
                    return name;
                })
                .concatWith(Flux.just("D"))
                .onErrorContinue((exception, value) -> {
                    System.out.println("Value is : " + value);
                    System.out.println("Exception is : " + exception.getMessage());
                });


        return flux;

    }


    /**
     * Used to transform the error from one type to another
     *
     * @param e
     * @return
     */
    public Flux<String> explore_OnErrorMap(Exception e) {

        var flux =
                Flux.just("A", "B", "C")
                .map(name -> {
                    if (name.equals("B")) {
                        throw new IllegalStateException("Exception Occurred");
                    }
                    return name;
                })
                .concatWith(Flux.just("D"))
                .onErrorMap((exception) -> {
                    log.error("Exception is : " , exception);
                    // difference between errorResume and this one is that you dont need to add
                    // Flux.error() to throw the exception
                    return new ReactorException(exception, exception.getMessage());
                });

        return flux;

    }

    /**
     * Used to transform the error from one type to another
     *
     * @param e
     * @return
     */
    public Flux<String> explore_OnErrorMap1(Exception e) {

        var flux =
//                Flux.just("A", "B", "C")
//                .map(name -> {
//                    if (name.equals("B")) {
//                        throw new IllegalStateException("Exception Occurred");
//                    }
//                    return name;
//                })
//                .concatWith(Flux.just("D"))
                Flux.just("A")
                        .concatWith(Flux.error(e))
                        .onErrorMap((exception) -> {
                            log.error("Exception is : " , exception);
                            // difference between errorResume and this one is that you dont need to add
                            // Flux.error() to throw the exception
                            return new ReactorException(exception, exception.getMessage());
                        });

        return flux;

    }

    /**
     * Used to tranform the error from one type to another
     *
     * @param e
     * @return
     */
    public Flux<String> explore_OnErrorMap_checkpoint(Exception e) {

        var flux =
                /*
                Flux.just("A", "B", "C")
                .map(name -> {
                    if (name.equals("B")) {
                        throw new IllegalStateException("Exception Occurred");
                    }
                    return name;
                })
                */
                Flux.just("A")
                        .concatWith(Flux.error(e))
                        .checkpoint("errorSpot")    // introduce checkpoint which will give proximity of error while debugging
                        .onErrorMap((exception) -> {
                            log.error("Exception is : ", exception);
                            // difference between errorResume and this one is that you dont need to add
                            // Flux.error() to throw the exception
                            return new ReactorException(exception, exception.getMessage());
                        });

        return flux;

    }



    public Flux<String> explore_doOnError(Exception e) {

        var flux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(e))
                .doOnError((exception) -> {
                    System.out.println("Exception is : " + e);
                    //Write any logic you would like to perform when an exception happens
                });

        return flux;

    }


    public Mono<Object> exception_mono_exception() {

        var mono = Mono.just("B")
                .map(value -> {
                    throw new RuntimeException("Exception Occurred");
                });
        return mono;

    }


    /**
     * This operator can be used to provide a default value when an error occurs
     *
     * @return
     */
    public Mono<Object> exception_mono_onErrorReturn() {

        return Mono.just("B")
                .map(value -> {
                    throw new RuntimeException("Exception Occurred");
                }).onErrorReturn("abc");
    }

    /***
     *  This operator can be used to resume from an exception.
     *  The recovery value here will be a Mono instead of the direct value
     * @return
     */
    public Mono<Object> exception_mono_onErrorResume(Exception e) {

        var mono = Mono.error(e);

        return mono.onErrorResume((ex) -> {
            System.out.println("Exception is " + ex);
            if (ex instanceof IllegalStateException)
                return Mono.just("abc");
            else
                return Mono.error(ex);
        });
    }

    /**
     * This operator can be used to map the exception to another user defined or custom exception
     *
     * @param e
     * @return
     */
    public Mono<Object> exception_mono_onErrorMap(Exception e) {

        return Mono.just("B")
                .map(value -> {
                    throw new RuntimeException("Exception Occurred");
                }).onErrorMap(ex -> {
                    System.out.println("Exception is " + ex);
                    return new ReactorException(ex, ex.getMessage());
                });
    }

    /**
     * This operator allows the reactive stream to continue emitting elements when an error occured in the flow
     *
     * @return
     */
    public Mono<String> exception_mono_onErrorContinue(String input) {

        return Mono.just(input).
                map(data -> {
                    if (data.equals("abc"))
                        throw new RuntimeException("Exception Occurred");
                    else
                        return data;
                }).
                onErrorContinue((ex, val) -> {
                    log.error("Exception is " + ex);
                    log.error("Value that caused the exception is " + val);

                });
    }

    public Mono<String> explore_create_mono() {

        Mono<String> mono = Mono.create(sink -> {
            CompletableFuture.supplyAsync(() -> name())  // name is a function in thsi class
                    .thenAccept(name -> sink.success(name));
        });
        return mono;
    }

    public Mono<String> explore_mono_create() {

        Mono<String> abc = Mono.create(sink -> {
            delay(1000);
            sink.success("abc");
        });

        return abc;
    }


    // MAIN METHOD
    public static void main(String[] args) {

        //Create an instance of the present class
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        // Subscriber
        // Access the Flux
        //In order to access the flux created in the method above we have to subscribe to it
        //Consume the values returned by the flux and print it out
        //The subscribe method uses as a parameter a functional interface named Consumer .
        //The function interface Consumer has one method 'accept' the implementation of which we will define using a lambda
        // The parameter of the accept method is generic and we will use the console print as the method implementation
        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> {
                    System.out.println("Flux names are:" + name);
                });

        // Access the Mono
        //In order to access the mono created in the method above we have to subscribe to it
        //Consume the value returned by the  mono and print it out
        //The subscribe method uses as a parameter a functional interface named Consumer .
        //The function interface Consumer has one method 'accept' the implementation of which we will define using a lambda
        // The parameter of the accept method is generic and we will use the console print as the method implementation
        fluxAndMonoGeneratorService.namesMono()
                .subscribe(name -> {
                    System.out.println(("Mono Name is :" + name));
                });

    }





}
