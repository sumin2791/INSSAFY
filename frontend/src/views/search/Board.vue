<template>
  <v-card
    class="mx-auto
      img-wrap"
    max-width="400"
  >
    <div class="text">
      <div class="action">
        <div class="d-flex flex-row align-center ml-2">
          <v-icon color="#fff">mdi-account-group</v-icon>
          <div 
            class="ml-2
              text-overline">{{ board.count }}</div>
        </div>
        <v-btn 
          small
          fab
          class="subscription"
          @click="subscriptionBoard()"
        >
          <v-icon :color="board.isSubscription ? '#fff' : '#AA2610'">mdi-heart</v-icon>
        </v-btn>
      </div>
      <div
        id="board-title"
        class="pa-2"
      >{{ board.title }}</div>
      <div
        id="board-description"
        class="ma-2">
        {{ board.description }}
      </div>

      <div
        id="board-hastag"
        class="ma-2">
        {{ board.hashtag }}
      </div>
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
  data() {
    return {
      board: {
        id: 1,
        type: 'curation',
        title: '모르고리즘이 알고리즘이 될 때까지',
        description: '아 나도 코테 잘 보고 싶다 .......',
        hashtag: '#코테 #SWEA #네카라 #쩔어 #레쓰고 #내코가석자 #같이가자',
        count: 167,
        isSubscription: true,
      },
    }
  },
  methods: {
    subscriptionBoard() {
      
      this.board.isSubscription = !this.board.isSubscription
    },
  },
}
</script>

<style scoped>
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