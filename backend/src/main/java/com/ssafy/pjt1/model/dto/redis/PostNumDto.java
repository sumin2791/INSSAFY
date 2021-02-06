package com.ssafy.pjt1.model.dto.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("postNum") // 테이블명으로 이해
public class PostNumDto {
    @Id
    String board_id;
    String post_num;

    public String getBoard_id() {
        return this.board_id;
    }

    public void setBoard_id(String board_id) {
        this.board_id = board_id;
    }

    public String getPost_num() {
        return this.post_num;
    }

    public void setPost_num(String post_num) {
        this.post_num = post_num;
    }

    public PostNumDto(String board_id, String post_num) {
        this.board_id = board_id;
        this.post_num = post_num;
    }

}
