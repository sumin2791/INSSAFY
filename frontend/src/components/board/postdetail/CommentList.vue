<template>
  <div>
    <Comment
      v-for="(comment,idx) in comments"
      :key="idx"
      :comment="comment"
    />
  </div>
</template>

<script>
import Comment from "@/components/board/postdetail/Comment.vue"
// import {mapState} from 'vuex'

export default {
  name:"CommentList",
  components:{
    Comment
  },
  props:{
    comments:Array,
  },
  computed:{
    // comments() {
    //   return this.$store.state.comment.commentList
    // },
  }
}
</script>

<style>

</style>