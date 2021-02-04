<template>
  <v-container
    class="my-1    
    mx-0
    px-1"
  >
    <div 
      class="img-wrap
      d-flex"
    >
      <!-- 보드 이미지 위로 나오는 부분 -->
      <div 
        class="text"
      >
        <!-- 즐겨찾기 항목에 추가 -->
        <v-btn
            icon
            x-large
            fab
            :color="isFavorite"
            @click="addInFavoriteBoard"
          >
            <v-icon   
              dark
            >
              mdi-star
            </v-icon>
          </v-btn>
        <!-- board title -->
        <v-card-title
          class="flex-grow-1
          title"
          @click="moveToBoard()"
        >
          {{board_name}}
        </v-card-title>
        <!-- 삭제 버튼 -->
        <div class="align-self-start">
          <v-btn
            icon
            color="#AA2610"
            @click="removeBoard()"
          >
            <v-icon
              dark
            >
              mdi-close-thick
            </v-icon>
          </v-btn>
        </div>
      </div>
      <!-- 보드 이미지 부분 -->
      <v-img
        height="100px"
        src="@/assets/images/slide.jpg"
        class="blur"
      >
      </v-img>
    </div>
  </v-container>
</template>

<script>
import * as boardApi from '@/api/board'
export default {
  name: "Subscription",
  components: {

  },
  data() {
    return {
      isFavorite: '#ffc83d',
      board_name:'',
    };
  },
  props:{
    board:Object
  },
  mounted(){
    this.getBoardDetail()
  },
  methods: {
    getBoardDetail(){
      boardApi.board_detail(this.board.board_id)
      .then(res=>{
        this.board_name = res.data.boardDto.board_name
      })
      .catch(err=>{
        console.log(err)
      })
    },
    // 즐겨찾는 보드에 추가
    addInFavoriteBoard() {
      if (this.isFavorite === '#ffc83d') {
        this.isFavorite = '#fff'
      } else {this.isFavorite = '#ffc83d'}
    },
    // 해당 보드로 이동(상세 주소 넘겨주기)
    moveToBoard() {
      this.$router.push({ name: 'Board', params: { board_id:this.board.board_id} });
    },
    // 보드 구독목록에서 삭제
    removeBoard() {
      console.log('삭제')
    },
  },
}
</script>

<style scoped>
/* board image 부분 */
.img-wrap {
  width: 100%;
  height: 100%;
  position: relative;
}
/* 이미지 흐리게 하기 */
.blur {
  filter: brightness(50%);
  z-index: 1;
}
.text {
  position: absolute;
  width: 100%;
  height: 100%;
  color: #FFFFFF;
  display: flex;
  text-align: center;
  align-items: center;
  justify-content: space-around;
  z-index: 2;
}
.title {
  cursor: pointer;
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
  white-space: nowrap;
}
</style>