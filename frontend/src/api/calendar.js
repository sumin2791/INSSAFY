import http from './http';

//보드와 일치하는 일정 리스트 모두 불러옴
export function getCalendar(board_id) {
  return http.get(`/calendar/read/${board_id}`);
}

// 일정 추가 REST API
export function postCalendarCreate(payload) {
  return http.post('/calendar/create', payload);
}

//임박한 일정 리스트 (7일 내)
export function getDeadline() {
  return http.get('/calendar/deadline');
}
