<template>
  <v-list id="list" three-line>
    <v-list-item 
      class="ma-auto"
    >
      <!-- 상대 프로필 이미지(임시 - 연결해줘야 함) -->
      <v-avatar>
        <Profile :getUserImage="chatList.opp_img"></Profile>
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

//소켓 설정
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';


export default {
  name:'ChatRoom',
  components:{
    Profile: () => import('@/components/etc/OtherProfile'),
  },
  props: {
    chatList: Object
  },
  data() {
    return {
    }
  },
  computed: {
    stompClient() {
      return this.$store.state.chat.stompClient
    },
    // 선택된 채팅방 정보
    selectChatRoom() {
      return this.$store.state.chat.selectedChatRoom
    },
    // 선택된 주인 정보
    selectOppId() {
      return this.$store.state.chat.selectedId
    },
  },
  methods: {
    // 현재 채팅방 메세지들 가져오기 - 초기 채팅방 가져올 때 한번만
    selectThisChat(chatList) {
      // 1. vuex !== 클릭한 채팅방: 연결 끊고 재 연결 시켜준다
      if (chatList.roomId !== this.selectChatRoom ) {
        // 소켓 끊었다가 다시 연결
        this.reConnect()
        // vuex에 새로운 정보 갱신 - 지우고 => 다시 넣기
        this.$store.dispatch('chat/isNotSelected')
        this.$store.dispatch('chat/isSelected', chatList)
      }
      // 메세지 정보를 가져오자
      else { return }
    },
    // 소켓 재연결하기(끊었다가 다시 연결)
    reConnect() {
      // const serverURL = 'http://i4c109.p.ssafy.io/api/ws';
      const serverURL = 'http://localhost:8000/ws';
  
      let socket = new SockJS(serverURL);
      console.log(this.stompClient, '여기야여기')
      if (this.stompClient != null) {
        // 끊었다가 재연결
        this.stompClient.disconnect();
        this.$store.dispatch('chat/checkSocket', null)
        // vuex 업데이트 후 재연결
        this.stompClient = Stomp.over(socket);
        this.$store.dispatch('chat/checkSocket', Stomp.over(socket))
        console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
        this.stompClient.connect(
          {},
          (frame) => {
            // 소켓 연결 성공
            this.connected = true;
            console.log('소켓 연결 성공', frame);
            // 서버의 메시지 전송 endpoint를 구독합니다.
            // 이런형태를 pub sub 구조라고 합니다.
            this.stompClient.subscribe('/message/' + this.oppRoomId + '/' + this.userId, (res) => {
              console.log('구독으로 받은 메시지 입니다.', res.body);
              const received = JSON.parse(res.body);
              
              // 채팅방 입장시 알림 메세지 개수 초기화
              const params = {
                my_id: this.userId,
                opp_id: this.oppUserId,
              }
              chatApi.updateNotice(params)
                .then(res => {
                  console.log(res, '잘 넘어옴?')
                })
                .catch(err => {
                  console.error(err)
                })
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
          }
        );
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