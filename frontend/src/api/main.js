import http from './http';

export function getFavorites(user_id) {
  return http.get(`/main/selectFavorite/${user_id}`);
}

export function getFollowRank() {
  return http.get('/main/getFollowRank');
}
