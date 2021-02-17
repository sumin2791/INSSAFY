<template>
<v-main class="main-bg-color">
    <v-container>
      <!-- 검색어 결과 노출 부분 -->
      <v-row>          
        <v-col 
          class="mb-4
          text-center
            text-body-1">
            게시글 중 "{{ search.searchKeyword }}"에 대한 검색 결과 &nbsp; '{{ search.searchCount }}건'
        </v-col>
      </v-row>
      <!-- 검색 건수 / 정렬 / 보드 생성 -->
      <div 
        class="d-flex 
          flex-row
          justify-content-between"
      >
        <!-- 검색 건수 / 정렬 -->
        <div>
          <v-select
            v-model="search.currentSort"
            :items="sortResult"
            label="정렬기준"
            solo
            color="##fff"
            class="sort-dropdown"
            block
          ></v-select>
        </div>
      </div>
    <!-- 보드 보여주는 부분 -->
      <v-row >
        <v-col class="col-6 col-sm-4">
          <MyPost />
        </v-col>
        <v-col class="col-6 col-sm-4">
          <MyPost />
        </v-col>
        <v-col class="col-6 col-sm-4">
          <MyPost />
        </v-col>
        <v-col class="col-6 col-sm-4">
          <MyPost />
        </v-col>
        <v-col class="col-6 col-sm-4">
          <MyPost />
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import MyPost from "@/components/mypage/MyPost.vue"


export default {
  name: 'SearchPost',
  components: {
    MyPost,
  },
  data() {
    return {
      search: {
        searchKeyword: '게임',
        searchCount: 248,
        currentSort: '',
      },
      // 검색 정렬 조건
      sortResult: ['최신순', '인기순'],
    }
  },
  computed:{
    boardList(){
      return this.$store.state.board.boardList
    }
  },
  methods: {
    goToCreateBoard() {
      return this.$router.push({ name: 'BoardForm' });
    },
  }
}
</script>

<style scoped>
.main-bg-color {
  background-color: #ebebe9;
}
.sort-dropdown {
  max-width: 50%;
}
/* 검색결과들 담는 컨테이너 */
.search-result {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 0;
  padding-top: 0;
  flex-flow: wrap;
  border: 3px solid #000000;
}
/* 검색보드 감싸는 div */
.search-result-board {
  flex-basis: 19.5%;
  border: 3px dotted #000000;
}
</style>