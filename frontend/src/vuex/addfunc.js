export default {
  namespaced: true,
  state: {
    // 추가기능 체크 flag
    isCheckList: false,
    isCalendar: false,
    isVote: false,
    isRank: false,

    flagWrite:false,
  },
  getters: {

  },
  mutations: {
    // response에서 받아오는 부분
    IS_USED(state, data) {
      state.isCheckList = data.checklist_flag
      state.isCalendar = data.calendar_flag
      state.isVote = data.vote_flag
      state.isRank = data.user_rank_flag
    },
    FLAG_WRITE(state){
      state.flagWrite = !state.flagWrite
    }
  },
  actions: {
    isUsed({commit}, addfunc) {
      commit('IS_USED', addfunc)
    },
    flagWrite({commit}){
      commit('FLAG_WRITE')
    }
  },
}