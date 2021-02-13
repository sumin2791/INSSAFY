<template>
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
        
        <!-- 보드 생성 버튼 -->
        <div>
          <v-btn
            id="create-btn"
            @click="goToCreateBoard()"
          >
            <v-icon
              color="#AA2610"
            >
              mdi-plus
            </v-icon>
            <div class="text-btn">보드 만들기</div>
          </v-btn>
        </div>
      </div>
    <!-- 보드 보여주는 부분 -->
      <v-row >
        <v-col class="col-6 col-sm-3" v-for="(board,idx) in boardList" :key="idx">
          <Board :board="board"/>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import Board from '@/components/search/Board.vue'
import * as boardApi from '@/api/board'

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
  computed:{
    boardList(){
      return this.$store.state.board.boardList
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    goToCreateBoard() {
      return this.$router.push({ name: 'BoardForm' });
    },
    fetchData() {
      boardApi.get_boards('sort')
        .then(res=>{
          this.$store.dispatch('board/getBoardList',res.data.boardList)
          // this.boardList = res.data.boardList
          console.log(res)
        })
        .catch(err=>{
          console.log(err)
        })

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
/* 보드 만들기 버튼 */
#create-btn {
  position: sticky;
  text-align: center;
  margin: auto;
  height: 50px;
  width:100%;
  border: none;
  color: var(--basic-color-fill);
  text-shadow: 0 0px 1px var(--basic-color-fill3);
  background: #ebebe9 !important;
  box-shadow: 10px 10px 20px #bcbcba, 
              -10px -10px 20px #ffffff;
  border-radius: 15px !important;
  transition: 0.3s all ease;
}
#create-btn:hover,
#create-btn:active,
#create-btn:focus {
  color: #ebebe9 !important;    
  background-color: var(--basic-color-bg2) !important;
}
/* 보드 생성 버튼 */
.text-btn {
  color: #AA2610;
}
</style>