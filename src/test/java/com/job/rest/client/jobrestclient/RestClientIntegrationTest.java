package com.job.rest.client.jobrestclient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.UniformDistribution;
import com.job.rest.client.jobrestclient.client.RestClient;
import com.job.rest.client.jobrestclient.model.JobWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class RestClientIntegrationTest {

    Logger logger = Logger.getLogger(RestClientIntegrationTest.class.getName());

    private WireMockServer wireMockServer;

    private RestClient restClient = new RestClient();
    
    @Before
    public void setup() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        restClient.createWebClient("http://localhost:" + wireMockServer.port());
    }

    @After
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void givenRequestNumber_whenRetrievingJobID_thenExecutionTimeIsLessThanDouble() {
        // Arrange
        int requestsNumber = 100;
        int singleRequestTime = 3000;

        //Create a list of Job ID to make simultaneous rest calls
        List<String> jobIDs = IntStream.rangeClosed(1, requestsNumber)
                .boxed()
                .map (elem -> UUID.randomUUID().toString())
                .collect(Collectors.toList());

        for (String id : jobIDs) {
            stubFor(get(urlEqualTo("/randomWaitEndpoint?id=" + id)).willReturn(aResponse().withRandomDelay(new UniformDistribution(1, 10))
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                //.withBody(String.format("{ \"jobId\": %d }", i))));
                .withBody(String.format("{\"jobId\": \"%s\" }", id)))); // Expected {'jobId': <String UUID, which is not the unique Identifier from the request >
        }

        // Start the actual call
        long start = System.currentTimeMillis();
        JobWrapper jobWrapper = new JobWrapper(restClient.retrieveJobsUsingPath(jobIDs)
                                          .collectList()
                                          .block());
        long end = System.currentTimeMillis();

        // Asserts
        long totalExecutionTime = end - start;
        logger.info("totalExecutionTime: " + totalExecutionTime);
        logger.info("Output JSON: " +  jobWrapper.toString());

        assertEquals("Unexpected number of jobs", requestsNumber, jobWrapper.getJobs().size());
        assertTrue("Execution time is too big", 2 * singleRequestTime > totalExecutionTime);
    }
}
