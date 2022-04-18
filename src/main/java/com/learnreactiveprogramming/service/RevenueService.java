package com.learnreactiveprogramming.service;

import com.learnreactiveprogramming.domain.Revenue;

import static com.learnreactiveprogramming.util.CommonUtil.delay;

public class RevenueService {

    /*
     Note: The following function does not return a Reactive type (instead returns a Revenue object) and males a blocking calls
     */
    public Revenue getRevenue(Long movieId){
        delay(1000); // simulating a network call ( DB or Rest call)  -> Blocking call
        return Revenue.builder()
                .movieInfoId(movieId)
                .budget(1000000)
                .boxOffice(5000000)
                .build();

    }
}
