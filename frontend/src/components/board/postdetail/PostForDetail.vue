<template>
  <v-card
    class="d-flex
      flex-column"
    color="#F9F9F9"
  >
    <!-- 포스트 작성자 부분 -->
    <v-card-title 
      class="d-flex 
        flex-row 
        align-center 
        ma-0 
        pr-0
        py-0"
    >
      <v-list-item 
        two-line
        class="pl-0
          pr-0"
      >
        <v-list-item-avatar class="rounded-circle">
          <v-img
            class="elevation-6"
            alt=""
            :src="profileImg"
          ></v-img>
        </v-list-item-avatar>
        <!-- 작성자이름, 작성일자 -->
        <v-list-item-content>
          <v-list-item-subtitle 
            class="text-body
              ml-2 
              font-weight-bold"
          >
            {{nickname}}
          </v-list-item-subtitle>
          <v-list-item-subtitle 
            class="text-overline
              ml-2 
              font-weight-medium"
          >
            {{ date }}
          </v-list-item-subtitle>
        </v-list-item-content>
        <v-spacer></v-spacer>
        <!-- 수정 삭제 신고 버튼 -->
        <b-dropdown 
          id="dropdown-dropleft" 
          class="user-name" 
          variant="link" 
          toggle-class="text-decoration-none" 
          no-caret
        >
          <template #button-content>
            <v-btn icon x-small fab>
              <v-icon dark>
                mdi-dots-vertical
              </v-icon>
            </v-btn>
          </template>
          <!-- 수정, 삭제는 작성자만 보이게 -->
          <b-dropdown-item v-if="flagWriter"><PostModify :post="post"/></b-dropdown-item>
          <b-dropdown-item v-if="flagWriter">삭제</b-dropdown-item>
          <b-dropdown-item>신고</b-dropdown-item>
          <!-- <b-dropdown-item href="#">Something else here</b-dropdown-item> -->
        </b-dropdown>
      </v-list-item>
    </v-card-title>
    
    <!-- 포스트 제목 -->
    <div
      id="title"
      class="text-h6
        mx-4 
        font-weight-bold"
    >
      {{post.post_title}}
    </div>
    <!-- 게시글 내용 -->
    <div
      id="description"
      class="text-caption
        mx-4
        mt-4"
    >
      {{post.post_description}}
      <!-- 이미지 미리보기 -->
      <img v-if="viewImage" :src="viewImage" alt="이미지 미리보기...">
    </div>

    <!-- 게시글 관련 이미지/댓글/좋아요 들어갈 부분 -->
    <v-card-actions id="actions">
      <!-- 북마크 -->
      <button
        @click="postScrap"
      >
        <!-- 북마크 중 -->
        <v-icon
          middle
          v-if="flagScrap"
          color="#0B2945"
        >mdi-bookmark</v-icon>
        <!-- 북마크 취소상태 -->
        <v-icon
          middle
          v-else
        >mdi-bookmark-outline</v-icon>
      </button>
      <v-spacer></v-spacer>
      <!-- 댓글 수 -->
      <v-icon
        middle
      >mdi-comment-processing</v-icon>
      <span>{{ commentCount }}</span>
      <!-- 좋아요 -->
      <button
        @click="postLike"
      >
        <!-- 좋아요 중 -->
        <v-icon
          middle
          v-if="flagLike"
          color="#FFC400"
        >mdi-emoticon-excited</v-icon>
        <!-- 좋아요 취소상태 -->
        <v-icon
          middle
          v-else
        >mdi-emoticon-neutral-outline</v-icon>
        <span>{{ countLike }}</span>
      </button>
      
    </v-card-actions>
  </v-card>
</template>

<script>
import PostModify from '@/components/board/postdetail/PostModify'

import * as postApi from '@/api/post'
import timeForToday from '@/plugins/timeForToday'

export default {
  name:"PostForDetail",
  components:{
    PostModify
  },
  props:{
    post:Object,
    nickname:String,
    commentCount:Number,
  },
  data() {
    return {
      // nickname:'',
      viewImage:null,
      // post:{},
      tempComment:'',

      // 추후에 연결해줘야하는 부분 - 이미지(프로필 사진)
      profileImg: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
    }
  },
  computed:{
    flagLike(){
      return this.$store.state.post.flagLike
    },
    flagScrap(){
      return this.$store.state.post.flagScrap
    },
    countLike(){
      return this.$store.state.post.countLike
    },
    // countComment(){
    //   const commentList = this.$store.state.comment.commentList
    //   return commentList.length
    // },
    date(){
      return timeForToday(this.post.post_date)
    },
    flagWriter(){
      return this.post.user_id===localStorage.userId
    }
  },
  watch:{

    // '$route':'fetchData'
  },
  created() {
    console.log(this.post)
    // this.fetchData()

    // 이미지 하나만 추출했어요.
    var input = this.post.post_image
    if (input && input[0]) {
      var reader = new FileReader();
      reader.onload = e => {
        this.viewImage = e.target.result;
      };
      reader.readAsDataURL(input[0]);
    } else {
        this.viewImage = null;
    }
  },
  methods:{
    
    // user가 좋아요 버튼 클릭 시 vuex에서 flag 변화 + 서버와 연결
    postLike(e){
      postApi.likePost({user_id:localStorage.getItem('userId'), post_id:this.post.post_id})
        .then((res)=>{
          if(res.data.message==='No Subscription'){
            alert('구독 후에 이용가능합니다.')
          }else{
            this.$store.dispatch('post/postLike',this.flagLike)
          }
          // console.log(res)
        })
        .catch(err=>{
          console.error(err)
        })
      
    },

    // user가 스크랩 버튼 클릭 시 vuex에서 flag 변화 + 서버와 연결
    postScrap(e){
      postApi.scrapPost({user_id:localStorage.getItem('userId'), post_id:this.post.post_id})
        .then((res)=>{
          if(res.data.message==='No Subscription'){
            alert('구독 후에 이용가능합니다.')
          }else{
            this.$store.dispatch('post/postScrap') 
          }
            // console.log(res)
          })
          .catch(err=>{
            console.error(err)
          })
    }
  }
}
</script>

<style scoped>
/* 이미지 여부, 댓글, 좋아요, 북마크 부분 */
#actions * {
  margin: 2px 2px !important;
}
/* 게시글 제목 */
#title {
  font-size: 20px;
}
/* 게시글 내용 */
#description {
  font-size: 16px;
}
#declare{
  color:var(--basic-color-new)
}
</style>