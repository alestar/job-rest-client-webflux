# Client Rest  API

# Introduction
    This application is an example of a Rest Client that handles multiple rest call to a specific endpoint simultaneoslly, based on reactive programming paradim.
    This application use Spring Webflux and Web CLient perform multiple queries rest call to an specific rest endpoint, that retrieve Job id in this case.

# Problem
    
    A program is needed to issue between 1000 and 2000 REST requests to a single endpoint to collect a set of unique identifiers. 
    
    The identifiers returned from the REST call are JOB Id's of another system that can be tracked later. 
    
    The program must issue all the requests and return at exit a JSON output that is the list of JOB identifiers as returned from the REST call. 

    It is expected that the REST calls will respond within a random distribution of 1 -  10 seconds.    

# Requirements 
    
    1.  Must make 1 to 2 thousand REST calls to a single REST endpoint as efficiently as possible to collect the JOB Id's.
    2.  Format of the REST call will be GET
    3.  REST call will include the URL parameter called id
    4.  Each REST call will be issued with a different value assigned to the id parameter
    5.  Expected Responses from the REST call will take between 1 and 10 seconds and will be random in nature.
    6.  The REST response format will  be well formatted JSON.
    7.  The REST response format will have one key value pair with the key called "jobId" and the value being a generated UUID.
    8.  All response must be collected and stored for a final response at program exit.
    9.  At program exit a well formatted JSON object will be print for analysis
    10.  The JSON package on exit must have a key value pair with the key being 'jobs' and the value being a JSON List of the response job id's values from the REST calls made.
    
    # Interface Expectations:
    
    Rest call expectations as GET:
    <url>/randomWaitEndpoint?id=<string>
    
    Response from REST GET will be well formed JSON with the following format.
    
    {
    'jobId': <String UUID, which is not the unique Identifier from the request >
    }
    
    Program Exit Format:
    {
     'jobs': [<list of job Id's from REST calls]
    }
    
 # Spec
    - Spring Framework: A Java platform that provides comprehensive infrastructure support for developing Java applications.
    - Spring boot: A platform to develop a stand-alone and production-grade spring application that can run, with minimum configurations and without the need for an entire Spring configuration setup.
    - Webflux: Is a framework that provides reactive programming support for web applications.
    - WireMock - A web service test Web Clients
    - JVM/JDK 8: The Java Development Kit used for creating this application.	

