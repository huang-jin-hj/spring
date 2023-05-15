package com.yes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangJin on 2023/5/15.
 */
@RestController
public class MyController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
