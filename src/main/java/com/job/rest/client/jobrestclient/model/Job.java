package com.job.rest.client.jobrestclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.xml.internal.ws.developer.Serialization;

import java.util.UUID;

@Serialization
public class Job {
    private String id;

    public Job(@JsonProperty("jobId") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id ;
    }
}
