package com.example.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
class HystrixService {

    @HystrixCommand(fallbackMethod = "recover")
    String doUpload(String payload) {
        doCall(payload);
        return payload;
    }

    private void doCall(String payload)
    {
        System.out.println("real service called");
        if(payload.contentEquals("FAIL")) {
           throw new RestClientException("");
        }
    }


    public String recover(String payload) {
        return "recovered";
    }
}