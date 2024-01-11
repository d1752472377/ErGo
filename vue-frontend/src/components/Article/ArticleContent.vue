<template>
   <div class="home-content">
    <Col :xs="24" :sm="24" :md="24" :lg="17">
      <div class="blog-detail">
      <h1>{{ blog[0] && blog[0].title ? blog[0].title : '博客标题不可用' }}</h1>
      <!-- <p>文章ID: {{ $route.params.articleId }}</p> -->
      <div class="author-info">
        <img :src="blog[0] && blog[0].photo ? blog[0].photo : '博客作者头像不可用'" alt="Author Avatar" />
        <span>{{ blog[0] && blog[0].userName ? blog[0].userName : '博客作者不可用' }}</span>
      </div>
      <div class="meta-info">
        <span>{{ blog[0] && blog[0].createTime ? blog[0].createTime : '博客创建时间不可用' }}</span>
        <span>阅读量: {{ blog[0] && blog[0].readCount ? blog[0].readCount : '阅读量不可用' }}</span>
      </div>
      <div class="content">
        <!-- 这里使用 v-html 来渲染富文本内容 -->
        <div v-html="blog[0] && blog[0].content ? blog[0].content : '博客内容不可用'"></div>
      </div>
    </div>
    </Col>
    <Col :xs="0" :sm="0" :md="0" :lg="7">
      <div class="layout-right">
          <el-card>
            <recommend></recommend>
          </el-card>
          <el-card>
            <TagCloud></TagCloud>
          </el-card>
        </div>
    </Col>

  </div>
</template>

<script>
import { getArticleInfo } from '@/api/blog';
import Recommend from "@/components/Recommend.vue";
import TagCloud from "@/components/TagCloud.vue";

export default {
  components: {
    recommend: Recommend,
    TagCloud: TagCloud,
  },
  data() {
    return {
      blog: {}
    }
  },
  created() {
    let id = this.$route.params.articleId;
    this.getList(id)
  },
  watch: {
    $route(to, from) {
      // 路由参数变化时重新获取数据
      this.getList(to.params.articleId);
    },
  },
  methods: {

    getList(id) {
      const params = {
        id: id
      }
      getArticleInfo(params).then(res => {
        this.blog = res.data.data.info
        console.log(this.blog)

      })
    }
  }
}
</script>

<style scoped>


.home-content {
  display: flex;
  justify-content: space-between;
  width: auto;
  min-height: calc(100vh - 108px);
  margin: 15px;

  @media only screen and (max-width: 768px) {
    flex-direction: column;
    margin: 5px;
  }

  @media screen and (min-width: 1200px) {
    width: 1200px;
    margin: 15px auto 0;
    margin-bottom: 200px;
  }
}

.blog-detail {
  width: 100%;
}

.blog-detail h1 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 15px;
}

.blog-detail .author-info {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.blog-detail .author-info img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.blog-detail .author-info span {
  font-size: 16px;
  font-weight: bold;
}

.blog-detail .meta-info {
  font-size: 14px;
  color: #777;
  margin-bottom: 15px;
}

.blog-detail .meta-info span {
  margin-right: 15px;
}

.blog-detail .content {
  font-size: 16px;
  line-height: 1.6;
}

.layout-right {
  width: 100%;

  @media only screen and (max-width: 768px) {
    margin-top: 20px;
  }


}
</style>