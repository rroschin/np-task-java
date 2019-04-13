# NP-TASK-JAVA

Simple Java + SpringBoot + Maven project that attemts to solve the task described in TASK.md file

## How To Build
Project uses maven as a build tool with maven wrapper commads. To build fat-jar from the main directory run:

`mvnw.cmd clean package` on Windows

`./mvnw clean package` on Linux

## How To Run
There are several ways to run the project

### Maven Build + Java Jar
From the main project directory execute:

`./mvnw clean package`

`java -jar target/job-task-1.0.0.jar`

### Spring Boot + Maven
From the main project directory execute:

`./mvnw spring-boot:run`

### In Docker
From the main project directory execute:

`docker build -t np-task-java .`

`docker run -p 5000:5000 --name=np-task-java-app np-task-java`

### How To Test
After application is tarted is is available at this URL:
https://localhost:5000/

*Note that self-signed SSL certificate is used, some browsers might show warning*

#### HealthCheck URL: 
https://localhost:5000/healthcheck
#### Search Hotels URL:
https://localhost:5000/api/v1/hotels?cityCode=PAR&checkInDate=2019-04-14&checkOutDate=2019-04-15
##### Query Parameters
- cityCode - City Code in 3 characters, similar to Amadeus API. Example: _cityCode=PAR_
- checkInDate - Date of Check-In in YYYY-MM-DD format, similar to Amadeus API. Example: _checkInDate=2019-04-14_
- checkOutDate - Date of CheckOut in YYYY-MM-DD format, similar to Amadeus API. Example: _checkOutDate=2019-04-15_

##### Example Response:
`
[
    {
        "hotelName": "HOLIDAY INN PARIS-NOTRE DAME",
        "hotelAddress": "4 RUE DANTON, PARIS, 75006",
        "hotelPhoneNumber": "33-1-81690060",
        "roomRate": "79.99 USD"
    },
    {
        "hotelName": "MELIA COLBERT",
        "hotelAddress": "7, RUE DE L'HOTEL COLBERT, PARIS, 75005",
        "hotelPhoneNumber": "33-1-56811900",
        "roomRate": "172.00"
    },
    {
        "hotelName": "HOLIDAY INN PARIS-NOTRE DAME",
        "hotelAddress": "4 RUE DANTON, PARIS, 75006",
        "hotelPhoneNumber": "+33 1 81690060",
        "roomRate": "301.88"
    }
]
`

#### Postman collection is available in the main directory of the project
`NP-TASK-JAVA.postman_collection.json`
