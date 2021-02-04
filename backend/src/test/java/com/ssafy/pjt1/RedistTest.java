package com.ssafy.pjt1;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

@SpringBootTest
public class RedistTest {
    public static final Logger logger = LoggerFactory.getLogger(RedistTest.class);

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void zSetTest() {
        String key = "followRank";
        // ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        // zset.add(key, "a", 1);
        // zset.add(key, "b", 2);
        // zset.add(key, "c", 3);
        // zset.add(key, "d", 4);
        // zset.add(key, "e", 5);
        // Set<String> set = zset.reverseRange(key, 0, 2);
        // logger.info("msg: {}", zset.range(key, 0, 1));
        
    }
}
