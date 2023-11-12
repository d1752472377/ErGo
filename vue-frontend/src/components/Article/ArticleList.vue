<template>
  <section-title :mainTitle="'技术分享'" :btnFlag="true" :tipText="'文章'" :tipHref="'/postArticle'"></section-title>
</template>

<script>
import SectionTitle from '../SectionTitle.vue';
export default {
    components:{
        "section-title": SectionTitle,
    }
}
</script>

<style>

</style>