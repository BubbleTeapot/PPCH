<template>
  <div class="nav-wrp">
    <div class="mn-main bgCl1 shdCl1" @click.stop="switchMenu()"></div>
    <div class="circle bgCl1 shdCl1" :class="{'mn-cld1': opMenu}" @click.stop="clMenu()"></div>
    <div class="circle bgCl1 shdCl1" :class="{'mn-cld2': opMenu}" @click.stop="clMenu()"></div>
    <div class="circle bgCl1 shdCl1" :class="{'mn-cld3': opMenu}" @click.stop="clMenu()"></div>
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
  },
  methods: {
    switchMenu() {
      this.opMenu = !this.opMenu;
    },
    clMenu() {
      if(!this.opMenu) return;
      this.opMenu = false;
    }
  },
  beforeDestory() {
    console.log(11)
  }
}
</script>
<style lang="less" src="./header.less" scoped></style>