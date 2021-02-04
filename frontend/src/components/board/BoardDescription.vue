<template>
  <div class="board-description">
    <div class="board-name-detail">
      <div class="name-setting">
        <div class="board-name">{{this.board.name}}</div>
        <a href=""><b-icon icon="gear-fill"></b-icon></a>
      </div>
      <div class="board-detail">{{this.board.description}}</div>
    </div>
    <div class="board-hash-people">
      <div class="hashtaglist">
        <div class="board-hashtag" v-for="(hash,idx) in this.board.hashtags" :key="idx">{{hash}}</div>
      </div>
      <div class="board-people"><b-icon icon="person-fill" aria-hidden="true"></b-icon> 50</div>
    </div>
  </div>
</template>

<script>
import * as boardApi from '@/api/board';


export default {
  name:"BoardDescription",
  data() {
    return {
      loading: false,
      board:{
        name:'',
        description:'',
        hashtags:[],
        subscribe_count:50,
        location:'',
        igmyeong:'',
      }
    }
  },
  created() {
    this.fetchData()
  },
  watch:{
    '$route':'fetchData'
  },
  methods: {
    fetchData(){
      this.loading=true
      boardApi.board_detail(this.$route.params.board_id)
        .then(res=>{
          if(res.data.message==="NULL"){

            this.route.push({ name: 'PageNotFound'})
          }else{
            this.board.name=res.data.boardDto.board_name
            this.board.description=res.data.boardDto.board_description
            this.board.hashtags=res.data.boardDto.board_hash.split('|')
            if(this.board.hashtags[0]===''){
              this.board.hashtags=[]
            }
            this.board.location=res.data.boardDto.board_location
            this.board.igmyeong=res.data.boardDto.board_igmyeong
          }
        })
        .catch(err=>{
          console.log(err)
        })

    }
  }

}
</script>

<style scoped>
.board-description{
  display: flex;
  flex-direction: column;
}
.name-setting{
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 0.5rem;
}
.board-name{
  font-weight: bold;
  font-size:1.3rem;
  margin:0;
}
.board-hash-people{
  display: grid;
  place-items: center;
  grid-template-columns: 70% 30%
}
.board-hashtag{
  display:inline;
  background-color: #0B2945;
  border-radius: 0.5rem;
  color:#f9f9f9;
  padding-left:0.2rem;
  padding-right:0.2rem;
  margin: 0.3rem;
}
</style>