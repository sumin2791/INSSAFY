<template>
  <div class="board-description">
    <div class="board-name-detail">
      <div class="name-setting">
        <div class="board-name">{{this.board.name}}</div>
        <b-icon icon="gear-fill" v-if="isManager && !Edit" style="cursor:pointer" @click="btnModify"></b-icon>
      </div>
      <!--보드 show & edit-->
      <div class="board-detail" v-if="!Edit">{{this.board.description}}</div>
      <div
        class="board-detail-form"
        v-if="Edit"
      >
        <v-textarea
          solo
          clearable
          no-resize
          clear-icon="mdi-close-circle"
          label=""
          v-model="tempDescription"
          color="grey-darken-4"
        ></v-textarea>
      </div>
    </div>
    <!--해쉬태그 show & edit -->
    <div class="board-hash-people" v-if="!Edit">
      <div class="hashtaglist">
        <div class="board-hashtag" v-for="(hash,idx) in this.board.hashtags" :key="idx">{{hash}}</div>
      </div>
      <div class="board-people">
        <v-icon left>
          mdi-account-group
        </v-icon>{{board.subscribe_count}}</div>
    </div>
    <div
      class="board-hash-form"
      v-if="Edit"
    >
      <v-text-field
        dense
        label="해쉬태그"
        v-model="tempHashtag"
        class="text-h5"
        color="grey-darken-4"
        @keypress.enter="addHashtag"
      ></v-text-field>
      <div
        class="hashtaglist-form"
        v-for="(hashtag, idx) in tempHashtags"
        :key="idx">
        <v-chip
          class="ma-2"
          close
          color="indigo darken-4"
          text-color="white"
          @click:close="deleteHashtag(idx)"
        > {{ hashtag }}
        </v-chip>
      </div>
    </div>
    <div class="edit-button-set" v-if="Edit">
      <div>
        <!-- <p class="r-desc delete-button" @click="boardDelete">삭제</p> -->
        <p class="r-desc delete-button" v-b-modal.modal-delete>삭제</p>
        <b-modal id="modal-delete" title="🗑" @ok="boardDelete">
          <p class="my-4">보드를 삭제하시겠어요?</p>
          <template #modal-footer="{ok}">
            <b-button variant="delete" @click="ok()">
              삭제하기
            </b-button>
          </template>
        </b-modal>
      </div>
      <div>
        <button class="p-button-cancel r-desc" @click="cancel">cancel</button>
        <button class="p-button r-desc" @click="submit">  Edit  </button>
      </div>
    </div>
    <div class="careful-line"></div>
  </div>
</template>

<script>
import * as boardApi from '@/api/board';
import {imageDelete} from '@/api/main';
import deepClone from '@/plugins/deepClone';


export default {
  name:"BoardDescription",
  props:{
    inBoard:Boolean,
    isManager:Boolean,
  },
  data() {
    return {
      loading: false,
      board:{
        id:'',
        name:'',
        description:'',
        hashtags:[],
        subscribe_count:0,
        location:'',
        igmyeong:'',
        image:''
      },
      tempDescription:'',
      tempHashtags: [],
      tempHashtag:'',
      Edit:false,
    }
  },
  created() {
    this.fetchData()
    this.tempDescription = this.board.description
  },
  watch:{
    '$route':'fetchData',
    inBoard:'count'
  },
  methods: {
    fetchData(){
      boardApi.board_detail(this.$route.params.board_id)
        .then(res=>{
          console.log(res)
          if(res.data.message==="NULL"){

            this.$router.push({ name: 'PageNotFound'})
          }else{
            this.board.id = res.data.boardDto.board_id
            this.board.name=res.data.boardDto.board_name
            this.board.description=res.data.boardDto.board_description
            this.board.hashtags=res.data.boardDto.board_hash.split('|')
            if(this.board.hashtags[0]===''){
              this.board.hashtags=[]
            }
            this.board.location=res.data.boardDto.board_location
            this.board.igmyeong=res.data.boardDto.board_igmyeong
            this.board.subscribe_count = res.data.board_count
            this.tempDescription = res.data.boardDto.board_description
            this.tempHashtags = deepClone(this.board.hashtags)
            this.image = res.data.boardDto.board_image
            this.$emit('board-image',res.data.boardDto.board_image)
          }
        })
        .catch(err=>{
          console.log(err)
        })
    },
    count(){
      if(this.inBoard){
        this.board.subscribe_count += 1
      }else{
        this.board.subscribe_count -= 1
      }
    },
    btnModify() {
      this.Edit = !this.Edit
      alert(`Edit태그 불러오기!`);
    },
    addHashtag(){
      if(this.tempHashtags.length===5){
        alert('해쉬태그는 5개 이하만 가능합니다.')
        return
      }

      this.tempHashtags.push(`#${this.tempHashtag}`)
      this.tempHashtag=''
    },
    deleteHashtag(e){
      this.tempHashtags.splice(e,1)
    },
    cancel(){
      this.tempHashtags = deepClone(this.board.hashtags)
      this.tempDescription = this.board.description
      this.tempHashtag=''
      this.Edit = !this.Edit
    },
    submit(){
      this.board.hashtags = [this.board.hashtags, ...this.tempHashtags]
      this.board.description = this.tempDescription
      this.board.hashtags = deepClone(this.tempHashtags)
      boardApi.board_modify(this.board,localStorage.userId)
      .then(res=>{
        console.log(res)
      })
      .catch(err=>{
        console.log(err)
      })
      this.cancel()
      alert(`수정!`);
    },

    //보드삭제
    async boardDelete(){
      if(!this.isManager){
        return
      }

      try{
        if(this.board.image!=''){
          await imageDelete(this.board.image)
          .then(res=>{
            console.log('이미지 삭제 완료!')
          })
          .catch(err=>{
            console.log(err)
          })
        }
  
        await boardApi.board_delete(Number(this.board.id),localStorage.userId)
        .then(res=>{
          console.log('보드 삭제')
          this.$router.push({name:'Main'})
        })
        .catch(err=>{
          console.log(err)
        })
      }catch(err){
        console.log('PostForDetail- 보드 삭제 에러')
        console.log(err)
      }
    }
  }

}
</script>

<style scoped>
/* 전체 description-container */
/* .board-description{
  display: flex;
  flex-direction: column;
  padding: 5% 10%;
  min-height: 30vh;
} */
.name-setting{
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1em;
}
.board-detail{
  margin: 1em 0;
  padding:0 0 0 1em;
  min-height: 15vh;
}
.board-detail-form{
  padding:0 0.5rem;
}
.p-button {
  margin-left: 5px;
  margin-top: 3px;
  font-size: 14px;
  padding: 4px 8px;
  border: 1px solid #000;
  border-radius: 30px;
  transition: background-color 0.3s, color 0.3s ease;
}
.p-button:hover,
.p-button:active {
  color: #fff;
  background-color: #000 !important;
}
.p-button-cancel {
  margin-left: 5px;
  margin-top: 3px;
  font-size: 14px;
  padding: 4px 8px;
  border: 1px solid #000;
  border-radius: 30px;
  transition: background-color 0.3s, color 0.3s ease;
}
.p-button-cancel:hover,
.p-button-cancel:active {
  color: #fff;
  background-color: #aa2610 !important;
}
.board-detail{
  margin:0.5rem 0;
  padding:0 0.5rem;
}
.board-detail-form{
  padding:0 0.5rem;
}
.btn-delete {
  margin-left: 5px;
  margin-top: 3px;
  font-size: 14px;
  padding: 4px 8px;
  border: 1px solid #aa2610 !important;
  border-radius: 30px;
  color: #fff;
  background-color: #aa2610 !important;
  transition: background-color 0.3s, color 0.3s ease;
}
.btn-delete:hover,
.btn-delete:active {
  color: #fff;
  background-color: #f0725b !important;
}
.p-button {
  margin-left: 5px;
  margin-top: 3px;
  font-size: 14px;
  padding: 4px 8px;
  border: 1px solid #000;
  border-radius: 30px;
  transition: background-color 0.3s, color 0.3s ease;
}
.p-button:hover,
.p-button:active {
  color: #fff;
  background-color: #000 !important;
}
.p-button-cancel {
  margin-left: 5px;
  margin-top: 3px;
  font-size: 14px;
  padding: 4px 8px;
  border: 1px solid #000;
  border-radius: 30px;
  transition: background-color 0.3s, color 0.3s ease;
}
.p-button-cancel:hover,
.p-button-cancel:active {
  color: #fff;
  background-color: #aa2610 !important;
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
.hashtaglist{
  width:100%;
}
.board-hash-form{
  padding:0 0.5rem;
  margin-bottom: 20px;
}
.hashtaglist-form{
  display: inline-block;
}
.board-hashtag{
  display:inline-block;
  background-color: #0B2945;
  border-radius: 0.5rem;
  color:#f9f9f9;
  padding: 0 8px;
  margin: 5px 8px 2px 0px;
}

.edit-button-set{
  display: flex;
  justify-content:space-between;
  align-items: center;
}
.delete-button{
  cursor: pointer;
  margin:0;
  text-decoration: none;
  color:#aa2610;
}
.careful-line{
  height: 50px;
}

</style>