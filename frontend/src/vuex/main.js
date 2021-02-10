import * as mainApi from '@/api/main';

const INIT_FAVORITES = () => {
  return {
    board_id: -1,
    board_name: '',
    board_description: '',
    board_image: null,
    board_igmyeong: 0,
    board_location: '',
    board_hash: '',
    board_date: 'YYYY-MM-DD HH:mm:ss',
    user_id: '',
    board_state: 0,
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

  mutations: {
    //즐겨찾기
    setFavorites(state, payload) {
      state.favorites = payload;
    },

    //인기
    setFollowRank(state, payload) {
      // state.followRank = payload.arr;
    },
  },

  actions: {
    //즐겨찾기 목록 요청
    async actFavorites(context, userId) {
      try {
        const res = await mainApi.getFavorites(userId);
        // console.log(res);
        if (res.data.status == 'SUCCESS') {
          context.commit('setFavorites', res.data.favorite);
        }
      } catch (e) {
        console.log(e);
        alert('즐겨찾기 목록을 가져오는 중 문제가 발생했습니다.');
      }
    },

    //인기보드
    //구독자 순 목록 요청
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

  getters: {
    //즐겨찾기
    getFavorites(state) {
      return state.favorites;
    },

    //인기보드
    //구독자 순(팔로우 순)
    getFollowRank(state) {
      return state.followRank;
    },
  },
};
