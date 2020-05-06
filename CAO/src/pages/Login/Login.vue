<template>
  <div class="container">
    <div class="lg-wrapper">
      <div class="lg-bg"></div>
      <div class="lg-box">
        <div class="lg-title">登录</div>
        <div class="lg-welcome">欢迎回来</div>
        <el-form :model="lgForm" :rules="rules" ref="lgForm" class="lg-form">
          <el-form-item label="用户名" props="username">
            <el-input v-model="lgForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" props="password">
            <el-input type="password" v-model="lgForm.password" autocomplete="off"></el-input>
          </el-form-item>
          <div @click="submitForm('lgForm')">提交</div>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    // const checkUsername = (rule, value, callback) => {
    //   console.log(111)
    //   if(!value) {
    //     return callback(new Error('用户名不能为空!'))
    //   }
    //   callback()
    // }
    // const checkPassword = (rule, value, callback) => {
    //   if(!value) {
    //     return callback(new Error('密码不能为空!'))
    //   }
    //   callback()
    // };
    return {
      lgForm: {
        usernameL: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          // {validator: checkUsername, trigger: 'blur'}
        ],
        password: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          // {validator: checkPassword, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        console.log(valid)
      })
    }
  }
}
</script>
<style lang="less" src="./styles/login.less" scoped></style>