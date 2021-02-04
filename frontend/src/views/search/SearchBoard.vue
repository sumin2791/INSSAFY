<template>
  <v-app id="inspire">

    <v-main class="main-bg-color">
      <v-container>
        <v-row>
          <template v-for="n in 4">
            <v-col
              :key="n"
              class="mt-2"
              cols="12"
            >
              <strong>Category {{ n }}</strong>
            </v-col>

            <v-col
              v-for="j in 6"
              :key="`${n}${j}`"
              cols="6"
              md="2"
            >
              <v-sheet height="150"></v-sheet>
            </v-col>
          </template>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
// import Board from './Board.vue'

export default {
  name: 'SearchBoard',
  components: {
    // Board,
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