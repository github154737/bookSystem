package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * The type Hello.
 */
@RestController
public class Hello {

    @Autowired
    private DataSource dataSource;


    /**
     * Hello string.
     *
     * @return the string
     */
    @RequestMapping("/Hello")
    public String hello(){
        return "Hello";
    }


}
