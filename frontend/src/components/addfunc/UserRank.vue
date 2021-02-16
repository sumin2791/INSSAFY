<template>
  <v-card id="container">
    <div id="title">
    <!-- 체크리스트 제목 -->
    <div @click="test">Ranking</div>
    </div>
    <div v-if="!isEnough">
      Oops! 데이터가 부족해요 더 활발히 활동해요!
    </div>
    <!-- 실제 랭킹 데이터 -->
    <div 
      v-if="isEnough"
      id="rank-list"
    >
      <v-simple-table dense>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-center pb-0">
                RANK
              </th>
              <th class="text-center">
                NickName
              </th>
              <th class="text-center">
                총 좋아요 수
              </th>
            </tr>
          </thead>
          <tbody>
            <!-- 1등 -->
            <tr class="text-center">
              <td><v-icon color="#FFD700">mdi-medal</v-icon></td>
              <td>1</td>
              <td>like 1</td>
            </tr>
            <!-- 2등 -->
            <tr class="text-center">
              <td><v-icon color="#C0C0C0">mdi-medal</v-icon></td>
              <td>2</td>
              <td>like 2</td>
            </tr>
            <!-- 3등 -->
            <tr class="text-center">
              <td><v-icon color="#8B4513">mdi-medal</v-icon></td>
              <td>3</td>
              <td>like 3</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>
  </v-card>
</template>

<script>
// 추가기능 api
import * as addFuncApi from '@/api/addfunc';


export default {
  name: 'UserRank',
  data() {
    return {
      rankingList: [],
      // 랭킹 보여주기엔 데이터 부족할 때
      isEnough: true,
    }
  },
  methods: {
    test() {
      // 보드 id
      const params = {
        board_id: Number(this.$route.params.board_id)
      }
      addFuncApi.getUserRank(params)
        .then(res => {
          console.log(res.status)
          // 조건 처리
          if (res.status === 204) {
            this.isEnough = false;
            console.log(this.isEnough)
          } else { this.rankingList = res.data.topUsers;
            console.log(this.rankingList, '랭킹리스트야!')
          }
        })
        .catch(err => {
          console.error(err)
        })
    },
  },
}
</script>

<style scoped>
/* 전체 폰트 */
#container {
  font-family: 'Noto Sans KR', sans-serif !important;
  box-shadow: var(--basic-shadow-s) !important;
  border-radius: 15px;
  background-color: var(--basic-color-bg2) !important;
  margin: 5% 0;
  padding: 5% 10%;
  min-height: 30vh;
}
/* 제목 */
#title {
  font-size: 20px;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1em;
}
</style>