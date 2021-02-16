import http from './http';

export function getSearchAllBoard(payload) {
  return http.get(`/board/searchBoard`);
}
