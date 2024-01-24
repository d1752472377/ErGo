<template>
  <div class="cont">
    <h3 class="ui dividing header">Comments | 共 {{ allComment }} 条评论<span v-if="closeComment!==0">（{{ closeComment }} 条评论被隐藏）</span></h3>
	<h3 class="ui header" v-if="allComment===0">快来抢沙发！</h3>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.cont{
    background-color: beige;
}
</style>