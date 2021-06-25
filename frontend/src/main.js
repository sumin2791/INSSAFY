import Vue from 'vue';
import App from './App.vue';
import VueRouter from 'vue-router';
import routes from './routes';
import store from './vuex/store';

// Polyfills
import 'core-js/stable';
import 'regenerator-runtime/runtime';

// bootstrap
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

// vuetify
import vuetify from './plugins/vuetify';

// fontawesome
import '@/FontAwesomeIcon.js';
// masonry
import VueMasonry from 'vue-masonry-css';
Vue.use(VueMasonry);

Vue.config.productionTip = false;

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

//https://www.npmjs.com/package/vue-moment
//[moment 사용법]: https://flamingotiger.github.io/javascript/momentjs/
Vue.use(require('vue-moment'));

//toast
//https://www.npmjs.com/package/vue-toast-notification
import VueToast from 'vue-toast-notification';
// Import one of the available themes
//import 'vue-toast-notification/dist/theme-default.css';
import 'vue-toast-notification/dist/theme-sugar.css';

Vue.use(VueToast, {
  position: 'bottom-right',
  duration: 2000,
  //message: msg,
  //typs: 'default'
  // duration: 1000,
  // position: 'bottom-right',
  // dismissible: true,
  // onClick: function() {
  //   alert('alert');
  // },
  // onDismiss: function() {
  //   alert('dismissed!');
  // },
  // queue: false,
  // pauseOnHover: true,
});

export const router = new VueRouter({
  mode: 'history',
  // base: process.env.BASE_URL,
  routes,
  //앞으로가기 뒤로가기시 해당 스크롤 위치 기억
  //native와 유사하게 동작
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { x: 0, y: 0 };
    }
  },
});

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount('#app');
