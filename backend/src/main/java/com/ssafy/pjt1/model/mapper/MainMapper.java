package com.ssafy.pjt1.model.mapper;

import java.util.List;

import com.ssafy.pjt1.model.dto.subscription.SubscriptionDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

    public List<com.ssafy.pjt1.controller.SubscriptionDto> selectFavorite(String user_id);
}
