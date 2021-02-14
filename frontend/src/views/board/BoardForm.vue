<template>
  <v-app class="main-bg-color">
    <!-- 전체 포함하는 컨테이너 -->
    <v-container
      class="pt-8"
    >
      <!-- 보드 생성하기 제목 -->
      <div 
        class="text-overline 
          text-center
          font-weight-black"
        style="font-size: 24px !important;"
      >보드 생성하기</div>
      <v-row no-gutters dense>
        <!-- (PC기준)왼쪽 -->
        <v-col 
          class="col-12 col-sm-8">
          <!-- 보드 이름 -->
          <v-col>
            <div class="my-2">보드 이름</div>
            <v-text-field
              placeholder="보드이름을 작성해주세요"
              solo
              v-model="title"
            ></v-text-field>
          </v-col>
          <!-- 보드 설명 -->
          <v-col>
            <div class="my-2">보드 설명</div>
            <v-textarea
              filled
              solo
              rows="4"
              row-height="30"
              placeholder="보드에 대한 설명을 적어보세요"
              v-model="description"
              class="description"
            ></v-textarea>
          </v-col>
          <!-- 해쉬태그 -->
          <v-col>
            <div class="my-2">Hash Tag</div>
            <v-text-field
              placeholder="ex. SSAFY"
              solo
              v-model.trim="hashtag"
              @keypress.enter="addHashtag()"
            ></v-text-field>
            <!-- 해쉬태그 보여지는 부분 -->
            <div>
              <v-chip-group 
                v-for="(hashtag, idx) in hashtags"
                :key="idx">
                <v-chip
                  class="ma-2"
                  close
                  color="indigo darken-4"
                  text-color="white"
                  @click:close="deleteHashtag(idx)"
                > {{ hashtag }}
                </v-chip>
              </v-chip-group>
            </div>
          </v-col>
        </v-col>
        <!-- (PC기준)오른쪽 -->
        <v-col class="col-12 col-sm-4">
          <!-- 보드 대표 이미지 -->
          <v-col>
            <div class="my-2">대표 이미지</div>
            <v-file-input class="d-flex"
              filled
              solo
              prepend-icon="mdi-image"
              placeholder="대표 이미지 선택하기"
            ></v-file-input>
          </v-col>
          <!-- 익명 여부 -->
          <v-col>
            <div class="my-2">익명성 여부</div>
            <b-form-checkbox v-model="options.ikmyeong" name="check-button" switch>
              <p v-if="!options.ikmyeong" style="margin:0; text-decoration:line-through;">익명 보드</p>
              <b v-if="options.ikmyeong">익명 보드</b>
            </b-form-checkbox>
          </v-col>
          <!-- 지역 정보 -->
          <v-col>
            <v-select
              v-model="location"
              :items="options.location"
              label="지역"
              solo
            ></v-select>
          </v-col>
          <!-- 추가 기능 -->
          <v-col>
            <!-- 추가기능 추가 정보(편집) -->
            <div id="add-func-group">
              <div>추가기능</div>
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
            <div><button @click="checkAddFunc">test</button></div>
          </v-col>
        </v-col>
      </v-row>
      <v-row 
        no-gutters 
        dense 
        class="d-flex 
          justify-center">
        <v-btn
          color="blue-grey"
          x-large
          class="ma-2 white--text text-overline"
          @click="onCreate()"

        >
          보드 만들기
        </v-btn>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import * as boardApi from '@/api/board';

export default {
  name:"BoardForm",
  watch: {
    addFuncAll:'checkAddFunc'
  },
  data() {
    return {
      switch1: true,
      title:'',
      description:'',
      file1:[],
      hashtag:'',
      hashtags:[],
      location:'',
      selected: [],
      options: {
        location: ['서울', '대전', '구미', '광주'],
        ikmyeong:false,
      },
      // 전체 추가기능 목록 (체크되어 있는지)
      addFuncAll: [
        {
          option: '체크리스트',
          state: false,
          explain: '구성원들과 함께 간단한 <strong>할 일 목록</strong>을 만들어서 관리해보세요.<br>보드를 효율적으로 사용할 수 있게 됩니다.',
        }, 
        {
          option: '캘린더',
          state: false,
          explain: '캘린더에 일정을 표시하여 서로의 일정을 공유하고<br> 구성원들의 <strong>스케쥴 관리를 효율적</strong>으로 할 수 있게 도와줍니다.',
        }, 
        // {
        //   option: '인기글',
        //   state: false,
        //   explain: '좋아요 순으로 보드 내<br>포스트의 인기글 <strong>TOP3</strong>를 보여줍니다.',
        // }, 
        {
          option: '투표',
          state: false,
          explain: '결정하기 힘든 일은 투표를 통해 확인하는 것은 어떨까요?<br>투표를 만들고 투표내 항목을 만들어 투표를 생성해보세요',
        }, 
        {
          option: '랭킹',
          state: false,
          explain: '보드를 활발히 활동하는 유저는 누군지 확인할 수 있게<br> <strong>TOP3</strong> 활동 유저를 확인해보세요',
        }, 
      ],
    }
  },
  methods: {
    // 추가기능 test
    checkAddFunc() {
      console.log(this.addFuncAll)
    },
    addHashtag() {
      if(this.hashtags.length===5){
        alert('해쉬태그는 5개 이하만 가능합니다.')
        return
      }
      this.hashtags.push(`#${this.hashtag}`)
      this.hashtag=''
    },
    deleteHashtag(e){
      this.hashtags.splice(e,1)
    },
    onCreate(){
      //@param : user_id, board_name, board_description, board_location, board_igmyeong, board_hash, checklist_flag, calendar_flag, vote_flag
      let board={
        user_id: localStorage.getItem('userId'),
        board_name: this.title,
        board_description: this.description,
        board_location: '전체',
        board_igmyeong: 0,
        board_hash:this.hashtags.join('|'),
        // 추가기능 삽입 추가
        checklist_flag:(this.addFuncAll[0].state ? 1 : 0),
        calendar_flag:(this.addFuncAll[1].state ? 1 : 0),
        // // 인기글 현재 없으니 주석처리
        // popular_post_flag:(this.addFuncAll[2].state ? 1 : 0),
        // // 랭킹 현재 연결 없으니 주석처리
        // rank_flag:(this.addFuncAll[4].state ? 1 : 0),
        vote_flag:(this.addFuncAll[3].state ? 1 : 0),
        board_state:0
      };
      boardApi.board_create(board).then(response => {
        console.log(response.data);
        const subBoard = JSON.parse(localStorage.subBoard)
        subBoard.push({
          board_id:response.data.board_id,
          user_id:localStorage.userId,
          favorite_flag:0,
          is_used:1,
          user_role:1,
          write_post_count:0
        })
        localStorage.subBoard = JSON.stringify(subBoard)
        alert('게시판 보드 생성')
        
        this.$router.push({name:'Board',params:{board_id:response.data.board_id}})
      }).catch(error => {
        console.log(error);
        alert("보드 생성에 실패하였습니다.");
      })
    }
  }
}
</script>

<style scoped>
/* 전체 메인 배경색 */
.main-bg-color {
  background-color: #ebebe9;
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
</style>