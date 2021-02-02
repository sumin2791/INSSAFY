<template>
  <div id="wrap" :class="{ active: getSpinnerActive }">
    <p class="l-desc">처리 중</p>
    <div id="spinner" class="center" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  data() {
    return {};
  },
  computed: {
    ...mapGetters(['getSpinnerActive']),
  },
};
</script>

<style scoped>
#wrap {
  display: none;
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #695c4cdd;
  color: #fff;
  text-align: center;
  transition: opacity 0.3s, transform 0.3s ease;
  opacity: 0;
  transform: scale(1.4);
  z-index: -30;
}

.active {
  display: block !important;
  opacity: 1 !important;
  transform: scale(1) !important;
  z-index: 30 !important;
}

p {
  font-size: 14px;
  margin-top: calc(50% - 32px);
}

.center {
  position: absolute;
  top: 50%;
  left: 50%;
}

#spinner {
  display: inline-block;
  width: 100px;
  height: 100px;
  margin-left: -50px;
  margin-top: -50px;
  border: 3px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  border-top-color: #fff;
  animation: spin 1s ease-in-out infinite;
  -webkit-animation: spin 1s ease-in-out infinite;
}

@keyframes spin {
  to {
    -webkit-transform: rotate(360deg);
  }
}
@-webkit-keyframes spin {
  to {
    -webkit-transform: rotate(360deg);
  }
}
</style>
