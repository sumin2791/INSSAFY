package com.ssafy.pjt1.model.service.chat;

import java.util.List;

import com.ssafy.pjt1.model.dto.chat.ChatMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private RedisTemplate redisTemplate;

    private ListOperations listOps;

    @Override
    public List<ChatMessage> getMessage(int startNum, int endNum, String room_id) {
        String key = "message::" + room_id;
        listOps = redisTemplate.opsForList();
        List<ChatMessage> list = listOps.range(key, startNum, endNum);
        return list;
    }

}
