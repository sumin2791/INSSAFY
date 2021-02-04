import axios from 'axios';
import * as boardApi from '../api/board';
import * as userApi from '../api/user';
import store from './store';

//initialized
const INIT_SUB_BOARD = () => {
  return {};
};

export default {
  state: {},
  mutations: {},
  actions: {
    async getSubBoard(context) {
      try {
        // 구독 보드 리스트도 가져오기
        const responseSubBoard = await userApi.getSubBoards(store.auth.user.user_id);
        console.log('구독보드리스트 결과:');
        console.log(responseSubBoard);
        context.commit('SET_SUBSCRIBE_BOARD', responseSubBoard);
      } catch (e) {
        console.log(e);
      }
    },
  },
};
