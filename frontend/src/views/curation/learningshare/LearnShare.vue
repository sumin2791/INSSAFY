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
          <v-col class="col-12 col-sm-4">
            <div id="description" class="rounded-bg container-description">
              <h4 class="b-desc">학습공유</h4>
              <p class="l-desc">
                오늘의 학습 내용<br />
                함께 나누고픈 내용 공유
              </p>
              <v-divider class="my-2"></v-divider>
              <v-list color="transparent">
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
              </v-list>
              <v-divider class="my-2"></v-divider>
              <v-list-item>워드 클라우드 부분</v-list-item>
              <v-col class="d-flex justify-center p-0">
                <wordcloud
                  :data="defaultWords"
                  nameKey="name"
                  valueKey="value"
                  color="Category10"
                  :margin="wordcloudmargin"
                  :wordClick="wordClickHandler">
                </wordcloud>
                <!-- <v-avatar size="200">
                  <v-img src="@/assets/images/wordcloud.jpg"></v-img>
                </v-avatar> -->
              </v-col>
            </div>
          </v-col>
          <!-- 오른쪽 학습공유 본문 부분 -->
          <v-col
            class="col-12 col-sm-8"  
          >
            <!-- 학습공유 게시글쓰기 -->
            <!-- <LearningPostWrite class="mx-4 mb-2"/> -->
            <PostWrite :in-board="inBoard" style="margin:0 10px"/>
            <!-- 학습공유 게시물 부분 -->
            <LearningSharePostList />

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
// import LearningPost from "@/components/curation/learningshare/LearningPost.vue"
// 학습공유 게시물 쓰기
// import LearningPostWrite from "@/components/curation/learningshare/LearningPostWrite.vue"
// 학습공유 리스트
import LearningSharePostList from "@/components/board/PostList"
import PostWrite from '@/components/board/PostWrite'

//워드클라우드
import wordcloud from 'vue-wordcloud'
import * as learnshareApi from '@/api/study'

export default {
  name:'LearningShare',
  components: {
    LearningRank,
    // LearningPost,
    // LearningPostWrite,
    LearningSharePostList,
    PostWrite,
    wordcloud,
  },
  created(){
    learnshareApi.getWordCloud()
    .then(res=>{
      res.data.defaultWords.forEach(element => {
        this.defaultWords.push({name:element.name,value:element.score})
      });
    })
    .catch(err=>{
      console.log(err)
    })
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
      defaultWords:[],
      wordcloudmargin:{top: 10, right: 10, bottom: 10, left: 10 },
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
      wordcloudImg: '@/assets/images/wordcloud.jpg',

      //변수 사용할 것들
      inBoard:true,
    }
  },
  methods: {
    // 현재 활성화된 기기에 따라 flag 변경
    onResize() {
      this.ResponsiveSize.isMobile = window.innerWidth < 426;
      this.ResponsiveSize.viewSize = window.innerWidth;
    },
    wordClickHandler(name, value, vm) {
      console.log('wordClickHandler', name, value, vm);
    }
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
