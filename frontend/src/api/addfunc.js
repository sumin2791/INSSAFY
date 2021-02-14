import http from './http';

// 보드에 달린 추가기능 수정 및 삭제
export function modifyAddFunction(params) {
  return http.put('/board/modifyFunction', {}, {params})
}
// 체크리스트 생성
export function checkListCreate(params) {
  return http.post('/CheckList/create', params)
}
// 체크리스트 목록 조회
export function getCheckList(board_id) {
  return http.get(`/CheckList/read/${board_id}`)
}
// 체크리스트 삭제 - put은 1, 2, 3(위치인자) 필수
export function deleteCheckList(params) {
  return http.put('/CheckList/delete', {}, {params})
}
// 체크리스트 수정
export function modifyCheckList(params) {
  return http.put('/CheckList/update', params)
}