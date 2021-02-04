package com.ssafy.pjt1.model.mapper;

import java.util.List;

import com.ssafy.pjt1.model.dto.post.PostDto;
import com.ssafy.pjt1.model.dto.subscription.SubscriptionDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

    public List<SubscriptionDto> selectFavorite(String user_id);

    public int getSubsriptionNumber(String board_id);

    public List<PostDto> getRecentFive(String board_id);
}
