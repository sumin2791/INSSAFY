<template>
  <div>
    <b-button v-b-modal.modal-post class="btn-write">글쓰기</b-button>
    <b-modal id="modal-post" title="Info" v-if="!inBoard" ok-only>
      <p class="my-4">구독하시면 글을 작성할 수 있어요😊</p>
      <template #modal-footer="{ok}">
        <!-- Emulate built in modal footer ok and cancel button actions -->
        <b-button variant="submit" @click="ok()">
          오키
        </b-button>
      </template>
    </b-modal>
    <b-modal
      id="modal-post"
      ref="modal"
      size="xl"
      title="Post"
      no-close-on-backdrop
      ok-only
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
      v-if="inBoard"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <div class="form-row" v-if="flagBoard==='Market'">
          <b-form-group
            label-for="location-input"
            invalid-feedback="지역을 선택해주세요"
            :state="locationState"
           class="col-12 col-sm-3 mb-0"
          >
            <b-form-select
              id="location-input"
              v-model="selected"
              :options="options"
              :state="locationState"
              required
            >
              <template #first>
                <b-form-select-option :value="null" disabled>-- 지역을 선택해주세요 --</b-form-select-option>
              </template>
            </b-form-select>
          </b-form-group>
          <b-form-group
            label-for="title-input"
            invalid-feedback="title is required"
            :state="titleState"
            class="col-sm-9 col-12"
          >
            <b-form-input
              id="title-input"
              placeholder="제목"
              v-model="title"
              :state="titleState"
              required
            ></b-form-input>
          </b-form-group>
        </div>
        <div class="form-row" v-if="flagBoard==='others'">
          <b-form-group
            label-for="title-input"
            invalid-feedback="title is required"
            :state="titleState"
            class="col-12"
          >
            <b-form-input
              id="title-input"
              placeholder="제목"
              v-model="title"
              :state="titleState"
              required
            ></b-form-input>
          </b-form-group>
        </div>
        <b-form-group
          label-for="description-input"
          invalid-feedback="description is required"
          :state="descriptionState"
        >
          <b-form-textarea
            id="description-input"
            placeholder="무슨생각하고 있음?"
            style="height:300px"
            v-model="description"
            :state="descriptionState"
            required
            no-resize
          ></b-form-textarea>
        </b-form-group>
        <b-form-group
          label-for="multiple-media"
        >
          <b-form-file 
            id="multiple-media"
            v-model="images"
            placeholder="Choose a file or drop it here..."
            browse-text='🖼'
            @change="onChangeImages"
          >
          <!--card-image-->
            <template slot="file-name" slot-scope="{ names }">
              <b-badge variant="dark">{{ names[0] }}</b-badge>
              <b-badge v-if="names.length > 1" variant="dark" class="ml-1">
                + {{ names.length - 1 }} More files
              </b-badge>
            </template>
          </b-form-file>
        </b-form-group>
        <div v-if="imageUrl" class="image-section">
          <b-img
              :src="imageUrl"
              style="max-width: 10rem;"
          ></b-img>
          <b-icon class="deleteImg" @click="deleteImage" icon="x-circle-fill" aria-hidden="true"></b-icon>
        </div>
      </form>
      <template #modal-footer="{ok}">
        <!-- Emulate built in modal footer ok and cancel button actions -->
        <b-button variant="submit" @click="ok()">
          게시
        </b-button>
      </template>
    </b-modal>
    <!-- <b-modal
      id="modal-post"
      ref="modal"
      size="xl"
      title="Post"
      no-close-on-backdrop
      ok-only
      @show="resetModal"
      @hidden="resetModal"
      @ok="handleOk"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        여기?
      </form>
      <template #modal-footer="{ok}">
        <b-button variant="submit" @click="ok()">
          게시
        </b-button>
      </template>
    </b-modal> -->
  </div>
</template>

<script>
import * as postApi from '@/api/post';

export default {
  name:'PostWrite',
  data() {
    return {
      title: '',
      description:'',
      header:'전체',
      state:0,
      images:[],
      imageUrl:null,
      titleState: null,
      descriptionState: null,
      locationState:null,
      selected:null,
      options:[
        {value:'전체',text:'전체'},
        {value:'서울',text:'서울'},
        {value:'대전',text:'대전'},
        {value:'광주',text:'광주'},
        {value:'구미',text:'구미'},
      ]
    }
  },
  props:{
    inBoard:Boolean
  },
  computed:{
    flagBoard() {
      const boardName = this.$route.name
      if(boardName==="Market"){
        return "Market"
      }else if(boardName==="learnShare"){
        return "learnShare"
      }else if(boardName){
        return "others"
      }
      return ''
    }
  },
  methods: {
    deleteImage(){
      this.imageUrl = null
      this.images=[]
    },
    onChangeImages(e) {
        console.log(e.target.files)
        const file = e.target.files[0];
        this.imageUrl = URL.createObjectURL(file);
    },
    titleCheckFormValidity() {
      const valid = this.title.length >1 && this.title.length <28 ? true : false
      this.titleState = valid
      return valid
    },
    descriptionCheckFormValidity() {
      const valid = this.description.length > 1 && this.description.length < 1000 ? true : false
      this.descriptionState = valid
      return valid
    },
    // locationCheckFormValidity() {
    //   const valid = this.header!='' ? true : false
    //   this.locationState = valid
    //   return valid
    // },
    resetModal() {
      this.title = ''
      this.description = ''
      this.header = '전체'
      this.state = 0
      this.titleState = null
      this.descriptionState = null
      
      this.locationState = null
      this.selected = null

      this.images=[]
      this.imageUrl=''
    },
    handleOk(bvModalEvt) {
      // Prevent modal from closing
      bvModalEvt.preventDefault()
      // Trigger submit handler
      this.handleSubmit()
    },
    handleSubmit() {
      // Exit when the form isn't valid
      if (!this.titleCheckFormValidity() ) {
        return
      }
      if (!this.descriptionCheckFormValidity()) {
        return
      }
      // if (!this.locationCheckFormValidity()) {
      //   return
      // }
      // Push the name to submitted names
      const curationName = this.$route.name
      let BOARD_ID
      if(curationName!="Board"){
        BOARD_ID = this.$store.state.curationId[curationName]
      }else{
        BOARD_ID = Number(this.$route.params.board_id)
      }

        // 재사용을 위해 들어오는 데이터에 따라
        if(this.selected!=null){
          this.header = this.selected
        }



      const postItem ={
        user_id:String(localStorage.getItem('userId')),
        board_id:BOARD_ID,
        post_title:this.title, 
        post_description:this.description,
        post_image:'',
        post_iframe:'',
        post_header:this.header,
        post_state:0
      }


      postApi.create(postItem)
        .then(res=>{
          console.log(res)
          this.$store.dispatch('board/isWriteFlag')
        })
        .catch(err=>{
          
          console.log(`post 생성 실패 ${err}`)
        })

      this.$nextTick(() => {
        this.$bvModal.hide('modal-post')
      })
    }
  }
}
</script>

<style scoped>
.image-section{
  display: flex;
  /* align-items: baseline; */
}
.deleteImg{
  transform: translate(-5px,0);
  cursor: pointer;
}
/* 글쓰기 (모달-진입)버튼 */
.btn-write {
  position: sticky;
  text-align: center;
  margin: auto;
  height: 50px;
  width:100%;
  border: none;
  color: var(--basic-color-fill);
  text-shadow: 0 0px 1px var(--basic-color-fill3);
  background: #ebebe9 !important;
  box-shadow: 10px 10px 20px #bcbcba, 
              -10px -10px 20px #ffffff;
  border-radius: 15px !important;
  transition: 0.3s all ease;
}
.btn-write:hover,
.btn-write:active,
.btn-write:focus {
  color: #ebebe9 !important;    
  background-color: var(--basic-color-key) !important;
}
.btn-submit {
  width: 100%;
  height: 40px;
  
  font-size: 20px;
  transition: color 0.3s, background-color 0.3s ease;
}
.btn-submit:hover,
.btn-submit:active {
  background-color: #000 !important;
  color: #fff;
}
@media screen and (max-width:576px) {
  .btn-submit{
    width: 100%;
    background-color: #000 !important;
    color: #fff;
  }
}
</style>