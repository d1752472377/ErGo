<template>
  <div>
		<div class="ui top segment" style="text-align: center">
			<h2 class="m-text-500">分类 {{ categoryName }} 下的文章</h2>
		</div>
		<BlogList :getBlogList="getBlogList" :blogList="blogList" :totalPage="totalPage"/>
	</div>
</template>

<script>
import HomeContent from '@/components/HomeContent.vue'
export default {
    name:"Category",
    comments:{HomeContent}
}
</script>

<style>

</style>