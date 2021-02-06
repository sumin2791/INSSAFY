<template>
  <b-container>
    <b-row class="comment" v-cloak>
      <b-col sm="2" class="comment-header">
        <div class="user-profile-img">
          <b-avatar src="https://placekitten.com/300/300" size="3rem">유저프로필</b-avatar>
        </div>
        <div class="header-detail">
          <div class="user-name-date">
            <b-dropdown id="dropdown-left" class="user-name" variant="link" toggle-class="text-decoration-none" no-caret>
              <template #button-content>
                {{comment.user_nickname}}
              </template>
              <b-dropdown-item href="#">Profile</b-dropdown-item>
              <b-dropdown-item href="#">메시지 보내기</b-dropdown-item>
              <!-- <b-dropdown-item href="#">Something else here</b-dropdown-item> -->
            </b-dropdown>
            <div class="post-date">{{date}}</div>
          </div>
          <div @click="showBtn" id="btnCommentMobile">
            <div>수정</div>
            <div>삭제</div>
          </div>
        </div>
      </b-col>
      <b-col class="main">
        <div class="comment" @mouseover="showBtn" v-if="!Edit">
          {{commentDescription}}
        </div>
        <div
          class="comment"
          v-if="Edit"
        >
          <v-text-field
            dense
            label=""
            v-model="commentDescription"
            class="text-h5"
            color="grey-darken-4"
          ></v-text-field>
        </div>
        <div id="btnComment" v-if="!Edit">
          <div @click="btnCommentModify">수정</div>
          <div @click="btnCommentDelete">삭제</div>
        </div>
        <button class="p-button r-desc" v-if="Edit" @click="submit">Edit</button>
        
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import * as commentApi from '@/api/comment'

import timeForToday from '@/plugins/timeForToday'

export default {
  name:"Comment",
  props:{
    comment:Object,
  },
  data(){
    return {
      commentDescription:this.comment.comment_description,
      Edit:false,
      // flagComment:false
    }
  },
  computed:{
    date(){
      return timeForToday(this.comment.comment_date)
    }
  },
  methods:{
    showBtn(e) {
      // 댓글쓴 사람이랑 로그인한 사람이랑 같아야, 다음 버튼이 보인다.
      function sleep(ms) {
        return new Promise((r) => setTimeout(r, ms))
      }
      if(this.comment.user_id===localStorage.userId){
        e.path[1].querySelector('#btnComment').style.visibility="visible"
        sleep(2000).then(() => e.path[1].querySelector('#btnComment').style.visibility="hidden")
      }
      
    },
    btnCommentDelete() {
      commentApi.comment_delete({login_id:localStorage.userId, comment_id:this.comment.comment_id})
        .then(res=>{
          if(res.data.message==='No Permission'){
            alert('구독 후에 이용가능합니다.')
          }else{
            this.$store.dispatch('comment/isWriteFlag')
          }
        })
        .catch(err=>{
          console.log(err)
        })
    },
    btnCommentModify() {
      
      this.Edit = !this.Edit
      alert(`Edit태그 불러오기!`);
    },
    submit(){
      // 아직 수정부분은 완료되지 않았습니다
      // let params = {}
      // params.commentDto = this.comment
      // params.commentDto.comment_description = this.commentDescription
      // params.login_id = localStorage.userId
      // console.log(params)
      // commentApi.comment_modify(params)
      //   .then(res=>{
      //     console.log(res.data.message)
      //     if(res.data.message==='No Permission'){
      //       alert('구독 후에 이용가능합니다.')
      //     }else{
      //       this.$store.dispatch('comment/isWriteFlag')
      //     }
      //   })
      //   .catch(err=>{
      //     console.log(err)
      //   })
      this.Edit = !this.Edit
      alert(`수정!`);
    },
  }
}
</script>

<style scoped>
.comment{
  display:flex;
  /* margin-bottom: 1rem; */
  padding:0.8rem;
  flex:1;
}
.comment .comment-header{
  display: flex;
  /* margin-bottom: 0.5rem; */
  padding-left:0;
  padding-right:0;
}
.comment .comment-header .header-detail{
  display:flex;
  justify-content:space-between;
  width: 100%;
}
#btnComment{
  visibility:hidden;
}
.p-button {
  margin-left: 5px;
  margin-top: 3px;
  font-size: 14px;
  padding: 4px 8px;
  border: 1px solid #000;
  border-radius: 30px;
  transition: background-color 0.3s, color 0.3s ease;
}
.p-button:hover,
.p-button:active {
  color: #fff;
  background-color: #000 !important;
}
/* #btnCommentMobile{
  display:flex;
} */
.user-name{
  display: inline-block;
  font-size:1fr;
}
.post-date{
  font-size:0.5em;
}
.main{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0;
  /* width:100%; */
}
@media screen and (max-width:576px){
  #btnComment{
    display: none;
  }
}
@media screen and (min-width:576px){
  #btnCommentMobile{
    display: none;
  }
}
</style>