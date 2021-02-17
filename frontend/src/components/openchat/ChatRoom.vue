<template>
  <v-card id="chatroom-bg">
    <!-- 위쪽 툴바 부분 -->
    <v-toolbar
      color="#5C5C64"
      dark
      id="room-title"
    >
      <!-- 채팅 상대방 닉네임 보여줄 부분 -->
      <v-toolbar-title text-color="white">{{ oppNickName }}</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon @click="exitRoom">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </v-toolbar>
    <div id="chat-message">
      <!-- 채팅메세지 부분 -->
      <div v-if="this.messages">
        <Message 
          v-for="(message, idx) in messages.slice().reverse()"
          :key="idx"
          :message="message"
        />
      </div>
      <!-- 채팅방 내에 있을 때 보여줄 부분 -->
      <v-container id="socket">
      </v-container>
      <!-- 채팅 입력 부분 -->
      <div id="input">
          <v-text-field
            solo
            dense
            hide-details=""
            id="comment"
            v-model="sendContents"
            @keypress.enter="sendMessage"
            placeholder="메세지를 입력해주세요"
          >
          </v-text-field>
          <v-btn 
            :class="{'enableSend': isPossibleChat}"
            @click="sendMessage"
          >
            <v-icon>mdi-send</v-icon>
          </v-btn>
      </div>
    </div>
  </v-card>
</template>

<script>
import Message from '@/components/openchat/Message.vue'
//소켓 설정
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';
// chat api
import * as chatApi from "@/api/chat"
// 날짜 지정
import timeForToday from '@/plugins/timeForToday'

export default {
  name: "ChatRoom",
  components: {
    Message,
  },
  created() {

  },
  mounted() {

  },
  watch: {
    // 값이 바뀌면 소켓 연결
    isInRoom: 'connect',
    // 채팅방 정보가 바뀌면 소켓 연결
    // oppRoomId: 'connect'
  },
  computed: {
    // 현재 활성화 채팅방 정보
    oppNickName() {
      if (this.$store.state.chat.selectedNickname) { return this.$store.state.chat.selectedNickname + '님과의 대화'} else { return '대화를 선택해주세요'}
    },
    oppRoomId() {
      return this.$store.state.chat.selectedChatRoom
    },
    oppUserId() {
      return this.$store.state.chat.selectedId
    },
    // 상대방 닉네임
    oppUserNickname() {
      return this.$store.state.chat.selectedNickname
    },
    // 채팅방 보낼 메세지 있는지 확인
    isPossibleChat() {
      return Boolean(this.sendContents.trim())
    },
    isInRoom() {
      return this.$store.state.chat.isInChatRoom
    },
    messages() {
      return this.$store.state.chat.selectedMessages
    },
  },
  data() {
    return {
      // 보낼 내용
      sendContents: '',
      // 현재 접속한 유저
      userId: String(localStorage.userId),
      // socket 재연결 막기
      stompClient: null,
    }
  },
  methods: {
    // 소켓 연결하기
    connect() {
      const serverURL = 'http://i4c109.p.ssafy.io/api/ws';
      // const serverURL = 'http://localhost:8000/ws';
  
      let socket = new SockJS(serverURL);
      if (this.stompClient != null) {
        this.stompClient.disconnect();
        this.stompClient = Stomp.over(socket);
      } else {
        this.stompClient = Stomp.over(socket);
      }
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

            // 뿌려줄 res.data 태그
            // 1 - 전체 감쌀 부분
            const divReceive = document.createElement("div");
            // 속성 주기
            divReceive.setAttribute("style", 
            "display: flex; flex-direction: row-reverse; justify-content: flex-end; align-items: flex-end; width: 100%; padding: 12px 0;"
            );
            // 2- 날짜 부분
            const divReceiveDate = document.createElement("div");
            divReceiveDate.setAttribute("style", "font-size: 0.75rem; font-weight: 400; margin: 0 4px;");
            // 텍스트 요소 꼭 필요
            divReceiveDate.textContent = timeForToday(received.date)
            divReceive.appendChild(divReceiveDate)
            // 3- 내용 부분
            const divReceiveMsg = document.createElement("div");
            divReceiveMsg.textContent = received.msg
            divReceiveMsg.setAttribute("style", "display: inline; float: right; padding: 0.4em 0.8em; border-radius: 0.7em 0.7em 0.7em 0; background-color: var(--basic-color-bg); color: var(--basic-color-fill); max-width: 70%; margin-left: 4px;")
            divReceive.appendChild(divReceiveMsg)
            // 이를 id로 검색해서 해당 지점에 달아준다
            const target = document.getElementById("socket")
            target.appendChild(divReceive)
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );
    },
    // 메세지 보내기
    sendMessage() {
      if (this.stompClient && this.stompClient.connected && this.isPossibleChat) {
      // 한국시간으로 설정
      const date = new Date().toLocaleString({timeZone: "Asia/Seoul"});
      
      // 소켓 통신
      const message = {
        room_id: this.oppRoomId,
        user_id:String(localStorage.userId),
        // 상대방 아이디 넣기
        opp_id: this.oppUserId,
        msg: this.sendContents,
        date: date,
        opp_nickName: this.oppUserNickname,
      };
      console.log(message, '파라미터 변경')
      console.log('연결중 보내기')
      this.stompClient.send('/app/receive', JSON.stringify(message), {});
      
      this.$store.dispatch('chat/sendMessages')

      // 초기화(input)
      this.sendContents = ''
      
      // 뿌려줄 res.data 태그
      // 1 - 전체 감쌀 부분
      const div = document.createElement("div");
      // 속성 주기
      div.setAttribute("style", 
      "display: flex; flex-direction: row; justify-content: flex-end; align-items: flex-end; width: 100%; padding: 12px 0;"
      );
      // 2- 날짜 부분
      const divDate = document.createElement("div");
      divDate.setAttribute("style", "font-size: 0.75rem; font-weight: 400; margin: 0 4px;");
      // 텍스트 요소 꼭 필요
      divDate.textContent = timeForToday(message.date)
      div.appendChild(divDate)
      // 3- 내용 부분
      const divMsg = document.createElement("div");
      divMsg.textContent = message.msg
      divMsg.setAttribute("style", "display: inline; float: right; padding: 0.4em 0.8em; border-radius: 0.7em 0.7em 0 0.7em; background-color: var(--basic-color-key); color: #fff; max-width: 70%;")
      div.appendChild(divMsg)
      // 이를 id로 검색해서 해당 지점에 달아준다
      const target = document.getElementById("socket")
      target.appendChild(div)
      }
    },
    exitRoom() {
      // 활성화 채팅방 없애고 메세지도 지우기
      this.$store.dispatch('chat/isNotSelected')
      // data를 갱신해서 re-rendar되게
      this.sendContents = ''
    },
    // message 갱신용
    messagesUpdate() {
    },

  },
}
</script>

<style scoped>
#chatroom-bg {
  background-color: #F9F9F9;
  font-family: 'Noto Sans KR', sans-serif !important;
  box-shadow: var(--basic-shadow-s) !important;
  border-radius: 15px 15px 5px 5px;
  padding: 0px 0px 0px 0px !important;
  height: 100%;
}
.chat-area {
/*   border: 1px solid #ccc; */
  background-color: #FFF;
  min-height: 90vh;
  max-height: 92vh;
  padding: 1em;
  /* overflow: auto; */
  box-shadow: 2px 2px 5px 2px rgba(0, 0, 0, 0.3)
}
/* 채팅방 부분 */
#chat-message {
  min-height: calc(90vh - 64px);
  max-height: 90vh;
  display: flex;
  flex-direction: column;
  overflow-y: scroll !important;
}
/* 댓글 입력 부분 */
#input {
  display: flex;
  flex-direction: row;
  margin-top: auto;
}
/* 활성화 버튼 */
.enableSend {
  background-color: #695c4c !important;
  color: #fff !important;
}
/* message 속성(상대방) */
.you {
  display: inline;
  padding: 0.4em 0.8em;
  border-radius: 0.7em 0.7em 0.7em 0;
  background-color: var(--basic-color-bg);
  color: var(--basic-color-fill);
  margin-left: 4px;
  max-width: 70%;
}
/* message 속성(나) */
.mine {
  display: inline;
  float: right;
  padding: 0.4em 0.8em;
  border-radius: 0.7em 0.7em 0 0.7em;
  background-color: var(--basic-color-key);
  color: #FFF;
  max-width: 70%;
}
/* 날짜 포함 전체 메세지 */
.textMine {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: flex-end;
  width: 100%;
}
.textYou {
  display: flex;
  flex-direction: row-reverse;
  justify-content: flex-end;
  align-items: flex-end;
  width: 100%;
}
/* 날짜 스타일 */
.date {
  font-size: 0.75rem;
  font-weight: 400;
  margin: 0 4px;
}
</style>