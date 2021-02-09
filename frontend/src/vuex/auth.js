import axios from 'axios';
import * as authApi from '../api/auth';
import * as userApi from '../api/user';

// const enhanceAccessUser = () => {
//   const { user } = localStorage;
//   if (!user) {
//     return (axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`);
//   }
// };
// enhanceAccessUser();

//initialized
const INIT_USER = () => {
  return {
    token: localStorage.getItem('token'),
    userId: localStorage.getItem('userId'),
    email: localStorage.getItem('email'),
    nickname: localStorage.getItem('nickname'),
    generation: localStorage.getItem('generation'),
    image: localStorage.getItem('image'),
    location: localStorage.getItem('location'),
  };
};
const INIT_SUB_BOARD = () => {
  const getSubBoard = localStorage.subBoard;
  if (!getSubBoard) return null;
  const subBoard = JSON.parse(localStorage.subBoard);
  let result = [];
  subBoard.forEach((element) => {
    result.push({
      board_id: element.board_id,
      favorite_flag: element.favorite_flag,
      is_used: element.is_used,
      user_id: element.user_id,
      user_role: element.user_role,
      write_post_count: element.write_post_count,
    });
  });
  return result;
};

export default {
  namespaced: true,
  state: {
    user: INIT_USER(),
    subBoard: INIT_SUB_BOARD(),
  },

  mutations: {
    setToken(state, token) {
      state.user.token = token;
    },
    setId(state, id) {
      state.user.userId = id;
    },
    setNickname(state, nickname) {
      state.user.nickname = nickname;
    },
    setEmail(state, email) {
      state.user.email = email;
    },
    //로그인 시, 유저정보를 payload 형태로 set하고 localStorage에 저장
    setUser(state, payload) {
      state.user = payload;
      //로컬 저장
      localStorage.token = state.user.token;
      localStorage.userId = state.user.userId;
      localStorage.email = state.user.email;
      localStorage.nickname = state.user.nickname;
      localStorage.generation = state.user.generation;
      localStorage.image = state.user.image;
      localStorage.location = state.user.location;
    },
    //구독목록 가져오기
    SET_SUBSCRIBE_BOARD(state, responseSubBoard) {
      localStorage.subBoard = JSON.stringify(responseSubBoard.data.boards);
      state.subBoard = INIT_SUB_BOARD();
    },
    //구독목록 새로고침,
    setSubBoardRefresh(state) {
      state.subBoard = INIT_SUB_BOARD();
    },

    setLogoutState(state) {
      //로컬 삭제
      localStorage.clear();
      state.user = INIT_USER();
    },
  },
  actions: {
    async login(context, { email, password }) {
      try {
        const response = await authApi.login(email, password);
        // console.log(response);
        //로그인 성공 && 인증 완료
        if (response.data.message === 'SUCCESS') {
          // context.commit('setToken', response.data.auth_token);
          // context.commit('setId', response.data.user.user_id);
          // context.commit('setEmail', response.data.user.user_email);
          // context.commit('setNickname', response.data.user.user_nickname);
          context.commit('setUser', {
            token: response.data.auth_token,
            userId: response.data.user.user_id,
            email: response.data.user.user_email,
            nickname: response.data.user.user_nickname,
            generation: response.data.user.user_generation,
            image: response.data.user.user_image,
            location: response.data.user.user_location,
          });

          //이메일 인증을 완료하지 않은 유저의 경우 email 활용하여 링크생성
        } else if (response.data.message === 'NO_AUTH') {
          context.commit('setEmail', response.data.user.user_email);
        }
        return response;
      } catch (error) {
        alert('이메일 또는 비밀번호 불일치!');
        return error;
      }
    },

    logout({ commit }) {
      // axios.defaults.headers.common['Authorization'] = undefined;
      commit('setLogoutState');
    },

    async getSubBoard(context) {
      try {
        // 구독 보드 리스트도 가져오기
        const responseSubBoard = await userApi.getSubBoard(this.state.auth.user.userId);
        // console.log('구독보드리스트 결과:');
        // console.log(responseSubBoard);
        context.commit('SET_SUBSCRIBE_BOARD', responseSubBoard);
        return responseSubBoard;
      } catch (e) {
        console.log(e);
      }
    },

    async getMyInfo({ commit }) {
      try {
        const responseMyinfo = await userApi.getMyInfo(this.state.auth.user.userId);
        console.log(responseMyinfo);
      } catch (e) {
        console.log(e);
      }
    },
  },
  getters: {
    //구독목록 리스트 가져오기
    getSubBoardList(state) {
      return state.subBoard;
    },
    //구독목록 리스트 중 즐겨찾기한 리스트 가져오기
    getSubBoardFavoriteList: (state) => {
      return state.subBoard.filter((board) => board.favorite_flag == 1);
    },

    //이미지 null이면 기본 링크 반환
    getUserImage(state) {
      const userImage = state.user.image;
      if (userImage == null || userImage == 'null') {
        return null;
      }
      return userImage;
    },
    //기수별 대표 색상 반환
    getGenerationColor(state) {
      const userGeneration = state.user.generation;
      switch (userGeneration) {
        case '1':
          return 'solid 4px #1d80dd';
        case '2':
          return 'solid 4px #fcff55';
        case '3':
          return 'solid 4px #f1a248';
        case '4':
          return 'solid 4px #61d1be';
        default:
          return 'solid 4px #fff';
      }
    },
  },
};
