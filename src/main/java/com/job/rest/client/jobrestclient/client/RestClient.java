package com.job.rest.client.jobrestclient.client;

import com.job.rest.client.jobrestclient.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.logging.Logger;

@Component
@PropertySource("application.properties")
public class RestClient {

    private static final Logger logger = Logger.getLogger(RestClient.class.getName());

    private WebClient webClient;

    @Value("${uri.endpoint}")
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Autowired
    public void createWebClient() {
        this.webClient = WebClient.create(uri);
    }

    public void createWebClient(String uri) {
        this.webClient = WebClient.create(uri);
    }

    public Mono<Job> getJob(String id) {
        logger.info(String.format("Calling getJob(%s)", id));

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/randomWaitEndpoint")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(Job.class);
    }

    public Mono<Job> getJobFromURI(String id) {
        logger.info(String.format("Calling getJob(%s)", id));

        return  WebClient.create().get()
                .uri(uriBuilder -> uriBuilder
                        .path(uri)
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(Job.class);
    }

    public Flux<Job> retrieveJobs(List<String> ids) {
        return Flux.fromIterable(ids)
            .parallel()
            .runOn(Schedulers.elastic())
            .flatMap(this::getJob)
            .ordered((j1, j2) -> j2.getId().compareTo(j1.getId()));
    }

    public Flux<Job> retrieveJobsFromURI(List<String> ids) {
        return Flux.fromIterable(ids)
                .parallel()
                .runOn(Schedulers.elastic())
                .flatMap( this::getJobFromURI)
                .ordered((j1, j2) -> j2.getId().compareTo(j1.getId()));
    }


}
