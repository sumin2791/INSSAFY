import http from './http';

export function getCalendar(board_id) {
  return http.get(`/calendar/read/${board_id}`);
}

// 일정 추가 REST API requestbody 값
// "board_id": 0,
// "calendar_item_title": "string",
// "calendar_item_description": "string",
// "calendar_item_datetime_start": "string",
// "calendar_item_datetime_end": "string",
export function postCalendarCreate(payload) {
  return http.post('/calendar/create', payload);
}
