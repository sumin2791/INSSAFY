<template>
  <v-app class="main-bg-color">
    <v-main>
      <v-container id="container" class="pt-8">
        <!-- PC에서 보여줄 curation이름과 검색 -->
        <v-row
          id="board-header"
          v-if="!ResponsiveSize.isMobile"
          no-gutters
          class="d-flex 
            flex-row 
            justify-space-between"
        >
          <!-- 페이지 이름 -->
          <div class="text-overline  text-weight-black" style="font-size: 20px !important;">Board</div>
          <!-- 검색관련 부분 -->
          <div
            class="d-flex 
              flex-row 
              justify-flex-end"
          >
            <!-- 검색바 -->
            <v-text-field placeholder="검색" solo v-model="searchKeyword"></v-text-field>
          </div>
        </v-row>

        <v-row>
          <!-- 왼쪽 보드 설명 및 추가 기능 -->
          <v-col class="col-12 col-sm-4">
            <div id="description" class="rounded-bg container-description">
              <v-list color="transparent">
                <div
                  class="d-flex
                  flex-column
                  justify-space-between"
                  style="min-height: 25vh;"
                >
                  <!-- 보드 설명 -->
                  <BoardDescription :in-board="inBoard" :is-manager="isManager" @board-image="boardImage" />
                  <button class="btn-subscribe b-title" @click="onSubscribe" v-if="!inBoard">Subscribe</button>
                  <button class="btn-subscribing b-title" @click="onSubscribe" v-if="inBoard">Subscribing</button>
                </div>
              </v-list>
            </div>
            <CheckList :is-manager="isManager" v-if="isCheck" />
            <VoteList v-if="isVote" />
            <UserRank v-if="isRank" />
          </v-col>
          <v-col class="col-12 col-sm-8">
            <PostWrite :in-board="inBoard" style="margin:0 10px" />     
            <PostList />
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import BoardDescription from '@/components/board/BoardDescription.vue';

import PostWrite from '@/components/board/PostWrite.vue';
import PostList from '@/components/board/PostList.vue';

//board api
import * as boardApi from '@/api/board';

// 스타일 적용
import '@/assets/css/static/style.css';

// 체크리스트
import CheckList from '@/components/addfunc/CheckList';
// 투표
import VoteList from '@/components/addfunc/VoteList';
// 유저랭킹
import UserRank from '@/components/addfunc/UserRank';

export default {
  name: 'Board',
  components: {
    BoardDescription,
    PostList,
    PostWrite,
    CheckList,
    VoteList,
    UserRank,
  },
  // 뷰 인스턴스 제거될 때 resize 호출
  beforeDestroy() {
    if (typeof window === 'undefined') return;

    window.removeEventListener('resize', this.onResize, { passive: true });
  },
  mounted() {
    // resize 실시해서 현재 화면 크기 확인
    this.onResize();

    window.addEventListener('resize', this.onResize, { passive: true });
  },
  data() {
    return {
      // 모바일 화면 체크 mobile화면인지, 사이즈 이용할 값
      ResponsiveSize: {
        isMobile: false,
        viewSize: 0,
      },
      // 검색 키워드
      searchKeyword: '',

      inBoard: '',
      isManager: false,
      boardimg: '',
    };
  },
  created() {
    // this.$store.dispatch('board/IsInBoard',Number(this.$route.params.board_id))
    // 구독했는 지 파악하기 : inBoard
    const BOARD_ID = Number(this.$route.params.board_id);
    const boards = JSON.parse(localStorage.subBoard);
    const boardIds = boards.map((e) => {
      return e.board_id;
    });
    this.inBoard = boardIds.includes(BOARD_ID);

    // 구독했다면 관리자인가? 아님 그냥 유저인가? : isManager
    if (this.inBoard) {
      const idx = boards.findIndex((board) => board.board_id === BOARD_ID);
      if (boards[idx].user_role == 1) {
        this.isManager = true;
      }
    }
    if (this.boardimg != '') {
      console.log(this.boardimg);
      const header = document.querySelector('#board-header');
      header.style.background = `url(${this.boardimg})`;
    }
    // 추가기능 정보 vuex 저장
    this.fetchData();
  },
  computed: {
    // inBoard() {
    //   return this.$store.state.board.inBoard
    // }
    // 추가기능 체크
    isCheck() {
      return this.$store.state.addfunc.isCheckList;
    },
    isCalendar() {
      return this.$store.state.addfunc.isCalendar;
    },
    isVote() {
      return this.$store.state.addfunc.isVote;
    },
    isRank() {
      return this.$store.state.addfunc.isRank;
    },
  },
  methods: {
    boardImage(boardimg) {
      this.boardimg = boardimg;
      if (this.boardimg != null) {
        const header = document.querySelector('#board-header');
        header.style.minHeight = '250px';
        header.style.background = `url(${this.boardimg})`;
      }
    },
    // 현재 활성화된 기기에 따라 flag 변경
    onResize() {
      this.ResponsiveSize.isMobile = window.innerWidth < 426;
      this.ResponsiveSize.viewSize = window.innerWidth;
    },
    onSubscribe() {
      const BOARD_ID = Number(this.$route.params.board_id);

      const boards = JSON.parse(localStorage.subBoard);
      const board = boards.filter((board) => board.board_id === Number(this.$route.params.board_id));

      const params = {
        user_id: String(localStorage.userId),
        board_id: BOARD_ID,
        user_role: 0,
      };
      boardApi
        .subscribe(params)
        .then((res) => {
          console.log(res);
          if (res.data.message === 'fail') {
            return;
          } else {
            this.inBoard = !this.inBoard;

            // localStorage 수정해주는 부분
            if (board.length > 0) {
              // 보드가 있네? 그럼 구독 해지!
              const idx = boards.findIndex((board) => board.board_id === Number(this.$route.params.board_id));
              boards.splice(idx, 1);
            } else {
              // 보드가 없었어. 그러면 바로 구독하면 돼!
              boards.push({
                board_id: Number(this.$route.params.board_id),
                user_id: localStorage.userId,
                favorite_flag: 0,
                write_post_count: 0,
                is_used: 0,
                user_role: 0,
              });
            }
            localStorage.subBoard = JSON.stringify(boards);
            this.$store.commit('auth/setSubBoardRefresh');
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 추가기능 flag 정보 가져오기
    fetchData() {
      boardApi
        .board_detail(this.$route.params.board_id)
        .then((res) => {
          const addfunc = res.data.board_function;
          // console.log(addfunc, '나옴?')
          // 추가기능 여부 갱신
          this.$store.dispatch('addfunc/isUsed', addfunc);
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
};
</script>

<style scoped>
.main-bg-color {
  background-color: #ebebe9;
}
/* 전체 폰트 */
#container {
  font-family: 'Noto Sans KR', sans-serif !important;
}
/* 왼쪽, 오른쪽 섹션 컨테이너 */
.custom-container {
  box-shadow: var(--basic-shadow-s) !important;
  border-radius: 15px !important;
  background-color: var(--basic-color-bg2) !important;
}

/* 구독버튼 */
.container-description {
  width: 100%;
  padding: 10px;
  box-shadow: var(--basic-shadow-w);
}
.btn-subscribe {
  text-align: center;
  margin: auto;
  height: 50px;
  width: 80%;
  border: none;
  border-radius: 5px;
  color: var(--basic-color-bg);
  text-shadow: 0 0px 1px var(--basic-color-fill3);
  background: var(--basic-color-key) !important;
  box-shadow: 10px 10px 20px #bcbcba, -10px -10px 20px #ffffff;
  transition: 0.3s all ease;
  display: flex;
  justify-content: center;
  align-items: center;
}
.btn-subscribe:hover,
.btn-subscribe:active {
  background-color: #ebebe9 !important;
  color: var(--basic-color-key);
}
.btn-subscribing {
  text-align: center;
  margin: auto;
  height: 50px;
  width: 80%;
  border: none;
  border-radius: 5px;
  color: var(--basic-color-key);
  text-shadow: 0 0px 1px var(--basic-color-fill3);
  background: var(--basic-color-bg2) !important;
  /* box-shadow: 10px 10px 20px #bcbcba, -10px -10px 20px #ffffff; */
  transition: 0.3s all ease;
  display: flex;
  justify-content: center;
  align-items: center;
}
.btn-subscribing:hover,
.btn-subscribing:active {
  background-color: var(--basic-color-bg) !important;
  color: var(--basic-color-key);
}
#custom-container {
  background-color: var(--basic-color-bg2) !important;
}
</style>
