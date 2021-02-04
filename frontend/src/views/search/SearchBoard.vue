<template>
  <v-app>

    <v-main class="main-bg-color">
      <v-container>
        <!-- 검색어 결과 노출 부분 -->
        <v-row>          
          <v-col 
            class="mb-4
            text-center
              text-body-1">
              보드 중 "{{ search.searchKeyword }}"에 대한 검색 결과 &nbsp; '{{ search.searchCount }}건'
          </v-col>
        </v-row>
        <!-- 검색 건수 / 정렬 / 보드 생성 -->
        <div 
          class="d-flex 
            flex-row
            justify-space-between"
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
          
          <!-- 보드 생성 버튼 -->
          <div>
            <v-btn
              color="brown darken-1"
            >
              <v-icon
                color="#fff"
              >
                mdi-plus
              </v-icon>
              <div class="text-white">보드 만들기</div>
            </v-btn>
          </div>
        </div>
      <!-- 보드 보여주는 부분 -->
      <v-row >
        <v-col class="col-6 col-sm-3">
          <Board />
        </v-col>
        <v-col class="col-6 col-sm-3">
          <Board />
        </v-col>
        <v-col class="col-6 col-sm-3">
          <Board />
        </v-col>
        <v-col class="col-6 col-sm-3">
          <Board />
        </v-col>
        <v-col class="col-6 col-sm-3">
          <Board />
        </v-col>
        <v-col class="col-6 col-sm-3">
          <Board />
        </v-col>
        <v-col class="col-6 col-sm-3">
          <Board />
        </v-col>
      </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Board from './Board.vue'

export default {
  name: 'SearchBoard',
  components: {
    Board,
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
  methods: {
    goToCreateBoard(){
      return this.$router.push({ name: 'BoardForm' });
    }
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