import * as searchApi from '@/api/search';
import store from './store';
//api로 넘길 payload 형태 객체 초기화
const INIT_PAYLOAD = () => {
  return {
    keyword: '',
    page: 0,
    size: 12,
    sort: 'new',
    type: '',
  };
};

//target(검색 주체) 초기화
const INIT_HEADER = () => {
  return {
    allBoard: '모든 보드에서 검색',
    allPost: '모든 게시글에서 검색',
    board: '보드 내 서브 보드 검색',
    post: '보드 내 게시글 검색',
  };
};
//type(이름, 내용, 지역, 해시 등) 초기화
const INIT_BOARD_TYPE = () => {
  return {
    allBoard: {
      key: ['보드명', '작성글', '지역', '해시태그'],
      value: ['name', 'description', 'location', 'hash'],
    },
    allPost: {
      key: ['머리말', '글 제목', '글 내용'],
      value: ['header', 'title', 'desc'],
    },
  };
};

const INIT_SORT = () => {
  return {
    key: ['최신순', '인기순'],
    value: ['new', 'hot'],
  };
};

export default {
  namespaced: true,
  //--------------------------------
  //------------ state -------------
  //--------------------------------
  state: {
    active: 'allBoard',

    //검색 request 매개변수
    payload: INIT_PAYLOAD(),

    //검색바 상태
    header: INIT_HEADER(),
    //검색 type
    type: INIT_BOARD_TYPE(),
    //검색 정렬
    sort: INIT_SORT(),

    //검색 결과
    searchList: [],

    //검색 버튼 클릭 이벤트 발생 지점
    epicenter: '',
  },

  //--------------------------------
  //---------- mutations -----------
  //--------------------------------
  mutations: {
    //검색 토스트 열릴 때 상태 관리
    SET_SEARCH_ACTIVE(state, payload) {
      state.active = payload.active;
      state.epicenter = payload.epicenter;
    },

    //검색 토스트 내에서 sort 변경
    SET_PAYLOAD_SORT(state, sortValue) {
      state.payload.sort = sortValue;
    },

    //payload 적용
    SET_PAYLOAD(state, payload) {
      state.payload.keyword = payload.keyword;
      state.payload.page = payload.page;
      state.payload.type = payload.type;
    },
    //다음 페이지
    SET_NEXT_PAGE(state) {
      state.payload.page = state.payload.page + 1;
    },
    //size 기본값 = 12
    SET_SIZE(state, size) {
      state.payload.size = size;
    },

    //result 적용
    SET_SEARCH_LIST(state, payload) {
      state.searchList = state.searchList.concat(payload);
      console.log(state.searchList);
    },
    CLEAR_SEARCH_LIST(state) {
      state.searchList = [];
    },
  },
  //--------------------------------
  //---------- actions -------------
  //--------------------------------
  actions: {
    //모든 보드 검색
    async actSearchAllBoard({ commit, state }) {
      try {
        const response = await searchApi.getSearchAllBoard(state.payload);
        if (response.data.message === 'success') {
          console.log(response);
          commit('SET_SEARCH_LIST', response.data.boardList);
          if (response.data.isLastPage === 'No data') {
            return false;
          }
          return true;
        }
      } catch (error) {
        console.log(error);
        alert('검색 도중에 문제가 발생했습니다.');
      }
      return false;
    },

    //모든 포스트 검색
  },

  //--------------------------------
  //---------- getters -------------
  //--------------------------------
  getters: {
    //활성화된 target 정보 반환
    getHeader(state) {
      return state.header[state.active];
    },
    getType(state) {
      return state.type[state.active];
    },
    getSort(state) {
      return state.payload.sort;
    },
  },
};
