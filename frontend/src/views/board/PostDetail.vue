<template>
  <div>
    <!-- chevron-left -->
    <b-container class="board">
      <b-row>
        <header>
          <router-link :to="{ name:'Board', params: {board_id:$route.params.board_id}}"><b-icon icon="chevron-compact-left" aria-hidden="true"></b-icon>Board</router-link>
          
        </header>
        <section>
          <Post :post="post" :nickname="nickname"/>
          <hr>
          <input 
            type="text" 
            placeholder="댓글을 입력해주세요" 
            id="comment"
            v-model="comment"
            @keypress.enter="createComment"
          >
          <CommentList :comments="commentList"/>
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
      commentList:[]
    }
  },
  components:{
    Post,
    CommentList,
  },
  computed:{
    writeFlag(){
      return this.$store.state.comment.writeFlag
    },
  },
  watch:{
    writeFlag:'fetchData'
  },
  created(){
    this.fetchData()
  },
  methods:{
    fetchData(){
      this.loading=true
      postApi.getPost({
        login_id:String(localStorage.getItem('userId')),
        post_id:Number(this.$route.params.post_id)
      })
      .then(res=>{
        if(res.data.message==="NULL"){
          this.$router.push({ name: 'PageNotFound'})
        }else{
          console.log(res)
          this.post = res.data.postDto
          this.nickname = res.data.writer_nickname
          
          //좋아요와 스크립트 여부는 vuex에 저장해놔야함...
          this.$store.dispatch('post/isLiked',res.data.isLiked)
          this.$store.dispatch('post/isScrapped',res.data.isScrapped)
          //그리고 댓글 리스트를 여기서 가져오니까 이것도 vuex에 저장해야 할 듯?
          this.commentList = res.data.commentList
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
      const params = {
        user_id: String(localStorage.userId),
        post_id: Number(this.$route.params.post_id),
        comment_description : String(this.comment)
      }

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
        this.comment=''
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
#comment {
  width:100%;
  margin-bottom: 1rem;
}
</style>
