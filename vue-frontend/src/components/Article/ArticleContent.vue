<template>
  <div class="blog-page">
    <div class="left-section">
      <Card>
        <div class="blog-detail" v-for="article in blog " :key="article.id">
          <h1>{{ article.title }}</h1>
          <div class="author-info">
            <img :src="article.photo" alt="Author Avatar" />
            <span style="margin-left: 5px;">{{ article.userName }}</span>
            <span class="publish-time" style="margin-left:20px;">
              <Icon type="ios-timer-outline" class="icon" />于 {{
                article.createTime | formatDate
              }} 发布
            </span>
            <span style="margin-left: 15px;">阅读量: {{ readCount }}</span>
          </div>
          <div class="content" v-html="article.content"></div>

        </div>
      </Card>
      <div class="comment">
        <Card>
          <CommentList></CommentList>
        </Card>
      </div>
    </div>
    <div class="right-section">
      <div v-for="article in blog" :key="article.id">
        <Author :userId="article.userId" :userName="article.userName" :avatar="article.photo"></Author>
      </div>
      <el-card>
        <recommend></recommend>
      </el-card>


    </div>
  </div>
</template>

<script>
import { getArticleInfo ,getNumberOfReadForArticle} from '@/api/blog';
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
      blog: {},
      readCount:'',
    };
  },
  created() {
    this.getList(this.$route.params.articleId);
    this.getReadNum()
  },
  watch: {
    $route(to, from) {
      this.getList(to.params.articleId);
    },
  },
  methods: {
    getReadNum(){
      const params = {
        documentId:this.$route.params.articleId
      }
      getNumberOfReadForArticle(params).then(res=>{
        this.readCount = res.data.data
      })
    },
    getList(id) {
      const params = {
        id: id
      };
      getArticleInfo(params).then(res => {
        this.blog = res.data.data.info;
      }).catch(error => {
        if (error.response) {
          const status = error.response.status;
          if (status === 404) {
            // 处理404错误
            this.$router.push({ path: '/404' });
          } else if (status === 400) {
            // 处理400错误
            // 例如，显示一个错误消息
            this.$router.push({ path: '/404' });
          } else if (status === 403) {
            // 处理403错误
            // 例如，显示一个权限不足的错误消息
            console.error('权限不足');
          } else {
            // 处理其他错误
            this.$router.push({ path: '/404' });
          }
        } else {
          // 处理没有响应的情况
          console.error('请求出错:', error);
        }
      })
    }
  },
  filters: {
    formatDate: function (value) {
      if (value != null) {
        let date = new Date(value);
        let y = date.getFullYear();
        let MM = date.getMonth() + 1;
        MM = MM < 10 ? "0" + MM : MM;
        let d = date.getDate();
        d = d < 10 ? "0" + d : d;
        let h = date.getHours();
        h = h < 10 ? "0" + h : h;
        let m = date.getMinutes();
        m = m < 10 ? "0" + m : m;
        let s = date.getSeconds();
        s = s < 10 ? "0" + s : s;
        return y + "-" + MM + "-" + d + " " + h + ":" + m + ":" + s;
      }
    },
  },
};
</script>

<style>
.blog-page {
  width: 1260px;
  display: flex;
  justify-content: space-between;
  margin: 15px auto;
  /* 设置上下边距为 15px，左右自动居中 */
}

.left-section {
  width: 75%;
  /* 调整左侧宽度 */
}

.right-section {
  width: 25%;
  /* 调整右侧宽度 */
  margin-left: 20px;
  /* 添加左右间距 */
}

.blog-detail {
  /* border: 1px solid #ccc; */
  padding: 15px;
}

.author-info {
  width: 800px;
  margin: 0 auto;
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
  margin: 0 auto;
  font-size: 16px;
  line-height: 1.6;
  max-width: 100%;
  /* 限制内容宽度不超过父容器 */
  text-align: left;
}

.content img {
  max-width: 100%;
  /* 限制图片宽度不超过父容器 */
  height: auto;
  /* 保持宽高比例 */
}

.content li {
  list-style-type: none;
  line-height: 1.5;
  margin-bottom: 10px;
}

/* 设置标题样式 */
.content h1,
.content h2,
.content h3,
.content h4 {
  color: #333;
  /* 设置标题颜色，可以根据需要调整 */
  font-weight: bold;
  /* 设置标题粗细，可以根据需要调整 */
  margin-top: 20px;
  /* 设置上边距 */
  margin-bottom: 10px;
  /* 设置下边距 */

}

/* 添加虚线效果 */
.content h1:after,
h2:after {
  content: '';
  display: block;
  border-bottom: 1px dashed #ccc;
  /* 设置虚线颜色，可以根据需要调整 */
  margin-top: 5px;
  /* 设置虚线与标题之间的距离，可以根据需要调整 */
  margin-bottom: 15px;
  /* 设置虚线底部间距，可以根据需要调整 */
}

.comment {
  margin-top: 10px;
}</style>
