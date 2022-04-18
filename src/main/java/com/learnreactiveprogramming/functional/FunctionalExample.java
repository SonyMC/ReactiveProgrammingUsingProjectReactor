package com.learnreactiveprogramming.functional;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
E.g. of Functional style of coding
Here we will filet a list of string based on input size
 */


public class FunctionalExample {

    public static void main(String[] args) {

        var namesList = List.of("alex","ben","chloe","adam","chloe");  //Note : "chloe" is duplicated

        var filteredNamesList = namesGreaterThanSize(namesList, 3);

        System.out.println("Filtered names List :" + filteredNamesList); // Print to console
    }

    private static List<String> namesGreaterThanSize(List<String> namesList, int size) {
        // Note: The below chained functions is called  apipeline
        return namesList
                 //.stream()   //Note:  stream() was released as part of Java 8 and is used to work on collection types . Wil lgive access to each element in collection
                 .parallelStream() // Note: Returns a possibly parallel Stream with this collection as its source. It is allowable for this method to return a sequential stream.
                //Note : filter() takes ainput a predicate which is a functional interface which returns true if the input argument matches the predicate, otherwise false .
                .filter(  // s represents the input string followed by implementation ( use {} is implementation consists of more than 1 line of code.
                   s ->  s.length() > size
                )
                .distinct() // discard duplicates
                .map(String::toUpperCase)  // convert to upper case
                .sorted() //sort the result
                .collect(Collectors.toList());  //collect to a list



    }
}
