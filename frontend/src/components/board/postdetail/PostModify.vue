<template>
  <div>
    <!-- <b-button v-b-modal.modal-post variant="light" class="btn-write">글쓰기</b-button> -->
    <div v-b-modal.modal-post>수정</div>
    <!-- <b-modal id="modal-post" title="Info" v-if="!inBoard" ok-only>
      <p class="my-4">구독하시면 글을 작성할 수 있어요😊</p>
      <template #modal-footer="{ok}">
        <b-button variant="submit" @click="ok()">
          오키
        </b-button>
      </template>
    </b-modal> -->
    <b-modal
      id="modal-post"
      ref="modal"
      size="xl"
      title="Modify"
      no-close-on-backdrop
      @show="resetModal"
      @hidden="resetModal"
      @modal-cancel="resetModal"
      @ok="handleOk"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-form-group
          label-for="title-input"
          invalid-feedback="title is required"
          :state="titleState"
        >
          <b-form-input
            id="title-input"
            placeholder="제목"
            v-model="tempTitle"
            :state="titleState"
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group
          label-for="description-input"
          invalid-feedback="description is required"
          :state="descriptionState"
        >
          <b-form-textarea
            id="description-input"
            placeholder="무슨생각하고 있음?"
            style="height:300px"
            v-model="tempDescription"
            :state="descriptionState"
            required
            no-resize
          ></b-form-textarea>
        </b-form-group>
        <b-form-group
          label-for="multiple-media"
          disabled
        >
          <b-form-file 
            disabled
            id="multiple-media"
            v-model="tempImages"
            placeholder="Choose a file or drop it here..."
            browse-text='🖼'
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
      </form>
      <template #modal-footer="{ok, cancel}">
        <!-- Emulate built in modal footer ok and cancel button actions -->
        <b-button variant="submit" @click="ok()">
          Edit
        </b-button>
        <b-button variant="cancel" @click="cancel()">
          Cancel
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

import deepClone from '@/plugins/deepClone'

export default {
  name:'ModifyForm',
  props:{
    post:Object,
  },
  data() {
    return {
      tempTitle: '',
      tempDescription:'',
      tempImages:[],
      titleState: null,
      descriptionState: null,
    }
  },
  methods: {
    titleCheckFormValidity() {
      const valid = this.$refs.form.checkValidity()
      this.titleState = valid
      return valid
    },
    descriptionCheckFormValidity() {
      const valid = this.$refs.form.checkValidity()
      this.descriptionState = valid
      return valid
    },
    resetModal() {
      this.tempTitle = this.post.post_title
      this.tempDescription = this.post.post_description
      this.titleState = null
      this.descriptionState = null
      this.tempImages=[]
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
      // Push the name to submitted names
      // this.submittedNames.push(this.name)
      // Hide the modal manually
      // const posts = this.$store.state.posts
      const BOARD_ID = Number(this.$route.params.board_id)
      // var fd = new FormData()
      // fd.append('post_image', this.images)

      let postItem = deepClone(this.post)
      postItem.post_title = this.tempTitle
      postItem.post_description = this.tempDescription
      postItem.post_image = '' //이미지는 DB설계가 아직 안 되어 있음.

      const login_id = localStorage.userId
      console.log(postItem)
      postApi.modify({postItem,login_id})
        .then(res=>{
          console.log('post 편집')
          this.$store.dispatch('post/isModifyFlag')
        })
        .catch(err=>{
          
          console.log(`post 편집 실패`)
        })

      this.$nextTick(() => {
        this.$bvModal.hide('modal-post')
      })
    }
  }
}
</script>

<style scoped>
.modal-footer{
  display: flex;
}

.btn-submit {
  /* width: 100%; */
  height: 40px;
  /* margin: 5px 10px; */
  font-size: 20px;
  border: solid 1px #000;
  transition: color 0.3s, background-color 0.3s ease;
}
.btn-submit:hover,
.btn-submit:active {
  background-color: #000 !important;
  color: #fff;
}
.btn-cancel {
  /* width: 100%; */
  height: 40px;
  /* margin: 5px 10px; */
  font-size: 20px;
  border: solid 1px #000;
  transition: color 0.3s, background-color 0.3s ease;
}
.btn-cancel:hover,
.btn-cancel:active {
  background-color: #aa2610 !important;
  border: solid 1px #aa2610;
  color: #fff;
}
/* @media screen and (max-width:576px) {
  .btn-submit{
    background-color: #000 !important;
    color: #fff;
  }
} */
</style>