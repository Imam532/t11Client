package ru.shara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.shara.model.User;
import ru.shara.service.UserService;

import javax.validation.Valid;

@Controller
@Transactional(propagation= Propagation.REQUIRED)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @GetMapping("/admin")
    public String adminHome() {
        return "admin";
    }

    @GetMapping({"/login", "/"})
    public String login(Model model) {
        return "login";
    }

    @GetMapping(value = "/user")
    @ResponseBody
    public ModelAndView currentUserName(Authentication authentication) {
        ModelAndView mav = new ModelAndView("index");
        User user = (User) authentication.getPrincipal();
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/admin/save")
    public String saveCustomer(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @RequestMapping("/admin/delete")
    public String deleteCustomerForm(@RequestParam long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
