<template>
  <v-app id="inspire">

    <v-main class="main-bg-color">
      <v-container>
        <v-row>
          <!-- 검색어 결과 노출 부분 -->
          <v-col 
          class="text-center
            text-body-2">
            보드 중 "{{ searchKeyword }}"에 대한 검색 결과: {{ searchCount }} 건
          </v-col>
          <template v-for="n in 4">
            <Board 
              v-for="n in 6"
              :key="`${n}`"
              cols="6"
              md="2"
            />{{n}}
          </template>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Board from './Board.vue'

export default {
  name: 'SearchBoard',
  components: {
    Board,
  },
  data() {
    return {
      searchKeyword: '게임',
      searchCount: 248,
    }
  },
  methods: {
    goToCreateBoard(){
      return this.$router.push({ name: 'BoardForm' });
    }
  }
}
</script>

<style scoped>
.main-bg-color {
  background-color: #ebebe9;
}
</style>