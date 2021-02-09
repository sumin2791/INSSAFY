import axios from 'axios';
import * as userApi from '../api/user';

//initialized

export default {
  namespaced: true,
  state: {
    //스크랩
  },

  mutations: {},
  actions: {
    //즐겨찾기 토글
    async actPutFavorite({ commit }, payload) {
      try {
        const response = await userApi.putFavorite(payload);
        // console.log(response);
      } catch (error) {
        console.log(error);
        alert('즐겨찾기 수정 작업 중 문제가 발생했습니다.');
      }
    },
    //보드 구독 취소
    async putDeleteSub({ commit }, payload) {
      try {
        const response = await userApi.putDeleteSub(payload);
        // console.log(response);
        if (response.data.message == 'SUCCESS') {
          return true;
        }
      } catch (error) {
        console.log(error);
        alert('구독 취소 작업 중 문제가 발생했습니다.');
      }
      return false;
    },
  },
  getters: {},
};
