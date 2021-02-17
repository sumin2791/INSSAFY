<template>
  <div id="wrap" @click="clickProfile">
    <div id="default-image" class="image" v-if="!getUserImage" />
    <div class="image" v-if="getUserImage" :style="{ backgroundImage: `url(${getUserImage})`}" />
  </div>
</template>

<script>
export default {
  name: 'Profile',
  props:{
    getUserImage:String,
  },
  data() {
    return {
      imageUrl: '',
    };
  },
  computed: {
    
  },
  methods: {
    clickProfile: function() {
      this.$emit('clickProfile');
    },
  },
};
</script>

<style scoped>
#wrap {
  width: 100%;
  height: 100%;
}
#default-image {
  background-image: url('../../assets/images/default_profile.jpg');
}
.image:hover,
.image:active {
  border-width: 6px !important;
}
.image {
  /* border-style: solid !important;
  border-width: 3px !important; */
  transition: border 0.5s ease;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-position: center;
  background-size: cover;
}
</style>
