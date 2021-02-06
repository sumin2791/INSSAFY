package com.ssafy.pjt1.model.service.redis;

import com.ssafy.pjt1.model.dto.post.PostDto;
import com.ssafy.pjt1.model.service.comment.CommentService;
import com.ssafy.pjt1.model.service.post.PostService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    /*
     * 기능: 회원삭제시 관련된 보드 삭제
     * 
     * developer: 문진환
     */
    public void userDelete(String user_id) {
        // 회원 삭제할때
    }

    /*
     * 기능: 구독삭제 시 redis sort set안에 게사판별 follow수 -1 감소
     * 
     * developer: 문진환
     */
    public void boardFollowSortSetDecrease(String board_id) {
        // board_id를 key값으로 follow수 얻어오기
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        String sortkey = "boardFollowSort";
        zset.incrementScore(sortkey, String.valueOf(board_id), -1);
    }

    /*
     * 기능: 보드삭제 시 redis sort set안에 게시판 삭제
     * 
     * developer: 문진환
     */
    public void boardSortSetDelete(String board_id) {
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        String boardFollow = "boardFollowSort";
        String boardPost = "boardPostSort";
        // sort set에서 삭제
        zset.remove(boardFollow, board_id);
        zset.remove(boardPost, board_id);
    }

    /*
     * 기능: post 삭제 시 redis sort set안에 -1 감소
     * 
     * developer: 문진환
     */
    public void boardPostSortSetDecrease(int post_id) {
        // board_id를 key값으로 follow수 얻어오기
        PostDto postDto = postService.getPostById(post_id);
        int board_id = postDto.getBoard_id();
        // post_id를 활용해서 board_id 얻음
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        String sortkey = "boardPostSort";
        zset.incrementScore(sortkey, String.valueOf(board_id), -1);
    }

    /*
     * 기능: 구독누를 시 redis sort set안에 게사판별 follow수 업데이트
     * 
     * developer: 문진환
     */
    public void boardFollowSortSet(String board_id) {
        // board_id를 key값으로 follow수 얻어오기
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        String sortkey = "boardFollowSort";
        zset.incrementScore(sortkey, String.valueOf(board_id), 1);
    }

    /*
     * 기능: post 작성 시 redis sort set안에 게시판별 post수 업데이트
     * 
     * developer: 문진환
     */
    public void boardPostSortSet(String board_id) {
        String sortkey = "boardPostSort";
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        // post개수 받고 1증가해서 저장
        zset.incrementScore(sortkey, String.valueOf(board_id), 1);
    }

    /*
     * 기능: 좋아요 redis sort set 1 증가
     * 
     * developer: 문진환
     */
    public void PostLikeSortSet(int post_id) {
        String sortkey = "postLikeSort";
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        // post개수 받고 1증가해서 저장
        zset.incrementScore(sortkey, String.valueOf(post_id), 1);
    }

    /*
     * 기능: 싫어요 redis sort set 1 감소
     * 
     * developer: 문진환
     */
    public void postLikeDecrease(int post_id) {
        String sortkey = "postLikeSort";
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        zset.incrementScore(sortkey, String.valueOf(post_id), -1);
    }

    /*
     * 기능: 댓글 달시 sort set 1 증가
     * 
     * developer: 문진환
     */
    public void postCommentSortset(int post_id) {
        String sortkey = "postCommentSort";
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        zset.incrementScore(sortkey, String.valueOf(post_id), 1);
    }

    /*
     * 기능: 댓글 삭제 sort set 1 감소
     * 
     * developer: 문진환
     */
    public void postCommentSortsetDecrease(int comment_id) {
        String sortkey = "postCommentSort";
        String post_id = commentService.getPostIdByCommentId(comment_id);
        // logger.info(">>>>>>>>>>>>>>>>post_id:{}", post_id);
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        zset.incrementScore(sortkey, post_id, -1);
    }
}
