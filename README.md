spring-mvc-cf-broker
====================
No infrastructure/cloud to start developing cloudfoundry service broker? Look at here as a Simple Java/Spring example:
A Spring mvc based service broker that manages the in-memory hash maps as resources/services

This README file walk through how to deploy the broker, provision service and bind service to the client app.

###Prerequisite
1. Read the service Broker API

http://docs.cloudfoundry.org/services/api.html

2. Bosh Lite Installation

In order to play this in a local environment, set up a cloudfoundry environment through bosh-lite:

https://github.com/cloudfoundry/bosh-lite

3. Install cf command line and point to your local cloudfoundry environment (Setup at step 2)

http://docs.cloudfoundry.org/devguide/installcf

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

   The two services are phsically two new HashMap<> instances in the memory of service broker JVM.
   
  ```
    cf cs dummy_service_name plan1 test-service
    cf cs dummy_service_name plan1 test-service2
    
  ```
  
    The above commands let cloud_controller pass in a service_id. Then the service broker creates a map with the service_id as a unique identifier. 
    
###Deploy the client application to cloudfoundry

  ```
    cd client
    mvn clean package
    cf push client target/client-1.0.0-BUILD-SNAPSHOT.war 
  ```

###Bind the client and test

1. Bind the first service
  
  ```
    cf bind-service client test-service
  ``` 

    Cloud_Controller pass in the service_id and the client app instance_id to the service broker. Service broker creates a credential includes: RestUrl, Username, and Password that one to one map to the combination of (service_id, instance_id). Then pass back the credential to the client application as VCAP_SERVICES environment variable.

2. Push some key values through browser form. The key-values are saved in the provisioned map as a service

  ```
    The form and key-values are displayed at: http://client.10.244.0.34.xip.io/map
  ```  

    The client app pushed the key-value throught RestUrl and credentials provided inside of VCAP_SERVICES environment variables.

3. Unbind the test-service and bind test-service2

  ```
    cf unbind-service client test-service
    cf bind-service client test-service2
    cf restart client
  ```
4. Create some key values on the test-service2

   Since client binded to another new service, the key-values are empty at http://client.10.244.0.34.xip.io/map
   
5. Rebind to test-service and access the map to find the old key values created in step 2.






  

  
