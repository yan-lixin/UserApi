package com.example.demo.controller;

import com.example.demo.dto.UserDetail;
import com.example.demo.service.UserCacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
@RestController
@RequestMapping("/query/user")
public class UserQueryController {

    private final UserCacheService userCacheService;

    public UserQueryController(UserCacheService userCacheService) {
        this.userCacheService = userCacheService;
    }

    @GetMapping("/{id}")
    public UserDetail findById(@PathVariable("id") Integer id) {
        return userCacheService.findById(id);
    }
}
