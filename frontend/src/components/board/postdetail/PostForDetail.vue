<template>
  <v-card id="container">
    <div id="post-detail">
      <!-- 포스트 디테일 헤더 부분 -->
      <div id=header>
        <!-- 클릭시 드롭다운 -->
        <v-menu
              bottom
              left
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  depressed
                  text
                  v-bind="attrs"
                  v-on="on"
                  class="px-0"
                >
                  <div id="header-user-info">
                    <!-- 프로필 사진 연결하기 -->
                    <v-avatar size="40">
                      <Profile id="profile-image"/>
                    </v-avatar>
                    <!-- 작성자이름, 작성일자 -->
                    <div id="header-info">
                      <div>{{nickname}}</div>
                      <div>{{ date }}</div>
                    </div>
                  </div>
                </v-btn>
              </template>
              
              <v-list>
                <!-- 프로필 보기 -->
                <v-list-item-group>
                  <v-list-item>
                    <v-list-item-title>
                      Profile 보기
                    </v-list-item-title>
                  </v-list-item>
                  <!-- 메세지 보내기 -->
                  <v-list-item>
                    <v-list-item-title>
                      메세지 보내기
                    </v-list-item-title>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
            </v-menu>
        
        <!-- 수정 삭제 신고 버튼과 판매상태 정보 -->
        <div id='header-right'>
          <!-- 판매정보 부분 -->
          <div>
            <v-chip id="sell-state">
              판매완료
            </v-chip>
          </div>
          <!-- 수정,삭제,신고 -->
          <div>
            <v-menu
              bottom
              left
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon 
                  x-small 
                  fab
                  v-bind="attrs"
                  v-on="on"
                >
                  <v-icon dark>mdi-dots-vertical</v-icon>
                </v-btn>
              </template>
              
              <v-list>
                <!-- 수정 -->
                <v-list-item-group>
                  <v-list-item>
                    <v-list-item-title v-if="flagWriter">
                      <PostModify :post="post" />
                    </v-list-item-title>
                  </v-list-item>
                  <!-- 삭제 -->
                  <v-list-item>
                    <v-list-item-title v-if="flagWriter">
                      삭제
                    </v-list-item-title>
                  </v-list-item>
                  <!-- 신고 -->
                  <v-list-item>
                    <v-list-item-title>
                      신고
                    </v-list-item-title>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
            </v-menu>
          </div>
        </div>
      </div>
    
      <!-- 포스트 제목 -->
      <div id="title">
        <!-- 중고장터용(지역) -->
        <div>
          <v-chip 
            outlined
            pill
            color="#695C4C"
            class="mr-3"
          >
            광주
          </v-chip>
        </div>
        <div>{{post.post_title}}</div>
      </div>
      <!-- 게시글 내용 -->
      <div id="description">
        {{post.post_description}}
        <!-- 이미지 미리보기 -->
        <img v-if="viewImage" :src="viewImage" alt="이미지 미리보기...">
      </div>

      <!-- 게시글 관련 이미지/댓글/좋아요 들어갈 부분 -->
      <div id="actions">
        <!-- 댓글 수 -->
        <div id="bottom-comment-like">
          <div>
            <v-icon
              middle
              class="mr-1"
            >mdi-comment-processing</v-icon>
            <span>{{ commentCount }}</span>
          </div>
          <!-- 좋아요 -->
          <div>
            <button
              @click="postLike"
            >
              <!-- 좋아요 중 -->
              <v-icon
                middle
                v-if="flagLike"
                color="#FFC400"
                class="mr-1 ml-2"
              >mdi-emoticon-excited</v-icon>
              <!-- 좋아요 취소상태 -->
              <v-icon
                middle
                v-else
              >mdi-emoticon-neutral-outline</v-icon>
              <span>{{ countLike }}</span>
            </button>
          </div>
        </div>
        <!-- 북마크 -->
        <div>
          <button
            @click="postScrap"
          >
            <!-- 북마크 중 -->
            <v-icon
              middle
              v-if="flagScrap"
              color="#0B2945"
            >mdi-bookmark</v-icon>
            <!-- 북마크 취소상태 -->
            <v-icon
              middle
              v-else
            >mdi-bookmark-outline</v-icon>
          </button>
        </div>
      </div>
    </div>
  </v-card>
</template>

<script>
// 게시글 수정
import PostModify from '@/components/board/postdetail/PostModify'
// 프로필 이미지
import Profile from '@/components/etc/Profile';

import * as postApi from '@/api/post'
import timeForToday from '@/plugins/timeForToday'

// 스타일 적용
import '@/assets/css/static/style.css';

export default {
  name:"PostForDetail",
  components:{
    PostModify,
    Profile,
  },
  props:{
    post:Object,
    nickname:String,
    commentCount:Number,
  },
  data() {
    return {
      // nickname:'',
      viewImage:null,
      // post:{},
      tempComment:'',

      // 추후에 연결해줘야하는 부분 - 이미지(프로필 사진)
      profileImg: 'https://avatars0.githubusercontent.com/u/9064066?v=4&s=460',
    }
  },
  computed:{
    flagLike(){
      return this.$store.state.post.flagLike
    },
    flagScrap(){
      return this.$store.state.post.flagScrap
    },
    countLike(){
      return this.$store.state.post.countLike
    },
    // countComment(){
    //   const commentList = this.$store.state.comment.commentList
    //   return commentList.length
    // },
    date(){
      return timeForToday(this.post.post_date)
    },
    flagWriter(){
      return this.post.user_id===localStorage.userId
    }
  },
  watch:{

    // '$route':'fetchData'
  },
  created() {
    console.log(this.post)
    // this.fetchData()

    // 이미지 하나만 추출했어요.
    var input = this.post.post_image
    if (input && input[0]) {
      var reader = new FileReader();
      reader.onload = e => {
        this.viewImage = e.target.result;
      };
      reader.readAsDataURL(input[0]);
    } else {
        this.viewImage = null;
    }
  },
  methods:{
    
    // user가 좋아요 버튼 클릭 시 vuex에서 flag 변화 + 서버와 연결
    postLike(e){
      postApi.likePost({user_id:localStorage.getItem('userId'), post_id:this.post.post_id})
        .then((res)=>{
          if(res.data.message==='No Subscription'){
            alert('구독 후에 이용가능합니다.')
          }else{
            this.$store.dispatch('post/postLike',this.flagLike)
          }
          // console.log(res)
        })
        .catch(err=>{
          console.error(err)
        })
      
    },

    // user가 스크랩 버튼 클릭 시 vuex에서 flag 변화 + 서버와 연결
    postScrap(e){
      postApi.scrapPost({user_id:localStorage.getItem('userId'), post_id:this.post.post_id})
        .then((res)=>{
          if(res.data.message==='No Subscription'){
            alert('구독 후에 이용가능합니다.')
          }else{
            this.$store.dispatch('post/postScrap') 
          }
            // console.log(res)
          })
          .catch(err=>{
            console.error(err)
          })
    }
  }
}
</script>

<style scoped>
/* 전체 폰트 */
#container {
  font-family: 'Noto Sans KR', sans-serif !important;
  background-color: #F9F9F9 !important;
}
/* 전체 detail 담겨진 부분 */
#post-detail {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 0;
  padding: 1% 2%;
}
/* 프로필 이미지 */
#profile-image {
  width: 100%;
  height: 100%;
}
/* 전체 최상단(유저 & 판매상태 & 신고) */
#header {
  margin: 1%;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
/* 사용자 정보 클릭시 드롭다운 연결 */

/* 프로필, 닉네임, 작성일  */
#header-user-info {
  display: flex;
  flex-direction: row;
  align-items: center;
}
/* 작성자 닉네임, 작성일 */
#header-info {
  height: 70%;
  display: flex;
  flex-direction: column;
  margin-left: 0.6em;
}
/* 닉네임 */
#header-info > div:first-child {
  line-height: 110%;
  font-size: 14px;
  font-weight: 400;
}
/* 작성일자 */
#header-info > div:last-of-type {
  line-height: 110%;
  font-size: 12px;
  font-weight: 300;
}
/* 헤더의 오른쪽 - 판매정보, 신고버튼 */
#header-right {
  display: flex;
  flex-direction: row;
}
/* 판매정보 */
#sell-state {
  background-color: #0B2945 ;
  color: #fff;
  border-radius: 10%;
}
/* 게시글 제목 */
#title {
  margin: 0 0 1% 1%;
  display: flex;
  flex-direction: row;
  font-size: 18px;
  font-weight: 600;
}
/* 게시글 내용 */
#description {
  margin: 0 0 1% 1%;
  font-size: 16px;
}
/* 댓글, 좋아요, 북마크 부분 */
#actions {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
/* 댓글 좋아요 부분 */
#bottom-comment-like {
  display: flex;
  flex-direction: row;
}
</style>