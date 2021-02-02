<template>
  <div>
    <v-list 
      three-line
      class="d-flex
      my-4
      mx-2"
    >
      <v-list-item 
        class="px-0
        "
      >
        <v-list-item-avatar
          tile
        >
          <v-img
            :src="post.boardImg"
          ></v-img>
        </v-list-item-avatar>

        <v-list-item-content 
          class="pa-1 mx-3"
        >
          <!-- 포스트 제목 -->
          <v-list-item-title>
            {{ post.title }}
            <span style="float:right;">
              <v-btn
                icon
                color="#AA2610"       
              >
              <v-icon>mdi-close</v-icon>
              </v-btn>
            </span>
          </v-list-item-title>
          <!-- 포스트 날짜 -->
          <v-list-item-subtitle>
            {{ post.date }} 
            <!-- 포스트 좋아요 / 댓글 수 -->
            <span style="float:right;">
              <v-icon x-small> mdi-thumb-up </v-icon>
              {{ post.likeCount }}
              <v-icon x-small> mdi-comment-processing </v-icon>
              {{ post.commentCount }}
            </span>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <v-divider class="mt-0"></v-divider>
  </div>
</template>

<script>
export default {
  name: "MyPost",
  data() {
    return {
      post:
        {
          boardImg: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
          title: '저희팀원들 짱이랍니다',
          contents: "다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)",
          date: `2021-02-02`,
          likeCount: 4,
          commentCount: 4,
        },
    }
  },
}
</script>

<style scoped>
div,
p {
  font-size: 1rem;
}
.post {
  display: flex;
  flex-direction: row;
  border: 4px dotted #797979;
  height: 80px;
}
.post .name {
  border: 2px solid;
  width: 1fr;
  height: 1fr;
  text-align: center;
  padding-top: 5%;
  background-color: #797979;
  box-sizing: border-box;
}
.post .title {
  display: flex; 
  flex-direction: column;
  justify-content: space-between;
  box-sizing: border-box;
  width: 100%;
}
.post .title *{
  box-sizing: border-box;
  margin: 0%;
}
.title .first-line {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
  border: 1px solid black;
}
.title h4 {
  align-self: flex-end;
}

</style>