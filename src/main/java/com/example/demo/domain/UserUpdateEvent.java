package com.example.demo.domain;

import org.springframework.context.ApplicationEvent;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
public class UserUpdateEvent extends ApplicationEvent {

    private final User user;

    public UserUpdateEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
