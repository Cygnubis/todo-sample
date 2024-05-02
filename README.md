### GET 
example: `curl -X GET http://0.0.0.0:8080/todo`

### POST 
example: `curl -X POST -H "Content-Type: application/json" -d '{"todo": "buy memory"}' http://0.0.0.0:8080/todo`

### PUT 
example: `curl -X PUT -H "Content-Type: application/json" -d '{"isDone": true}' http://0.0.0.0:8080/todo/{id}`

### DELETE
example: `curl -X DELETE http://0.0.0.0:8080/todo/{id}`
