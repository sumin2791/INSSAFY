package com.ssafy.pjt1.model.service.bamboo;

import java.util.List;
import java.util.Map;

import com.ssafy.pjt1.model.dto.bamboo.BambooDto;

public interface BambooService {

	public void createBamboo(BambooDto bambooDto);

	public List<BambooDto> getAllList();

	public List<BambooDto> searchPost(String keyword);

	public int isWriter(Map<String, Object> map);

	public int bambooModify(BambooDto bambooDto);

	public int bambooDelete(int bamboo_id);

}
