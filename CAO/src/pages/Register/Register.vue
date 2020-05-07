<template>
  <div class="container">
    <div class="rg-wrapper">
      <div class="rg-box">
        <div class="rg-title ftCl1 no-st">注册</div>
        <div class="rg-welcome ftCl1 no-st">遇事不决可问春风</div>
        <el-form :model="rgForm" :rules="rules" ref="rgForm" class="rg-form">
          <el-form-item prop="username">
            <span slot="label" class="ur-name ftCl1">用户名</span>
            <el-input v-model="rgForm.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <span slot="label" class="ur-pwd ftCl1">密码</span>
            <el-input type="password" v-model="rgForm.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="passwordAgain">
            <span slot="label" class="ur-pwd ftCl1">再次输入密码</span>
            <el-input type="password" v-model="rgForm.passwordAgain" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div class="sbmt-btn btn bgCl1 bdCl1 ftCl2 no-st csr" @click="submitForm('rgForm')">注册</div>
      </div>
      <div class="rg-bg"></div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    const checkUsername = (rule, value, callback) => {
      if(!value) {
        return callback(new Error('用户名不能为空!'))
      }
      callback()
    }
    const checkPassword = (rule, value, callback) => {
      if(!value) {
        return callback(new Error('密码不能为空!'))
      }
      callback()
    };
    return {
      rgForm: {
        usernameL: '',
        password: '',
        passwordAgain: ''
      },
      rules: {
        username: [
          {validator: checkUsername, trigger: 'blur'}
        ],
        password: [
          {validator: checkPassword, trigger: 'blur'}
        ],

      }
    }
  },
  methods: {
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        if(!valid) {
          return;
        }
      })
    }
  }
}
</script>
<style lang="less" src="./styles/register.less" scoped></style>