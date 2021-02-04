import http from './http';

export function getSubBoards(user_id) {
  return http.get(`account/user/getSubBoards/${user_id}`);
}

export function getMyPage(user_id){
  return http.get(`account/user/${user_id}`);
}

export function getSubBoard(user_id){
  return http.get(`account/user/getSubBoards/${user_id}`);
}