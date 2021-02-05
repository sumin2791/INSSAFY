<template>
  <v-card
    class="mx-auto"
    outlined
  >
    <v-list-item three-line>
      <v-list-item-content>
        <v-list-item-title class="text-body-1 mb-1">
          {{ title }}
        </v-list-item-title>
        <v-list-item-subtitle>
          {{ contents }}
        </v-list-item-subtitle>
      </v-list-item-content>

      <v-list-item-avatar
        tile
        size="80"
      >
        <img
          alt="Avatar"
          :src="img"
        >
      </v-list-item-avatar>
    </v-list-item>

    <v-card-actions>
      <v-btn
        outlined
        rounded
        text
        block
      >
        판매중
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "MarketItem",
  data() {
    return {
      // 판매정보 가져올 부분
      title: '(제목)가로본능 팔아요',
      contents: '(내용)미개봉 완제품',
      img: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
      status: '',
      // 판매중, 판매완료, 거래중 상태 표시
      optionStatus: ['판매중', '판매완료', '거래중'],
    }
  },
}
</script>

<style scoped>
</style>