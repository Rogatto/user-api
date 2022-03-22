package com.app.crud.unit.service;

import com.app.crud.entity.User;
import com.app.crud.repository.UserRepository;
import com.app.crud.service.Impl.UserServiceImpl;
import com.app.crud.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RedisTemplate<String, User> redisTemplate;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldReturnAllusers(){

        User user = User.builder()
                .username("admin")
                .name("simple admin")
                .password("{bcrypt}$2a$10$kavSouNY7FWP2TpIPwedNOvNX4BXjBkXTG5fk71zWmX/4.yH2oC1O")
                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                .build();

        UserService userService = new UserServiceImpl(userRepository, redisTemplate, applicationEventPublisher);
        userService.saveUser(user);

    }
}
