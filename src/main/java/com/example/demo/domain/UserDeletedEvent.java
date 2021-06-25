package com.example.demo.domain;

import org.springframework.context.ApplicationEvent;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
public class UserDeletedEvent extends ApplicationEvent {

    private final Integer id;

    public UserDeletedEvent(Object source, Integer id) {
        super(source);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
