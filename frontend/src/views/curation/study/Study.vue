<template>
  <v-app class="main-bg-color">
    <v-main class="grey lighten-3">
      <v-container
        class="pt-8"
      >
        <!-- PC에서 보여줄 curation이름과 검색 -->
        <v-row 
          v-if="!ResponsiveSize.isMobile"
          no-gutters 
          dense
          class="d-flex 
            flex-row 
            justify-space-between"
        >
          <!-- 페이지 이름 -->
          <div 
            class="text-overline  text-weight-black page-title"
            style="font-size: 20px !important;"
            @click="goMainStudy()"
          >Curation</div>
          <!-- 검색관련 부분 -->
          <div 
            class="d-flex 
              flex-row 
              justify-flex-end"
          >
            <!-- 검색바 -->
            <v-text-field
              placeholder="검색"
              solo
              v-model="searchKeyword"
            ></v-text-field>
          </div>
        </v-row>
        <v-row dense>
          
          <!-- 왼쪽 스터디 설명 부분 -->
          <v-col class="col-12 col-sm-3">
            <div id="description" class="rounded-bg container-description">
              <div class="board-name-detail">
                <h4 class="b-desc">{{groupDto.board_name}}</h4>
                <p class="l-desc board-detail">
                  {{groupDto.board_description}}
                </p>
              </div>
              <!-- <button class="btn-subscribe b-title" @click="onSubscribe" v-if="!inBoard">가입신청</button>
              <button class="btn-subscribe b-title" @click="onSubscribe" v-if="!inBoard">Subscribe</button>
              <button class="btn-subscribing b-title" @click="onSubscribe" v-if="inBoard">Subscribing</button> -->
              <!---->
              <button class="btn-subscribe b-title" @click="onSubscribe">Subscribe</button>
              <button class="btn-Checking b-title" @click="onSubscribe">Checking</button>
              <button class="btn-subscribing b-title" @click="onSubscribe">Subscribing</button>
              <v-divider class="my-2"></v-divider>
              <v-list color="transparent" v-if="isManager">
                <v-list-item class="mb-3"><a id="scrap-item" v-b-toggle href="#check-collapse" @click.prevent>신청목록<b-icon icon="chevron-down" aria-hidden="true"></b-icon></a></v-list-item>
                <b-collapse id="check-collapse">
                  <div class="p-1" v-for="(group,idx) in myStudyGroup" :key="idx">
                    <CheckList :group="group"/>
                  </div>
                </b-collapse>
              </v-list>
              <v-divider class="my-2"></v-divider>
              <v-list color="transparent">
                <v-list-item><a id="scrap-item" v-b-toggle href="#item-collapse" @click.prevent>내 스터디 목록 <b-icon icon="chevron-down" aria-hidden="true"></b-icon></a></v-list-item>
                <b-collapse visible id="item-collapse">
                  <v-col v-for="(group,idx) in myStudyGroup" :key="idx">
                    <MyStudyGroup :group="group"/>
                  </v-col>
                </b-collapse>
              </v-list>
            </div>

            
          </v-col>
          <!-- 오른쪽 스터디 본문 부분 -->
          <v-col
            class="col-12 col-sm-9"  
          >
            <!-- 달력 추가기능 선택시 들어갈 부분 -->
            <div id="center-post">
              <!-- 캘린더 들어가는 부분 -->
              <StudyCalendarSpan id="study-calendar" class="rounded-bg" />
            </div>
            <CalendarDialog />
            <!-- 스터디 게시글쓰기 -->
            
            <PostWrite class="mt-3" :in-board="inBoard"/>
            <GroupPostList />
            <!-- 스터디 게시물 부분 -->
            <!-- <StudyPost class="mx-4 mb-2"/> -->
            <!-- <StudyPost class="mx-4 mb-2"/> -->
            <!-- <StudyPost class="mx-4 mb-2"/>  -->
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
// 스터디 홍보 게시물
// import StudyPost from "@/components/curation/study/StudyPost.vue"
// 스터디 홍보 게시물 쓰기
// import StudyPostWrite from "@/components/curation/study/StudyPostWrite.vue"
// 스터디 내 그룹
// import StudyGroup from "@/components/curation/study/StudyGroup.vue"
// 캘린더 추가기능 추가시 넣을 부분
import StudyCalendarSpan from '@/components/curation/study/StudyCalendarSpan.vue';
import CalendarDialog from '@/components/etc/CalendarDialog';

import MyStudyGroup from "@/components/curation/study/MyStudyGroup.vue"
import CheckList from "@/components/curation/study/CheckList.vue"
import PostWrite from '@/components/board/PostWrite'
import GroupPostList from "@/components/board/PostList"

import * as studyApi from "@/api/study"
import * as boardApi from '@/api/board';

export default {
  name:'StudyGroupMain',
  components: {
    // StudyPost,
    // StudyPostWrite,
    // StudyGroup,
    // 캘린더 부분
    StudyCalendarSpan,
    CalendarDialog,
    MyStudyGroup,
    CheckList,
    PostWrite,
    GroupPostList,
  },
  watch:{
  },
  created(){
    //왼쪽 스터디 디테일 가져오기
    this.getStudyDetail()
    
    // 왼쪽에 내 스터디 목록 가져오기
    this.getMyStudyGroup()

    // 구독했는 지 파악하기 : inBoard
    this.isInBoard()

    
    
  },
  // 뷰 인스턴스 제거될 때 resize 호출
  beforeDestroy () {
      if (typeof window === 'undefined') return

      window.removeEventListener('resize', this.onResize, { passive: true })
  },
  mounted () {
    // resize 실시해서 현재 화면 크기 확인
    this.onResize()

    window.addEventListener('resize', this.onResize, { passive: true })
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
      
      inBoard:'',
      myStudyGroup: {},
      groupDto:{}
    }
  },
  computed:{
    isManager(){
      const boards = JSON.parse(localStorage.subBoard);
      const BOARD_ID = Number(this.$route.params.board_id);

      if (this.inBoard) {
        const idx = boards.findIndex((board) => board.board_id === BOARD_ID);
        if (boards[idx].user_role == 1) {
          return true;
        }
      }
      return false;
    }
  },
  methods: {
    // 현재 활성화된 기기에 따라 flag 변경
    onResize() {
      this.ResponsiveSize.isMobile = window.innerWidth < 426;
      this.ResponsiveSize.viewSize = window.innerWidth;
    },
    // study main으로 이동
    goMainStudy() {
      this.$router.push({ name: 'StudyMain' });
    },
    getMyStudyGroup(){
      studyApi.getMyGroupList(localStorage.userId)
      .then(res=>{
        const group = res.data.studyList.filter((group) => {
          if(group!=null){
            return group
          }
        })
        this.myStudyGroup = group
      })
      .catch(err=>{
        console.log(err)
      })
    },
    isInBoard(){
      const BOARD_ID = Number(this.$route.params.board_id);
      const boards = JSON.parse(localStorage.subBoard);
      const boardIds = boards.map((e) => {
        return e.board_id;
      });
      this.inBoard = boardIds.includes(BOARD_ID);
    },
    getStudyDetail(){
      boardApi.board_detail(this.$route.params.board_id)
      .then(res=>{
        console.log(res)
        this.groupDto = res.data.boardDto
      })
      .catch(err=>{
        console.log(err)
      })
    },
    // 스터디보드 신청 확인
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
      return;
    },
    
  }
}
</script>

<style scoped>
/* study main 화면 이동 pointer */
.page-title {
  cursor: pointer;
}

.main-bg-color {
  background-color: #ebebe9;
}
.description {
  margin: 2%;
  padding: 10%;
  flex-basis: 20%;
}
.board-detail{
  margin: 8px 0;
  padding: 0 8px;
  min-height: 15vh;
}
.container-description {
  width: 100%;
  margin: 0px 0 20px;
  padding: 10px;
  box-shadow: var(--basic-shadow-w);
}
/* 버튼 */
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
.btn-Checking {
  text-align: center;
  margin: auto;
  height: 50px;
  width: 80%;
  border: none;
  border-radius: 5px;
  color: var(--basic-color-bg2);
  text-shadow: 0 0px 1px var(--basic-color-fill3);
  background: var(--basic-color-fill3) !important;
  /* box-shadow: 10px 10px 20px #bcbcba, -10px -10px 20px #ffffff; */
  transition: 0.3s all ease;
  display: flex;
  justify-content: center;
  align-items: center;
}
.btn-Checking :hover,
.btn-Checking :active {
  background-color: var(--basic-color-bg) !important;
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

#scrap-item{
  text-decoration: none;
  color:#000;
}

/* calendar 내용 */
#study-calendar {
  overflow: hidden;
  width: 100%;
  height: 500px;
  min-height: 400px;
  box-shadow: var(--basic-shadow-w);
}
/* 모바일 웹화면 */
@media (max-width: 426px) {
  #container-box {
    position: relative;
    flex-direction: column;
  }
  #left-info {
    width: 100%;
    margin: 0;
    overflow-y: inherit;
  }
  #type {
    margin-top: 0;
  }
  #center-post {
    width: 100%;
    height: auto;
    overflow-y: inherit;
  }
  #study-calendar {
    margin-top: 0;
  }
}
</style>
