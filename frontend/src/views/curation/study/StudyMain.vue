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
            class="text-overline  text-weight-black"
            style="font-size: 20px !important;"
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
          <v-col class="col-12 col-sm-4">
            <div id="description" class="rounded-bg container-description">
              <h4 class="b-desc">스터디</h4>
              <p class="l-desc">
                스터디원을 구해서<br />
                같이 공부해보는 건 어떨까요?
              </p>
            </div>
            <div id="description" class="rounded-bg container-description">
              <v-list color="transparent">
                <v-list-item>내 스터디 목록</v-list-item>
                  <!-- 스터디 목록 활성화 버튼 -->
                  <!-- <v-list-item>
                    <v-switch
                      v-model="isMyStudy"
                      inset
                      color="#0B2945"
                      :label="`${state}`"
                      @click="filterMyStudyGroup()"
                    ></v-switch>
                  </v-list-item> -->
                  <v-col>
                    <StudyGroup />
                    <StudyGroup />
                    <StudyGroup />
                    <StudyGroup />
                  </v-col>
              </v-list>
            </div>
          </v-col>
          <!-- 오른쪽 스터디 본문 부분 -->
          <v-col
            class="col-12 col-sm-8"  
          >
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

export default {
  name:'LearningShare',
  components: {
    StudyPost,
    StudyPostWrite,
    StudyGroup,
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
    }
  },
  methods: {
    // 현재 활성화된 기기에 따라 flag 변경
    onResize() {
      this.ResponsiveSize.isMobile = window.innerWidth < 426;
      this.ResponsiveSize.viewSize = window.innerWidth;
    },
    // 내 스터디 그룹 / 전체 스터디 그룹 전환
    // filterMyStudyGroup() {
    //   if (this.isMyStudy) {
    //     this.state = this.myStudyGroup[0]
    //   } else {this.state = this.myStudyGroup[1]}
    // },
  }
}
</script>

<style scoped>
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
</style>
