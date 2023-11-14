<template>
  <div class="home-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
      <div class="layout-left" style="background-color: #ffffff; padding: 30px 20px 10px 20px">
        <article-page-header :article="article"></article-page-header>
        <article-page-content>
          <article id="article-main-page" class="typo container" slot="content" ref="article" v-html="article.content"
            v-viewer></article>
        </article-page-content>
        <article-page-footer
            :tags="article.tagList"
            :postId="article.id"
          ></article-page-footer>

      </div>
      </Col>
      <Col :xs="0" :sm="0" :md="0" :lg="7">
      <div class="layout-right">
        <recommend></recommend>
      </div>
      </Col>
    </Row>
  </div>
</template>

<script>
import { Col, Row } from 'view-design';
import Recommend from '../Recommend.vue';
import ArticlePageHeader from "@/components/Article/ArticlePageHeader";
import ArticlePageContent from "@/components/Article/ArticlePageContent";
import ArticlePageFooter from "@/components/Article/ArticlePageFooter.vue";

export default {
  data() {
    return {
      article: {
        title: "夕颜源码 - 专注于技术|源码分享的IT技术平台",
        remark:
          "夕颜博客,夕颜源码,夕颜社区,夕颜技术社区,,夕颜IT社区,IT社区,技术社区,Java技术分享,Spring教程,开发者社区,Java毕设,Java博客,Java项目,Java源码,Vue博客,代码,教程,web编程,前端开发,后端开发",
      },
    }
  },
  components: {
    "article-page-header": ArticlePageHeader,
    "article-page-content": ArticlePageContent,
    "article-page-footer": ArticlePageFooter,
    Row,
    Col,
    recommend: Recommend,
  }
}
</script>

<style></style>