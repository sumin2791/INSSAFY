package com.ssafy.pjt1.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.pjt1.model.dto.board.BoardDto;
import com.ssafy.pjt1.model.dto.user.UserDto;

@Mapper
public interface BoardMapper {

	public void createBoard(BoardDto boardDto);

	public void subscribe(Map<String, Object> map);

	public void addFunction(Map<String, Object> map);

	public int isSubscribed(Map<String, Object> map);

	public void unsubscribe(Map<String, Object> map);

	public List<UserDto> searchUser(String keyword);

	public void updateManager(Map<String, Object> map);

	public int modifyBoard(BoardDto boardDto);

	public List<BoardDto> getBoardsNew();

	public List<BoardDto> getBoardsPopular();

	public List<BoardDto> searchBoardNew(String keyword);

	public List<BoardDto> searchBoardPopular(String keyword);

	public int deleteBoard(int board_id);

	public BoardDto detailBoard(int board_id);

	public int isUnSubscribed(Map<String, Object> map);

	public void updateSubscribe(Map<String, Object> map);

	public void deleteBoardAll(int board_id);

	public void deleteSubscription(int board_id);

	public void deletePostAll(int board_id);

	public void deleteCalendar(int board_id);

	public void deleteCheckList(int board_id);

	public void deleteVote(int board_id);

	public List<Integer> getVoteList(int board_id);

	public List<Integer> getPostList(int board_id);

	public int getBoardCount(int board_id);

}
