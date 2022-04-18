package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.exception.ReactorException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;
import reactor.tools.agent.ReactorDebugAgent;


import java.time.Duration;
import java.util.List;

public class FluxAndMonoGeneratorServiceTest {

    // Create instance of original class which we are going to test FluxAndMonoGeneratorService
    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    // To generate test method : right click -> Generate -> Test Method -> 'name' test method will be generated which you can rename
    // the dependency("io.projectreactor:reactor-test:3.4.0") needed for testing reactive code is available in build.gradle
    @Test
    void namesFlux() {

        //*** GIVEN:

        //*** WHEN:
        // call original method in 'fFluxAndMonoGeneratorService.java' and assign to a publisher
        var  namesFlux= fluxAndMonoGeneratorService.namesFlux();

        //** THEN:
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux is going to be the output of namesFlux() declared in FluxAndMonoGeneratorService.java
        // the .create function can be considered analogous to the subscribe function allowing event flow
        StepVerifier.create(namesFlux)
                //   .expectNext("alex","ben","chloe")     // assert returned values
                //   .expectNextCount(3)                   // assert no. of values returned
                .expectNext("alex")                     // asserting with first event
                .expectNextCount(2)                     // assert remaining no. of events
                .verifyComplete();                      // assert request is completed

    }

    // Test Mono
    @Test
    void namesMono() {

        //*** GIVEN:

        //*** WHEN:
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var namesMono = fluxAndMonoGeneratorService.namesMono();


        //** THEN:
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of mono is going to be the output of namesMono() declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(namesMono)
                .expectNext("achudhan")     // assert returned values
                .verifyComplete();          // assert request is completed

    }

    // Test namesFlux_map
    @Test
    void namesFlux_map() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var nameFlux_map = fluxAndMonoGeneratorService.namesFlux_map();

        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of mono is going to be the output of namesFlux_map() declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(nameFlux_map)
                .expectNext("ALEX","BEN","CHLOE") // assert mapper values
                .verifyComplete();                // assert request is completed

    }

    // Test namesFlux_map
    @Test
    void namesFlux_map_OnNext() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var nameFlux_map_OnNext = fluxAndMonoGeneratorService.namesFlux_map_OnNext();

        //*** THEN
        StepVerifier.create(nameFlux_map_OnNext)
                .expectNext("ALEX","BEN","CHLOE") // assert mapper values
                .verifyComplete();                // assert request is completed

    }

    @Test
    void namesFlux_map_OnNext_OnSubscribe() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var nameFlux_map_OnNext_onSub = fluxAndMonoGeneratorService.namesFlux_map_OnNext_OnSubscribe();

        //*** THEN
        StepVerifier.create(nameFlux_map_OnNext_onSub)
                .expectNext("ALEX","BEN","CHLOE") // assert mapper values
                .verifyComplete();                // assert request is completed

    }

    @Test
    void namesFlux_map_OnNext_OnSubscribe_onComplete() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var nameFlux_map_OnNext_onSub_onComplete = fluxAndMonoGeneratorService.namesFlux_map_OnNext_OnSubscribe_onComplete();

        //*** THEN
        StepVerifier.create(nameFlux_map_OnNext_onSub_onComplete)
                .expectNext("ALEX","BEN","CHLOE") // assert mapper values
                .verifyComplete();                // assert request is completed

    }


    @Test
    void namesFlux_map_OnNext_OnSubscribe_onComplete_Finally() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var nameFlux_map_OnNext_onSub_onComplete_Finally = fluxAndMonoGeneratorService.namesFlux_map_OnNext_OnSubscribe_onComplete_doFinal();

        //*** THEN
        StepVerifier.create(nameFlux_map_OnNext_onSub_onComplete_Finally)
                .expectNext("ALEX","BEN","CHLOE") // assert mapper values
                .verifyComplete();                // assert request is completed

    }

    @Test
    void namesMono_map() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var nameMono_map = fluxAndMonoGeneratorService.namesMono_map();

        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of mono is going to be the output of namesFlux_map() declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(nameMono_map)
                .expectNext("ACHUDHAN") // assert mapper values
                .verifyComplete();                // assert request is completed

    }


    @Test
    void namesFlux_immutability() {
        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var nameFlux_immutability = fluxAndMonoGeneratorService.namesFlux_immutability();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of namesFlux_immutability() declared in FluxAndMonoGeneratorService.java
        // However since Reactive Stream are immutable and we are assigning the flux to a variable before trying to apply mapper to transform to upper case, this will still return lower cases and test will fail if we try to assert for caps.
        StepVerifier.create(nameFlux_immutability)
                // .expectNext("ALEX","BEN","CHLOE") // assert mapper values : will fail due to immutability of Reactive Streams
                .expectNext("alex","ben","chloe") // assert mapper values
                .verifyComplete();                // assert request is completed
    }

    @Test
    void namesMono_immutability() {
        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var nameMono_immutability = fluxAndMonoGeneratorService.namesMono_immutability();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of Mono  is going to be the output of namesMono_immutability() declared in FluxAndMonoGeneratorService.java
        // However since Reactive Stream are immutable and we are assigning the Mono to a variable before trying to apply mapper to transform to upper case, this will still return lower case and test will fail if we try to assert for caps.
        StepVerifier.create(nameMono_immutability)
                //        .expectNext("ACHUDHAN") // assert mapper values : will fail due to immutability of Reactive Streams
                .expectNext("achudhan")
                .verifyComplete();                // assert request is completed
    }

    @Test
    void namesFlux_mapAndFilter() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var nameFlux_mapAndFilter = fluxAndMonoGeneratorService.namesFlux_mapAndFilter();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of namesFlux_mapAndFilter() declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(nameFlux_mapAndFilter)
                .expectNext("4-ALEX","5-CHLOE") // assert mapper values : only strings greater than 3 in length will be allowed through the filter
                .verifyComplete();                // assert request is completed
    }

    @Test
    void namesMono_mapAndFilter() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var namesMono_mapAndFilter = fluxAndMonoGeneratorService.namesMono_mapAndFilter();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of namesMono_mapAndFilter declared in FluxAndMonoGeneratorService.java
        // However since Reactive Stream are immutable and we are assigning the flux to a variable before trying to apply mapper to transform to upper case, this will still return lower cases and test will fail.
        StepVerifier.create(namesMono_mapAndFilter)
                .expectNext("8-ACHUDHAN") // assert mapper values : only strings greater than 3 in length will be allowed through the filter
                .verifyComplete();

    }



    @Test
    void flux_flatMap() {
        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var flux_flatMap = fluxAndMonoGeneratorService.flux_flatMap();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of flux_flatMap() declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(flux_flatMap)
                .expectNext("A","L","E","X","C","H","L","O","E") // assert mapper values : only strings greater than 3 in length will be allowed through the filter and will be output as individual chars
                .verifyComplete();                // assert request is completed

    }


    @Test
    void flux_flatMap_async() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var flux_flatMap_async = fluxAndMonoGeneratorService.flux_flatMap_async();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of flux_flatMap_async() declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(flux_flatMap_async)
                // .expectNext() will fail as we have introduced a delay and flux reactor processes asynchronously which means we will not be able to prodict teh order of .onNext() reponses.
                //.expectNext("A","L","E","X","C","H","L","O","E") // assert mapper values : only strings greater than 3 in length will be allowed through the filter and will be output as individual chars
                .expectNextCount(9)
                .verifyComplete();                // assert request is completed
    }

    @Test
    void flux_concatMap() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var flux_concatMap = fluxAndMonoGeneratorService.flux_concatMap();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of flux_concatMap() declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(flux_concatMap)
                // .expectNext() will not fail as we have are using a concatMap() which preserves ordering
                .expectNext("A","L","E","X","C","H","L","O","E") // assert mapper values : only strings greater than 3 in length will be allowed through the filter and will be output as individual chars
                .expectNextCount(0)  // NextCount() will be 0 as there is nothing left to process
                .verifyComplete();                // assert request is completed
    }

    @Test
    void flux_concatMap_virtualTimer() {

        //*** GIVEN
        VirtualTimeScheduler.getOrSet();  // will set virtual clock for this particular test case. Can be used ot reduce test time

        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var flux_concatMap = fluxAndMonoGeneratorService.flux_concatMap();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of flux_concatMap() declared in FluxAndMonoGeneratorService.java
        StepVerifier.withVirtualTime(() -> flux_concatMap)
                        .thenAwait(Duration.ofSeconds(10    ))
                       // .expectNext() will not fail as we have are using a concatMap() which preserves ordering
                       .expectNext("A","L","E","X","C","H","L","O","E") // assert mapper values : only strings greater than 3 in length will be allowed through the filter and will be output as individual chars
                       .expectNextCount(0)  // NextCount() will be 0 as there is nothing left to process
                       .verifyComplete();                // assert request is completed
    }

    @Test
    void namesMono_flatMap() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var namesMono_flatMap = fluxAndMonoGeneratorService.namesMono_flatMap();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of namesMono_flatMap declared in FluxAndMonoGeneratorService.java
        //
        StepVerifier.create(namesMono_flatMap)
                .expectNext(List.of("8","-","A","C","H","U","D","H","A","N")) // assert mapper values : only strings greater than 3 in length will be allowed through the filter
                .verifyComplete();

    }

    @Test
    void namesMono_flatMapMany() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var namesMono_flatMapMany = fluxAndMonoGeneratorService.namesMono_flatMapMany();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of namesMono_flatMapMany declared in FluxAndMonoGeneratorService.java
        //
        StepVerifier.create(namesMono_flatMapMany)
                .expectNext("8","-","A","C","H","U","D","H","A","N") // assert mapper values : only strings greater than 3 in length will be allowed through the filter
                .verifyComplete();

    }

    @Test
    void flux_transform() {
        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var flux_transform = fluxAndMonoGeneratorService.flux_transform();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of flux_transform declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(flux_transform)
                .expectNext("A","L","E","X","C","H","L","O","E") // assert mapper values : only strings greater than 3 in length will be allowed through the filter and will be output as individual chars
                .verifyComplete();                // assert request is completed
    }


    @Test
    void flux_transform_switchDefault() {
        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var flux_transform_switchDefault = fluxAndMonoGeneratorService.flux_transform_switchDefault();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of flux_transform_switchDefault declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(flux_transform_switchDefault)
                .expectNext("default") // assert mapper values
                .verifyComplete();                // assert request is completed
    }

    @Test
    void flux_transform_switchifEmpty() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var flux_transform_switchifEmpty = fluxAndMonoGeneratorService.flux_transform_switchifEmpty();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of flux_transform_switchifEmpty declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(flux_transform_switchifEmpty)
                .expectNext("D","E","F","A","U","L","T") // assert mapper values
                .verifyComplete();                // assert request is completed

    }

    @Test
    void explore_concat() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_concat = fluxAndMonoGeneratorService.explore_concat();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_concat declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_concat)
                .expectNext("A","B","C","D","E","F") // assert mapper values
                .verifyComplete();                // assert request is completed
    }

    @Test
    void explore_concatWith() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_concatWith = fluxAndMonoGeneratorService.explore_concatWith();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_concatWith declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_concatWith)
                .expectNext("A","B","C","D","E","F") // assert mapper values
                .verifyComplete();                // assert request is completed
    }

    @Test
    void explore_concatWithMono() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_concatWithMono = fluxAndMonoGeneratorService.explore_concatWithMono();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_concatWithMono declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_concatWithMono)
                .expectNext("A","B")              // assert mapper values
                .verifyComplete();                // assert request is completed
    }

    @Test
    void explore_merge() {

        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_merge = fluxAndMonoGeneratorService.explore_merge();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_merge declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_merge)
                .expectNext("A","D","B","E","C","F") // assert mapper values
                .verifyComplete();                // assert request is completed
    }

    @Test
    void explore_mergetWith() {


        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_mergetWith = fluxAndMonoGeneratorService.explore_mergetWith();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_mergetWith declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_mergetWith)
                .expectNext("A","D","B","E","C","F") // assert mapper values
                .verifyComplete();                // assert request is completed

    }

    @Test
    void explore_mergeWithMono() {


        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_mergeWithMono = fluxAndMonoGeneratorService.explore_mergeWithMono();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_mergeWithMono declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_mergeWithMono)
                .expectNext("A","B")              // assert mapper values
                .verifyComplete();                // assert request is completed
    }

    @Test
    void explore_mergeSequential() {


        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_mergeSequential = fluxAndMonoGeneratorService.explore_mergeSequential();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_mergeSequential declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_mergeSequential)
                .expectNext("A","B","C","D","E","F") // assert mapper values
                .verifyComplete();                // assert request is completed

    }

    @Test
    void explore_zip() {


        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_zip = fluxAndMonoGeneratorService.explore_zip();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_zip declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_zip)
                .expectNext("AD","BE","CF") // assert mapper values
                .verifyComplete();                // assert request is completed
    }

    @Test
    void explore_zipMono() {


        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_zipMono = fluxAndMonoGeneratorService.explore_zipMono();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_zipMono declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_zipMono)
                .expectNext("AB") // assert mapper values
                .verifyComplete();                // assert request is completed
    }

    @Test
    void explore_zip_tuple() {


        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_zip_tuple = fluxAndMonoGeneratorService.explore_zip_tuple();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_zip_tuple declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_zip_tuple)
                .expectNext("AD14","BE25","CF36") // assert mapper values
                .verifyComplete();                // assert request is completed
    }


    @Test
    void explore_zip_tuple_mono() {


        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_zip_tuple_mono = fluxAndMonoGeneratorService.explore_zip_tuple_mono();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_zip_tuple_mono declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_zip_tuple_mono)
                .expectNext("ABC") // assert mapper values
                .verifyComplete();                // assert request is completed
    }

    @Test
    void explore_zipWith() {
        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_zipWith = fluxAndMonoGeneratorService.explore_zipWith();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_zipWith declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_zipWith)
                .expectNext("AD","BE","CF") // assert mapper values
                .verifyComplete();                // assert request is completed

    }

    @Test
    void explore_zipWithMono() {


        //*** GIVEN


        //*** WHEN
        //call original method in 'FluxAndMonoGeneratorService.java' and assign to a publisher
        var explore_zipWithMono = fluxAndMonoGeneratorService.explore_zipWithMono();


        //*** THEN
        //StepVerifier belongs to the library "io.projectreactor:reactor-test:3.4.0" which we added as a dependency in build.gradle
        // Output of flux  is going to be the output of explore_zipWithMono declared in FluxAndMonoGeneratorService.java
        StepVerifier.create(explore_zipWithMono)
                .expectNext("AB") // assert mapper values
                .verifyComplete();                // assert request is completed

    }

    @Test
    void namesMono_map_empty() {

        //given
        int stringLength = 4;

        //when
        var stringMono = fluxAndMonoGeneratorService.namesMono_map_filter(stringLength);

        //then
        StepVerifier.create(stringMono)
                .expectNext("default")
                .verifyComplete();

    }

    @Test
    void namesMono_map_filter_switchIfEmpty() {

        //given
        int stringLength = 4;

        //when
        var stringMono = fluxAndMonoGeneratorService.namesMono_map_filter_switchIfEmpty(stringLength);

        //then
        StepVerifier.create(stringMono)
                .expectNext("default")
                .verifyComplete();

    }

    @Test
    void exception_flux() {

        //given

        //when
        var flux = fluxAndMonoGeneratorService.exception_flux();

        //then
        StepVerifier.create(flux)
                .expectNext("A", "B", "C")
                .expectError(RuntimeException.class)
                .verify(); // you cannot do a verify complete in here

    }

    @Test
    void exception_flux_1() {

        //given

        //when
        var flux = fluxAndMonoGeneratorService.exception_flux();

        //then
        StepVerifier.create(flux)
                .expectNext("A", "B", "C")
                .expectError()
                .verify(); // you cannot do a verify complete in here

    }

    @Test
    void exception_flux_2() {

        //given

        //when
        var flux = fluxAndMonoGeneratorService.exception_flux();

        //then
        StepVerifier.create(flux)
                .expectNext("A", "B", "C")
                .expectErrorMessage("Exception Occurred")
                .verify(); // you cannot do a verify complete in here

    }


    @Test
    void explore_OnErrorReturn() {

        //given

        //when
        var flux = fluxAndMonoGeneratorService.explore_OnErrorReturn().log();

        //then
        StepVerifier.create(flux)
                .expectNext("A", "B", "C", "D")
                .verifyComplete();

    }


    @Test
    void explore_OnErrorResume() {

        //given
        var e = new IllegalStateException("Not a valid state");

        //when
        var flux = fluxAndMonoGeneratorService.explore_OnErrorResume(e).log();

        //then
        StepVerifier.create(flux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();

    }

    @Test
    void explore_OnErrorResume_1() {

        //given
        var e = new RuntimeException("Not a valid state");

        //when
        var flux = fluxAndMonoGeneratorService.explore_OnErrorResume(e).log();

        //then
        StepVerifier.create(flux)
                .expectNext("A", "B", "C")
                .expectError(RuntimeException.class)
                .verify();

    }

    @Test
    void explore_OnErrorMap() {

        //given
        var e = new RuntimeException("Not a valid state");

        //when
        var flux = fluxAndMonoGeneratorService.explore_OnErrorMap(e)
                .log();

        //then
        StepVerifier.create(flux)
                .expectNext("A")
                .expectError(ReactorException.class)
                .verify();
    }

    @Test
    void explore_OnErrorMap1() {

        //given
        var e = new RuntimeException("Not a valid state");

        //when
        var flux = fluxAndMonoGeneratorService.explore_OnErrorMap1(e)
                .log();

        //then
        StepVerifier.create(flux)
                .expectNext("A")
                .expectError(ReactorException.class)
                .verify();
    }

    @Test
    void explore_OnErrorMap_checkpoint() {

        /*Error has been observed at the following site(s):
	        |_ checkpoint ⇢ errorspot*/
        //given
        // In production, it could the code or the data thats executing in the run time that might throw the exception
        var e = new RuntimeException("Not a valid state");

        //when
        var flux = fluxAndMonoGeneratorService.explore_OnErrorMap_checkpoint(e).log();

        //then
        StepVerifier.create(flux)
                .expectNext("A")
                .expectError(ReactorException.class)
                .verify();
    }


    @Test
    /**
     * This gives the visibility of which operator caused the problem
     * This operator gives the "Assembly trace" which is not available when you use chekpoint
     * This also gives you the line that caused the problem
     */
    void explore_OnErrorMap_onOperatorDebug() {

        //You will see the below in the code
/*
        Error has been observed at the following site(s):
	|_      Flux.error ⇢ at com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_OnErrorMap_checkpoint(FluxAndMonoGeneratorService.java:336)
	|_ Flux.concatWith ⇢ at com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_OnErrorMap_checkpoint(FluxAndMonoGeneratorService.java:336)
	|_      checkpoint ⇢ errorspot
*/

        //given
        Hooks.onOperatorDebug();  // enable detailed stack trace with line number
        var e = new RuntimeException("Not a valid state");

        //when
        //var flux = fluxAndMonoGeneratorService.explore_OnErrorMap_checkpoint(e).log();
        var flux = fluxAndMonoGeneratorService.explore_OnErrorMap1(e).log();

        //then
        StepVerifier.create(flux)
                .expectNext("A")
                .expectError(ReactorException.class)
                .verify();
    }


    @Test
    /**
     * This gives the visibility of which operator caused the problem
     * This operator gives the "Assembly trace" which is not available when you use chekpoint
     * This also gives you the line that caused the problem
     */
    void explore_OnErrorMap_onOperatorDebug1() {

        //You will see the below in the code
/*
        Error has been observed at the following site(s):
	|_      Flux.error ⇢ at com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_OnErrorMap_checkpoint(FluxAndMonoGeneratorService.java:336)
	|_ Flux.concatWith ⇢ at com.learnreactiveprogramming.service.FluxAndMonoGeneratorService.explore_OnErrorMap_checkpoint(FluxAndMonoGeneratorService.java:336)
	|_      checkpoint ⇢ errorspot
*/

        //given
        Hooks.onOperatorDebug();  // enable detailed stack trace with line number
        var e = new RuntimeException("Not a valid state");

        //when
        var flux = fluxAndMonoGeneratorService.explore_OnErrorMap_checkpoint(e).log();


        //then
        StepVerifier.create(flux)
                .expectNext("A")
                .expectError(ReactorException.class)
                .verify();
    }


    @Test
    /**
     * This gives the visibility of which operator caused the problem without any performance overhead
     * Recommended Option for debugging exceptions in Project Reactor
     * Java agent runs alongside your app
     * It collects the stack trace information of each operator without any performance overhead
     * In a Spring Boot app add the s   following in main():
     *    - ReactorDebugAgent.init()
     */
    void explore_OnErrorMap_reactorDebugAgent() {

        //given

        //Reactor Debug Agents
        ReactorDebugAgent.init();
        ReactorDebugAgent.processExistingClasses();

        var e = new RuntimeException("Not a valid state");

        //when
        var flux = fluxAndMonoGeneratorService.explore_OnErrorMap_checkpoint(e).log();

        //then
        StepVerifier.create(flux)
                .expectNext("A")
                .expectError(ReactorException.class)
                .verify();
    }

    @Test
    void doOnError() {

        //given
        var e = new RuntimeException("Not a valid state");

        //when
        var flux = fluxAndMonoGeneratorService.explore_doOnError(e);

        //then
        StepVerifier.create(flux)
                .expectNext("A", "B", "C")
                .expectError(RuntimeException.class)
                .verify();


    }

    @Test
    void explore_OnErrorContinue() {

        //given

        //when
        var flux = fluxAndMonoGeneratorService.explore_OnErrorContinue().log();

        //then
        StepVerifier.create(flux)
                .expectNext("A", "C", "D")
                .verifyComplete();

    }


    @Test
    void exception_mono() {

        //given

        //when
        var mono = fluxAndMonoGeneratorService.exception_mono_exception();

        //then
        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();

    }

    @Test
    void exception_mono_1() {

        //given

        //when
        var mono = fluxAndMonoGeneratorService.exception_mono_exception();

        //then
        StepVerifier.create(mono)
                .expectErrorMessage("Exception Occurred")
                .verify();

    }

    @Test
    void exception_mono_onErrorResume() {

        //given
        var e = new IllegalStateException("Not a valid state");


        //when
        var mono = fluxAndMonoGeneratorService.exception_mono_onErrorResume(e);

        //then
        StepVerifier.create(mono)
                .expectNext("abc")
                .verifyComplete();
    }

    @Test
    void exception_mono_onErrorReturn() {

        //given


        //when
        var mono = fluxAndMonoGeneratorService.exception_mono_onErrorReturn();

        //then
        StepVerifier.create(mono)
                .expectNext("abc")
                .verifyComplete();
    }

    @Test
    void exception_mono_onErrorMap() {

        //given
        var e = new IllegalStateException("Not a valid state");


        //when
        var mono = fluxAndMonoGeneratorService.exception_mono_onErrorMap(e);

        //then
        StepVerifier.create(mono)
                .expectError(ReactorException.class)
                .verify();
    }

    @Test
    void exception_mono_onErrorContinue() {

        //given
        var input = "abc";

        //when
        var mono = fluxAndMonoGeneratorService.exception_mono_onErrorContinue(input);

        //then
        StepVerifier.create(mono)
                .verifyComplete();
    }

    @Test
    void exception_mono_onErrorContinue_1() {

        //given
        var input = "reactor";

        //when
        var mono = fluxAndMonoGeneratorService.exception_mono_onErrorContinue(input);

        //then
        StepVerifier.create(mono)
                .expectNext(input)
                .verifyComplete();
    }


    @Test
    void explore_generate() {

        //given
        //Demonstrate multiple emissions per round is not supported

        //when
        var flux = fluxAndMonoGeneratorService.explore_generate().log();

        //then
        StepVerifier.create(flux)
                .expectNext(2, 4)
                .expectNextCount(8)
                .verifyComplete();

    }


    @Test
    void explore_create() {

        //given

        //when
        var flux = fluxAndMonoGeneratorService.explore_create().log();

        //then
        StepVerifier.create(flux)
                //.expectNext("alex", "ben", "chloe")
                .expectNextCount(9)
                .verifyComplete();

    }

    @Test
    void explore_create_mono() {

        //given

        //when
        var mono = fluxAndMonoGeneratorService.explore_create_mono().log();

        //then
        StepVerifier.create(mono)
                //.expectNext("alex", "ben", "chloe")
                .expectNext("alex")
                .verifyComplete();

    }

    @Test
    void explore_push() {

        //given

        //when
        var flux = fluxAndMonoGeneratorService.explore_push().log();

        //then
        StepVerifier.create(flux)
                //.expectNext("alex", "ben", "chloe")
                .expectNextCount(3)
                // .thenConsumeWhile(Objects::nonNull)
                .verifyComplete();

    }

    @Test
    void explore_handle() {

        //given

        //when
        var flux = fluxAndMonoGeneratorService.explore_handle().log();

        //then
        StepVerifier.create(flux)
                //.expectNext("alex", "ben", "chloe")
                .expectNextCount(2)
                .verifyComplete();

    }


    @Test
    void explore_mono_create() {

        //given

        //when
        var mono = fluxAndMonoGeneratorService.explore_mono_create();

        //then
        StepVerifier.create(mono)
                .expectNext("abc")
                .verifyComplete();

    }

    @Test
    void namesFlux_flatmap_sequential() {

        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_sequential(stringLength).log();

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("A", "L", "E", "X")
                .expectNextCount(9)
                .verifyComplete();

    }


    @Test
    void namesFlux_delay() {

        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_delay(stringLength).log();

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("ALEX", "BEN", "CHLOE")
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();
    }

    @Test
    void range() {

        //given

        //when
        var rangeFlux = fluxAndMonoGeneratorService.range(5).log();

        //then
        StepVerifier.create(rangeFlux)
                //.expectNext(0,1,2,3,4)
                .expectNextCount(5)
                .verifyComplete();
    }

}
