package com.ssafy.pjt1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt1.model.dto.subscription.SubscriptionDto;
import com.ssafy.pjt1.model.service.main.MainService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController("/main")
public class MainController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    @Autowired
    private MainService service;

    @ApiOperation(value = "즐겨찾기 리스트 불러오기")
    @GetMapping("/selectFavorite/{user_id}")
    public ResponseEntity<Map<String, Object>> selectFavorite(@PathVariable("user_id") String user_id) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            List<SubscriptionDto> list = service.selectFavorite(user_id);
            resultMap.put("favorite", list);
            resultMap.put("status", SUCCESS);
        } catch (Exception e) {
            logger.error("error", e);
            resultMap.put("status", FAIL);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
