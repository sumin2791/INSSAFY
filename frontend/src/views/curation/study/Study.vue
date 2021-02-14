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
              <h4 class="b-desc">{{groupDto.board_name}}</h4>
              <p class="l-desc">
                {{groupDto.board_description}}
              </p>
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
    }
    
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
.container-description {
  width: 100%;
  margin: 0px 0 20px;
  padding: 10px;
  box-shadow: var(--basic-shadow-w);
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
