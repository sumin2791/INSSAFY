<template>
  <div>
    <!--https://www.npmjs.com/package/vue-masonry-css-->
  <masonry
    :cols="{default: 3, 576: 1}"
    :gutter="10"
    >
      <div class="bamboo-description">
        <h3>대나무숲</h3>
        <p>마음 터놓고 이야기 해요.<br>
          내 마음을 편하게 이야기하는 공간입니다
        </p>
      </div>
      <BambooPost
        v-for="(post,idx) in posts"
        :key="idx"
        :post="post"
      />
  </masonry>
  
  </div>
  
</template>

<script scoped>
import BambooPost from "@/components/curation/bamboo/BambooPost.vue"
// import Waterfall from 'vue-waterfall/lib/waterfall'
// import WaterfallSlot from 'vue-waterfall/lib/waterfall-slot'
// import {mapState} from 'vuex'

export default {
  name:"PostList",
  components:{
    BambooPost,
    // Waterfall,
    // WaterfallSlot,
  },
  computed:{
    posts() {
      return this.$store.state.posts
    },
  },
  created() {
  }
}

</script>

<style scoped>
.bamboo-list{
  /* display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-rows: auto; */
}
.bamboo-description{
  margin: 16px 0;
}

@media screen and (max-width: 576px) {
  .bamboo-list {
    display:inline-block;
    min-width: 100%;
  }
  .bamboo-description{
  display:inline-block;
  min-width: 100%;
}
  
}
</style>