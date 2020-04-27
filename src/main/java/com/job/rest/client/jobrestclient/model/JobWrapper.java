package com.job.rest.client.jobrestclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobWrapper {

    private List<Job> jobs;

    @JsonCreator
    public JobWrapper(@JsonProperty("jobs") List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    @Override
    public String toString() {
        return "jobs{"
                + jobs +
                '}';
    }
}
