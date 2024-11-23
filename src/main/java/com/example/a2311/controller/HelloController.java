package com.example.a2311.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
        @RequestMapping("/acccc")
        public String Hello () {
            return "test aaa";
        }
}
