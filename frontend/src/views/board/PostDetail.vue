<template>
  <div>
    <!-- chevron-left -->
    <b-container class="board">
      <b-row>
        <header>
          <router-link :to="{ name:'Board', params: {board_id:$route.params.board_id}}"><b-icon icon="chevron-compact-left" aria-hidden="true"></b-icon>Board</router-link>
          
        </header>
        <section>
          <Post :post="post" :nickname="nickname" :commentCount="commentCount"/>
          <div class="comment-set">
            <input 
              type="text" 
              placeholder="착한 한마디 남겨주세요 :)" 
              id="comment"
              v-model="comment"
              @keypress.enter="createComment"
            >
            <button class="btn-submit" @click="createComment">한마디!</button>
          </div>
          <CommentList :comments="comments"/>
        </section>
      </b-row>
    </b-container>
    
  </div>
</template>

<script>
import Post from '@/components/board/postdetail/PostForDetail.vue'
import CommentList from '@/components/board/postdetail/CommentList.vue'

import * as commentApi from '@/api/comment';
import * as postApi from '@/api/post'

export default {
  name:'PostDetail',
  data() {
    return {
      comment: '',
      post:{},
      nickname:'',
      comments:[],
      commentCount:0,
    }
  },
  components:{
    Post,
    CommentList,
  },
  computed:{
    flagComment(){
      return this.$store.state.comment.flagComment
    },
    flagModify(){
      return this.$store.state.post.flagModify
    },
  },
  watch:{
    flagComment:'fetchData',
    flagModify : 'fetchData'
  },
  mounted(){
    this.fetchData()
  },
  methods:{
    fetchData(){
      console.log('댓글수정했는데 이게 되나?')
      this.loading=true
      postApi.getPost({
        login_id:String(localStorage.getItem('userId')),
        post_id:Number(this.$route.params.post_id)
      })
      .then(res=>{
        if(res.data.message==="NULL"){
          this.$router.push({ name: 'PageNotFound'})
        }else{
          this.post = res.data.postDto
          this.nickname = res.data.writer_nickname
          
          //좋아요와 스크립트 여부는 vuex에 저장해놔야함...
          this.$store.dispatch('post/isLiked',res.data.isLiked)
          this.$store.dispatch('post/isScrapped',res.data.isScrapped)
          //그리고 댓글 리스트를 여기서 가져오니까 이것도 vuex에 저장해야 할 듯?
          this.comments = res.data.commentList
          this.commentCount = this.comments.length
          // this.$store.dispatch('comment/getCommentList',res.data.commentList)
          // 포스트 라이크 카운트, 댓글 카운트 도 vuex에!
          this.$store.dispatch('post/isLikeCount',this.post.post_like)
        }
        
      })
      .catch(err=>{
        console.log(err)
      })
    },
    createComment(){
      if(this.comment.trim()!=""){
        const params = {
          user_id: String(localStorage.userId),
          post_id: Number(this.$route.params.post_id),
          comment_description : String(this.comment)
        }
        this.comment=''
        commentApi.comment_create(params)
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
      }else{
        alert('빈칸이에요..')
      }
        
      // this.$store.dispatch('createComment',params)
    }
  }
  
}
</script>

<style scoped>
.board{
  max-width: 1024px !important;
  margin: 0 auto;
}
header{
  display:flex !important;
  width: 100%;
  flex-direction: column;
}
header a{
  font-size:2rem;
  text-decoration: none;
}
section {
  margin:0 2%;
  width:96%;
}
.comment-set{
  display: flex;
  margin-bottom: 1rem;
}
#comment {
  width:90%;
  border: 1px solid #949590 ;
  border-radius: 1rem;
  height: 2.5rem;
  padding:0.1rem 1rem
}
.btn-submit {
  padding:0.1rem 1rem;
  margin: 0 auto;
  font-size:0.8em;
  border-radius: 1rem;
  border: solid 1px #000;
  transition: color 0.3s, background-color 0.3s ease;
}
.btn-submit:hover,
.btn-submit:active {
  background-color: #000 !important;
  color: #fff;
}
@media screen and (max-width:576px){
  #comment{
    width:100%;
  }
  .btn-submit{
    display: none;
  }
}
</style>
