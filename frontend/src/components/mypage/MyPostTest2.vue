<template>
  <v-card
    class="mx-auto my-4"
    color="F9F9F9"
  >
    <v-row class="mx-auto">
      <v-img 
        src="@/assets/images/slide.jpg"
        height="300px"
        class="blur"
      >
        <span style="float:right;">
            <v-btn
              icon
              color="#AA2610"       
            >
            <v-icon>mdi-close</v-icon>
            </v-btn>
          </span>
        <!-- 포스트 제목 -->
        <v-card-title 
          class="title font-weight-black grow">
        {{ post.title }}
        </v-card-title>
        <!-- 포스트 글 내용 -->
        <v-card-text class="font-weight-bold">
          {{ post.contents }}
        </v-card-text>
        <v-card-actions>
          <v-list-item class="grow">
            <v-list-item-content>
              <v-list-item-title>
                <span>
                  <v-icon>
                  mdi-account-multiple
                </v-icon>
                </span>
                {{ post.inBoard }}
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
      </v-img>
    </v-row>
  </v-card>
</template>

<script>
export default {
  name: 'MyPostTest2',
  data() {
    return {
      post:
        {
          inBoard: '모르고리즘이 알고리즘이 될 때까지',
          boardImg: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
          title: '(게시물제목)저희팀원들 짱이랍니다',
          contents: "(게시물내용)다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)",
          date: `2021-02-02`,
          likeCount: 4,
          commentCount: 4,
        },
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
  height: 100%;
  color: #FFFFFF !important;
  /* display: flex;
  text-align: center;
  align-items: center;
  justify-content: space-around; */
  z-index: 2 !important; 
}
</style>