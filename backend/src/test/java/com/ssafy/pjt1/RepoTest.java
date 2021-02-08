package com.ssafy.pjt1;

import java.util.Map;

import com.ssafy.pjt1.model.mapper.BoardMapper;
import com.ssafy.pjt1.model.mapper.MainMapper;
import com.ssafy.pjt1.model.service.BoardService;
import com.ssafy.pjt1.model.service.comment.CommentService;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepoTest {

    static final Logger log = LoggerFactory.getLogger(RepoTest.class);

    @Autowired
    MainMapper mainRepo;

    @Autowired
    private BoardMapper boardRepo;

    @Autowired
    private CommentService commentService;

    @Test
    public void selectTest() {
        int res = mainRepo.getSubsriptionNumber("10");

        log.info("결과값:{}", res);
    }

    @Test
    public void selectBoard() {
        Map<String, String> map = boardRepo.getBoardInfo("21");

        log.info("결과값:{}", map.get("board_id"));
    }

    @Test
    public void commentSerivce() {
        String id = commentService.getPostIdByCommentId(11);

        log.info("결과값:{}", id);
    }
}
