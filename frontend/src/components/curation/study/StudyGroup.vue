<template>
  <v-list three-line>
    <v-list-item class="pl-0">
      <!-- 스터디 대표 이미지 -->
      <v-avatar>
        <v-img :src="studyInfo.studyImage"></v-img>
      </v-avatar>
      <!-- 스터디 이름, 설명 -->
      <v-list-item-content 
        @click="goThisStudy()"
        class="ml-3 go-study"
      >
        <v-list-item-title>{{ studyInfo.studyTitle }}</v-list-item-title>
        <v-list-item-subtitle>{{ studyInfo.studyDescription }}</v-list-item-subtitle>
      </v-list-item-content>
      <!-- 구성원 숫자 -->
      <v-list-item-action>
        <v-icon>mdi-account-group</v-icon>
        {{ studyInfo.studyMemberCount }}
      </v-list-item-action>
    </v-list-item>
    <v-divider></v-divider>
  </v-list>
</template>

<script>
export default {

  name: "StudyGroup",

  data() {
    return {
      // 스터디 그룹들 정보
      studyInfo: 
        {
          studyImage: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
          studyTitle: '모르고리즘',
          studyDescription: `알고리즘 끝까지 파보자!`,
          studyMemberCount: 23,
        },
    }
  },
  methods: {
    goThisStudy() {
      this.$router.push({ name: 'Study' });
    }
  },
}
</script>

<style scoped>
/* 해당 스터디로 이동시키는 부분 pointer */
.go-study {
  cursor: pointer;
}
</style>