package com.example

import com.example.plugins.*
import com.google.gson.reflect.TypeToken
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.receive
import io.ktor.http.HttpStatusCode
import io.ktor.util.reflect.Type
import TodoItem

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    
    val todoList = mutableListOf<TodoItem>()

    routing {

        get("/todo") {
            call.respond(todoList)
        }

        post("/todo") {
            val requestBody: Map<String, String> = call.receive<Map<String, String>>()
            val newTodo = requestBody.get("todo")

            if (newTodo != null) {
                val todoItem = TodoItem(todoList.generateId(), newTodo)
                todoList.add(todoItem)

                call.respond(status = HttpStatusCode.OK, "created id: " + todoItem.id)
            } else {
                call.respond(status = HttpStatusCode.BadRequest, "Bad request")
            }
        }
        
        put("/todo/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val requestBody: Map<String, Boolean> = call.receive<Map<String, Boolean>>()

            if (id != null) {
                val todoItem = todoList.firstOrNull { it.id == id }
                val isDone = requestBody.get("isDone")

                if (todoItem != null && isDone != null) {
                    todoItem.isChecked = isDone

                    call.respond(status = HttpStatusCode.OK, "Updated")
                } else {
                    call.respond(status = HttpStatusCode.NotFound, "requested item not found")
                }
            } else {
                call.respond(status = HttpStatusCode.BadRequest, "id is required")
            }
        }

        delete("/todo/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id != null) {
                todoList.removeIf { it.id == id }
                call.respond(status = HttpStatusCode.OK, "Item Removed")
            } else {
                call.respond(status = HttpStatusCode.BadRequest, "id is required")
            }
        }
    }
}

fun MutableList<TodoItem>.generateId(): Int = lastIndex + 1