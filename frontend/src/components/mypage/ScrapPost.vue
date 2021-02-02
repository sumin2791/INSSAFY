<template>
  <v-card
    class="mx-auto"
    color="F9F9F9"
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
        <v-list-item-avatar color="grey darken-3">
          <v-img
            class="elevation-6"
            alt=""
            src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
          ></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title>{{ post.writer }}</v-list-item-title>
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
  name: 'scrapPost',
  data() {
    return {
      post:
        {
          writer: '김싸피',
          boardImg: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
          title: '(게시물제목)저희팀원들 짱이랍니다',
          contents: "다 똑똑박사들인가? 왜케 잘 하지...? 나만 잘하면 되겠다 :)",
          date: `2021-02-02`,
          likeCount: 4,
          commentCount: 4,
        },
    }
  },
}
</script>

<style>

</style>