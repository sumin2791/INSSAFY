<template>
  <v-app class="main-bg-color">
    <v-main class="grey lighten-3">
      <v-container
        class="pt-8"
      > 
        <v-row>
          <!-- 왼쪽 채팅방 리스트 -->
          <v-col 
            class="col-12 col-sm-5"
            id="sheet" 
          >
            <v-sheet min-height="90vh">
              <v-list color="transparent">
                <!-- 채팅방 리스트 타이틀 -->
                <div class="text-h5">채팅방 목록</div>
                <v-divider class="my-2"></v-divider>
                <!-- 채팅중인 대화상대 목록 -->
                <v-col class="chat-list">
                  <ChatRoomList />
                  <ChatRoomList />
                  <ChatRoomList />
                  <ChatRoomList />
                </v-col>
              </v-list>
            </v-sheet>
          </v-col>
          <!-- 채팅방 부분 -->
          <v-col
            class="col-12 col-sm-7"  
          >
            <v-sheet min-height="90vh">
              <ChatRoom />
            </v-sheet>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
// 왼쪽 채팅 목록
import ChatRoomList from "@/components/openchat/ChatRoomList.vue"
// 오른쪽 채팅방 부분
import ChatRoom from "@/components/openchat/ChatRoom.vue"

export default {
  name:'LearningShare',
  components: {
    ChatRoomList,
    ChatRoom,
  },
  // 뷰 인스턴스 제거될 때 resize 호출
  beforeDestroy () {
      if (typeof window === 'undefined') return

      window.removeEventListener('resize', this.onResize, { passive: true })
  },
  mounted () {
    // resize 실시해서 현재 화면 크기 확인
    this.onResize()

    window.addEventListener('resize', this.onResize, { passive: true })
    this.filterMyStudyGroup()
  },
  data() {
    return {
      // 모바일 화면 체크 mobile화면인지, 사이즈 이용할 값
      ResponsiveSize: {
        isMobile: false, 
        viewSize: 0,
      },
      // 검색 키워드
      searchKeyword: '',
      // 내 스터디 목록 활성화 버튼
      isMyStudy: false,
      myStudyGroup: ['내 스터디 목록', '전체 스터디 목록'],
      state: '',
    }
  },
  methods: {
    // 현재 활성화된 기기에 따라 flag 변경
    onResize() {
      this.ResponsiveSize.isMobile = window.innerWidth < 426;
      this.ResponsiveSize.viewSize = window.innerWidth;
    },
    // 내 스터디 그룹 / 전체 스터디 그룹 전환
    filterMyStudyGroup() {
      if (this.isMyStudy) {
        this.state = this.myStudyGroup[0]
      } else {this.state = this.myStudyGroup[1]}
    },
  }
}
</script>

<style scoped>
.main-bg-color {
  background-color: #ebebe9;
}
.description {
  margin: 2%;
  padding: 10%;
  flex-basis: 20%;
}
</style>
