<template>
  <v-list id="list" three-line>
    <v-list-item 
      class="ma-auto"
    >
      <!-- 상대 프로필 이미지(임시 - 연결해줘야 함) -->
      <v-avatar>
        <v-img :src="profileImg"></v-img>
      </v-avatar>

      <v-list-item-content 
        class="ml-3 chat-partner"
        @click="selectThisChat(chatList)"
      >
        <!-- 상대방 닉네임 -->
        <v-list-item-title>{{ chatList.opp_nickName }}</v-list-item-title>
        <!-- 상대방 최근 메세지 -->
        <v-list-item-subtitle>{{ chatList.recentMsg }}</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
    <v-divider></v-divider>
  </v-list>
</template>

<script>
// chat api
import * as chatApi from "@/api/chat" 

export default {
  name:'ChatRoom',
  props: {
    chatList: Object
  },
  data() {
    return {
      // 임시 프로필 이미지
      profileImg: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
    }
  },
  methods: {
    // 현재 채팅방 메세지들 가져오기 - 초기 채팅방 가져올 때 한번만
    selectThisChat(chatList) {
      // 정보 갱신전 제거를 해줘야 업데이트 반영됨
      // 활성화 채팅방 없애고 메세지도 지우기
      this.$store.dispatch('chat/isNotSelected')
      // 현재 선택된 채팅방 정보 갱신
      this.$store.dispatch('chat/isSelected', chatList)
      const currentRoom = this.$store.state.chat.selectedChatRoom
      // 채팅방 주인 정보
      const oppId = this.$store.state.chat.selectedId
      // console.log(this.$store.state.chat, '채팅')
      // 선택 X이면 안 보여주고 선택됐을 때만 보여주기
      if (currentRoom) {
        // API 요청으로 메세지 가져오기
        const params = {
          endNUm: 15,
          startNUm: 0,
          room_id: currentRoom,
          // 추가 파라미터
          user_id: String(localStorage.userId),
          opp_id: oppId, 
        }
        chatApi.getMessages(params)
          .then(res => {
            this.$store.dispatch('chat/getMessages', res.data.msgList)
          })
          .catch(err => {
            console.error(err)
          })
      } else {
        // vuex 초기화
        this.$store.dispatch('chat/isNotSelected')
        } 
    },
  },
}
</script>

<style scoped>
/* 전체 폰트 */
#list {
  font-family: 'Noto Sans KR', sans-serif !important;
  height: 100% !important;
  border: none;
  border-radius: 5px;
  color: var(--basic-color-key);
  text-shadow: 0 0px 1px var(--basic-color-fill3);
  background: #ebebe9 !important;
  box-shadow: 10px 10px 20px #bcbcba, 
            -10px -10px 20px #ffffff;
  border-radius: 15px !important;
  padding: 0px;
}
.chat-partner:hover {
  cursor: pointer;
}
</style>