<template>
  <v-app>
    <v-navigation-drawer app>
      <!-- 왼쪽 내 정보 부분 -->
      <v-card
        height="400"
        width="256"
        class="mx-auto"
      >
      <v-navigation-drawer permanent>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title class="title">
              Application
            </v-list-item-title>
            <v-list-item-subtitle>
              subtext
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>

        <v-list
          dense
          nav
        >
          <v-list-item
            v-for="item in items"
            :key="item.title"
            link
          >
            <v-list-item-icon>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
    </v-card>
    </v-navigation-drawer>

    <!-- Sizes your content based upon application components -->
    <v-main>

      <!-- Provides the application the proper gutter -->
      <v-container fluid>

      </v-container>
    </v-main>
  </v-app>
</template>

<script>
// import Subscription from "@/components/mypage/Subscription.vue"
// import MyPost from "@/components/mypage/MyPost.vue"
// import ScrapPost from "@/components/mypage/ScrapPost.vue"

export default {
  name: "mypage",
  components: {
    // Subscription,
    // MyPost,
    // ScrapPost,
  },
  data() {
    return {
      mobileTap: [true, false, false, false],
    }
  },
  methods: {
    clickMyBoard: function() {
      // 전체 false로 초기화
      this.mobileTap = this.mobileTap.map(function (el) {
        return false
      })
      // 클릭하게 되면 index를 가장 높게 만들겠다
      this.mobileTap[0] = true;
    },
    clickMyPost: function() {
      // 전체 false로 초기화
      this.mobileTap = this.mobileTap.map(function (el) {
        return false
      })
      // 클릭하게 되면 index를 가장 높게 만들겠다
      this.mobileTap[1] = true;
    },
    clickMyComment: function() {
      // 전체 false로 초기화
      this.mobileTap = this.mobileTap.map(function (el) {
        return false
      })
      // 클릭하게 되면 index를 가장 높게 만들겠다
      this.mobileTap[2] = true;
    },
    clickMyScrap: function() {
      // 전체 false로 초기화
      this.mobileTap = this.mobileTap.map(function (el) {
        return false
      })
      // 클릭하게 되면 index를 가장 높게 만들겠다
      this.mobileTap[3] = true;
    },
  },
}
</script>
<style scoped>
/* 마이페이지 전체 컨테이너 속성 */
.container-box {
  font-family: sans-serif;
  display: grid;
  grid-template-rows: 1fr 2fr;
  grid-template-columns: repeat(4, 1fr);
  grid-template-areas: 
    "info mypost mycomment scrap"
    "myboard mypost mycomment scrap";
  margin: 0 5% 0 5%;
  height: 100vh;
  }
/* 전체 페이지의 컬럼 */
.container-box .column {
  flex-basis: 24%;
}
/* 각 컬럼들 - 내정보&구독 / 작성글 / 작성 댓글/ 스크랩한 글 */
div > div {
  padding-top: 0;
}
/* 내 정보 보여줄 부분 */
#left-sidebar {
  grid-area: info;
  display: flex;
  flex-direction: column;
  align-items: stretch;
}

.my-board {
  grid-area: myboard;
}
.my-post {
  grid-area: mypost;
}
.my-comment {
  grid-area: mycomment;
}
.my-scrap {
  grid-area: scrap;
}

/* 사용자 정보관련 */
.user-profile {
  display: grid;
  grid-template-rows: 1fr 2fr 1.2fr;
  grid-template-columns: 1fr 2fr 1fr;
  grid-template-areas: 
    "hd hd hd"
    ". img ."
    "myinfo myinfo myinfo";
  
  /* place-content: center; */
}
/* 내 정보의 header */
.hd {
  grid-area: hd;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  align-self: flex-start;
  margin: 5%;
}
.hd .info {
  margin: 0;
}
/* 프로필 이미지 부분 */
.img {
  grid-area: img;
}
.avatar {
  width: 140px;
  height: 140px;
  border-radius: 50%;
}
/* 프로필 이미지 편집 버튼 */
.img div {
  z-index: 1;
  position: relative;
  display: table-cell;
  left: 100px;
  top: -40px;
  background-color: antiquewhite;
  text-align: center;
}
/* 사용자 입력값 보여줄 부분 */
.my-info {
  grid-area: myinfo;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}
/* 비밀번호 변경 */
.password-change {
  text-align: end; 
  margin: 0;
}
/* 모바일 이동 탭 */
.mobile-tap {
  grid-area: tap;
  display: none;
}

/* 모바일 웹 반응형 사이즈 */
@media (max-width: 425px) {
  /* 마이페이지 전체 컨테이너 속성 */
  .container-box {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: column;
    margin: 0.5rem 0.5rem;
  }
  /* 사용자 정보관련 */
  .user-profile {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    box-sizing: border-box;
  }
  .hd {
    display: flex;
    order: 1;
    box-sizing: border-box;
    margin: 0;
    padding-top: 2%;
  }
  .info {
    display: none;
  }
  .img {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 20%;
    height: 60%;
    margin: 0 2%;
    padding: 2%;
  }
  .avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  }
  .my-info {
    flex-basis: 67%;
  }
  .img div {
    position: relative;
    left: 20px;
    top: -30px;
  }
  .password-change > p {
    margin: 0;
  }
  /* 모바일 이동 탭 */
  .mobile-tap {
    display: flex;
    justify-content: center;
    align-content: center;
    background-color: #695C4C;
  }
  .mobile-tap div {
    margin: 4% auto;
    color: #ffffff;
    font-size: 30px;
  }
  /* 첫 모바일 화면에서 진입시 */
  .z-index-forward {
    background-color: #ebebe9;
    position: absolute;
    width: 96%;
    height: 70%;
    top: 37.5%;
    z-index: 1;
  }
  .z-index-back {
    background-color: #ebebe9;
    position: absolute;
    width: 96%;
    height: 70%;
    z-index: 0;
    top: 37.5%;
  }
  /* 화면 탭 클릭시 탭 강조 */
  .focustap {
    color:#0B2945;
  }
  /* 화면 탭 클릭시 보여줄 리스트 옵션 - 최상위로 */
  .focuslist {
    z-index: 10;
  }
}

</style>