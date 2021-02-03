package com.ssafy.pjt1.model.service.main;

import java.util.List;

import com.ssafy.pjt1.model.dto.subscription.SubscriptionDto;
import com.ssafy.pjt1.model.mapper.MainMapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    public SqlSession sqlSession;

    @Override
    public List<SubscriptionDto> selectFavorite(String user_id) {
        return sqlSession.getMapper(MainMapper.class).selectFavorite(user_id);
    }
}
