<template>
  <v-card min-height="90vh" id="chatroom-bg">
    <!-- 위쪽 툴바 부분 -->
    <v-toolbar
      color="#5C5C64"
      dark
      class="mb-5"
    >
      <!-- 채팅 상대방 닉네임 보여줄 부분 -->
      <v-toolbar-title text-color="white">{{ youNickName }}님과의 대화</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </v-toolbar>
    <!-- 채팅메세지 부분 -->
    <Message />
    <Message />
    <Message />
  </v-card>
</template>

<script>
import Message from '@/components/openchat/Message.vue'

export default {
  name: "ChatRoom",
  components: {
    Message,
  },
  data() {
    return {
      // 오른쪽 채팅방 상대 이름
      youNickName: '아몰랑',
      bobMessage: '',
      youMessage: '',
      messages: [
        
        {
          body: 'Thank you Bob',
          author: 'you'
        },
        {
          body: 'You\'re most welcome',
          author: 'bob'
        },
        {
          body: `Welcome to the chat, I'm Bob!`,
          author: 'bob'
        },
      ]
    }
  },
  methods: {
    sendMessage(direction) {
      if (!this.youMessage && !this.bobMessage) {
        return
      }
      if (direction === 'out') {
        this.messages.push({body: this.youMessage, author: 'you'})
        this.youMessage = ''
      } else if (direction === 'in') {
        this.messages.push({body: this.bobMessage, author: 'bob'})
        this.bobMessage = ''
      } else {
        alert('something went wrong')
      }
    },
    clearAllMessages() {
      this.messages = []
    }
  },
}
</script>

<style scoped>
#chatroom-bg {
  background-color: #F9F9F9;
}
.chat-area {
/*   border: 1px solid #ccc; */
  background-color: #FFF;
  min-height: 90vh;
  padding: 1em;
  overflow: auto;
  box-shadow: 2px 2px 5px 2px rgba(0, 0, 0, 0.3)
}
.message {
  overflow: auto;
  width: 45%;
  border-radius: 10px;
  padding: .5em;
  margin-bottom: 1em;
  font-size: .8em;
}
.message-out {
  background: #407FFF;
  color: white;
  margin-left: 55%;
}
.message-in {
  background: #F1F0F0;
  color: black;
}
.chat-inputs {
  display: flex;
  justify-content: space-between;
}
#person1-input {
  padding: .5em;
}
#person2-input {
  padding: .5em;  
}
</style>