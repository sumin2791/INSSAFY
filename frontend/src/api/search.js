import http from './http';

export function getSearchAllBoard(payload) {
  return http.get(
    `/board/searchBoard?keyword=${payload.keyword}&page=${payload.page}&size=${payload.size}&sort=${payload.sort}&type=${payload.type}`
  );
}
