package com.ssafy.pjt1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt1.model.dto.board.BoardDto;
import com.ssafy.pjt1.model.service.BoardService;
import com.ssafy.pjt1.model.service.post.PostService;
import com.ssafy.pjt1.model.service.study.StudyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/study")
public class StudyController {

    public static final Logger logger = LoggerFactory.getLogger(StudyController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String PERMISSION = "No Permission";

    @Autowired
    private StudyService studyService;

    @Autowired
    private PostService postService;

    /*
     * 기능: 스터디 모집글 리스트
     * 
     * developer: 윤수민
     * 
     * @param : page, size
     * 
     * @return : message, 
     * postList(post_id,user_id,post_date,post_title,post_description,
     * post_image,post_iframe,post_header,post_state,like_count, comment_count)
     */
    @GetMapping("/getPromoList")
    public ResponseEntity<Map<String, Object>> getPromoList(@RequestParam(value = "page") int page, 
    @RequestParam(value = "size") int size) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        logger.info("post/getPostList 호출성공");
        try {
            int totalCnt = postService.getTotalPostCnt(77);
            if(totalCnt>(page+1)*size) resultMap.put("isLastPage","false");
            else if(totalCnt>page*size) resultMap.put("isLastPage","true");
            else resultMap.put("isLastPage","No data");

            Map<String, Object> map = new HashMap<>();
            map.put("start", page*size);
            map.put("size", size);
            List<Map<String, Object>> postList = studyService.getPromoList(map);
            resultMap.put("postList", postList);
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            logger.error("실패", e);
            resultMap.put("message", FAIL);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
     * 기능: 내 스터디 리스트
     * 
     * developer: 윤수민
     * 
     * @param : login_id
     * 
     * @return : message, studyList(board_name,board_description,board_count)
     * 
     */
    @GetMapping("/getStudyList")
    public ResponseEntity<Map<String, Object>> getStudyList(@RequestParam(value = "login_id") String login_id){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        logger.info("study/getStudyList 호출성공");
        try {
            List<Integer> studyIdList = studyService.getStudyId(login_id); 
            List<Map<String, Object>> studyList = new ArrayList<Map<String, Object>>();
            for (Integer board_id : studyIdList) {
                studyList.add(studyService.getStudyInfo(board_id)); 
            }
            resultMap.put("studyList", studyList);          
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            logger.error("실패", e);
            resultMap.put("message", FAIL);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
     * 기능: 모든 스터디 리스트(생성순)
     * 
     * developer: 윤수민
     * 
     * @param : page, size
     * 
     * @return : message, isLastPage, studyList(board_name,board_description,board_count)
     * 
     */
    @GetMapping("/getAllList")
    public ResponseEntity<Map<String, Object>> getAllList(@RequestParam(value = "page") int page, 
    @RequestParam(value = "size") int size){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        logger.info("study/getAllList 호출성공");
        try {
            int totalCnt = studyService.getTotalCnt();
            if(totalCnt>(page+1)*size){
                resultMap.put("isLastPage","false");
            }else if(totalCnt>page*size){
                resultMap.put("isLastPage","true");
            }else{
                resultMap.put("isLastPage","No data");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("start", page*size);
            map.put("size", size);
            List<Map<String, Object>> studyList = studyService.getAllList(map);
            resultMap.put("studyList", studyList);          
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            logger.error("실패", e);
            resultMap.put("message", FAIL);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
