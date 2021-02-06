package com.ssafy.pjt1.model.service.bamboo;

import java.util.List;

import com.ssafy.pjt1.model.dto.bamboo.BambooDto;
import com.ssafy.pjt1.model.mapper.BambooMapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BambooServiceImpl implements BambooService {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void createBamboo(BambooDto bambooDto) {
        sqlSession.getMapper(BambooMapper.class).createBamboo(bambooDto);
    }
    
}
