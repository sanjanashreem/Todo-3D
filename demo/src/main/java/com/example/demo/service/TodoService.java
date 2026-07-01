package com.example.demo.service;
import com.example.demo.modules.Todo;
import com.example.demo.modules.User;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepo;

    @Autowired
    private UserRepository userRepository; // We need this to find the user

    // UPDATED: Now requires the email of the logged-in user
    public Todo createTodo(Todo todo, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        todo.setUser(user); // Attach the user to the todo before saving
        return todoRepo.save(todo);
    }

    public Page<Todo> getAllTodosPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return todoRepo.findAll(pageable);
    }

    public Todo getTodoById(Long id) {
        return todoRepo.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    // UPDATED: Fetch only the todos for this specific email
    public List<Todo> getTodosByUser(String email){
        return todoRepo.findByUserEmail(email);
    }

    // UPDATED: Ensure the user actually owns the todo before updating it
    public Todo updateTodo(Todo updatedTodo, String email){
        Todo existingTodo = getTodoById(updatedTodo.getId());

        // Security check
        if (!existingTodo.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You do not have permission to update this todo");
        }

        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setDescription(updatedTodo.getDescription());
        existingTodo.setIsCompleted(updatedTodo.getIsCompleted());

        return todoRepo.save(existingTodo);
    }

    // UPDATED: Ensure the user actually owns the todo before deleting
    public void deleteTodoById(Long id, String email){
        Todo todo = getTodoById(id);

        if (!todo.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You do not have permission to delete this todo");
        }

        todoRepo.delete(todo);
    }
}