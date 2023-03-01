# MovieDB

How to start the MovieDB application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/MovieDB-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Commands to add and query movies
---
```
curl http://localhost:8080/movies
# OK
curl -H "Content-Type: application/json" -d '{"name":"The Matrix", "publicationYear" : 1999, "description" : "The Matrix is a 1999 science fiction action film written and directed by the Wachowskis. It depicts a dystopian future in which humanity is unknowingly trapped inside the Matrix, a simulated reality that intelligent machines have created to distract humans while using their bodies as an energy source." }' http://localhost:8080/movies
curl -H "Content-Type: application/json" -d '{"name":"The Test", "description" : "test" }' http://localhost:8080/movies

# Failing
curl -H "Content-Type: application/json" -d '{"name":"Missing Description" }' http://localhost:8080/movies

```