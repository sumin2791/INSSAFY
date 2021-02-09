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
          
          <!-- 왼쪽 학습공유 설명 부분 -->
          <v-col 
            class="col-12 col-sm-4" 
          >
            <v-sheet>
              <!-- 학습공유 설명 부분 -->
              <v-list color="transparent">
                <!-- 학습공유 설명 부분 -->
                <div
                  class="d-flex
                  flex-column
                  justify-space-between"
                  style="min-height: 25vh;"
                >
                  <!-- 내 정보 타이틀 -->
                  <div class="text-h5">학습공유</div>
                  <div class="text-start pa-1 ma-auto">
                    오늘의 학습 내용<br>함께 나누고픈 내용 공유
                  </div>
                </div>
                <!-- ranking 부분 -->
                <v-divider class="my-2"></v-divider>
                <v-list-item>RANK</v-list-item>
                <v-col>
                  <LearningRank />
                </v-col>
                <!-- ranking 1위의 한마디 -->
                <v-list-item>RANK 1위의 한마디</v-list-item>
                <v-col class="font-weight-black text-center">
                  "{{ first.speech }}"
                </v-col>
                <v-col class="text-end text-caption">
                  -{{ first.nickName }}-
                </v-col>
                <!-- 워드 클라우드 부분 -->
                <v-divider class="my-2"></v-divider>
                <v-list-item>워드 클라우드 부분</v-list-item>
                <v-col class="d-flex justify-center">
                  <v-avatar size="200">
                    <v-img src="@/assets/images/wordcloud.jpg"></v-img>
                  </v-avatar>
                </v-col>
              </v-list>
            </v-sheet>
          </v-col>
          <!-- 오른쪽 학습공유 본문 부분 -->
          <v-col
            class="col-12 col-sm-8"  
          >
            <!-- 학습공유 게시글쓰기 -->
            <LearningPostWrite class="mx-4 mb-2"/>
            <!-- 학습공유 게시물 부분 -->
            <LearningPost class="mx-4 mb-2"/>
            <LearningPost class="mx-4 mb-2"/>
            <LearningPost class="mx-4 mb-2"/> 
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
// rank 리스트
import LearningRank from "@/components/curation/learningshare/LearningRank.vue"
// 학습공유 게시물
import LearningPost from "@/components/curation/learningshare/LearningPost.vue"
// 학습공유 게시물 쓰기
import LearningPostWrite from "@/components/curation/learningshare/LearningPostWrite.vue"

export default {
  name:'LearningShare',
  components: {
    LearningRank,
    LearningPost,
    LearningPostWrite,
  },
  // 뷰 인스턴스 제거될 때 resize 호출
  beforeDestroy () {
      if (typeof window === 'undefined') return

      window.removeEventListener('resize', this.onResize, { passive: true })
  },
  // resize 실시해서 현재 화면 크기 확인
  mounted () {
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
      // rank1위 한마디
      first: {
        nickName: '김싸피',
        // rank 1위
        speech: '저는 밥숟가락만 얹었을 뿐인데 아름다운 밤이네요!😎'
      },
      wordcloudImg: '@/assets/images/wordcloud.jpg'
    }
  },
  methods: {
    // 현재 활성화된 기기에 따라 flag 변경
    onResize() {
      this.ResponsiveSize.isMobile = window.innerWidth < 426;
      this.ResponsiveSize.viewSize = window.innerWidth;
    },
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
</style>
