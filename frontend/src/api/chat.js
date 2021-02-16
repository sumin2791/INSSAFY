import http from './http';

// 채팅방 만들기
export function createChatRoom(params) {
  return http.post('/chat/checkRoomDupl', {}, {params})
}
// 채팅리스트 가져오기(대화상대 목록)
export function getChatList(params) {
  return http.post('/chat/getRoomList', {}, {params})
}
// 채팅방 내 메세지 가져오기
export function getMessages(params) {
  return http.post('/chat/enterRoom', {}, {params})
}