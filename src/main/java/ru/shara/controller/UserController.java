package ru.shara.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.shara.model.User;

@Controller
public class UserController {

    @GetMapping(value = "/user")
    public ModelAndView userPage() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        model.addObject("username", userDetail.getUsername());
        model.addObject("user", new User());

        model.setViewName("userPage");
        return model;
    }
}
