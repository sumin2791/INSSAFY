import * as calendarApi from '@/api/calendar';

const INIT_EVENT = () => {
  return {
    title: '',
    content: '',
    startDate: '',
    startTime: '',
    endDate: '',
    endTime: '',
    hasTime: false,
  };
};

export default {
  namespaced: true,
  state: {
    event: INIT_EVENT(),
    events: [],
    dialog: false,
  },
  mutations: {
    //dialog 열릴 때, 사용되는 변수(state) 초기화
    OPEN_CALENDAR_DIALOG(state, payload) {
      state.event.startDate = payload.date;
      state.event.startTime = payload.time;
      state.event.hasTime = payload.hasTime;
      state.dialog = true;
    },

    //dialog 닫기
    CLOSE_CALENDAR_DIALOG(state) {
      state.dialog = false;
    },

    //vuex로 일정 관리
    ADD_EVENTS(state, event) {
      state.events.push(event);
      state.dialog = false;
      state.event = INIT_EVENT();
    },
  },
  actions: {
    //캘린더 리스트 요청
    async actGetEvents({ commit }, type) {
      try {
        const response = await calendarApi.getCalendar(getBoardId(type));
        if (response.date.message === 'SUCCESS') {
          console.log(response.data.calendars);
        }
      } catch (error) {
        console.log(error);
        alert('일정 목록을 가져오는 도중 문제가 발생했습니다.');
      }
    },

    //캘린더 일정 등록
    async actAddEvent({ commit }, payload) {
      try {
        //db 컬럼에 맞는 데이터로 변환
        const board_id = getBoardId(payload.type);
        const apiEvent = makeApiEvent(board_id, payload.event);
        const response = await calendarApi.postCalendarCreate(apiEvent);
        if (response.data.message === 'SUCCESS') {
          //db에 event 저장될 경우 vuex에서 활용하는 일정 추가
          const newEvent = makeEvent(payload.event);
          commit('ADD_EVENTS', newEvent);
          return true;
        }
      } catch (error) {
        console.log(error);
        alert('일정 등록 중에 문제가 발생했습니다.');
      }
      return false;
    },
  },
  getters: {},
};

const getBoardId = (type) => {
  switch (type) {
    case '채용일정':
      return 73;
    default:
      break;
  }
};

const colors = ['#70ae98', '#ecbe7a', '#e58b88', '#9dabdd', '#d9effc', '#bc85a3', '#909090'];

const getTime = (hasTime, time) => {
  return !hasTime ? '' : ` ${time}:00`;
};
const makeEvent = (event) => {
  return {
    name: event.title,
    content: event.content,
    start: event.startDate + getTime(event.startTime),
    end: event.endDate + getTime(event.endTime),
    color: colors[Math.floor(Math.random() * 6)],
  };
};

const makeApiEvent = (board_id, event) => {
  return {
    board_id: board_id,
    calendar_item_title: event.title,
    calendar_item_description: event.content,
    calendar_item_datetime_start: `${event.startDate}${getTime(event.hasTime, event.startTime)}`,
    calendar_item_datetime_end: `${event.endDate}${getTime(event.hasTime, event.endTime)}`,
  };
};
