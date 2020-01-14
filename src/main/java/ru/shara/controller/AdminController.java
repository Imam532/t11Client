package ru.shara.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AdminController {

    @GetMapping(value = "/admin/users")
    public String listUsers() {
        return "mainPage";
    }
}
