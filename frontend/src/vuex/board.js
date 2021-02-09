import axios from 'axios';

export default {
  namespaced: true,
  state: {
    writeFlag:false,
    boardList:[],
  },
  mutations: {
    IS_WRITE_FLAG(state){
      console.log('post 작성후 플래그 변화')
      state.writeFlag = !state.writeFlag
    },
    GET_BOARD_LIST(state,boardList){
      state.boardList = boardList
    },
  },
  actions: {
    isWriteFlag({commit}){
      commit('IS_WRITE_FLAG')
    },
    getBoardList({commit},boardList){
      commit('GET_BOARD_LIST',boardList)
    }
  },
};
