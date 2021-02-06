<template>
  <b-container>
    <b-row class="comment" v-cloak>
      <b-col sm="2" class="comment-header">
        <div class="user-profile-img">
          <b-avatar src="https://placekitten.com/300/300" size="2rem">유저프로필</b-avatar>
        </div>
        <div class="header-detail">
          <div class="user-name-date">
            <b-dropdown id="dropdown-left" class="user-name" variant="link" toggle-class="text-decoration-none" no-caret>
              <template #button-content>
                {{comment.user_nickname}}
              </template>
              <b-dropdown-item href="#">Profile</b-dropdown-item>
              <b-dropdown-item href="#">메시지 보내기</b-dropdown-item>
            </b-dropdown>
            <div class="comment-date">{{date}}</div>
          </div>
          <!-- <div @click="showBtn" id="btnCommentMobile">
            <div>수정</div>
            <div>삭제</div>
          </div> -->
        </div>
      </b-col>
      <b-col class="main">
        <div class="comment-description" @mouseover="showBtn" v-if="!Edit">
          {{commentDescription}}
        </div>
        <div
          class="comment-description"
          v-if="Edit"
        >
          <v-text-field
            dense
            label=""
            v-model="tempComment"
            class="text-h5"
            color="grey-darken-4"
          ></v-text-field>
        </div>
        <div id="btnComment" v-if="!Edit">
          <div @click="btnCommentModify">수정</div>
          <div @click="btnCommentDelete">삭제</div>
        </div>
        <div class="edit-button-set" v-if="Edit">
          <button class="p-button r-desc" @click="submit">Edit</button>
          <button class="p-button-cancel r-desc" @click="cancel">cancel</button>
        </div>
        
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import * as commentApi from '@/api/comment'

import timeForToday from '@/plugins/timeForToday'
import deepClone from '@/plugins/deepClone'

export default {
  name:"Comment",
  props:{
    comment:Object,
  },
  data(){
    return {
      commentDescription:this.comment.comment_description,
      Edit:false,
      tempComment:'',
    }
  },
  computed:{
    date(){
      return timeForToday(this.comment.comment_date)
    }
  },
  mounted(){
    this.tempComment = this.commentDescription
  },
  methods:{
    showBtn(e) {
      // 댓글쓴 사람이랑 로그인한 사람이랑 같아야, 다음 버튼이 보인다.
      function sleep(ms) {
        return new Promise((r) => setTimeout(r, ms))
      }
      if(this.comment.user_id===localStorage.userId){
        e.path[1].querySelector('#btnComment').style.visibility="visible"
        sleep(2000).then(() => {
          if(typeof e === Object || !Object.keys(e.path[1].querySelector('#btnComment')).includes('style'))
            e.path[1].querySelector('#btnComment').style.visibility="hidden"
        })
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
    cancel(){
      this.tempComment = this.commentDescription
      this.Edit = !this.Edit
    },
    submit(){
      this.commentDescription = this.tempComment
      this.cancel()
      // 아직 수정부분은 완료되지 않았습니다
      let params = {}
      params.commentDto = deepClone(this.comment)
      params.commentDto.comment_description = this.commentDescription
      params.login_id = String(localStorage.userId)
      delete params.commentDto.user_nickname
      console.log(params)
      commentApi.comment_modify(params)
        .then(res=>{
          console.log(res.data.message)
          if(res.data.message==='No Permission'){
            alert('구독 후에 이용가능합니다.')
          }else{
            this.$store.dispatch('comment/isWriteFlag')
          }
        })
        .catch(err=>{
          console.log(err)
        })
      alert(`수정!`);
    },
  }
}
</script>

<style scoped>
.row .comment{
  display:flex;
  /* margin-bottom: 1rem; */
  padding:0.8rem;
  flex:1;
}
.comment-description{
  display:flex;
  /* margin-bottom: 1rem; */
  padding:0rem;
  flex:1;
  margin:auto 0;
}
.comment .comment-header{
  display: flex;
  /* margin-bottom: 0.5rem; */
  padding-left:0;
  padding-right:0;
}
.comment .comment-header .header-detail{
  /* display:flex; */
  /* justify-content:space-between; */
  width: 100%;
}
.user-name-date{
  display:grid;
  grid-template-columns: 1fr;
  align-items: center;
}
.comment-date{
  
  font-size:0.6rem;
}
#btnComment{
  visibility:hidden;
}
.edit-button-set{
  display: flex;
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
.p-button-cancel {
  margin-left: 5px;
  margin-top: 3px;
  font-size: 14px;
  padding: 4px 8px;
  border: 1px solid #000;
  border-radius: 30px;
  transition: background-color 0.3s, color 0.3s ease;
}
.p-button-cancel:hover,
.p-button-cancel:active {
  color: #fff;
  background-color: #aa2610 !important;
}
/* #btnCommentMobile{
  display:flex;
} */
.user-name{
  display: inline-block;
  font-size:1fr;
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
  .user-name-date{
  display:flex;
  flex-direction: row;
  justify-content: space-between;
  /* grid-template-columns: 1fr 0.2fr; */
}
}
@media screen and (min-width:576px){
  #btnCommentMobile{
    display: none;
  }
}
</style>