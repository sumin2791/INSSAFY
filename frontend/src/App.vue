<template>
  <div id="app">
    <Spinner />
    <Nav id="navigation" />
    <router-view></router-view>
  </div>
</template>
<script>
// import './components/css/style.scss';
import './assets/css/static/style.css';

import Nav from './components/header/Nav.vue';
import Spinner from './components/etc/Spinner.vue';

export default {
  name: 'app',
  components: {
    Nav,
    Spinner,
  },
};
</script>

<style scoped>
#navigation {
  position: relative;
  z-index: 50;
}
</style>
