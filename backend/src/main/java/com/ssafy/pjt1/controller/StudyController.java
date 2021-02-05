package com.ssafy.pjt1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt1.model.dto.board.BoardDto;
import com.ssafy.pjt1.model.service.BoardService;
import com.ssafy.pjt1.model.service.study.StudyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/study")
public class StudyController {

    public static final Logger logger = LoggerFactory.getLogger(StudyController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String PERMISSION = "No Permission";

    @Autowired
    private StudyService studyService;

    /*
     * 기능: 스터디 모집글 리스트
     * 
     * developer: 윤수민
     * 
     * @param : 
     * 
     * @return : message, postList(post_id,user_id,post_date,post_title,post_description,
     * post_image,post_iframe,post_header,post_state,like_count, comment_count)
     */
    @GetMapping("/getPromoList")
    public ResponseEntity<Map<String, Object>> getPromoList(){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        logger.info("post/getPostList 호출성공");
        try {
            List<Map<String, Object>> postList = studyService.getPromoList(); 
            resultMap.put("postList", postList);          
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            logger.error("실패", e);
            resultMap.put("message", FAIL);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
