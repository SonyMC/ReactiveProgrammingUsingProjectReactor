package com.learnreactiveprogramming.imperative;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/*
E.g. of Imperative style of coding
Here we will filet a list of string based on input size
 */

public class ImperativeExample {

    public static void main(String[] args) {

        var namesList = List.of("alex","ben","chloe","adam","chloe");  //Note : "chloe" is duplicated

        var filteredNamesList = namesGreaterThanSize(namesList, 3);

        System.out.println("Filtered names List :" + filteredNamesList); // Print to console
    }

    private static List<String> namesGreaterThanSize(List<String> namesList, int size) {

        var newNamesList = new ArrayList<String>();  // Note: ArrayList is an implementation of the List Interface

        for(String name : namesList){
            if(name.length() > size && !newNamesList.contains(name.toUpperCase()))  // only accept names greater than 3 and discard duplicates. We are converting to upper case as finally we will be using an Upper Case List and comparison will fail if we do not use Upper Case
                newNamesList.add(name.toUpperCase());  // add to new list and convert to upper case
            }

        return newNamesList;

    }
}
