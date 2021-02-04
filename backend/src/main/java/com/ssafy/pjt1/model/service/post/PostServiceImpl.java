package com.ssafy.pjt1.model.service.post;

import java.util.List;
import java.util.Map;

import com.ssafy.pjt1.model.dto.comment.CommentDto;
import com.ssafy.pjt1.model.dto.post.PostDto;
import com.ssafy.pjt1.model.mapper.PostMapper;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private SqlSession sqlSession;

	public static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

	@Override
	public void createPost(PostDto postDto) {
		sqlSession.getMapper(PostMapper.class).createPost(postDto);
	}

	@Override
	public PostDto getPostById(int post_id) {
		return sqlSession.getMapper(PostMapper.class).getPostById(post_id);
	}

	@Override
	public int postModify(PostDto postDto) {
		return sqlSession.getMapper(PostMapper.class).postModify(postDto);
	}
	
	@Override
	public int stateModify(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).stateModify(map);
	}

	@Override
	public int postDelete(int post_id) {
		return sqlSession.getMapper(PostMapper.class).postDelete(post_id);
	}

	@Override
	public int isScrapped(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).isScrapped(map);
	}

	@Override
	public void scrap(Map<String, Object> map) {
		sqlSession.getMapper(PostMapper.class).scrap(map);
	}

	@Override
	public void deleteScrap(Map<String, Object> map) {
		sqlSession.getMapper(PostMapper.class).deleteScrap(map);
	}

	@Override
	public int isUnScrapped(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).isUnScrapped(map);
	}

	@Override
	public void updateScrap(Map<String, Object> map) {
		sqlSession.getMapper(PostMapper.class).updateScrap(map);
	}

	@Override
	public int isLiked(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).isLiked(map);
	}

	@Override
	public void like(Map<String, Object> map) {
		sqlSession.getMapper(PostMapper.class).like(map);
	}

	@Override
	public int isUnLiked(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).isUnLiked(map);
	}

	@Override
	public void updateLike(Map<String, Object> map) {
		sqlSession.getMapper(PostMapper.class).updateLike(map);

	}

	@Override
	public void plusCount(int post_id) {
		sqlSession.getMapper(PostMapper.class).plusCount(post_id);
	}

	@Override
	public void unlike(Map<String, Object> map) {
		sqlSession.getMapper(PostMapper.class).unlike(map);
	}

	@Override
	public void minusCount(int post_id) {
		sqlSession.getMapper(PostMapper.class).minusCount(post_id);
	}

	@Override
	public int getPostLikeCount(int post_id) {
		return sqlSession.getMapper(PostMapper.class).getPostLikeCount(post_id);
	}

	@Override
	public List<Map<String, Object>> getComment(int post_id) {
		return sqlSession.getMapper(PostMapper.class).getComment(post_id);
	}

	@Override
	public List<Map<String, Object>> getPostList(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).getPostList(map);
	}

	@Override
	public List<PostDto> searchPostNew(String keyword) {
		return sqlSession.getMapper(PostMapper.class).searchPostNew(keyword);
	}

	@Override
	public List<PostDto> searchPostPopular(String keyword) {
		return sqlSession.getMapper(PostMapper.class).searchPostPopular(keyword);
	}

	@Override
	public List<PostDto> boardPostNew(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).boardPostNew(map);
	}

	@Override
	public List<PostDto> boardPostPopular(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).boardPostPopular(map);
	}

	@Override
	public void deleteScrapAll(int post_id) {
		sqlSession.getMapper(PostMapper.class).deleteScrapAll(post_id);
	}

	@Override
	public void deleteLikeAll(int post_id) {
		sqlSession.getMapper(PostMapper.class).deleteLikeAll(post_id);
	}

	@Override
	public void deleteCommentAll(int post_id) {
		sqlSession.getMapper(PostMapper.class).deleteCommentAll(post_id);
	}

	@Override
	public List<Map<String, Object>> getSalesList(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).getSalesList(map);
	}

	@Override
	public List<PostDto> marketPostNew(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).marketPostNew(map);
	}

	@Override
	public List<PostDto> marketPostPopular(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).marketPostPopular(map);
	}

	@Override
	public String getWriterName(String user_id) {
		return sqlSession.getMapper(PostMapper.class).getWriterName(user_id);
	}

	@Override
	public int isWriter(Map<String, Object> map) {
		return sqlSession.getMapper(PostMapper.class).isWriter(map);
	}

}
