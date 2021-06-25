package com.example.demo.domain;

import org.springframework.context.ApplicationEvent;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
public class UserCreateEvent extends ApplicationEvent {

    private final User user;

    public UserCreateEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
