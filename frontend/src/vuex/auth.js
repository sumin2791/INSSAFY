import axios from 'axios';
import * as authApi from '../api/auth';
import * as userApi from '../api/user';
import { router } from '../main';

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
    setLogoutState(state) {
      //로컬 삭제
      localStorage.clear();
      state.user = INIT_USER();
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

    updateUser(state, payload) {
      state.user.nickname = payload.user_nickname;
      state.user.generation = payload.user_generation;
      state.user.image = payload.user_image;
      state.user.location = payload.user_location;
      localStorage.nickname = state.user.nickname;
      localStorage.generation = state.user.generation;
      localStorage.image = state.user.image;
      localStorage.location = state.user.location;
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

    async logout({ commit }) {
      // axios.defaults.headers.common['Authorization'] = undefined;
      try {
        commit('setLogoutState');
        router.push({ name: 'Login' });
      } catch (error) {
        console.log(error);
      }
    },

    //구독보드 가져오기
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
        return null;
      }
    },

    //내정보 가져오기
    async getMyInfo({ commit }) {
      try {
        const responseMyinfo = await userApi.getMyInfo(this.state.auth.user.userId);
        // console.log(responseMyinfo);
        if (responseMyinfo.data.message == 'SUCCESS') {
          commit('updateUser', responseMyinfo.data.user);
        } else {
          alert('내 정보를 불러올 수 없습니다.');
        }
      } catch (e) {
        console.log(e);
        alert('내 정보를 불러오는 도중 오류가 발생했습니다.');
      }
    },

    //내정보 수정
    async putMyinfo({ commit }, member) {
      try {
        const response = await userApi.putMyInfo(member);
        console.log(response);
        if (response.data.message == 'SUCCESS') {
          commit('updateUser', member);
          return true;
        } else {
          return false;
        }
      } catch (e) {
        console.log(e);
        return false;
      }
    },

    //비밀번호 이중체크
    async postPassword({ commit, state }, password) {
      try {
        const payloard = {
          user_id: state.user.userId,
          user_password: password,
        };
        const response = await authApi.postPassword(payloard);
        if (response.data.message == 'SUCCESS') {
          return true;
        }
      } catch (error) {
        console.log(error);
      }
      return false;
    },
    //비밀번호 수정
    async putPassword({ dispatch, state }, password) {
      const passwordConfirmPass = await dispatch('postPassword', password[0]);
      try {
        if (passwordConfirmPass) {
          const payloard = {
            user_email: state.user.email,
            user_password: password[1],
          };
          console.log(payloard);
          const response = await authApi.putPassword(payloard);
          if (response.data.message == 'SUCCESS') {
            alert('비밀번호가 변경되었습니다.\n다시 로그인해주세요.');
            dispatch('logout');
          }
        } else {
          alert('기존 비밀번호를 잘못 입력하였습니다.\n확인 후 다시 시도해 주십시오.');
          return false;
        }
      } catch (error) {
        console.log(error);
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
    // getSubBoardIndex: (state) => (id) => {
    //   let index = 0;
    //   state.subBoard.forEach((board) => {
    //     if (board.baord_id == id) {
    //       return this.index;
    //     } else {
    //       index++;
    //     }
    //     return -1;
    //   });
    //   return index;
    // },

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
      switch (state.user.generation) {
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
    getsMyInfo(state) {
      return state.user;
    },
  },
};