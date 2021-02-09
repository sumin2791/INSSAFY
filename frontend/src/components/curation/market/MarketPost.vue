<template>
  <v-card
    class="mx-auto
      my-0
      d-flex
      flex-column"
    color="F9F9F9"
  >
    <!-- 포스트 작성자 부분 -->
    <v-card-title class="d-flex flex-row align-center ma-0 py-2 pb-0">
      <v-list-item-avatar class="rounded-circle">
        <v-img
          class="elevation-6"
          alt=""
          :src="post.boardImg"
        ></v-img>
      </v-list-item-avatar>
      <!-- 작성자이름, 작성일자 -->
      <div class="text-caption">
        <div>{{ post.writer }}</div>
        <div>{{ post.date }}</div>
      </div>
      <!-- 판매상태 들어갈 부분 -->
      <v-btn-toggle
        group
        class="align-self-end 
          ma-2 ml-auto"
      >
        <v-chip
          style="background-color: #0B2945 !important;
          color: #fff;
          border-radius: 10%;"
        >
          {{ isState }}
        </v-chip>
      </v-btn-toggle>
    </v-card-title>
    <!-- 포스트 제목 -->

    <v-col
      class="font-weight-black"
    >{{ post.title }}</v-col>


    <!-- 댓글 내용 -->
    <v-col>
      {{ post.comment }}
    </v-col>
  </v-card>
</template>

<script>
export default {
  name: 'ScrapPost',
  mounted() {
    this.showState()
  },
  data() {
    return {
      post:
        {
          writer: 'hahawhoa',
          title: '(게시물제목)지금까지 이런 핸드폰은 없었다',
          boardImg: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
          comment: "For sale, cellphone, never used For sale, cellphone, never used For sale, cellphone, never used For sale, cellphone, never used For sale, cellphone, never used For sale, cellphone, never used For sale, cellphone, never used For sale, cellphone, never used",
          date: `21.02.02`,
          likeCount: 4,
          commentCount: 4,
          boardCount: 241,
          // 현재 판매상태
          state: 2,
          stateString: '',
        },
      // 판매상태 정보
      isState: ['판매중', '거래중', '판매완료'],
    }
  },
  methods: {
    // 작성글 삭제
    removeBoard() {
      console.log('작성글 삭제')
    },
    // 해당 보드로 이동(상세 주소 넘겨주기)
    moveToBoard() {
      this.$router.push({ name: 'Board' });
    },
    // 판매상태 정보
    showState() {
      this.isState = this.isState[this.post.state]
    }
  },
}
</script>

<style scoped>
/* 이미지 흐리게 하기 */
.blur {
  filter: brightness(50%);
  z-index: 1;
}
.text {
  position: absolute;
  width: 100%;
  height: 100px;
  color: #FFFFFF;
  display: flex;
  flex-direction: column;
  text-align: center;
  justify-content: flex-start;
  align-items: center;
  z-index: 2;
}
/* 보드로 이동 */
.board-title {
  flex-grow: 100%;
  cursor: pointer;
}
/* 게시글 제목 넘치는 부분 처리 */
#post-title {
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
  white-space: nowrap;
}
/* 날짜 넘치는 부분 처리 */
#post-date {
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
  white-space: nowrap;
}
/* 댓글 내용 넘치는 부분 처리 */
#post-contents {
  font-size: 14px;
  line-height: 20px;
  max-height: 40px;
  overflow: hidden;
  display: -webkit-box;
  word-break: break-all;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
/* stepper 가로정렬로 */
.stepper {
}
</style>