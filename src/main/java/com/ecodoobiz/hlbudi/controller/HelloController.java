package com.ecodoobiz.hlbudi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/me")
    public String helloMe(Principal principal) {
        return "Hello, " + principal.getName();
    }
}