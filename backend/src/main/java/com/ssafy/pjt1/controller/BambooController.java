package com.ssafy.pjt1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt1.model.dto.bamboo.BambooDto;
import com.ssafy.pjt1.model.service.bamboo.BambooService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/bamboo")
public class BambooController {

    public static final Logger logger = LoggerFactory.getLogger(BambooController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private BambooService bambooService;

    /*
     * 기능: 대나무숲 포스트 작성
     * 
     * developer: 윤수민
     * 
     * @param : user_id, bamboo_title, bamboo_description, bamboo_image,
     * bamboo_iframe, bamboo_header, writer_nickname
     * 
     * @return : message
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> postCreate(@RequestBody Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        logger.info("bamboo/create 호출성공");
        try {
            BambooDto bambooDto = new BambooDto();
            bambooDto.setBamboo_description((String) param.get("bamboo_description"));
            bambooDto.setBamboo_header((String) param.get("bamboo_header"));
            bambooDto.setBamboo_iframe((String) param.get("bamboo_iframe"));
            bambooDto.setBamboo_image((String) param.get("bamboo_image"));
            bambooDto.setBamboo_title((String) param.get("bamboo_title"));
            bambooDto.setUser_id((String) param.get("user_id"));
            bambooDto.setWriter_nickname((String) param.get("writer_nickname"));
            bambooService.createBamboo(bambooDto);
            resultMap.put("message", SUCCESS);

        } catch (Exception e) {
            logger.error("실패", e);
            resultMap.put("message", FAIL);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
     * 기능: 대나무숲 포스트 리스트
     * 
     * developer: 윤수민
     * 
     * @param : 
     * 
     * @return : message,
     * bambooList(bamboo_title, bamboo_description, bamboo_image,
     * bamboo_iframe, bamboo_header, writer_nickname)
     */
    @GetMapping("/getAllList")
    public ResponseEntity<Map<String, Object>> getPostByList() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        logger.info("bamboo/getAllList 호출성공");
        try {
            List<Map<String, Object>> bambooList = bambooService.getAllList();
            resultMap.put("bambooList", bambooList);
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            logger.error("실패", e);
            resultMap.put("message", FAIL);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}