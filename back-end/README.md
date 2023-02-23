# Back-End

## Requirements

|             | For Development | For Deploy |
| ----------- | --------------- | ---------- |
| Docker      | &#10004; | &#10004; |
| Java        | &#10004; |
| Maven       | &#10004; |
| Spring Boot | &#10004; |

### IDE recommended :

* Spring Tools 4 for Eclipse ([download here](https://spring.io/tools))

## Services

* [Data](Data)
* [Solver](Solver)
* [User](User)

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
