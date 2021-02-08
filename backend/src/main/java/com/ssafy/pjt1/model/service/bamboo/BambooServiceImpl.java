package com.ssafy.pjt1.model.service.bamboo;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<BambooDto> getAllList() {
        return sqlSession.getMapper(BambooMapper.class).getAllList();
    }

    @Override
    public List<BambooDto> searchPost(String keyword) {
        return sqlSession.getMapper(BambooMapper.class).searchPost(keyword);
    }

    @Override
    public int isWriter(Map<String, Object> map) {
        return sqlSession.getMapper(BambooMapper.class).isWriter(map);
    }

    @Override
    public int bambooModify(BambooDto bambooDto) {
        return sqlSession.getMapper(BambooMapper.class).bambooModify(bambooDto);
    }

    @Override
    public int bambooDelete(int bamboo_id) {
        return sqlSession.getMapper(BambooMapper.class).bambooDelete(bamboo_id);
    }
    
}
