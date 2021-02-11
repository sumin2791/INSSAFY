<template>
  <v-list three-line>
    <v-list-item class="pl-0">
      <!-- 스터디 대표 이미지 -->
      <v-avatar>
        <v-img :src="chatPartner.profileImg"></v-img>
      </v-avatar>
      <!-- 스터디 이름, 설명 -->
      <v-list-item-content 
        @click="goThisStudy()"
        class="ml-3 go-study"
      >
        <!-- 상대방 닉네임 -->
        <v-list-item-title>{{ chatPartner.nickName }}</v-list-item-title>
        <!-- 상대방 최근 메세지 -->
        <v-list-item-subtitle>{{ chatPartner.recentMsg }}</v-list-item-subtitle>
      </v-list-item-content>
      <!-- 메세지 수 보여줄 부분 -->
      <v-list-item-action class="align-self-end">
          <v-chip
            class="ma-2"
            x-small
            color="#0B2945"
            text-color="#F9F9F9"
          >
            {{ chatPartner.msgCount }}
          </v-chip>
      </v-list-item-action>
    </v-list-item>
    <v-divider></v-divider>
  </v-list>
</template>

<script>
export default {
  name:'ChatRoom',
  data() {
    return {
      // 채팅방 상대 정보
      chatPartner: 
        {
          profileImg: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
          nickName: '아몰랑',
          recentMsg: `I'll be in your neighborhood doing errands this weekend. Do you want to hang out?`,
          msgCount: 4,
        },
    }
  },
}
</script>

<style>
</style>