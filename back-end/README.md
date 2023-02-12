# Back-End

## Requirements

| For Development | For Deploy |
| --------------- | ---------- |
| Java | Docker |
| Spring Boot |
| Maven |

## Services

* [Data](Data)
* [Solver](Solver)

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

```bash
bash create-test-users.sh 
```