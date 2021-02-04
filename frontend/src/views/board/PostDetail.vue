<template>
  <div>
    <!-- chevron-left -->
    <b-container class="board">
      <b-row>
        <header>
          <router-link :to="{ name:'Board', params: {board_id:$route.params.board_id}}"><b-icon icon="chevron-compact-left" aria-hidden="true"></b-icon>Board</router-link>
          
        </header>
        <section>
          <Post/>
          <hr>
          <input 
            type="text" 
            placeholder="댓글을 입력해주세요" 
            id="comment"
            v-model="comment"
            @keypress.enter="createComment"
          >
          <CommentList/>
        </section>
      </b-row>
    </b-container>
    
  </div>
</template>

<script>
import Post from '@/components/board/postdetail/PostForDetail.vue'
import CommentList from '@/components/board/postdetail/CommentList.vue'

import * as commentApi from '@/api/comment';

export default {
  name:'PostDetail',
  data() {
    return {
      comment: '',
    }
  },
  components:{
    Post,
    CommentList,
  },
  methods:{
    
    createComment(){
      const params = {
        user_id: String(localStorage.userId),
        post_id: Number(this.$route.params.post_id),
        comment_description : String(this.comment)
      }

      commentApi.create(params)
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
