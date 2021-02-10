package com.ssafy.pjt1.model.service.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private ObjectMapper objMapper;

    @Override
    public List<ChatMessage> getMessage(int startNum, int endNum, String room_id) throws IOException {
        String key = "message::" + room_id;
        listOps = redisTemplate.opsForList();

        List<String> str = listOps.range(key, startNum, endNum);
        List<ChatMessage> list = new ArrayList<>();
        for (String json : str) {
            list.add(objMapper.readValue(json, ChatMessage.class));
        }
        return list;
    }

    @Override
    public void insertMessage(ChatMessage message) throws IOException {
        String key = "message::" + message.getRoom_id();
        listOps = redisTemplate.opsForList();
        String strMsg = objMapper.writeValueAsString(message);
        listOps.leftPush(key, strMsg);
    }

}
