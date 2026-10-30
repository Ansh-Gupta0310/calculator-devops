# Calculator DevOps
A small Spring Boot calculator wrapped in a full CI/CD pipeline.
Mini-project for CSE 816 Software Production Engineering (M.Tech CSE, 2026-27).
## Build and test
```
mvn clean package
mvn test
```
## Run locally
```
mvn spring-boot:run
```
# builds target/calculator-devops-1.0.0.jar
# runs JUnit 5 unit + integration tests
# http://localhost:8080
## Endpoints
| Method | Path
|
Example
|
|--------|------------------------------------|----------------------------------
------------|
| GET
| `/api/v1/add?a=&b=`
| `curl
'localhost:8080/api/v1/add?a=2&b=3'`
|
| GET
| `/api/v1/subtract?a=&b=`
| `curl
'localhost:8080/api/v1/subtract?a=5&b=2'` |
| GET
| `/api/v1/multiply?a=&b=`
| `curl
'localhost:8080/api/v1/multiply?a=4&b=3'` |
| GET
| `/api/v1/divide?a=&b=`
| `curl
'localhost:8080/api/v1/divide?a=10&b=2'` |
| GET
| `/actuator/health`
| `curl
localhost:8080/actuator/health`
|