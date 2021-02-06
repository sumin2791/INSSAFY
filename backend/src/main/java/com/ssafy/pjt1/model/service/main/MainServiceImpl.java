package com.ssafy.pjt1.model.service.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.pjt1.model.dto.post.PostDto;
import com.ssafy.pjt1.model.dto.subscription.SubscriptionDto;
import com.ssafy.pjt1.model.mapper.MainMapper;
import com.ssafy.pjt1.model.service.BoardService;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {

    private static final Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private BoardService boardService;

    @Override
    public List<SubscriptionDto> selectFavorite(String user_id) {
        return sqlSession.getMapper(MainMapper.class).selectFavorite(user_id);
    }

    @Scheduled(fixedDelay = 60000) // 1분 마다 캐시 갱신
    @Override
    public void updateSubscriptionCache() {
        logger.info("boardFollowRank 캐시 업데이트");
        String key = "follow";
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        // follow캐시에서 top3 뽑기
        Set<String> set = zset.reverseRange(key, 0, 2);// board_id 얻어옴
        Map<Map<String, String>, List<PostDto>> resultMap = new HashMap<>();
        for (String board_id : set) {
            // db조회 객체 top3 얻음
            resultMap.put(boardService.getBoardInfo(board_id),
                    sqlSession.getMapper(MainMapper.class).getRecentFive(board_id));
        }
        // boardFollowRank캐시에 넣기
        ObjectMapper mapper = new ObjectMapper();// jackson 라이브러리
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        try {
            String msg = mapper.writeValueAsString(resultMap);
            // logger.info("캐시:{}", msg);
            valueOps.set("boardFollowRank", msg);
            // logger.info(valueOps.get("followRank"));
        } catch (JsonProcessingException e) {
            logger.error("msg", e);
        }
    }

    // @Scheduled(fixedDelay = 60000) // 10초 마다 캐시 갱신
    @Override
    public void updateCommentCache() {

    }

    @Override
    public int getSubsriptionNumber(String board_id) {
        return sqlSession.getMapper(MainMapper.class).getSubsriptionNumber(board_id);
    }

    @Scheduled(fixedDelay = 60000) // 1분 마다 캐시 갱신
    @Override
    public void updatePostSort() {
        logger.info("boardPostRank 캐시 업데이트");
        String key = "postSort";
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        // postSort캐시에서 top3 뽑기
        Set<String> set = zset.reverseRange(key, 0, 2);// board_id 얻어옴
        Map<Map<String, String>, List<PostDto>> resultMap = new HashMap<>();
        for (String board_id : set) {
            // db조회 객체 top3 얻음
            resultMap.put(boardService.getBoardInfo(board_id),
                    sqlSession.getMapper(MainMapper.class).getRecentFive(board_id));
        }
        // boardPostRank캐시에 넣기
        ObjectMapper mapper = new ObjectMapper();
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        try {
            String msg = mapper.writeValueAsString(resultMap);
            // logger.info("캐시:{}", msg);
            valueOps.set("boardPostRank", msg);
        } catch (JsonProcessingException e) {
            logger.error("msg", e);
        }
    }
}
