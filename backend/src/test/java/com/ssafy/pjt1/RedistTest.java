package com.ssafy.pjt1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.pjt1.model.dto.chat.ChatMessage;
import com.ssafy.pjt1.model.service.chat.ChatService;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    public void getMessage() throws IOException {
        String room_id = "c1287b25-e9b3-4e55-9d56-b6c3c6c3072e";
        List<ChatMessage> list = chatService.getMessage(0, 3, room_id);
    }

    @Test
    public void getRecent() {
        String room_id = "14c51daf-ea99-4b63-9e6a-102e21303ff2";
        String str = chatService.getRecentMessage(room_id);
        log.info("값:{}", str);
    }
}
