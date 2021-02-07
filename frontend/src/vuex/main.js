import * as mainApi from '@/api/main';

const INIT_FAVORITES = () => {
  return {
    board_id: -1,
    user_id: '',
    favorite_flag: -1,
    write_post_count: -1,
    user_role: -1,
    is_used: -1,
  };
};

const INIT_FOLLOW_RANK = () => {
  return {
    additionalProp1: {
      board_id: -1,
      post_date: '',
      post_description: '',
      post_header: '',
      post_id: -1,
      post_iframe: '',
      post_image: '',
      post_like: -1,
      post_state: -1,
      post_title: '',
      user_id: '',
    },
    additionalProp2: {},
    additionalProp3: {},
  };
};

export default {
  namespaced: true,
  state: {
    favorites: INIT_FAVORITES(),
    followRank: INIT_FOLLOW_RANK(),
  },
  getters: {
    getFavorites(state) {
      return state.favorites;
    },
    getFollowRank(state) {
      return state.followRank;
    },
  },
  actions: {
    async actFavorites(context, userId) {
      try {
        const res = await mainApi.getFavorites(userId);
        // console.log(res);
        const arr = res.data.favorite;
        context.commit('setFavorites', { arr });
      } catch (e) {
        console.log(e);
      }
    },
    async actFollowRank({ commit }) {
      try {
        const res = await mainApi.getFollowRank();
        console.log(res);
        commit('setFollowRank');
      } catch (e) {
        console.log(e);
      }
    },
  },
  mutations: {
    setFavorites(state, payload) {
      state.favorites = payload.arr;
    },
    setFollowRank(state, payload) {
      // state.followRank = payload.arr;
    },
  },
};
