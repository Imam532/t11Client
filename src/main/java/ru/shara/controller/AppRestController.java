package ru.shara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.shara.model.User;
import ru.shara.service.UserService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class AppRestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "admin/users")
    public List<User> userList() throws SQLException{
        return userService.getAllUsers();
    }

    @PostMapping(value = "/admin/add")
    public User addUser(@RequestBody User user) throws SQLException {
        userService.createUser(user);
        return  userService.getUser(user.getId());
    }

    @PutMapping(value = "/admin/update")
    public User updateUser(@RequestBody User user) throws SQLException {
        userService.updateUser(user);
        return userService.getUser(user.getId());
    }

    @GetMapping(value = "/admin/update/{id}")
    public User getUserById(@PathVariable Long id) throws SQLException {
        return userService.getUser(id);
    }

    @DeleteMapping(value = "/admin/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
    }
}
