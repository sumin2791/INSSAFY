<template>
  <v-app class="main-bg-color">
    <v-main class="grey lighten-3">
      <v-container
        id="container"
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
            <!-- 지역설정 -->
            <v-select
              v-model="searchLocation"
              :items="location"
              label="지역"
              solo
            ></v-select>
            <!-- 검색바 -->
            <v-text-field
              placeholder="검색"
              solo
              v-model="searchKeyword"
            ></v-text-field>
          </div>
        </v-row>
        <v-row dense>
          
          <!-- 왼쪽 중고장터 설명 부분 -->
          <v-col 
            class="col-12 col-sm-4" 
          >
            <div id="description" class="rounded-bg container-description">
              <h4 class="b-desc">중고장터</h4>
              <p class="l-desc">
                내겐 필요없는 물건이<br />
                누군가에겐 꼭 필요하다면?
              </p>
            </div>
            <div id="description" class="rounded-bg container-description">
              <v-list color="transparent">
                <!-- 중고장터 설명 부분 -->
                <!-- 관심 품목 부분 -->
                <v-list-item><a id="scrap-item" v-b-toggle href="#item-collapse" @click.prevent>관심품목 <b-icon icon="chevron-down" aria-hidden="true"></b-icon></a></v-list-item>
                <b-collapse id="item-collapse">
                  <v-col>
                  <MarketItem />
                </v-col>
                </b-collapse>
              </v-list>
            </div>
          </v-col>
          <!-- 오른쪽 중고장터 본문 부분 -->
          <v-col
            class="col-12 col-sm-8"  
          >
            <!-- 중고장터 게시글 작성 -->
            <!-- <MarketPostWrite class="mx-4 mb-2"/> -->
            <PostWrite :in-board="inBoard"/>
            <!-- 중고장터 게시물 부분 -->
            <MarketPostList/>
            <!-- <div v-for="(post,idx) in posts" :key="idx">
              <MarketPost class="mx-4 mb-2" :post="post"/>
            </div> -->
            <!-- <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
              <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
            </infinite-loading> -->
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
// 관심 품목 등록 리스트
import MarketItem from "@/components/curation/market/MarketItem.vue"
// 중고장터 게시물 작성
// import MarketPostWrite from "@/components/curation/market/MarketPostWrite.vue"

// 중고장터 리스트
import MarketPostList from "@/components/board/PostList"
//글작성
import PostWrite from '@/components/board/PostWrite'


import * as marketApi from '@/api/market'
// import InfiniteLoading from 'vue-infinite-loading';

export default {
  name:'Market',
  components: {
    MarketItem,
    // MarketPostWrite,
    MarketPostList,
    PostWrite,
    // MarketPost,
    // InfiniteLoading
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
      // 검색 조건 지역 설정
      location:['서울', '대전', '구미', '광주'],
      // 선택된 검색 조건 지역
      searchLocation: '',
      // 검색 키워드
      searchKeyword: '',
      posts:[],
      page:0,
      inBoard:true,
    }
  },
  methods: {
    // 현재 활성화된 기기에 따라 flag 변경
    onResize() {
      this.ResponsiveSize.isMobile = window.innerWidth < 426;
      this.ResponsiveSize.viewSize = window.innerWidth;
    },
    // infiniteHandler($state){
    //   const EACH_LEN = 5
    //   marketApi.getSaleList({board_id:34,login_id:localStorage.userId,page:this.page,size:EACH_LEN})
    //   .then((res)=>{
    //     console.log(res)
    //     setTimeout(()=>{
    //       if(res.data.postList){
    //         this.posts = this.posts.concat(res.data.postList);
    //         this.page += 1;
    //         $state.loaded();
    //         if(res.data.postList.length / EACH_LEN <1){
    //           $state.complete();
    //         }
    //       }else{
    //         $state.complete();
    //       }
    //     },1000);
    //   })
    //   .catch(err=>{console.log(err)})
    // },
  }
}
</script>

<style scoped>
/* 전체 감싼 컨테이너 */
#container {
 max-width: 1200px !important; 
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
</style>