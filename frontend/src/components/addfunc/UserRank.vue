<template>
  <v-card id="container">
    <div id="title">
    <!-- 체크리스트 제목 -->
    <div>유저랭킹(추가기능)</div>
    <!-- 수정버튼 -->
    <v-icon id="edit-icon"
      large
    >
      mdi-cog
    </v-icon>
    </div>
  </v-card>
</template>

<script>
export default {
  name: 'UserRank',
}
</script>

<style scoped>
/* 전체 폰트 */
#container {
  font-family: 'Noto Sans KR', sans-serif !important;
  box-shadow: var(--basic-shadow-s) !important;
  border-radius: 15px;
  background-color: var(--basic-color-bg2) !important;
  margin: 5% 0;
  padding: 5% 10%;
  min-height: 30vh;
}
/* 제목 */
#title {
  font-size: 20px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1em;
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