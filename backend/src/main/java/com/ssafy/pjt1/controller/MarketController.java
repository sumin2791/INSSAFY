package com.ssafy.pjt1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt1.model.dto.board.BoardDto;
import com.ssafy.pjt1.model.dto.post.PostDto;
import com.ssafy.pjt1.model.dto.user.UserDto;
import com.ssafy.pjt1.model.service.BoardService;
import com.ssafy.pjt1.model.service.post.PostService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/board")
public class MarketController {

    public static final Logger logger = LoggerFactory.getLogger(MarketController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private PostService postService;

    /*
     * 기능: 포스트 리스트 (판매완료 제외)
     * 
     * developer: 윤수민
     * 
     * @param : board_id
     * 
     * @return : message, postList(post_id,user_id,post_date,post_title,post_description,
     * post_image,post_iframe,post_header,post_state,like_count, comment_count)
     */
    @GetMapping("/getPostList")
    public ResponseEntity<Map<String, Object>> getPostByList(@RequestParam(value = "board_id")int board_id){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        logger.info("post/getPostList 호출성공");
        try {
            List<Map<String, Object>> postList = postService.getSalesList(board_id); 
            logger.info("postList: "+postList);
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
     * 기능: 전체 보드 포스트 검색 (최신순, 인기순)
     * 
     * developer: 윤수민
     * 
     * @param : sort, keyword
     * 
     * @return : postList, message
     */
    @GetMapping("/searchPost")
    public ResponseEntity<Map<String, Object>> searchPost(@RequestParam(value = "sort")String sort, 
    @RequestParam(value = "keyword")String keyword){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        logger.info("/post/searchBoard 호출 성공");
        try {
            List<PostDto> postList;
            if(sort.equals("new")){
                logger.info("최신순 포스트 검색");
                postList = postService.searchPostNew(keyword);
            }else{
                logger.info("좋아요순 포스트 검색");
                postList = postService.searchPostPopular(keyword);
            }
            resultMap.put("postList",postList);
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            resultMap.put("message", FAIL);
            logger.error("검색 호출 실패", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /*
     * 기능: 보드 내 포스트 검색 (최신순, 인기순)
     * 
     * developer: 윤수민
     * 
     * @param : sort, keyword, board_id
     * 
     * @return : postList, message
     */
    @GetMapping("/board/searchPost")
    public ResponseEntity<Map<String, Object>> searchBoardPost(@RequestParam(value = "sort")String sort, 
    @RequestParam(value = "keyword")String keyword, @RequestParam(value = "board_id")String board_id){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        logger.info("/post/board/searchBoard 호출 성공");
        try {
            List<PostDto> postList;
            Map<String, Object> map = new HashMap<>();
            map.put("keyword", keyword);
            map.put("board_id", board_id);
            if(sort.equals("new")){
                logger.info("최신순 포스트 검색");
                postList = postService.boardPostNew(map);
            }else{
                logger.info("좋아요순 포스트 검색");
                postList = postService.boardPostPopular(map);
            }
            resultMap.put("postList",postList);
            resultMap.put("message", SUCCESS);
        } catch (Exception e) {
            resultMap.put("message", FAIL);
            logger.error("검색 호출 실패", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}