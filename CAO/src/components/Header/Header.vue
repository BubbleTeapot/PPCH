<template>
  <div class="nav-wrp">
    <div class="mn-main bgCl1 shdCl1 ftCl2" @click.stop="switchMenu()">菜 单</div>
    <div class="circle bgCl1 shdCl1 ftCl2" :class="{'mn-cld1': opMenu}" @click.stop="toLogin()">登录</div>
    <div class="circle bgCl1 shdCl1 ftCl2" :class="{'mn-cld2': opMenu}" @click.stop="toReg()">注册</div>
    <div class="circle bgCl1 shdCl1 ftCl2" :class="{'mn-cld3': opMenu}" @click.stop="clMenu()"></div>
  </div>
</template>
<script>
export default {
  name: 'HeaderNav',
  data() {
    return {
      opMenu: false,
    }
  },
  mounted() {
    this.remove = this.docLs(this.clMenu);
    this.$once('hook:beforeDestroy', () => {
      this.remove();
    });
  },
  methods: {
    switchMenu() {
      this.opMenu = !this.opMenu;
    },
    clMenu() {
      if(!this.opMenu) return;
      this.opMenu = false;
    },
    toLogin() {
      this.clMenu();
      this.$router.push({name: 'login'});
    },
    toReg() {
      this.clMenu();
      this.$router.push({name: 'register'});
    }
  },
}
</script>
<style lang="less" src="./header.less" scoped></style>