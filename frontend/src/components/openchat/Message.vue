<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <div id="message-you">
          Single-line item
        </div>
      </v-col>
      <v-col cols="12">
        <div id="message-me">
          Single-line
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'Message',
}
</script>

<style scoped>
/* message 속성(상대방) */
#message-you {
  display: inline;
  padding: 0.4em 0.8em;
  border-radius: 0.7em 0.7em 0.7em 0;
  background-color: var(--basic-color-bg);
  color: var(--basic-color-fill);
  max-width: 70%;
}
/* message 속성(나) */
#message-me {
  display: inline;
  float: right;
  padding: 0.4em 0.8em;
  border-radius: 0.7em 0.7em 0 0.7em;
  background-color: var(--basic-color-key);
  color: #FFF;
  max-width: 70%;
}
</style>