<template>
  <div id="wrap">
    <VideoBg :sources="['@/assets/video/intro.mov']" img="@/assets/images/intro.jpg">
      iN.SSAFY
    </VideoBg>
  </div>
</template>
<script>
export default {
  name: 'Intro',
  data() {
    return {};
  },
};
</script>
<style scoped>
#wrap {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 100;
}
</style>
