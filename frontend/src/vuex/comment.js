
export default {
  namespaced: true,
  state: {
    commentList:[],
    writeFlag:false
  },

  mutations: {

    // response에서 받아오는 부분
    GET_COMMENTLIST(state,commentList){
      state.commentList = commentList
    },
    


    // user의 입력으로 인해 변하는 부분
    IS_WRITE_FLAG(state){
      console.log('post 작성후 플래그 변화')
      state.writeFlag = !state.writeFlag
    },
  },

  actions: {
    // response에서 받아오면 처리할 부분.
    getCommentList({commit},commentList){
      commit('GET_COMMENTLIST',commentList)
    },
    
    //component에서 유저에게 받아오면 처리할 부분
    isWriteFlag({commit}){
      commit('IS_WRITE_FLAG')
    },
    
  },
};
