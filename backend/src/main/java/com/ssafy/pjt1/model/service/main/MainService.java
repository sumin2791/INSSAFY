package com.ssafy.pjt1.model.service.main;

import java.util.List;

import com.ssafy.pjt1.model.dto.subscription.SubscriptionDto;

public interface MainService {

    public List<com.ssafy.pjt1.controller.SubscriptionDto> selectFavorite(String user_id);
}
