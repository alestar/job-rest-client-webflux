package com.job.rest.client.jobrestclient;

import com.job.rest.client.jobrestclient.client.RestClient;
import com.job.rest.client.jobrestclient.model.JobWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class JobRestClientApplication implements CommandLineRunner {


    @Autowired
    RestClient restClient;

    public static void main(String[] args) {
        SpringApplication.run(JobRestClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        //Create a list of Job ID to make simultaneous rest calls
        List<String> jobIDs = Arrays.asList("1","3","4","5","6","7","9","10");
        restClient.retrieveJobsFromURI(jobIDs);

        System.out.println("My uri: " + restClient.getUri());

        JobWrapper jobWrapper = new JobWrapper(restClient.retrieveJobsFromURI(jobIDs)
                .collectList()
                .block());

        System.out.println(jobWrapper);
    }
}
