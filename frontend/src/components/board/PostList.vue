<template>
  <div>
    <Post
      v-for="(post,idx) in posts"
      :key="idx"
      :post="post"
    />
  </div>
</template>

<script scoped>
import Post from "@/components/board/Post.vue"
// import {mapState} from 'vuex'

//board api
import * as postApi from '@/api/post';

export default {
  name:"PostList",
  data() {
    return {
      posts:[]
    }
  },
  components:{
    Post
  },
  watch:{
    writeFlag:'create'
  },
  computed:{
    writeFlag(){
      return this.$store.state.board.writeFlag
    }
  },
  created() {
    this.create()
  },
  methods: {
    create(){
      const BOARD_ID = Number(this.$route.params.board_id)
    postApi.getPostList(BOARD_ID)
      .then(res=>{
        console.log(res)
        this.posts = res.data.postList
      })
      .catch(err=>{
        console.log(err)
      })
    }
  }
}
</script>

<style>

</style>