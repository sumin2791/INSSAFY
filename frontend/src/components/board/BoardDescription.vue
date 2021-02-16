<template>
  <div id="container">
    <div class="board-name-detail">
      <div class="name-setting">
        <div class="board-name">{{this.board.name}}</div>
        <v-icon id="edit-icon"
          large
          v-if="isManager && !Edit" 
          @click="btnModify"
        >
          mdi-cog
        </v-icon>
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
          placeholder="보드를 설명해주세요"
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
        class="text-body2"
        color="grey-darken-2"
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
    <!-- 추가기능 추가 정보(편집) -->
    <div id="add-func-group" v-if="Edit">
      <div>추가기능 모음</div>
      <div
        id="add-func-item"
        v-for="(func, idx) in addFuncAll"
        :key="idx"
      >
        <v-checkbox
          hide-details
          color="#0B2945"
          :label="func.option"
          v-model="func.state"
        ></v-checkbox>
        <v-tooltip right>
          <template v-slot:activator="{ on, attrs }">
            <v-icon
              id="add-func-info"
              v-bind="attrs"
              v-on="on"
              color="#AA2610"
            >mdi-information-outline</v-icon>
          </template>
          <span v-html="func.explain"></span>
        </v-tooltip>
      </div>
    </div>
    <div class="edit-button-set" v-if="Edit">
      <button class="p-button-cancel r-desc" @click="cancel">cancel</button>
      <button class="p-button r-desc" @click="submit">  Edit  </button>
    </div>
    <div class="careful-line"></div>
  </div>
</template>

<script>
import * as boardApi from '@/api/board';
import deepClone from '@/plugins/deepClone';
// style 적용
import '@/assets/css/static/style.css';
// 추가기능 api
import * as addFuncApi from '@/api/addfunc';


export default {
  name:"BoardDescription",
  props:{
    inBoard:Boolean,
    isManager:Boolean,
  },
  data() {
    return {
      loading: false,
      boardDto:{},
      board:{
        name:'',
        description:'',
        hashtags:[],
        subscribe_count:0,
        location:'',
        igmyeong:'',
      },
      tempDescription:'',
      tempHashtags: [],
      tempHashtag:'',
      
      Edit:false,

      // 전체 추가기능 목록 (체크되어 있는지) - 추가기능 수정 - title 추가
      addFuncAll: [
        {
          title: 'checklist',
          option: '체크리스트',
          state: false,
          explain: '구성원들과 함께 간단한 <strong>할 일 목록</strong>을 만들어서 관리해보세요.<br>보드를 효율적으로 사용할 수 있게 됩니다.',
        }, 
        {
          title: 'calendar',
          option: '캘린더',
          state: false,
          explain: '캘린더에 일정을 표시하여 서로의 일정을 공유하고<br> 구성원들의 <strong>스케쥴 관리를 효율적</strong>으로 할 수 있게 도와줍니다.',
        }, 
        {
          title: 'vote',
          option: '투표',
          state: false,
          explain: '결정하기 힘든 일은 투표를 통해 확인하는 것은 어떨까요?<br>투표를 만들고 투표내 항목을 만들어 투표를 생성해보세요',
        }, 
        // 랭킹 수정 요청 연결하기
        {
          title: 'userRank',
          option: '랭킹',
          state: false,
          explain: '보드를 활발히 활동하는 유저는 누군지 확인할 수 있게<br> <strong>TOP3</strong> 활동 유저를 확인해보세요',
        }, 
        
      ],
    }
  },
  created() {
    this.fetchData()
    this.tempDescription = this.board.description
    console.log(this.isManager)
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
            this.boardDto = res.data.boardDto

            
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
            // 추가기능 연결
            const addCheck = res.data.board_function

            // 1. 체크리스트 연결
            if (addCheck.checklist_flag) {
              this.addFuncAll[0].state = true
            }
            // 2. 캘린더 연결
            if (addCheck.calendar_flag) {
              this.addFuncAll[1].state = true
            }
            // 3. 투표 연결
            if (addCheck.vote_flag) {
              this.addFuncAll[2].state = true
            }
            // 4. 유저 랭킹
            if (addCheck.user_rank_flag) {
              this.addFuncAll[3].state = true
            }
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
      // this.board.hashtags = [this.board.hashtags, ...this.tempHashtags]
      this.board.description = this.tempDescription
      this.board.hashtags = deepClone(this.tempHashtags)
      this.boardDto.board_description = this.board.description
      this.boardDto.board_hash = this.board.hashtags.join('|')
      boardApi.board_modify(this.boardDto,localStorage.userId)
        .then(res=>{
          console.log(res)
        })
        .catch(err=>{
          console.log(err)
        })
      // 추가기능 요청 api 하나로 연결되어 있어서 이를 반복문으로 처리 - 랭킹 빼고 일단
      const addFuncState = {}
      for ( let idx = 0; idx < this.addFuncAll.length; ++idx) {
 
      // 추가기능 편집 및 삭제 - 캘린더, 체크리스트, 투표
      const params = {
        board_id: Number(this.$route.params.board_id),
        login_id: localStorage.getItem('userId'),
        function: this.addFuncAll[idx].title,
        option:(this.addFuncAll[idx].state ? 1 : 0),      
      }
      // vuex 연결
      const title = this.addFuncAll[idx].title + '_flag'
      // 동적 키 할당
      addFuncState[title] = params.option
      console.log(addFuncState)
      addFuncApi.modifyAddFunction(params)
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.error(err)
        })
      }
      // vuex 반영
      this.$store.dispatch('addfunc/isUsed', addFuncState);

      this.cancel()
      alert(`수정!`);
    },
  }

}
</script>

<style scoped>
/* 전체 description-container */
#container {
  font-family: 'Noto Sans KR', sans-serif !important;
  display: flex;
  flex-direction: column;
  padding: 5% 10%;
  min-height: 30vh;
}
.name-setting{
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1em;
}
.board-detail{
  margin: 1em 0;
  padding:0 0 0 1em;
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
  justify-content: flex-end;
  margin: 25px 0 0 0;
}
.careful-line{
  height: 30px;
}
/* 추가기능 항목 아이템들 */
#add-func-item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 0 20px 0 25px;
}
/* 추가기능 항목 설명 아이콘 */
#add-func-info {
  align-self: flex-end;
}
/* 편집 들어오는 버튼(아이콘) */
#edit-icon {
  cursor:pointer;
  color: #0B2945;
}
#edit-icon:hover {
  color: #AA2610;
}
</style>