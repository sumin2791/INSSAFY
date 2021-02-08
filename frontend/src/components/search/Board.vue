<template>
  <v-card
    class="mx-auto
      img-wrap"
    max-width="400"
  >
    <div class="text">
      <v-btn 
        small
        fab
        class="subscription"
      >
        <v-icon color="#fff">mdi-heart</v-icon>
      </v-btn>
      <v-card-title @click="goToBoard" style="cursor:pointer">{{ board.board_name }}</v-card-title>
      <v-card-subtitle 
        class="pb-0
          text-white 
          text-start">
        {{ board.board_description }}
      </v-card-subtitle>

      <v-card-text class="text-white text-start">
        <div class="hashtag-list" v-for="(hash,idx) in hashtags" :key="idx">{{ hash }}</div>
      </v-card-text>
    </div>

    <v-img
      id="v-img"
      class="align-end blur"
      height="250px"
      src="@/assets/images/slide.jpg"
    >
    </v-img>


  </v-card>
</template>

<script>
export default {
  name: 'Board',
  props:{
    board:Object
  },
  data() {
    return {
      // board: {
      //   id: 1,
      //   type: 'curation',
      //   title: '보드명',
      //   description: '보드 설명 보드 설명 보드 설명 보드 설명 보드 설명 보드 설명',
      //   hashtag: '#싸피 #여행 #바다 #싸피 #여행 #바다 #싸피 #여행 #바다 #싸피 #여행 #바다',
      //   count: 100,
      // },
    }
  },
  created() {
  },
  computed:{
    hashtags() {
      if(this.board.board_hash!=null){
        return this.board.board_hash.split('|')
      }else{
        return ' '
      }
    }
  },
  methods:{
    goToBoard(){
      console.log(this.board.board_id)
      this.$router.push({ name: 'Board', params: { board_id:this.board.board_id}})
    }
  }
}
</script>

<style scoped>
.container-board {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: flex-start;
  width: 16rem;
  height: 16rem;
  border: 1px solid;
}
.subscription-btn {
  align-self: flex-end;
  margin: 1% 1% 0;
}
.board-name {
  align-self: flex-start;
  margin-left: 3%;
}
.board-description {
  align-self: flex-start;
  margin-left: 3%;
}
.board-name {
}
.board-hashtag {
  align-self: center;
  margin: 5%;
}
.hashtag-list{
  display:inline;
}
.scrap-count {
  align-self: flex-end;
  justify-self: flex-end;
  margin: 3% 2%;
}

/* 보드 디자인 부분 */
/* 보드 이미지 전체 감싼 div */
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

/* 정보부분 */
.text {
  position: absolute;
  width: 100%;
  height: 100%;
  color: #FFFFFF;
  display: flex;
  flex-direction: column;
  text-align: center;
  align-items: center;
  justify-content: space-between;
  z-index: 2;
}
.subscription {
  display: flex;
  align-self: flex-end;
}
/* 보드 정보(제목, 설명, 해쉬태그 넘치는 부분 처리) */
/* 구독자 수, 구독 버튼 */
.action {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-content: center;
}
#board-title {
  width: 70%;
  font-size: 16px;
  line-height: 32px;
  max-height: 64px;
  overflow: hidden;
  display: -webkit-box;
  word-break: break-all;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  text-align: center;
}
#board-description {
  width: 80%;
  font-size: 14px;
  line-height: 20px;
  max-height: 40px;
  overflow: hidden;
  display: -webkit-box;
  word-break: break-all;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  text-align: center;
}
#board-hastag {
  width: 75%;
  font-size: 11px;
  line-height: 20px;
  max-height: 40px;
  overflow: hidden;
  display: -webkit-box;
  word-break: break-all;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  text-align: start;
}
</style>