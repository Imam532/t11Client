package ru.shara.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.shara.model.User;
import ru.shara.model.UserRoleEnum;
import ru.shara.service.UserService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class RestApiController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public RestApiController(UserService userService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }



    @PostMapping("new-user")
    public User createUser(@Valid @RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);
        return user;
    }

    @GetMapping("all-users")
    public List<User> getAllUsers() {
        return userService.listAll();
    }

    @GetMapping("roles")
    public List<UserRoleEnum> getAllRoles() {
        return Arrays.asList(UserRoleEnum.values());
    }


    @GetMapping("user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) {
        User user = userService.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }


    @PutMapping("user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User userData) {
        User user = userService.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setName(userData.getName());
        user.setUserRoles(userData.getUserRoles());

        if (userData.getPassword() != null && !userData.getPassword().equals("") && !userData.getPassword().equals(" ")) {
            user.setPassword(encoder.encode(userData.getPassword()));
        }

        userService.save(user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long id) {
        User user = userService.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
