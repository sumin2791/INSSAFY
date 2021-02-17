<template>
  <div>
    <div>
      <Post
        v-for="(post,idx) in posts"
        :key="idx"
        :post="post"
        :flagComponent="flagComponent"
      />
    </div>
    <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
      <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
    </infinite-loading>
  </div>
</template>

<script scoped>
import Post from "@/components/board/Post.vue"
// import {mapState} from 'vuex'P

//board api
import * as postApi from '@/api/post';
import InfiniteLoading from 'vue-infinite-loading';

export default {
  name:"PostList",
  data() {
    return {
      posts:[],
      page:0,
    }
  },
  components:{
    Post,
    InfiniteLoading
  },
  watch:{
    writeFlag:function(){
      this.page=0
      location.reload();
    },
    re:function(){
      this.page=0
      location.reload();
      // this.infiniteHandler()
    },
  },
  computed:{
    writeFlag(){
      return this.$store.state.board.writeFlag
    },
    // 재사용의 핵심
    flagComponent(){
      
      let flag = {
        state:false,
        headerMarket:false,
        headerLearnShare:false,
      }
      const curationName = this.$route.name
      if(curationName==="Market"){
        flag.state = true
        flag.headerMarket = true
        return flag

      }else if(curationName==="LearnShare"){
        flag.state = false
        flag.headerLearnShare = true
      }
      return flag
    },
    re(){
      return this.$route.params.board_id
    }
  },
  created() {
  },
  methods: {
    // 재사용의 핵심
    infiniteHandler($state){
      console.log($state)
      const curationName = this.$route.name
      let BOARD_ID
      if(curationName==="Board" || curationName==="Study"){

        BOARD_ID = Number(this.$route.params.board_id)
      }else{
        BOARD_ID = this.$store.state.curationId[curationName]

      }
      // if(curationName!="Board" && curationName!="Study"){
      // }else{
      // }
      const EACH_LEN = 4

      postApi.getPostList({board_id:BOARD_ID, user_id:localStorage.userId,page:this.page,size:EACH_LEN})
      .then((res)=>{
        setTimeout(()=>{
          if(res.data.postList){
            this.posts = this.posts.concat(res.data.postList);
            this.page += 1;
            $state.loaded();
            if(res.data.postList.length / EACH_LEN <1){
              $state.complete();
            }
          }else{
            $state.complete();
          }
        },1000);
      })
      .catch(err=>{
        console.log(err)
      })
  },
  
  }
}
</script>

<style></style>