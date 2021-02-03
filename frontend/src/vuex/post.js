

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
    }
  },

  actions: {
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
    }
    
  },
};
