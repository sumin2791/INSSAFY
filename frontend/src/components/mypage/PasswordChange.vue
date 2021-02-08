<template>
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
            <!-- 기존의 비밀번호 확인 -->
            <v-text-field
              v-model="password"
              id="password"
              label="기존의 비밀번호"
              hint="비밀번호 변경을 위해 기존의 비밀번호를 입력해주세요"
              color="blue darken-4"
              :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
              :type="passwordShow ? 'text' : 'password'"
              required
              @click:append="passwordShow = !passwordShow"
            ></v-text-field>
            <!-- 새로운 비밀번호 -->
            <v-text-field
              v-model="newPassword"
              id="newPassword"
              label="새 비밀번호"
              hint="비밀번호 변경을 위해 새 비밀번호를 입력해주세요"
              color="blue darken-4"
              :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
              :type="passwordShow ? 'text' : 'password'"
              required
              @click:append="passwordShow = !passwordShow"
            ></v-text-field>
            <!-- 새 비밀번호 확인 -->
            <v-text-field
              v-model="newPasswordConfirm"
              id="newPasswordConfirm"
              label="새 비밀번호 확인"
              hint="비밀번호 변경을 위해 새 비밀번호를 한번 더 입력해주세요"
              color="blue darken-4"
              :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
              :type="passwordShow ? 'text' : 'password'"
              required
              @click:append="passwordShow = !passwordShow"
            >
            </v-text-field>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          text
          @click="dialog = false"
        >
          Close
        </v-btn>
        <v-btn
          text
          @click="dialog = false"
        >
          확인
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
// 비밀번호 양식 체크
import PV from 'password-validator';


export default {
  name: "PasswordChange",
  data() {
    return {
      // 기존 비밀번호
      password: '',
      passwordSchema: new PV(),
      newPassword: '',
      newPasswordConfirm: '',
      // 에러 메세지
      error: {
        password: false,
        newPassword: false,
        newPasswordConfirm: false,
      },

      // 비밀번호 변경 버튼
      dialog: false,
      // 비밀번호 텍스트 보여주기
      passwordShow: false,
    }
  },
  created() {
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    password() {
      this.checkForm();
    },
    newPassword() {
      this.checkForm();
    },
    newPasswordConfirm() {
      this.checkForm();
    },
  },
  methods: {
    checkForm() {
    // 비밀번호 양식 확인 - 여기서 기존의 비밀번호 가져와야한다
    if (this.password.length >= 0 && !this.passwordSchema.validate(this.password)) this.error.password = '영문,숫자 포함 8 자리이상이어야 합니다.';
    else this.error.password = false;

    // 새 비밀번호 같은지 확인
    if (this.newPassword.length >= 0 && this.newPassword != this.newPasswordConfirm) this.error.newPasswordConfirm = '새 비밀번호가 서로 같지 않습니다.';
    else this.error.newPasswordConfirm = false;
    },
  },
}
</script>

<style scoped>

</style>