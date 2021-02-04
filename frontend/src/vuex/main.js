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

export default {
  namespaced: true,
  state: {
    favorites: INIT_FAVORITES(),
  },
  getters: {
    getFavorites(state) {
      return state.favorites;
    },
  },
  actions: {
    async getFavorites(context, { userId }) {
      try {
        const res = await mainApi.getFavorites(userId);
        // console.log(res);
        const favorites = res.data.favorite;
        context.commit('setFavorites', { favorites });
      } catch (e) {
        console.log(e);
      }
    },
  },
  mutations: {
    setFavorites(state, payload) {
      state.favorites = payload;
    },
  },
};
