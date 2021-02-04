import axios from 'axios';
import * as boardApi from '../api/board';

export default {
  namespaced: true,
  state: {
    writeFlag:false
  },
  mutations: {
    IS_WRITE_FLAG(state){
      console.log('post 작성후 플래그 변화')
      state.writeFlag = !state.writeFlag
    }
  },
  actions: {
    isWriteFlag({commit}){
      commit('IS_WRITE_FLAG')
    }
  },
};
