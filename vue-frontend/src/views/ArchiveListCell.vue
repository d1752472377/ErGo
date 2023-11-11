<template>
  <div class="archive-list-cell">
    <div class="info">
        <Tag class="time-Tag">{{ post.createTime }}</Tag>
        <div class="base-info">
            <router-link :to="{ name: 'article', params: { articleId: post.id } }">
                {{ post.articleName }}
            </router-link>
            <a href="see-desc" @click.stop="seeDesc" :class="{show: showDesc}">
                查看简介&nbsp;&nbsp;<Icon type="android-arrow-dropup-circle"></Icon></a>
            <p class="desc" :class="{ show: showDesc }">{{ post.articleRemark }}</p>
        </div>
    </div>
  </div>
</template>

<script>
export default {
   name:"archive-list-cell",
   props: {
    post: {
        Type: Object,
    }
   },
   mixins: [minxin]
}
</script>

<style>

</style>