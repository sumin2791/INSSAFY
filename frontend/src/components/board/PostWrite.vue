<template>
  <div>
    <b-button v-b-modal.modal-post variant="light" class="btn-write">글쓰기</b-button>
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
            v-model="title"
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
            v-model="description"
            :state="descriptionState"
            required
          ></b-form-textarea>
        </b-form-group>
        <b-form-group
          label-for="multiple-media"
        >
          <b-form-file 
            multiple
            id="multiple-media"
            v-model="images"
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
  data() {
    return {
      title: '',
      description:'',
      images:[],
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
      this.title = ''
      this.description = ''
      this.titleState = null
      this.descriptionState = null
      this.images=[]
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
      let today= new Date()
      const posts = this.$store.state.posts
      let idx= posts.length
      const BOARD_ID = Number(this.$route.params.board_id)

      const postItem ={
        user_id:String(localStorage.getItem('userId')),
        board_id:BOARD_ID,
        post_title:this.title, 
        post_description:this.description,
        post_image:JSON.stringify(this.images),
        post_iframe:'',
        post_header:'',
        post_state:0
      }
      console.log(postItem)
      
      postApi.create(postItem)
        .then(res=>{
          console.log(`post 생성 완료`)
          console.log(res)
          this.$store.dispatch('board/isWriteFlag')
        })
        .catch(err=>{
          console.log(`post 생성 실패 ${err}`)
        })

      // this.$store.dispatch('createPost',postItem)
      

      console.log(this.images)
      this.$nextTick(() => {
        this.$bvModal.hide('modal-post')
      })
    }
  }
}
</script>

<style scoped>
.btn-write {
  width:100%;
  position: sticky;
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