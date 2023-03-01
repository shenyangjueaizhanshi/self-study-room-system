package com.example.selfstudyroomsystem.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "测试")
@RequestMapping("/test")
@RestController
public class Test {
    @PostMapping("/")
    public String test(){
       return "hello test";
    }
}
