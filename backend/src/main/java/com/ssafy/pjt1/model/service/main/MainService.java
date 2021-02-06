package com.ssafy.pjt1.model.service.main;

import java.util.List;

import com.ssafy.pjt1.model.dto.subscription.SubscriptionDto;

public interface MainService {

    public List<SubscriptionDto> selectFavorite(String user_id);

    public void updateSubscriptionCache();

    public int getSubsriptionNumber(String board_id);

    public void updatePostSort();

    public void updatePostLikeSort();
}
