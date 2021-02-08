import http from './http';

//구독 보드리스트
export function getSubBoard(user_id) {
  return http.get(`account/user/getSubBoards/${user_id}`);
}

//내정보 조회
export function getMyInfo(userId) {
  return http.get(`/account/user/${userId}`);
}
