package com.ssafy.pjt1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.pjt1.model.dto.chat.ChatMessage;
import com.ssafy.pjt1.model.service.chat.ChatService;
import com.ssafy.pjt1.model.service.redis.RedisService;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RedistTest {
    public static final Logger logger = LoggerFactory.getLogger(RedistTest.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper mapper;

    @Autowired
    private ChatService chatService;

    @Autowired
    private RedisService redisService;

    @Test
    public void zSetTest() {
        String key = "addFunc:";
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        // zset.add(key + "userRan:" + "study", "park", 1);
        // zset.add(key + "userRan:" + "study", "moon", 2);
        // zset.add(key + "userRan:" + "study", "kim", 3);
        // zset.add(key + "userRan:" + "game", "b", 8);
        // zset.add(key + "userRan:" + "movie", "c", 10);
        // zset.incrementScore(key, "b", 2);
        // logger.info("msg: {}", zset.range(key, 0, 2));
        // log.info(">>>>>>>>zset: {}", Math.round(zset.score(key, "b")));
        log.info(">>>user Rank:{}", zset.reverseRange(key + "userRan*:*", 0, 1));
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
    public void opsList() throws JsonProcessingException {
        mapper = new ObjectMapper();
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush("key", "0");
        listOps.leftPush("key", "1");
        listOps.leftPush("key", "2");
        log.info(">>>>>>>>>>{}", listOps.range("key", 0, 1));
    }

    @Test
    public void service() throws IOException {
        ChatMessage chat = new ChatMessage("room1", "user1", "opp1", "안녕하세요", "aaa");
        // chatService.insertMessage(chat);
        mapper = new ObjectMapper();
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        String str = mapper.writeValueAsString(chat);
        listOps.leftPush("key", str);
        log.info("msg:{}", listOps.range("key", 0, 0));
        log.info("테스트:{}", str);

    }

    @Test
    public void sendPush() {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        String key = "push:" + "moon:" + "opp";

        // listOps.leftPush("push:" + "moon:" + "opp", "true");
        // listOps.leftPush("push:" + "moon:" + "aaa", "true");
        // listOps.leftPush("push:" + "moon:" + "bbb", "true");

        Set<String> set = redisTemplate.keys("push:moon*");
        for (String k : set) {
            log.info("msg:{}", k);
        }
        // RedisOperations<String, String> redis = listOps.getOperations();
        // redis.keys("push:moon");

    }

    @Test
    public void addFunc() {
        // redisService.increaseUserRank(59, 71);
        ValueOperations valOps = redisTemplate.opsForValue();
        valOps.append("add:id:value", "hello");
    }
}
