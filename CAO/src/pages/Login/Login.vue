<template>
  <div class="container">
    <div class="lg-wrapper">
      <div class="lg-bg"></div>
      <div class="lg-box">
        <div class="lg-title ftCl1 no-st">登录</div>
        <div class="lg-welcome ftCl1 no-st">欢迎回来</div>
        <el-form :model="lgForm" :rules="rules" ref="lgForm" class="lg-form">
          <el-form-item prop="username">
            <span slot="label" class="ur-name ftCl1">用户名</span>
            <el-input v-model="lgForm.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <span slot="label" class="ur-pwd ftCl1">密码</span>
            <el-input type="password" v-model="lgForm.password" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div class="sbmt-btn btn bgCl1 bdCl1 ftCl2 no-st csr" @click="submitForm('lgForm')">登录</div>
        <div class="no-st crfix">
          <div class="rg-btn btn ftCl3 csr fl" @click="toRegister">注册</div>
          <div class="fd-pwd btn ftCl1 csr fr">忘记密码</div>
        </div>
      </div>
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
      lgForm: {
        usernameL: '',
        password: ''
      },
      rules: {
        username: [
          {validator: checkUsername, trigger: 'blur'}
        ],
        password: [
          {validator: checkPassword, trigger: 'blur'}
        ]
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
    },
    toRegister() {
      this.$router.push({name: 'register'})
    }
  }
}
</script>
<style lang="less" src="./styles/login.less" scoped></style>