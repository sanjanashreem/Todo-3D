package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class example {
    @GetMapping("/hello")
    String name(){
        return "Hello world";
    }
}
