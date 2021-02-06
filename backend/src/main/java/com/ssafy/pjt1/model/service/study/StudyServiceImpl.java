package com.ssafy.pjt1.model.service.study;

import java.util.List;
import java.util.Map;

import com.ssafy.pjt1.model.mapper.StudyMapper;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyServiceImpl implements StudyService {

	@Autowired
	private SqlSession sqlSession;

	public static final Logger logger = LoggerFactory.getLogger(StudyServiceImpl.class);

	@Override
	public List<Map<String, Object>> getPromoList() {
		return sqlSession.getMapper(StudyMapper.class).getPromoList();
	}

	@Override
	public List<Integer> getStudyId(String login_id) {
		return sqlSession.getMapper(StudyMapper.class).getStudyId(login_id);
	}

	@Override
	public Map<String, Object> getStudyInfo(Integer board_id) {
		return sqlSession.getMapper(StudyMapper.class).getStudyInfo(board_id);
	}

	@Override
	public List<Map<String, Object>> getAllList() {
		return sqlSession.getMapper(StudyMapper.class).getAllList();
	}

}
