package com.ssafy.pjt1.model.mapper.redis;

import com.ssafy.pjt1.model.dto.redis.PostNumDto;

import org.springframework.data.repository.CrudRepository;

public interface PostNumRepo extends CrudRepository<PostNumDto, String> {

}
