import axios from 'axios';
import * as boardApi from '../api/board';

export default {
  namespaced: true,
  state: {
    writeFlag:false
  },
  mutations: {
    IS_WRITE_FLAG(state){
      console.log('mutations 까지 오나요?')
      console.log(state.writeFlag)
      state.writeFlag = !state.writeFlag
      console.log(state.writeFlag)
    }
  },
  actions: {
    isWriteFlag({commit}){
      commit('IS_WRITE_FLAG')
      console.log('액션까지 오나요?')
    }
  },
};
