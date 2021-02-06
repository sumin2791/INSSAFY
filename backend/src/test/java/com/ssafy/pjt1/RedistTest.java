package com.ssafy.pjt1;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.ssafy.pjt1.model.dto.CommentNumDto;
import com.ssafy.pjt1.model.service.redis.RedisRepository;

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
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisRepository repo;

    @Test
    public void zSetTest() {
        String key = "test";
        // ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        // zset.add(key, "a", 1);
        // zset.add(key, "b", 2);
        // zset.add(key, "c", 3);
        // zset.add(key, "d", 4);
        // zset.add(key, "e", 5);
        // Set<String> set = zset.reverseRange(key, 0, 2);
        // logger.info("msg: {}", zset.range(key, 0, 1));
    }

    @Test
    public void repository() {
        String key = "test";
        CommentNumDto dto = new CommentNumDto("abc", "1");
        repo.save(dto);
        CommentNumDto dtos = repo.findById("abc").orElse(null);
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        zset.add(key, "abc", Integer.valueOf(dtos.getNum()));
        // logger.info("객체: {}", dtos.getNum());
        logger.info("객체: {}", zset.reverseRange(key, 0, 0));
    }
}
