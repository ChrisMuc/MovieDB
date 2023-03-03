# MovieDB

How to start the MovieDB application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/MovieDB-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


OpenAPI Specification
---
To see the OpenAPI endpoint specification, open url: `http://localhost:8080/openapi.json`

Commands to add and query movies
---
```
# Query movies and people
curl http://localhost:8080/people
curl http://localhost:8080/person/1
curl http://localhost:8080/movies
curl http://localhost:8080/movie/1

# Inserts: OK
curl -H "Content-Type: application/json" -d '{"name":"Keanu Reeves" }' http://localhost:8080/people
curl -H "Content-Type: application/json" -d '{"name":"The Matrix", "publicationYear" : 1999, "description" : "The Matrix is a 1999 science fiction action film written and directed by the Wachowskis. It depicts a dystopian future in which humanity is unknowingly trapped inside the Matrix, a simulated reality that intelligent machines have created to distract humans while using their bodies as an energy source." }' http://localhost:8080/movies
curl -H "Content-Type: application/json" -d '{"name":"The Test", "description" : "test" }' http://localhost:8080/movies

# Inserts: Failing
curl -H "Content-Type: application/json" -d '{"name":"Missing Description" }' http://localhost:8080/movies

# Search
curl http://localhost:8080/people?name=Reeves
curl http://localhost:8080/movies?publicationYear=1999

```


Important Notes
---
- Requests are not limited; i.e. findAll-requests return all objects.
- Update overwrites all attributes. Attributes which are not set, will be set to null!
  If null violates a validation rule, a Status Code of 400 (bad request) is returned