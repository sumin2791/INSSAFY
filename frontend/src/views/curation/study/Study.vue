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
          <v-col 
            class="col-12 col-sm-4" 
          >
            <v-sheet>
              <!-- 스터디 설명 부분 -->
              <v-list color="transparent">
                <!-- 스터디 설명 부분 -->
                <div
                  class="d-flex
                  flex-column
                  justify-space-between"
                  style="min-height: 25vh;"
                >
                  <!-- 해당 스터디 타이틀 -->
                  <div class="text-h5">{{ studyInfo.studyTitle }}</div>
                  <div class="text-start pa-1 ma-auto">
                    {{ studyInfo.studyDescription }}
                  </div>
                </div>
                <!-- StudyGroup 부분 -->
                <v-divider class="my-2"></v-divider>
                <v-list>
                  <v-list-item>스터디 목록</v-list-item>
                  <!-- 스터디 목록 활성화 버튼 -->
                  <v-list-item>
                    <v-switch
                      v-model="isMyStudy"
                      inset
                      color="#0B2945"
                      :label="`${state}`"
                      @click="filterMyStudyGroup()"
                    ></v-switch>
                  </v-list-item>
                  <v-col>
                    <StudyGroup />
                    <StudyGroup />
                    <StudyGroup />
                    <StudyGroup />
                  </v-col>
                </v-list>
              </v-list>
            </v-sheet>
          </v-col>
          <!-- 오른쪽 스터디 본문 부분 -->
          <v-col
            class="col-12 col-sm-8"  
          >
            <!-- 달력 추가기능 선택시 들어갈 부분 -->
            <div id="center-post">
              <!-- 캘린더 들어가는 부분 -->
              <StudyCalendarSpan id="study-calendar" class="rounded-bg" />
            </div>
            <CalendarDialog />
            <!-- 스터디 게시글쓰기 -->
            <StudyPostWrite class="mx-4 mb-2"/>
            <!-- 스터디 게시물 부분 -->
            <StudyPost class="mx-4 mb-2"/>
            <StudyPost class="mx-4 mb-2"/>
            <StudyPost class="mx-4 mb-2"/> 
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
// 스터디 홍보 게시물
import StudyPost from "@/components/curation/study/StudyPost.vue"
// 스터디 홍보 게시물 쓰기
import StudyPostWrite from "@/components/curation/study/StudyPostWrite.vue"
// 스터디 내 그룹
import StudyGroup from "@/components/curation/study/StudyGroup.vue"
// 캘린더 추가기능 추가시 넣을 부분
import StudyCalendarSpan from '@/components/curation/study/StudyCalendarSpan.vue';
import CalendarDialog from '@/components/etc/CalendarDialog';

export default {
  name:'LearningShare',
  components: {
    StudyPost,
    StudyPostWrite,
    StudyGroup,
    // 캘린더 부분
    StudyCalendarSpan,
    CalendarDialog,
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
    this.filterMyStudyGroup()
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
      // 내 스터디 목록 활성화 버튼
      isMyStudy: false,
      myStudyGroup: ['내 스터디 목록', '전체 스터디 목록'],
      state: '',
      // 내 스터디 정보 부분
      studyInfo: {
        studyTitle: '알고리즘 스터디',
        studyDescription: '알고리즘 스터디 바닥부터 가즈아',
      },
    }
  },
  methods: {
    // 현재 활성화된 기기에 따라 flag 변경
    onResize() {
      this.ResponsiveSize.isMobile = window.innerWidth < 426;
      this.ResponsiveSize.viewSize = window.innerWidth;
    },
    // 내 스터디 그룹 / 전체 스터디 그룹 전환
    filterMyStudyGroup() {
      if (this.isMyStudy) {
        this.state = this.myStudyGroup[0]
      } else {this.state = this.myStudyGroup[1]}
    },
    // study main으로 이동
    goMainStudy() {
      this.$router.push({ name: 'StudyMain' });
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
