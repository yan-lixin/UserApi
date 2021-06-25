package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.dto.UserCreateCmd;
import com.example.demo.dto.UserDetail;
import com.example.demo.dto.UserUpdateCmd;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserService(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public UserDetail create(UserCreateCmd cmd) {
        Assert.notNull(cmd, "UserCreateCmd 不能为空");
        Assert.notNull(cmd.getFullname(), "FullName 不能为空");
        Assert.notNull(cmd.getMobile(), "Mobile 不能为空");

        User user = User.create(cmd.getMobile(), cmd.getFullname());
        User userSaved = userRepository.save(user);
        eventPublisher.publishEvent(new UserCreateEvent(this, user));
        return toDetail(userSaved);
    }

    public UserDetail findById(Integer id) {
        return userRepository.findById(id)
                .map(this::toDetail)
                .orElse(null);
    }

    public void update(UserUpdateCmd cmd) {
        Assert.notNull(cmd, "UserUpdateCmd 不能为空");
        Assert.notNull(cmd.getId(), "Id 不能为空");
        userRepository.findById(cmd.getId()).ifPresent( user -> {
                    user.update(cmd.getMobile(), cmd.getFullname());
                    userRepository.save(user);
                    eventPublisher.publishEvent(new UserUpdateEvent(this, user));
                }
        );
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
        eventPublisher.publishEvent(new UserDeletedEvent(this, id));
    }

    private UserDetail toDetail(User userSaved) {
        UserDetail userDetail = new UserDetail();
        BeanUtils.copyProperties(userSaved, userDetail);
        return userDetail;
    }
}
