import http from './http';

export function getSubBoards(user_id) {
  return http.get(`account/user/getSubBoards/${user_id}`);
}