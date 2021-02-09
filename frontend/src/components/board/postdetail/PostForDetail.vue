<template>
  <div class="post">
    <div class="post-header">
      <div class="user-profile-img">
        <b-avatar src="https://placekitten.com/300/300" size="4rem">유저프로필</b-avatar>
      </div>
      <div class="username-date-option">
        <div class="user-name-date">
          <div>
            <b-dropdown id="dropdown-left" class="user-name" variant="link" toggle-class="text-decoration-none" no-caret>
              <template #button-content>
                {{nickname}}
              </template>
              <b-dropdown-item href="#">Profile</b-dropdown-item>
              <b-dropdown-item href="#">메시지 보내기</b-dropdown-item>
              <!-- <b-dropdown-item href="#">Something else here</b-dropdown-item> -->
            </b-dropdown>
          </div>
          <div class="post-date">{{date}}</div>
        </div>
          <b-dropdown v-if="flagWriter" id="dropdown-left" class="user-name" variant="link" toggle-class="text-decoration-none" no-caret>
            <template #button-content>
              <v-btn icon x-small fab>
                <v-icon dark>
                  mdi-dots-vertical
                </v-icon>
              </v-btn>
            </template>
            <b-dropdown-item ><PostModify :post="post"/></b-dropdown-item>
            <b-dropdown-item >삭제</b-dropdown-item>
            <b-dropdown-item id="declare">신고</b-dropdown-item>
            <!-- <b-dropdown-item href="#">Something else here</b-dropdown-item> -->
          </b-dropdown>
      </div>
    </div>
    <div class="post-body">
      <div class="title f-text b-desc">{{post.post_title}}</div>
      <div class="description r-desc">{{post.post_description}}</div>

      <img v-if="viewImage" :src="viewImage" alt="이미지 미리보기...">
    </div>
    <div class="post-footer">
      <div class="post-like" @click="postLike" v-if="flagLike"><b-icon icon="emoji-smile-fill" aria-hidden="true" color="#AA2610"></b-icon> {{countLike}}</div>
      <div class="post-like" @click="postLike" v-if="!flagLike"><b-icon icon="emoji-smile" aria-hidden="true"></b-icon> {{countLike}}</div>
      <!-- <div class="post-comment"><b-icon icon="chat" aria-hidden="true"></b-icon> {{countComment}}</div> -->
      <div class="post-comment"><b-icon icon="chat" aria-hidden="true"></b-icon> {{commentCount}}</div>
      <div class="post-bookmark" @click="postScrap" v-if="flagScrap"><b-icon icon="bookmark-fill" aria-hidden="true"></b-icon></div>
      <div class="post-bookmark" @click="postScrap" v-if="!flagScrap"><b-icon icon="bookmark" aria-hidden="true"></b-icon></div>
    </div>
  </div>
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
      tempComment:''
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

<style>
.post{
  margin:1rem 0;
  background-color: #fff;
  padding: 1rem;
  border:1px #949590 solid;
  border-radius:10px;
}
.post .post-header{
  margin-bottom: 0.5rem;
  display: flex;
  flex-direction: row;
}
.username-date-option{
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  width:100%;
}
.post .post-body{
  min-height: 150px;
}
img{
  /* height: 10rem; */
  width: 70%;
}
.post .post-footer{
  display:flex;
  flex-direction: row;
  justify-content: flex-end;
  margin-top:1rem;
}
.post-comment{
  margin-left: 0.5rem;
}
.post-bookmark{
  margin-left:0.5rem;
}
.post .title{
  font-weight: bold;
  font-size:1.5rem;
  margin-top: 0.5rem;
  margin-bottom: 1rem;
}
.user-profile-img{
  margin-right: 0.5rem;
}
.user-name button{
  padding:0;
  color:black;
}
.user-name button:hover{
  color:black;
  text-decoration: none;
}
.dropdown-item{
  padding:0 0.3rem !important;
}
.user-name-date{
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: auto 0;
}
.dropdown-menu {
   min-width: 3.1rem !important;
}
#declare{
  color:var(--basic-color-new)
}
</style>