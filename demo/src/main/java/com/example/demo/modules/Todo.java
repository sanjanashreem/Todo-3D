package com.example.demo.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue
    Long id;

    String title;
    String description;
    Boolean isCompleted;

    // NEW: Link this Todo to a specific User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore // Prevents the user's password/details from leaking in the JSON response
    private User user;
}