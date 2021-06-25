package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.dto.UserDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author: lixin
 * Date: 2021/6/9
 * Description:
 */
@Slf4j
@Service
public class UserCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCacheService.class);

    public static final String USER_KEY_PREFIX = "user:";

    private UserRepository userRepository;

    private RedisTemplate<String, String> redisTemplate;

    private ObjectMapper objectMapper;

    public UserCacheService(UserRepository userRepository, RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public UserDetail findById(Integer id) {
        String userKey = buildUserCacheKey(id);
        String userCache = redisTemplate.opsForValue().get(userKey);
        if (StringUtils.hasText(userCache)) {
            try {
                LOGGER.info("查询User，id为{}，命中缓存", id);
                User user = objectMapper.readValue(userCache, User.class);
                return toDetail(user);
            } catch (JsonProcessingException e) {
                LOGGER.warn("User json 反序列化失败", e);
            }
        }
        LOGGER.info("查询User，id为{}，没有命中缓存", id);
        return toDetail(refreshCache(id));
    }

    private User refreshCache(Integer id) {
        LOGGER.info("更新User，id为{}，缓存", id);
        String userKey = buildUserCacheKey(id);
        User user = userRepository.findById(id).orElse(null);
        try {
            String userCache = objectMapper.writeValueAsString(user);
            redisTemplate.opsForValue().set(userKey, userCache, 30, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            LOGGER.warn("User json 序列化失败", e);
        }
        return user;
    }

    private UserDetail toDetail(User user) {
        if (user == null) {
            return null;
        }
        UserDetail userDetail = new UserDetail();
        BeanUtils.copyProperties(user, userDetail);
        return userDetail;
    }

    private String buildUserCacheKey(Integer id) {
        return USER_KEY_PREFIX + id;
    }

    @Async
    @TransactionalEventListener
    public void on(UserCreateEvent event) {
        Integer id = event.getUser().getId();
        LOGGER.info("User id为{}，数据创建，生成缓存", id);
        refreshCache(id);
    }

    @Async
    @TransactionalEventListener
    public void on(UserUpdateEvent event) {
        Integer id = event.getUser().getId();
        LOGGER.info("User id为{}，数据更新，删除缓存", id);
        redisTemplate.delete(buildUserCacheKey(id));
    }

    @Async
    @TransactionalEventListener
    public void on(UserDeletedEvent event) {
        Integer id = event.getId();
        LOGGER.info("User id为{}，数据删除，删除缓存", id);
        redisTemplate.delete(buildUserCacheKey(id));
    }

}
