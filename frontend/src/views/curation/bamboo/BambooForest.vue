<template>
  <div class="board">
    <!-- 대나무숲 해더 -->
    <div class="bamboo-hd">
      <div class="bamboo-title">Curation</div>
      <PostWrite class="bamboo-write"/>
    </div>
    
    <BambooList/>
  </div>
</template>

<script>
// import BambooPost from '@/views/curation/bamboo/BambooPost.vue'
import PostWrite from '@/components/curation/bamboo/PostWrite.vue'
import BambooList from '@/components/curation/bamboo/BambooList.vue'

export default {
  name: "BambooForest",
  components: {
    // BambooPost,
    PostWrite,
    BambooList,
  }
}
</script>

<style scoped>
.board{
  max-width: 1200px !important;
  margin: 0 auto;
  margin-top:30px;
  /* width:80%;
  margin-left:10%;
  margin-right:10%; */
}


/* 해더부분 */
.bamboo-hd {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  border: 1px solid;
}

.bamboo-title {
  font-size: 1.5rem;
  border: 1px solid;
  background-color: #0B2945;
  flex-basis: 20%;
  color:#f9f9f9;
  padding-left:1rem;
}
.bamboo-write{
  flex-basis: 50%;
}

/* 모바일 웹화면 */
@media (max-width: 425px) {
  .bamboo-post-list {
    flex-direction: column;
  }
  /* 입력하는 창 고정시키고 밑으로 나오게하기 - 구현하기 */
}
</style>