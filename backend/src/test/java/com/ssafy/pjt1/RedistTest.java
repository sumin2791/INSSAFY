package com.ssafy.pjt1;

import com.fasterxml.jackson.databind.ObjectMapper;

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
