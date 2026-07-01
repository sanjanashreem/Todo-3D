package com.example.demo.repository;

import com.example.demo.modules.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Long> {
    // NEW: Spring automatically knows how to fetch Todos where the linked User has this email
    List<Todo> findByUserEmail(String email);
}