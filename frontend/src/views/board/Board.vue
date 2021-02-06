<template>
  <div id="board">
    <b-container class="board">
      <b-row>
        <b-col sm="3" class="board-aside">
          <BoardDescription :in-board="inBoard" :is-manager="isManager"/>
          <button
            class="btn-subscribe b-title"
            @click="onSubscribe"
            v-if="!inBoard"
          >Subscribe</button>
          <button
            class="btn-subscribing b-title"
            @click="onSubscribe"
            v-if="inBoard"
          >Subscribing</button>
          <hr>
          <div class="board-function">보드특수기능들</div>
          <div class="add-board-function">보드기능 추가</div>
        </b-col>
        <b-col sm="9" class="board-section">
          <div class="post-search">
            <div>찾기</div>
          </div>
          <div class="post-write">
            <!-- <b-button variant="light">글쓰기</b-button> -->
            <PostWrite :in-board="inBoard"/>
          </div>
          <PostList/>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import BoardDescription from '@/components/board/BoardDescription.vue'

import PostWrite from '@/components/board/PostWrite.vue'
import PostList from '@/components/board/PostList.vue'

//board api
import * as boardApi from '@/api/board';

export default {
  name:'Board',
  components: {
    BoardDescription,
    PostList,
    PostWrite,
  },
  data() {
    return {
      inBoard:'',
      isManager:false,
    }
  },
  created() {
    // this.$store.dispatch('board/IsInBoard',Number(this.$route.params.board_id))
    // 구독했는 지 파악하기 : inBoard
    const BOARD_ID = Number(this.$route.params.board_id)
    const boards = JSON.parse(localStorage.subBoard)
    const boardIds = boards.map(e => {
      return e.board_id
    });
    this.inBoard = boardIds.includes(BOARD_ID)

    // 구독했다면 관리자인가? 아님 그냥 유저인가? : isManager
    if(this.inBoard){
      const idx = boards.findIndex(board => board.board_id===BOARD_ID)
      if(boards[idx].user_role==1){
        this.isManager=true
      }
    }
  },
  computed: {
    // inBoard() {
    //   return this.$store.state.board.inBoard
    // }
  },
  methods:{
    onSubscribe(){
      const BOARD_ID = Number(this.$route.params.board_id)

      const boards = JSON.parse(localStorage.subBoard)
      const board = boards.filter(board => board.board_id===Number(this.$route.params.board_id))
      
      const params = {
        user_id:String(localStorage.userId),
        board_id:BOARD_ID,
        user_role:0,
      }
      boardApi.subscribe(params)
        .then(res => {
          console.log('들어왔나?')
          console.log(res)
          if (res.data.message==='fail'){
            return
          }else{
            this.inBoard = !this.inBoard
            
            // localStorage 수정해주는 부분
            if(board.length>0){
              // 보드가 있네? 그럼 구독 해지!
              const idx = boards.findIndex(board => board.board_id===Number(this.$route.params.board_id))
              boards.splice(idx,1)
            }else{
              // 보드가 없었어. 그러면 바로 구독하면 돼!
              boards.push({
                board_id:Number(this.$route.params.board_id),
                user_id: localStorage.userId,
                favorite_flag:0,
                write_post_count: 0,
                is_used:0,
                user_role: 0
              })
            }
            localStorage.subBoard = JSON.stringify(boards)
          }
        })
        .catch(err=>{
          console.log(err)
        })
      return
    }
  }
}
</script>

<style scoped>

.board{
  max-width: 1200px !important;
  margin: 0 auto;
  margin-top:30px;
  /* width:80%;
  margin-left:10%;
  margin-right:10%; */
}

/* .post-write {
  display: inline-block;
  position:sticky;
} */
.post-search{
  display: flex;
  justify-content: flex-end;
}
.btn-subscribe{
  /* position: inherit;  */
  height: 50px;
  width:100%;
  font-size: 24px;
  margin-top: 10px; 
  margin-bottom: 10px;
  text-align: center;
  background-color: #000 !important;
  color: #fff;
}
.btn-subscribe:hover,
.btn-subscribe:active {
  background-color: #fff !important;
  color: #000;
}
.btn-subscribing{
  height: 50px;
  width:100%;
  font-size: 24px;
  margin-top: 10px; 
  margin-bottom: 10px;
  text-align: center;
  /* background-color: #000 !important; */
  color: #000 ;
}
.btn-subscribing:hover,
.btn-subscribing:active {
  /* background-color: #fff !important; */
  color: #000;
}

@media screen and (max-width:576px){
  .btn-subscribe{
    width: 100%;
    background-color: #000 !important;
    color: #fff;
  }
  .btn-subscribing{
    width: 100%;
    /* background-color: #000 !important; */
    color: #000;
  }
  .post-search{
    display:none;
  }
  .add-board-function{
    display: none;
  }
}
</style>