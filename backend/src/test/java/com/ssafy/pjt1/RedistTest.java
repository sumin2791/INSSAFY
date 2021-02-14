package com.ssafy.pjt1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.pjt1.model.dto.chat.ChatMessage;
import com.ssafy.pjt1.model.dto.user.UserDto;
import com.ssafy.pjt1.model.service.UserService;
import com.ssafy.pjt1.model.service.chat.ChatService;
import com.ssafy.pjt1.model.service.redis.RedisService;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RedistTest {
    public static final Logger logger = LoggerFactory.getLogger(RedistTest.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper mapper;

    @Test
    public void valOpsTest() {
        ValueOperations<String, String> valOps = redisTemplate.opsForValue();
        String key = "notice:moon:kim";
        // valOps.set(key, "1");
        valOps.increment(key, 1);
    }
}
