<template>
  <v-card
    id="post-box"
    class="mx-2
      my-4
      pa-2
      pb-4
      d-flex
      flex-column"
    color="F9F9F9"
  >
    <div
      class="img-wrap
      d-flex
      flex-column"
    >
      <!-- 보드 이미지 위로 나오는 부분 -->
      <div class="text">
        <!-- 삭제 버튼 -->
        <div class="align-self-end">
          <v-btn icon color="#AA2610" @click="removeBoard()">
            <v-icon dark>
              mdi-close-thick
            </v-icon>
          </v-btn>
        </div>
        <div class="board-title" @click="moveToBoard()">
          {{ post.inBoard }}
        </div>
      </div>
      <GradientGenerator class="myinfo-list" style="height: 100px" v-if="board_image == null" :radius="radius" />
      <v-img src="@/assets/images/slide.jpg" height="100px" class="myinfo-list blur" v-if="board_image != null"> </v-img>
    </div>
    <!-- 포스트 제목 -->
    <v-card-title
      class="
        d-flex
        flex-row
        space-between
        pa-0"
    >
      <v-col cols="9" id="post-title" class="font-weight-black">{{ post.title }}</v-col>
      <v-col
        cols="3"
        id="post-date"
        class="text-overline 
        text-end"
        >{{ post.date }}</v-col
      >
    </v-card-title>

    <!-- 댓글 내용 -->
    <v-card-text
      id="post-contents"
      class="font-weight-bold
        py-0 pl-auto"
    >
      {{ post.comment }}
    </v-card-text>
    <!--
    <v-card-actions>
      <v-list-item class="grow">
        <v-list-item-avatar class="rounded-circle">
          <v-img class="elevation-6" alt="" :src="post.boardImg"></v-img>
        </v-list-item-avatar>
        <div>{{ post.writer }}</div>

        <v-row align="center" justify="end">
          <span style="float:right;">
            <v-icon small> mdi-thumb-up </v-icon>
            {{ post.likeCount }}
            <v-icon small> mdi-comment-processing </v-icon>
            {{ post.commentCount }}
          </span>
        </v-row>
      </v-list-item>
    </v-card-actions>
    -->
  </v-card>
</template>

<script>
import GradientGenerator from '@/components/etc/GradientGenerator';
export default {
  name: 'ScrapPost',
  props: {
    comment: Object,
  },
  components: {
    GradientGenerator,
  },
  data() {
    return {
      board_image: null,
      radius: '15px',
      post: {
        writer: 'hahawhoa',
        inBoard: '(보드 이름)모르고리즘이 알고리즘이 될 때까지',
        boardImg: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
        title: '(게시물제목)저희팀원들 짱이랍니다(게시물제목)저희팀원들 짱이랍니다',
        comment:
          '(댓글내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)(게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)(게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)(게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)(게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)(게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)',
        date: `21.02.02`,
        likeCount: 4,
        commentCount: 4,
        boardCount: 241,
      },
    };
  },
  methods: {
    // 작성글 삭제
    removeBoard() {
      console.log('작성글 삭제');
    },
    // 해당 보드로 이동(상세 주소 넘겨주기)
    moveToBoard() {
      this.$router.push({ name: 'Board' });
    },
  },
};
</script>

<style scoped>
/* 하나의 게시글 전체 부분 */
#post-box {
  cursor: pointer;
  border-radius: 15px;
  box-shadow: var(--basic-shadow-c);
}
/* 이미지 흐리게 하기 */
.blur {
  filter: brightness(50%);
  z-index: 1;
}
.text {
  position: absolute;
  width: calc(100% - 20px);
  height: 100px;
  color: #ffffff;
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
.myinfo-list {
  border-radius: 15px;
  box-shadow: var(--basic-shadow-m);
}
</style>
