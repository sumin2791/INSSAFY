import http from './http';

export function getFavorites(user_id) {
  return http.get(`/selectFavorite/${user_id}`);
}

export function getFollowRank() {
  return http.get('/getFollowRank');
}
