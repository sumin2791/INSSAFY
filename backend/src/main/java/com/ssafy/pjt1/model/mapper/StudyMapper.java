package com.ssafy.pjt1.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudyMapper {

	List<Map<String, Object>> getPromoList();

	List<Integer> getStudyId(String login_id);

	Map<String, Object> getStudyInfo(Integer board_id);

	List<Map<String, Object>> getAllList();

}
