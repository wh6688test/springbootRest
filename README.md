#  wsproject1 : sample springboot application
Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

Reference Links :
 
 https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-structuring-your-code
 
 openapi links : 
 https://www.baeldung.com/spring-rest-openapi-documentation
 https://springdoc.org/
 https://github.com/springdoc/springdoc-openapi-maven-plugin
 
 some other sample link just for reference : 
     https://raw.githubusercontent.com/codecentric/springboot-sample-app/master/README.md
     
  properties reference links : 
  https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-external-config.html
  
  test references : (Different levels of tests)
  hamcrest matchers : 
  http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matchers.html#hasSize
  
  Spring web layer tests : 
  https://spring.io/guides/gs/testing-web/
  
  https://reflectoring.io/spring-boot-web-controller-test/
  
  https://www.baeldung.com/mockito-void-methods
   
  maven doc link : 
  https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/maven-plugin/examples/repackage-disable-attach.html
  https://github.com/eugenp/tutorials/blob/master/maven-modules/maven-integration-test/pom.xml
  
  https://www.baeldung.com/spring-tests
  
## Requirements

For building and running the application you need:

- [JDK 1.8](open jdk 8)
- [Maven 3](https://maven.apache.org)

## Running the application locally

either in ide or run from cli maven (mvnw)
```shell
./mvnw spring-boot:run
```

##running test : 

./mvnw clean test
./mvnw  -Dit.test=Wproject1RestAssuredIT verify

## Deploying the application


## Copyright

