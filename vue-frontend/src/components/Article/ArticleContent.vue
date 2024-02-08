<template>
  <div class="blog-page">
    <div class="left-section">
     <Card>
      <div class="blog-detail">
        <h1>{{ blog[0] && blog[0].title ? blog[0].title : '博客标题不可用' }}</h1>
        <div class="author-info">
          <img :src="blog[0] && blog[0].photo ? blog[0].photo : '博客作者头像不可用'" alt="Author Avatar" />
          <span>{{ blog[0] && blog[0].userName ? blog[0].userName : '博客作者不可用' }}</span>
          <span>{{ blog[0] && blog[0].createTime ? blog[0].createTime : '博客创建时间不可用' }}</span>
          <span>阅读量: {{ blog[0] && blog[0].readCount ? blog[0].readCount : '阅读量不可用' }}</span>
        </div>
        <div class="content" v-html="blog[0] && blog[0].content ? blog[0].content : '博客内容不可用'"></div>
        
      </div>
     </Card>
      <div class="comment">
        <Card>
        <CommentList></CommentList>
      </Card>
      </div>
    </div>
    <div class="right-section">
        <Card>
          <Author
        :authorName="blog[0].userName"
        :avatar=" blog[0].photo"
        ></Author>
        </Card>
      <el-card>
        <recommend></recommend>
      </el-card>


    </div>
  </div>
</template>

<script>
import { getArticleInfo } from '@/api/blog';
import Recommend from "@/components/Recommend.vue";
import TagCloud from "@/components/TagCloud.vue";
import AuthorCard from '../AuthorCard.vue';
import CommentList from '../comment/CommentList.vue';
import { Card } from 'view-design';

export default {
  components: {
    recommend: Recommend,
    TagCloud: TagCloud,
    Author: AuthorCard,
    CommentList,
    Card
},
  data() {
    return {
      blog: {}
    };
  },
  created() {
    this.getList(this.$route.params.articleId);
  },
  watch: {
    $route(to, from) {
      this.getList(to.params.articleId);
    },
  },
  methods: {
    getList(id) {
      const params = {
        id: id
      };
      getArticleInfo(params).then(res => {
        this.blog = res.data.data.info;
      });
    }
  }
};
</script>

<style>
.blog-page {
  width: 1260px;
  display: flex;
  justify-content: space-between;
  margin: 15px auto; /* 设置上下边距为 15px，左右自动居中 */
}

.left-section {
  width: 75%; /* 调整左侧宽度 */
}

.right-section {
  width: 25%; /* 调整右侧宽度 */
  margin-left: 20px; /* 添加左右间距 */
}

.blog-detail {
  /* border: 1px solid #ccc; */
  padding: 15px;
}

.author-info {
  width: 800px;
  margin:0 auto;
  margin-top: 10px;
  display: flex;
  align-items: center;
}

.author-info img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.content {
  width: 840px;
  margin:0 auto;
  font-size: 16px;
  line-height: 1.6;
  max-width: 100%; /* 限制内容宽度不超过父容器 */
  text-align: left;
}

.content img {
  max-width: 100%; /* 限制图片宽度不超过父容器 */
  height: auto; /* 保持宽高比例 */
}
.content li {
  list-style-type: none;
  line-height: 1.5;
  margin-bottom: 10px;
}
/* 设置标题样式 */
.content h1,.content h2,.content h3,.content h4 {
  color: #333; /* 设置标题颜色，可以根据需要调整 */
  font-weight: bold; /* 设置标题粗细，可以根据需要调整 */
  margin-top: 20px; /* 设置上边距 */
  margin-bottom: 10px; /* 设置下边距 */
  
}

/* 添加虚线效果 */
.content h1:after,h2:after {
  content: '';
  display: block;
  border-bottom: 1px dashed #ccc; /* 设置虚线颜色，可以根据需要调整 */
  margin-top: 5px; /* 设置虚线与标题之间的距离，可以根据需要调整 */
  margin-bottom: 15px; /* 设置虚线底部间距，可以根据需要调整 */
}
.comment{
  margin-top: 10px;
}
</style>
