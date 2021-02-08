<template>
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
          <td>{{ first.nickName }}</td>
          <td>{{ first.postLike }}</td>
        </tr>
        <!-- 2등 -->
        <tr class="text-center">
          <td><v-icon color="#C0C0C0">mdi-medal</v-icon></td>
          <td>{{ second.nickName }}</td>
          <td>{{ second.postLike }}</td>
        </tr>
        <!-- 3등 -->
        <tr class="text-center">
          <td><v-icon color="#8B4513">mdi-medal</v-icon></td>
          <td>{{ third.nickName }}</td>
          <td>{{ third.postLike }}</td>
        </tr>
      </tbody>
    </template>
  </v-simple-table>
</template>

<script>
export default {
  name: "LearningRank",
  data() {
    return {
      // 1등 정보
      first: {
        // 닉네임
        nickName: '김싸피',
        // 받은 좋아요 총 개수
        postLike: 123,

      },
      // 2등 정보
      second: {
        // 닉네임
        nickName: '윤싸피',
        // 받은 좋아요 총 개수
        postLike: 21,

      },
      // 3등 정보
      third: {
        // 닉네임
        nickName: '황싸피',
        // 받은 좋아요 총 개수
        postLike: 9,

      },
    }
  },
}
</script>

<style scoped>

</style>