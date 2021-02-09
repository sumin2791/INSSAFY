<template>
  <div>
    <b-button v-b-modal.modal-bamboo variant="light" class="btn-write">글쓰기</b-button>

    <!-- <div class="mt-3">
      Submitted Names:
      <div v-if="submittedNames.length === 0">--</div>
      <ul v-else class="mb-0 pl-3">
        <li v-for="(name,idx) in submittedNames" :key="idx">{{ name }}</li>
      </ul>
    </div> -->

    <b-modal
      id="modal-bamboo"
      ref="modal"
      size="xl"
      title="대나무숲"
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
            placeholder="착한 말을 적어주세요 :)"
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
  </div>
</template>

<script>
import * as bambooApi from '@/api/bamboo'
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
      const randomNickname = `싸피_${Math.random().toString(36).substr(2,8)}`
      const bambooItem ={
        user_id:localStorage.userId, 
        bamboo_title:this.title, 
        bamboo_description:this.description,
        bamboo_image:'',
        bamboo_iframe:'',
        bamboo_header:'',
        writer_nickname:randomNickname
      }
      console.log(bambooItem)
      bambooApi.create(bambooItem)
      .then(res=>{
        console.log(res)
        this.$store.dispatch('bamboo/isWriteFlag')
      })
      .catch(err=>{
        console.log(`밤부 생성 실패`)
        console.log(err)
      })
      
      this.$nextTick(() => {
        this.$bvModal.hide('modal-bamboo')
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