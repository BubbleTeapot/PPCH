<template>
  <div class="nav-wrp">
    <div class="mn-main bgCl1 shdCl1" @click="switchMenu()"></div>
    <div class="circle bgCl1 shdCl1" :class="{'mn-cld1': opMenu}" @click="clMenu()"></div>
    <div class="circle bgCl1 shdCl1" :class="{'mn-cld2': opMenu}" @click="clMenu()"></div>
    <div class="circle bgCl1 shdCl1" :class="{'mn-cld3': opMenu}" @click="clMenu()"></div>
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
  methods: {
    switchMenu() {
      this.opMenu = !this.opMenu;
    },
    clMenu() {
      this.opMenu = false;
    }
  }
}
</script>
<style lang="less" src="./header.less" scoped></style>