<template>
  <v-card
    class="mx-auto
      my-4
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
      <div 
        class="text"
      >
        <!-- 삭제 버튼 -->
        <div class="align-self-end">
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
        <div>
          {{ post.inBoard }}
        </div>
      </div>
      <v-img 
        src="@/assets/images/slide.jpg"
        height="100px"
        class="blur"
      >
      </v-img>
    </div>
    <!-- 포스트 제목 -->
    <v-card-title 
      class="font-weight-black
        d-flex
        flex-row"
    >
      {{ post.title }}
    </v-card-title>

    <!-- 포스트 글 내용 -->
    <v-card-text 
      class="font-weight-bold
        py-0 pl-auto"
    >
      {{ post.contents }}
    </v-card-text>
    <v-card-actions>
      <v-list-item class="grow">
        <v-list-item-content>
          <v-list-item-title>
            <v-icon>
              mdi-account-group
            </v-icon>
              {{ post.boardCount }}
          </v-list-item-title>
        </v-list-item-content>

        <!-- 포스트 좋아요/댓글 수 -->
        <v-row
          align="center"
          justify="end"
        >
          <span style="float:right;">
            <v-icon small> mdi-thumb-up </v-icon>
            {{ post.likeCount }}
            <v-icon small> mdi-comment-processing </v-icon>
            {{ post.commentCount }}
          </span>
        </v-row>
      </v-list-item>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: 'MyPostTest',
  data() {
    return {
      post:
        {
          inBoard: '(보드 이름)모르고리즘이 알고리즘이 될 때까지',
          boardImg: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
          title: '(게시물제목)저희팀원들 짱이랍니다',
          contents: "(게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)(게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)(게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)(게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)",
          date: `2021-02-02`,
          likeCount: 4,
          commentCount: 4,
          boardCount: 241,
        },
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
  z-index: 2;
}
</style>