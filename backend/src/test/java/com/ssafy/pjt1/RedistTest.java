package com.ssafy.pjt1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import com.ssafy.pjt1.model.dto.chat.ChatMessage;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RedistTest {
    public static final Logger logger = LoggerFactory.getLogger(RedistTest.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ListOperations listOps;

    @Test
    public void zSetTest() {
        String key = "test";
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        zset.add(key, "a", 1);
        zset.add(key, "b", 2);
        zset.add(key, "c", 3);
        // zset.add(key, "d", 4);
        // zset.add(key, "e", 5);
        zset.incrementScore(key, "b", 2);
        logger.info("msg: {}", zset.range(key, 0, 2));
    }

    @Test
    public void repository() {
        String key = "test";
        // CommentNumDto dto = new CommentNumDto("abc", "1");
        // repo.save(dto);
        // CommentNumDto dtos = repo.findById("abc").orElse(null);
        // ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        // zset.add(key, "abc", Integer.valueOf(dtos.getNum()));
        // // logger.info("객체: {}", dtos.getNum());
        // logger.info("객체: {}", zset.reverseRange(key, 0, 0));
    }

    @Test
    public void Setops() {
        String uid = UUID.randomUUID().toString();
        log.info(uid);
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        setOps.add("checkId", "moonkim");
        setOps.add("checkId", "kimmoon");
        assertEquals(true, setOps.isMember("checkId", "moonkim"));
        log.info("msg: {}", setOps.isMember("checkId", "kkk"));
        log.info("결과나옴");
    }

    @Test
    public void opsList() {
        listOps = redisTemplate.opsForList();
        List<ChatMessage> list = listOps.range("key", 0, 10);
        log.info("msg:{}", list.size());
    }
}
