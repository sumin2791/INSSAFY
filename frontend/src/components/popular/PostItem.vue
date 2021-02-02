<template>
  <div id="wrap">
    <!-- <div id="pi-bg1" class="pi-bg" :style="{backgroundImage: `url(${items[0].image})`}"/> -->
    <div id="header">
      <div id="pi0" :class="{ aheader: active == 0 }">
        <div id="pi-bg1" class="pi-bg" />
        <div class="pi-order b-title">1st</div>
        <div class="pi-count t-desc-e">{{ items[0].count }}</div>
      </div>
      <div id="pi1" :class="{ aheader: active == 1 }">
        <div id="pi-bg2" class="pi-bg" />
        <div class="pi-order b-title">2nd</div>
        <div class="pi-count t-desc-e">{{ items[1].count }}</div>
      </div>
      <div id="pi2" :class="{ aheader: active == 2 }">
        <div id="pi-bg3" class="pi-bg" />
        <div class="pi-order b-title">3rd</div>
        <div class="pi-count t-desc-e">{{ items[2].count }}</div>
      </div>
    </div>
    <div id="title">
      <div id="pi-p-container">
        <p class="pi-p l-desc" :class="{ atitle: active == 0 }">{{ items[0].postTitle }}</p>
        <p class="pi-p l-desc" :class="{ atitle: active == 1 }">{{ items[1].postTitle }}</p>
        <p class="pi-p l-desc" :class="{ atitle: active == 2 }">{{ items[2].postTitle }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PostItme',
  props: {
    items: Array,
  },
  data() {
    return {
      active: 0,
    };
  },
  mounted() {
    //1초마다 반전시킴
    setInterval(() => {
      this.active = (this.active + 1) % 3;
    }, 2000);
  },
};
</script>

<style scoped>
#wrap {
  position: relative;
}

#header {
  position: absolute;
  width: 50%;
}
#header::after {
  content: '';
  display: block;
  padding-bottom: 60%;
}
#pi0 {
  opacity: 0;
  transition: opacity 1s ease;
}
#pi1 {
  opacity: 0;
  transition: opacity 1s ease;
}
#pi2 {
  opacity: 0;
  transition: opacity 1s ease;
}
#pi-bg1 {
  width: 100%;
  height: 100%;
  background-image: url(../../assets/images/img1.jpg);
  background-position: center;
  filter: brightness(0.8);
  background-size: cover;
}
#pi-bg2 {
  width: 100%;
  height: 100%;
  background-image: url(../../assets/images/img2.jpg);
  background-position: center;
  filter: brightness(0.8);
  background-size: cover;
}
#pi-bg3 {
  width: 100%;
  height: 100%;
  background-image: url(../../assets/images/img3.jpg);
  background-position: center;
  filter: brightness(0.8);
  background-size: cover;
}
.pi-bg {
  position: absolute;
  width: 100px;
  height: 100px;
}
.pi-order {
  position: absolute;
  left: 5%;
  color: #fff;
  font-size: 30px;
}
.pi-count {
  position: absolute;
  left: 5%;
  margin-top: 30px;
  color: #fff;
  font-size: 30px;
}

#title {
  position: relative;
  width: 100%;
  z-index: 2;
}
#title::after {
  content: '';
  display: block;
  padding-bottom: 30%;
}
#pi-p-container {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: flex-end;
  position: absolute;
  width: 100%;
  height: 100%;
  background: var(#ffffff80); /* fallback for old browsers */
  background: -webkit-linear-gradient(to right, #ffffff80 10%, #fff 50%); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(to right, #ffffff80 10%, #fff 50%); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}
.pi-p {
  margin-right: 20px;
  margin-bottom: 0;
  transition: transform 1s ease;
}

.aheader {
  opacity: 1 !important;
  z-index: 1 !important;
}
.atitle {
  transform: scale(1.1);
  font-weight: 400;
}
</style>
