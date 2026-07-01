package com.example.demo.controller;

import com.example.demo.service.TodoService;
import com.example.demo.modules.Todo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
@RequestMapping("/todo")
@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Todo Retrieved successfully"),
            @ApiResponse(responseCode = "404",description = "Todo not found!")
    })

    // get by id
    @GetMapping("/{id}")
    ResponseEntity<Todo> getTodoById(@PathVariable long id){
        try {
            Todo GetTodo = todoService.getTodoById(id);
            return new ResponseEntity<>(GetTodo ,HttpStatus.OK);
        } catch (RuntimeException exception){
            log.info("Error");
            return new ResponseEntity<>((HttpHeaders) null,HttpStatus.NOT_FOUND);
        }
    }

    // Get all todo for the logged-in user
    @GetMapping
    ResponseEntity<List<Todo>> getTodos(Principal principal){
        String email = principal.getName(); // Extracts the email from the JWT token
        return new ResponseEntity<>(todoService.getTodosByUser(email), HttpStatus.OK);
    }

    @GetMapping("/page")
    ResponseEntity<Page<Todo>> getTodosPage(@RequestParam int page, @RequestParam int size){
        return new ResponseEntity<>(todoService.getAllTodosPage(page, size),HttpStatus.OK);
    }

    // Get paginated todos (Note: This currently fetches all users' todos. You will need to update your repository to support pagination by email if you want this secured!

    // post create
    @PostMapping("/create")
    ResponseEntity<Todo> createUser(@RequestBody Todo todo, Principal principal){
        String email = principal.getName();
        Todo createdTodo = todoService.createTodo(todo, email);
        return new ResponseEntity<>(createdTodo ,HttpStatus.CREATED);
    }

    // Update a todo securely
    @PutMapping
    ResponseEntity<Todo> updateTodoById(@RequestBody Todo todo, Principal principal){
        String email = principal.getName();
        return new ResponseEntity<>(todoService.updateTodo(todo, email), HttpStatus.OK);
    }

    // Delete a todo securely
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteTodoById(@PathVariable long id, Principal principal){
        String email = principal.getName();
        todoService.deleteTodoById(id, email);

        // Changed this from void to ResponseEntity so your frontend fetch()
        // doesn't throw an error when trying to read response.text()
        return new ResponseEntity<>("Todo deleted successfully", HttpStatus.OK);
    }
}


