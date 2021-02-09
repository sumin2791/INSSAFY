<template>
  <div class="post">
    <div class="post-header">
      <div class="user-profile-img">
        <b-avatar src="https://placekitten.com/300/300" size="4rem">유저프로필</b-avatar>
      </div>
      <div class="user-name-date">
        <div>
          <b-dropdown id="dropdown-left" class="user-name" variant="link" toggle-class="text-decoration-none" no-caret>
            <template #button-content>
              {{post.user_nickname}}
            </template>
            <b-dropdown-item href="#">Profile</b-dropdown-item>
            <b-dropdown-item href="#">메시지 보내기</b-dropdown-item>
            <!-- <b-dropdown-item href="#">Something else here</b-dropdown-item> -->
          </b-dropdown>
        </div>
        <div class="post-date">{{date}}</div>
      </div>
    </div>
    <div class="post-body" @click="goToDetail">
      <div class="title f-text b-desc">{{post.post_title}}</div>
      <div class="description r-desc">{{post.post_description}}</div>
    </div>
    <div class="post-footer">
      <!-- <div class="post-like" @click="postLike" v-if="flagLike"><b-icon icon="emoji-smile-fill" aria-hidden="true" color="#AA2610"></b-icon> <div v-if="isLike"> {{post.post_like}}</div><div v-else>0</div></div>
      <div class="post-like" @click="postLike" v-if="!flagLike"><b-icon icon="emoji-smile" aria-hidden="true"></b-icon> <div v-if="isLike"> {{post.post_like}}</div><div v-else>0</div></div> -->
      <div class="post-like" @click="postLike" v-if="flagLike"><b-icon icon="emoji-smile-fill" aria-hidden="true" color="#AA2610"></b-icon>  {{countLike}}</div>
      <div class="post-like" @click="postLike" v-if="!flagLike"><b-icon icon="emoji-smile" aria-hidden="true"></b-icon> {{countLike}}</div>
      <div class="post-comment"><b-icon icon="chat" aria-hidden="true"></b-icon> <div v-if="isComment"> {{post.comment_count}}</div><div v-else>0</div></div>
      <div class="post-bookmark" @click="postScrap" v-if="flagScrap"><b-icon icon="bookmark-fill" aria-hidden="true"></b-icon></div>
      <div class="post-bookmark" @click="postScrap" v-if="!flagScrap"><b-icon icon="bookmark" aria-hidden="true"></b-icon></div>
    </div>
  </div>
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
.post .post-body{
  min-height: 150px;
  cursor: pointer;
}
.post .post-footer{
  display:flex;
  flex-direction: row;
  justify-content: flex-end;
  margin-top:1rem;
}
.post-like{
  display:flex;
  align-items: center;
  margin-left: 0.5rem;
}
.post-comment{
  display:flex;
  align-items: center;
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
}
</style>