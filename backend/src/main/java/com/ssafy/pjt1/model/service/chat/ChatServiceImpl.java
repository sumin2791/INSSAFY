package com.ssafy.pjt1.model.service.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.pjt1.model.dto.chat.ChatMessage;
import com.ssafy.pjt1.model.dto.user.UserDto;
import com.ssafy.pjt1.model.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ListOperations<String, String> listOps;

    private ObjectMapper objMapper;

    @Autowired
    private UserService userService;

    // 메시지 다 갖고오기
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

    // 메시지 저장
    @Override
    public void insertMessage(ChatMessage message) throws IOException {
        String key = "message::" + message.getRoom_id();
        listOps = redisTemplate.opsForList();
        log.info("key:{}", key);
        log.info("messages:{}", message.getMsg());
        log.info("여까진 잘 들어옴");
        objMapper = new ObjectMapper();
        String strMsg = objMapper.writeValueAsString(message);
        listOps.leftPush(key, strMsg);
    }

    // 채팅방 갖고오기
    @Override
    public void getRoomList(String user_id) {
        listOps = redisTemplate.opsForList();
        objMapper = new ObjectMapper();

    }

    // 방 만들기
    @Override
    public String makeRoom(String user_id, String opp_id) throws IOException {
        String uid = UUID.randomUUID().toString();
        listOps = redisTemplate.opsForList();
        objMapper = new ObjectMapper();
        log.info("들어옴");
        // 방 생성하기
        // 내 방과, 상대방 방을 동시에 생성
        Map<String, Object> roomInfo = new HashMap<>();
        UserDto oppDto = userService.userDtoById(opp_id);
        log.info("dto:{}", oppDto.getUser_email());
        roomInfo.put("roomId", uid);// 방 번호
        roomInfo.put("opp_nickName", oppDto.getUser_nickname());// 상대방 닉네임
        roomInfo.put("opp_img", oppDto.getUser_image());// 상대방 이미지
        // 채팅방에 넣기
        String infoString = objMapper.writeValueAsString(roomInfo);
        log.info("info:{}", infoString);
        listOps.leftPush("roomInfo::" + user_id, infoString);
        listOps.leftPush("roomInfo::" + opp_id, infoString);
        return uid;
    }

}
