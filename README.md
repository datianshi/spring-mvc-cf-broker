spring-mvc-cf-broker
====================
A Spring mvc based service broker that manages the in-memory hash maps as resources/services

This README file walk through how to deploy the broker, provision service and bind service to the client app, as well as explain the design.


###Bosh Lite installation

In order to play this in a local environment, set up a cloudfoundry environment through bosh-lite: https://github.com/cloudfoundry/bosh-lite

###Deploy the service broker to the cloudfoundry as an app.

  ```
    cd broker
    mvn clean package
    cf push shaozhen -p target/broker-1.0.0-BUILD-SNAPSHOT.war
  ```
  
###Register the broker to cloudfoundry  

  ```
    cf create-service-broker inmemory-map shaozhen inmemory http://shaozhen.10.244.0.34.xip.io
  ```
###Make the broker plan to be public.

This service broker provided two plans, while cloudfoundry makes them as private after creation. In order to use the plan, make it public.

1. Get the plan id:

   The plan id is the metadata => guid

  ```
    cf curl /v2/service_plans
    
    {
      "metadata": {
        "guid": "8ded5795-cb58-45cf-a900-f1644d5493b1",
        "url": "/v2/service_plans/8ded5795-cb58-45cf-a900-f1644d5493b1",
        "created_at": "2014-03-24T18:09:11+00:00",
        "updated_at": "2014-03-24T18:10:19+00:00"
      },
      "entity": {
        "name": "plan1"       
  ```
2. Make the plan to be public. 

  ```
    cf curl /v2/service_plans/8ded5795-cb58-45cf-a900-f1644d5493b1 -X PUT -d '{"public":true}'
  ```
   
###Create two services

   The two services are phsically two new HashMap<> instances in memory of service broker JVM
   
  ```
    cf cs dummy_service_name plan1 test-service
    cf cs dummy_service_name plan1 test-service2
  ```
  
###Deploy the client application to cloudfoundry

  ```
    cd client
    mvn clean package
    cf push client target/client-1.0.0-BUILD-SNAPSHOT.war 
  ```

###Bind the client and test

1. Bind the first client
  
  ```
    cf bind-service client test-service
  ```  

2. Push some key values on the through browser form. The key-values are saved in the provisioned map (Created )
