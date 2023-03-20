# Back-End

## Requirements

|             | For Development | For Deploy |
| ----------- | --------------- | ---------- |
| Docker      | &#10004; | &#10004; |
| Java        | &#10004; |
| Maven       | &#10004; |
| Spring Boot | &#10004; |

To visualize the sequence diagram you can use the PlantUML package for VScode.

### IDE recommended :

* Spring Tools 4 for Eclipse ([download here](https://spring.io/tools))

## File tree

* [Data/](.\Data) &#8594; Data service
  * [src/](.\Data\src) &#8594; Source code
  * [.gitignore](.\Data\.gitignore)
  * [data_service_openapi.yaml](.\Data\data_service_openapi.yaml)  &#8594; OpenAPI of Data service
  * [Dockerfile](.\Data\Dockerfile)
  * [mvnw](.\Data\mvnw)
  * [mvnw.cmd](.\Data\mvnw.cmd)
  * [pom.xml](.\Data\pom.xml)
  * [README.md](.\Data\README.md)
* [Solver/](.\Solver) &#8594; Solver service
  * [src/](.\Solver\src) &#8594; Source code
  * [.gitignore](.\Solver\.gitignore)
  * [Dockerfile](.\Solver\Dockerfile)
  * [mvnw](.\Solver\mvnw)
  * [mvnw.cmd](.\Solver\mvnw.cmd)
  * [pom.xml](.\Solver\pom.xml)
  * [README.md](.\Solver\README.md)
  * [solver_service_openapi.yaml](.\Solver\solver_service_openapi.yaml) &#8594; OpenAPI of Solver service
* [User/](.\User) &#8594; User service
  * [src/](.\User\src) &#8594; Source code
  * [.gitignore](.\User\.gitignore)
  * [Dockerfile](.\User\Dockerfile)
  * [mvnw](.\User\mvnw)
  * [mvnw.cmd](.\User\mvnw.cmd)
  * [pom.xml](.\User\pom.xml)
  * [README.md](.\User\README.md)
  * [user_service_openapi.yaml](.\User\user_service_openapi.yaml) &#8594; OpenAPI of User service
* [create-test-users.sh](.\create-test-users.sh) &#8594; File to create users in database for testing
* [diagram.puml](.\diagram.puml) &#8594; Sequence diagram
* [docker-compose.yml](.\docker-compose.yml) &#8594; Docker-compose file for deployment 
* [README.md](.\README.md)

## Deployment

Build or rebuild services
```bash
docker-compose build
```

Create and start containers
```bash
docker-compose up
```

Stop and remove containers
```bash
docker-compose down
```

## Tests

Run this command to initialize the database with some users for testing.
```bash
bash create-test-users.sh 
```
