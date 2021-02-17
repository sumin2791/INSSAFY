<template>
  <v-main class="main-bg-color">
    <v-container>
      <!-- 검색어 결과 노출 부분 -->
      <v-row>
        <v-col
          class="mb-4
          text-center
            text-body-1"
        >
          <h5 class="m-desc" v-if="payload.keyword !== ''">게시물 중 "{{ payload.keyword }}"에 대한 검색 결과</h5>
          <h5 class="m-desc" v-if="payload.keyword === ''">게시물 전체 검색 결과</h5>
        </v-col>
      </v-row>

      <!-- 보드 보여주는 부분 -->
      <v-row>
        <v-col class="col-12 col-sm-4 " v-for="(item, index) in searchList" :key="index">
          <Post :post="item" />
        </v-col>
      </v-row>
      <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
        <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">
          목록의 끝입니다 :)
        </div>
      </infinite-loading>
    </v-container>
  </v-main>
</template>

<script>
import Post from '@/components/search/Post.vue';

import InfiniteLoading from 'vue-infinite-loading';
import { mapActions, mapMutations, mapState } from 'vuex';
export default {
  name: 'SearchPost',
  components: {
    Post,
    InfiniteLoading,
  },
  data() {
    return {
      search: {
        infiniteState: null,
        image: false,
      },
    };
  },
  created: {
    image: false, //이미지 전환 필요, 보드 id 받아서 연결 필요
  },
  computed: {
    ...mapState('search', ['searchList', 'size', 'payload']),
  },
  watch: {
    searchList() {
      this.infiniteState.loaded();
    },
  },
  methods: {
    ...mapActions('search', ['actSearchAllPost']),
    ...mapMutations('search', ['SET_NEXT_PAGE']),
    infiniteHandler($state) {
      this.infiniteState = $state;
      this.actSearchAllPost().then((v) => {
        if (v) {
          setTimeout(() => {
            this.SET_NEXT_PAGE();
            $state.loaded();
          }, 1000);
        } else {
          $state.complete();
        }
      });
    },
  },
};
</script>

<style scoped>
.main-bg-color {
  background-color: #ebebe9;
}
.sort-dropdown {
  max-width: 50%;
}
/* 검색결과들 담는 컨테이너 */
.search-result {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 0;
  padding-top: 0;
  flex-flow: wrap;
  border: 3px solid #000000;
}
/* 검색보드 감싸는 div */
.search-result-board {
  flex-basis: 19.5%;
  border: 3px dotted #000000;
}
</style>
