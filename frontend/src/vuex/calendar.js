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
    detailDialog: false,
    deadlines: [],
  },
  mutations: {
    //서버에서 응답한 보드에 관련된 일정 리스트를 vuex에 설정
    SET_CALENDAR_LIST(state, payload) {
      state.events = [...payload];
    },

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

    //임박한 일정 값 설정
    SET_DEADLINE_LIST(state, payload) {
      state.deadlines = payload;
    },
  },

  actions: {
    //캘린더 리스트 요청
    async actGetEvents({ commit }, boardName) {
      try {
        const response = await calendarApi.getCalendar(getBoardId(boardName));
        if (response.data.message === 'SUCCESS') {
          // console.log(response.data.calendars);
          let payload = getEventsTransfer(response.data.calendars);
          commit('SET_CALENDAR_LIST', payload);
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
        const board_id = getBoardId(payload.boardName);
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

    //임박한 일정 리스트 요청(마감일 7일 내 일정)
    async actGetDeadline({ commit }) {
      try {
        const response = await calendarApi.getDeadline();
        // console.log(response);
        if (response.data.message === 'SUCCESS') {
          const payload = getEventsTransfer(response.data.calendarList);
          commit('SET_DEADLINE_LIST', payload);
        }
      } catch (error) {
        console.log(error);
        alert('임박한 채용 리스트를 불러오는 중에 문제가 발생했습니다.');
      }
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

const colors = [
  '#70ae98',
  '#ecbe7a',
  '#e58b88',
  '#9dabdd',
  '#bc85a3',
  '#909090',
  '#34568B',
  '#88B04B',
  '#F7CAC9',
  '#955251',
  '#009B77',
  '#B565A7',
  '#DFCFBE',
  '#282D3C',
  '#944743',
  '#7F4145',
  '#766F57',
];

const getTime = (hasTime, time) => {
  return !hasTime ? '' : ` ${time}:00`;
};
const makeEvent = (event) => {
  return {
    name: event.title,
    content: event.content,
    start: event.startDate + getTime(event.startTime),
    end: event.endDate + getTime(event.endTime),
    color: colors[Math.floor(Math.random() * 17)],
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

const timeChecker = (start, end) => {
  const starts = start.split(' ');
  const ends = end.split(' ');
  if (starts[1] == ends[1]) {
    return {
      start: starts[0],
      end: ends[0],
    };
  }
  return false;
};

const getEventsTransfer = (arr) => {
  let payload = [];
  arr.forEach((el) => {
    let transfer = {
      id: el.calendar_item_id,
      name: el.calendar_item_title,
      content: el.calendar_item_description,
      start: el.calendar_item_datetime_start,
      end: el.calendar_item_datetime_end,
      color: colors[Math.floor(Math.random() * 17)],
    };
    const check = timeChecker(transfer.start, transfer.end);
    if (check) {
      transfer.start = check.start;
      transfer.end = check.end;
    }
    payload.push(transfer);
  });
  return payload;
};
