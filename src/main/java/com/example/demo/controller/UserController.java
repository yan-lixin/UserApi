package com.example.demo.controller;

import com.example.demo.dto.UserCreateCmd;
import com.example.demo.dto.UserDetail;
import com.example.demo.dto.UserUpdateCmd;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDetail create(@RequestBody UserCreateCmd cmd) {
        return userService.create(cmd);
    }

    @GetMapping("/{id}")
    public UserDetail findById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @PutMapping
    public void update(@RequestBody UserUpdateCmd cmd) {
        userService.update(cmd);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }
}
