

export default {
  namespaced: true,
  state: {
    flagLike:false,
    flagScrap:false
  },

  mutations: {
    IS_LIKED(state,data){
      state.flagLike = data
    },
    IS_SCRAPPED(state,data){
      state.flagScrap = data
    },
    POST_LIKE(state){
      state.flagLike = !state.flagLike
    },
    POST_SCRAP(state){
      state.flagScrap = !state.flagScrap
    }
  },

  actions: {
    // response에서 받아오면 처리할 부분.
    isLiked({commit},isLiked){
      if(isLiked===0){
        commit('IS_LIKED',false)
      }else{
        commit('IS_LIKED',true)
      }
    },
    isScrapped({commit},isScrapped){
      if(isScrapped===0){
        commit('IS_SCRAPPED',false)
      }else{
        commit('IS_SCRAPPED',true)
      }
    },

    //component에서 유저에게 받아오면 처리할 부분
    postLike({commit}){
      commit('POST_LIKE')
    },
    postScrap({commit}){
      commit('POST_SCRAP')
    }
    
  },
};
