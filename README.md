## Running the application locally
#start rest service
./mvnw spring-boot:run

##running test : 
#unit test : do not need to start server
./mvnw clean test

#Integration tests : need to start server first
./mvnw spring-boot:run
./mvnw  -Dit.test=Wproject1RestAssuredIT verify
