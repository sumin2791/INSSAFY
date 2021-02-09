<template>
  <v-card
    class="mx-auto
      my-2
      d-flex
      flex-column"
    color="#F9F9F9"
  >
    <!-- 포스트 작성자 부분 -->
    <v-card-title class="d-flex flex-row align-center ma-0 py-2 pb-0">
      <v-list-item-avatar class="rounded-circle">
        <v-img
          class="elevation-6"
          alt=""
          :src="profileImg"
        ></v-img>
      </v-list-item-avatar>
      <!-- 작성자이름, 작성일자 -->
      <div class="text-caption">
        <div>{{post.user_nickname}}</div>
        <div>{{date}}</div>
      </div>
      <v-spacer></v-spacer>
    </v-card-title>
    
    <div class="post-body" @click="goToDetail">
      <!-- 포스트 제목 -->
      <v-col
        class="font-weight-black"
      >{{post.post_title}}</v-col>
      <!-- 게시글 내용 -->
      <v-col>
        {{post.post_description}}
      </v-col>
    </div>
    <!-- 게시글 관련 이미지/댓글/좋아요 들어갈 부분 -->
    <v-card-actions id="actions">
      <!-- 이미지 -->
      <v-icon v-if="haveImg" middle>mdi-image</v-icon>
      <!-- 댓글 수 -->
      <v-icon
        middle
      >mdi-comment-processing</v-icon>
      <span v-if="isComment">{{ post.comment_count }}</span>
      <span v-else>0</span>
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
      <v-spacer></v-spacer>
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
      
    </v-card-actions>
  </v-card>
</template>

<script>
import * as postApi from '@/api/post'
import timeForToday from '@/plugins/timeForToday'

export default {
  name:"Post",
  props:{
    post:Object
  },
  data() {
    return {
      flagLike:false,
      flagScrap:false,
      countLike:0,

      // 추후에 연결해줘야하는 부분 - 이미지(프로필 사진)
      profileImg: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
      // 추후에 연결해줘야하는 부분 - 게시글 내 이미지 포함 여부
      haveImg: true,
    }
  },
  computed:{
    onceLiked() {
      return Object.keys(this.post).includes('isLiked')
    },
    onceScrapped(){
      return Object.keys(this.post).includes('isScrapped')
    },
    isComment() {
      return Object.keys(this.post).includes('comment_count')
    },
    isLike() {
      return Object.keys(this.post).includes('like_count')
    },
    date(){
      let date = this.post.post_date.split('.')[0]
      date = date.split('T').join(' ')
      return timeForToday(date)
    }
  },
  mounted() {
    this.fetchData()
  },
  
  methods:{
    fetchData(){
      if(this.onceLiked){
        if(this.post.isLiked===0){
          this.flagLike=false
        }else{
          this.flagLike=true
        }
      }

      if(this.onceScrapped){
        if(this.post.isScrapped===0){
          this.flagScrap=false
        }else{
          this.flagScrap=true
        }
      }
      
      this.countLike = this.post.post_like

    },
    goToDetail() {
      console.log(this.post.post_id)
      // params를 이용해서 데이터를 넘겨줄 수 있다.
      this.$router.push({ name: 'Post', params: { board_id:this.$route.params.board_id, post_id: this.post.post_id }})
      // this.$router.push({ name: 'Post', params: {post:this.post} });
    },
    postLike(e){
      postApi.likePost({user_id:localStorage.getItem('userId'), post_id:this.post.post_id})
        .then((res)=>{
          if(res.data.message==='No Subscription'){
            alert('구독 후에 이용 가능합니다')
          }else{
            if(this.flagLike){
              this.countLike -= 1
            }else{
              this.countLike += 1
            }
            this.flagLike = !this.flagLike
          }
        })
        .catch((err)=>{
          console.err(err)
        })

      // 포스트좋아하는거 카운트 바꾸기 위한 지금 이 방식은 bug가 존재합니다. (유저와 연동이 안되어 있기 때문) 
    },
    postScrap(){
      postApi.scrapPost({user_id:localStorage.getItem('userId'), post_id:this.post.post_id})
        .then((res)=>{
          if(res.data.message==='No Subscription'){
            alert('구독 후에 이용 가능합니다')
          }else{
            this.flagScrap = !this.flagScrap
          }
        })
        .catch((err)=>{
          console.log(err)
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
/* 포스트 본문 클릭시 pointer 변화 */
.post-body {
  cursor: pointer;
}
</style>