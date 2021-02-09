import http from './http';

//구독 보드리스트
export function getSubBoard(user_id) {
  return http.get(`account/user/getSubBoards/${user_id}`);
}

//내정보 조회
export function getMyInfo(userId) {
  return http.get(`/account/user/${userId}`);
}

//내정보 수정
export function putMyInfo(member) {
  return http.put('/account/user', member);
}

//구독보드 즐겨찾기 토글
export function putFavorite(payload) {
  return http.put('/account/user/favorite', payload);
}

export function putDeleteSub(payload) {
  return http.put(`/account/user/deleteSub?board_id=${payload.board_id}&user_id=${payload.user_id}`);
}
