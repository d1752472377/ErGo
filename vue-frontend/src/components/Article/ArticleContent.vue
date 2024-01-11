<template>
  <div class="blog-detail">
    <h1>{{ blog[0] && blog[0].title ? blog[0].title : '博客标题不可用' }}</h1>
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
</template>

<script>
import {getArticleInfo } from '@/api/blog';

export default {
  data(){
    return{
      blog:{}
    }
  },
  created(){
    this.getList()
  },
  methods:{
    getList(){
      const params ={
        id:1
      }
      getArticleInfo(params).then(res=>{
        this.blog = res.data.data.info
        console.log(this.blog)
        
      })
    }
  }
}
</script>

<style scoped>
/* 样式可以根据你的设计进行修改 */
.blog-detail {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.author-info img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.meta-info {
  margin-top: 10px;
}

.categories,
.tags {
  margin-top: 10px;
}

.categories span,
.tags span {
  margin-right: 5px;
  padding: 5px;
  background-color: #eee;
  border-radius: 3px;
  cursor: pointer;
}

.related-blogs {
  margin-top: 20px;
}

.related-blogs div {
  margin-bottom: 10px;
}

.qr-code {
  margin-top: 20px;
}

.qr-code img {
  width: 150px;
  height: 150px;
  border: 1px solid #ccc;
}
</style>