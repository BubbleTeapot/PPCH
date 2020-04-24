<template>
  <div class="container">
    hello world!
    <img width="100" :src="require('../public/images/girl_one.jpg')" alt="">
    <div class="img"></div>
  </div>
</template>

<style scoped>
  .container {
    color: red;
  }
  .img {
    width: 600px;
    height: 600px;
    background-size: contain;
    background: url('../public/images/girl_one.jpg');
  }
</style>