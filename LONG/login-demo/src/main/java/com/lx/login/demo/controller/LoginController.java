package com.lx.login.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/23 11:18
 */
@Controller
//@RequestMapping("/user")
public class LoginController {
    @PostMapping("/login")
    @ResponseBody
    public String login(){
        return "login success";
    }

    @GetMapping("/loginPage")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/test1")
    @ResponseBody
    public String test1(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "test1";
    }

    @GetMapping("/test2")
    @ResponseBody
    public String test2(){
        return "test2";
    }

    @GetMapping("/test3")
    @ResponseBody
    public String test3(){
        return "test3";
    }

    @GetMapping("/test4")
    @ResponseBody
    public String test4(){
        return "test4";
    }

    @GetMapping("/getUser")
    @ResponseBody
    public Authentication getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
}
