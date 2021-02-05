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
            <v-sheet>
              <!-- 중고장터 설명 부분 -->
              <v-list color="transparent">
                <!-- 중고장터 설명 부분 -->
                <div
                  class="d-flex
                  flex-column
                  justify-space-between"
                >
                  <!-- 내 정보 타이틀 -->
                  <div class="text-h5">중고장터</div>
                  <div class="text-start pa-1 ma-auto">
                    내겐 필요없는 물건이<br> 누군가에겐 꼭 필요하다면?
                  </div>
                </div>
                <!-- 관심 품목 부분 -->
                <v-divider class="my-2"></v-divider>
                <v-list-item>관심품목</v-list-item>
                <v-col>
                  <MarketItem />
                  <MarketItem />
                  <MarketItem />
                </v-col>
              </v-list>
            </v-sheet>
          </v-col>
          <!-- 오른쪽 중고장터 본문 부분 -->
          <v-col
            class="col-12 col-sm-8"  
          >
            <!-- 중고장터 게시물 부분 -->
            <MarketPost class="mx-4 mb-2"/>
            <MarketPost class="mx-4 mb-2"/>
            <MarketPost class="mx-4 mb-2"/>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
// 관심 품목 등록 리스트
import MarketItem from "@/components/curation/market/MarketItem.vue"
// 중고장터 게시물
import MarketPost from "@/components/curation/market/MarketPost.vue"

export default {
  name:'Market',
  components: {
    MarketItem,
    MarketPost,
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