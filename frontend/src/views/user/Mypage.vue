<template>
  <v-app id="inspire">
    <v-main 
      class="grey 
      lighten-3"
    >
      <v-container
        class="pt-8"
      >
        <v-row>
          <!-- 모바일 화면 -->
          <!-- 좌측 내 정보 부분 -->
          <v-col 
            cols="12"
            v-if="ResponsiveSize.isMobile"
          >
            <v-sheet rounded="lg">
              <v-list color="transparent">
                <!-- 내 정보 부분 -->
                <v-list-item
                  color="grey 
                  lighten-4"
                  class="d-flex
                  flex-row
                  justify-space-between
                  pa-4"
                >
                  <!-- 프로필 사진 연결하기 -->
                  <v-avatar
                    :size="ResponsiveSize.viewSize * 0.3"
                    class="flex-shrink-1"
                  >
                    <img
                      alt="Avatar"
                      :src="myInfo.profileImg"
                    >
                  </v-avatar>
                  <!-- 회원이 입력한 정보 -->
                  <div 
                    class="ma-4
                    d-flex
                    flex-column
                    align-start"
                  >
                    <!-- 닉네임 -->
                    <div
                      class="text-h4"
                    >
                      {{ myInfo.nickName }}
                    </div>
                    <!-- 이메일 -->
                    <div>
                      {{ myInfo.email }}
                    </div>
                    <!-- 지역 & 기수 -->
                    <div>
                      {{ myInfo.location }} {{ myInfo.generation }}기
                    </div>
                  </div>
                  <!-- edit 버튼('나'일 때만 보여주기) -->
                  <div 
                    class="align-self-start"
                  >
                    <v-btn
                      icon
                      outlined
                      x-small
                      fab
                      color="#0B2945"
                    >
                      <v-icon dark>
                        mdi-pencil
                      </v-icon>
                    </v-btn>
                  </div>
                </v-list-item>
                <!-- 비밀번호 변경 버튼 -->
                <!-- 비밀번호 변경 부분 -->
                <v-dialog
                  v-model="dialog"
                  persistent
                  max-width="400px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      block
                      depressed
                      v-bind="attrs"
                      v-on="on"
                      class="pa-0"
                    >
                    비밀번호 변경
                    </v-btn>  
                  </template>
                  <v-card>
                    <v-card-title>
                      <span class="headline">비밀번호 확인</span>
                    </v-card-title>
                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-text-field
                            label="비밀번호 확인"
                            hint="비밀번호 변경을 위해 비밀번호를 입력해주세요"
                            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                            :type="passwordShow ? 'text' : 'password'"
                            required
                            @click:append="passwordShow = !passwordShow"
                          ></v-text-field>
                        </v-row>
                      </v-container>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        color="blue darken-1"
                        text
                        @click="dialog = false"
                      >
                        Close
                      </v-btn>
                      <v-btn
                        color="blue darken-1"
                        text
                        @click="dialog = false"
                      >
                        다음
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>

                <v-divider class="my-2"></v-divider>
                <!-- 이동 탭 부분 -->
                <v-list-item-group
                  class="d-flex
                  flex-row"
                  v-model="ResponsiveSize.whichTapPoint"
                  mandatory
                  >
                  <v-list-item
                    v-for="[icon, text, index] in links"
                    :key="text"
                    link
                    class="pa-1
                    ma-1"
                    @click="clickMobileTap(index)"
                  > 
                    <!-- 모바일 탭 전환 -->
                    <v-list-item-content
                      class="d-flex
                      flex-row
                      justify-center
                      ma-1
                      px-1
                      py-0"
                    >
                      <v-icon
                        class="pa-2
                        ma-1">
                        {{ icon }}
                      </v-icon>
                      <div 
                        style="font-size: 12px;"
                        class="text-center"
                      >
                        {{ text }}
                      </div>
                    </v-list-item-content>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
            </v-sheet>
          </v-col>
          
          <!-- PC화면 -->
          <!-- 좌측 내 정보 부분 -->
          <v-col sm="3" v-else>
            <v-sheet rounded="lg">
              <v-list color="transparent">
                <div
                  color="grey 
                  lighten-4"
                  class="d-flex
                  flex-column
                  justify-space-between
                  pa-4"
                >
                  <!-- 내 정보 부분 -->
                  <div
                    class="d-flex
                    flex-row
                    justify-space-between"
                  >
                    <!-- 내 정보 타이틀 -->
                    <div class="text-h5">내 정보</div>
                    <!-- 내 정보 수정버튼 -->
                    <div>
                      <v-btn
                        icon
                        outlined
                        x-small
                        fab
                        color="#0B2945"
                      >
                        <v-icon dark>
                          mdi-pencil
                        </v-icon>
                      </v-btn>
                    </div>
                  </div>
                  <!-- 프로필 이미지 -->
                  <v-avatar
                    :size="ResponsiveSize.viewSize * 0.1"
                    class="align-self-center"
                  >
                    <img
                      alt="Avatar"
                      :src="myInfo.profileImg"
                    >
                  </v-avatar>
                  
                  <!-- 사용자 입력한 값 보여주기 -->
                  <div
                    class="mt-4
                    text-h5 
                    font-weight-black">
                    {{ myInfo.nickName }}
                  </div>
                  <div>
                    {{ myInfo.email }}
                  </div>
                  <div>
                    {{ myInfo.location }} {{ myInfo.generation }}기
                  </div>
                </div>
                <!-- 비밀번호 변경 부분 -->
                <v-dialog
                  v-model="dialog"
                  persistent
                  max-width="400px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      block
                      depressed
                      v-bind="attrs"
                      v-on="on"
                      class="pa-0"
                    >
                    비밀번호 변경
                    </v-btn>  
                  </template>
                  <v-card>
                    <v-card-title>
                      <span class="headline">비밀번호 확인</span>
                    </v-card-title>
                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-text-field
                            label="비밀번호 확인"
                            hint="비밀번호 변경을 위해 비밀번호를 입력해주세요"
                            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                            :type="passwordShow ? 'text' : 'password'"
                            required
                            @click:append="passwordShow = !passwordShow"
                          ></v-text-field>
                        </v-row>
                      </v-container>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        color="blue darken-1"
                        text
                        @click="dialog = false"
                      >
                        Close
                      </v-btn>
                      <v-btn
                        color="blue darken-1"
                        text
                        @click="dialog = false"
                      >
                        다음
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>

                <v-divider class="my-2"></v-divider>

                <!-- 이동 탭 부분 -->
                <v-list-item-group
                  v-model="ResponsiveSize.whichTapPoint"
                  mandatory
                >
                  <v-list-item
                    v-for="[icon, text, index] in links"
                    :key="text"
                    link
                    @click="clickMobileTap(index)"
                  > 
                    <!-- 모바일 탭 전환 -->
                    <v-list-item-icon>
                      <v-icon>{{ icon }}</v-icon>
                    </v-list-item-icon>
                    <v-list-item-title>{{ text }}</v-list-item-title>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
            </v-sheet>
          </v-col>

          <v-col sm="9">
            <v-sheet
              min-height="85vh"
              rounded="lg"
              class="pa-2"
            >
              <!-- 내 구독보드 -->
              <div
                v-if="mobileTap[0]"
              >
                <v-card-title
                  class="text-h6
                  font-weight-black
                  pb-0"
                >구독중인 보드</v-card-title>
                <v-divider></v-divider>
                <Subscription />
                <Subscription />
                <Subscription />
              </div>
              
              <!-- 내 작성글 -->
              <div
                v-if="mobileTap[1]"
              >
                <v-card-title
                  class="text-h6
                  font-weight-black
                  pb-0"
                >내 작성글</v-card-title>
                <v-divider></v-divider>
                <MyPost />
                <MyPost />
                <MyPost />
              </div>
              <!-- 내 댓글 -->
              <div
                v-if="mobileTap[2]"
              >
                <v-card-title
                  class="text-h6
                  font-weight-black
                  pb-0"
                >내 작성댓글</v-card-title>
                <v-divider></v-divider>
                <MyPost />
              </div>

              <!-- 내가 스크랩한 글 -->
              <div
                v-if="mobileTap[3]"
              >
                <v-card-title
                  class="text-h6
                  font-weight-black
                  pb-0"
                >스크랩한 글</v-card-title>
                <v-divider></v-divider>
                <ScrapPost />
              </div>
            </v-sheet>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Subscription from "@/components/mypage/Subscription.vue"
import MyPost from "@/components/mypage/MyPost.vue"
import ScrapPost from "@/components/mypage/ScrapPost.vue"


export default {
  name: "mypage",
  components: {
    Subscription,
    MyPost,
    ScrapPost,
  },
  // 뷰 인스턴스 제거될 때 resize 호출
  beforeDestroy () {
      if (typeof window === 'undefined') return

      window.removeEventListener('resize', this.onResize, { passive: true })
  },
  // resize 실시해서 현재 화면 크기 확인
  mounted () {
    this.onResize()

    window.addEventListener('resize', this.onResize, { passive: true })
  },
  data() {
    return {
      // 모바일 화면 체크 mobile화면인지, 사이즈 이용할 값
      ResponsiveSize: {
        isMobile: false, 
        viewSize: 0,
        whichTapPoint: 0,
      },
      // 모바일 화면 전환 플래그
      mobileTap: [true, false, false, false],
      // 모바일 화면 전환 탭
      links: [
        ['mdi-view-list', '내 구독보드', 0],
        ['mdi-newspaper-variant-multiple-outline', '내 작성글', 1],
        ['mdi-comment-text-multiple', '내 작성댓글', 2],
        ['mdi-bookmark-multiple', '내 스크랩', 3],
      ],
      // 내 정보 부분
      myInfo: {
        nickName: "김싸피",
        email: "ssafy@ssafy.com",
        location: "광주",
        generation: 4,
        profileImg: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
      },
      // 비밀번호 변경 버튼
      dialog: false,
      // 비밀번호 텍스트 보여주기
      passwordShow: false,
      // 구독 중 보드
      list() {
        var list = [];
        for (let index = 1; index <= 10; index++) {
          var item = {
            id: index,
            type: 'curation',
            title: '보드명',
            description: '보드 설명 보드 설명 보드 설명 보드 설명 보드 설명 보드 설명',
            hashtag: '#싸피 #여행 #바다 #싸피 #여행 #바다 #싸피 #여행 #바다 #싸피 #여행 #바다',
            count: 100,
          };
          list.push(item);
        }
        return list;
      },
    }
  },
  methods: {
    // 현재 활성화된 기기에 따라 flag 변경
    onResize() {
      this.ResponsiveSize.isMobile = window.innerWidth < 426;
      this.ResponsiveSize.viewSize = window.innerWidth;
    },

    clickMobileTap: function(point) {
      // 전체 false로 초기화
      this.mobileTap = this.mobileTap.map(function (el) {
        return false
      })
      // 클릭하게 되면 pointing된 부분을 true로 변경
      this.mobileTap[point] = true;
      // 현재 가르키고 있는 탭 부분 변경
      this.ResponsiveSize.whichTapPoint = point
    },
  },
}
</script>
<style scoped>
</style>