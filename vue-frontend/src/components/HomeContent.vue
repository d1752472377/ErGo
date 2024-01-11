<template>
  <div class="home-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left">
          <article-list-cell
            v-for="article in articleList"
            :article="article"
            :key="article.id"
          ></article-list-cell>
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
    </Row>
    <div class="isEnd">
      <div class="loadContent" @click="loadContent" v-if="!isEnd && !loading">
        点击加载更多
      </div>
      <div class="lds-css ng-scope" v-if="!isEnd && loading">
        <div style="width:100%;height:100%" class="lds-facebook">
          <div></div>
          <div></div>
          <div></div>
        </div>
      </div>
      <span v-if="isEnd">我也是有底线的~</span>
    </div>
  </div>
</template>
<script>
import Recommend from "@/components/Recommend.vue";
import ArticleListCell from "./Article/ArticleListCell.vue";
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
import { list,getNewBlog } from "@/api/blog";
import TagCloud from "@/components/TagCloud.vue";

export default {
  components: {
    recommend: Recommend,
    TagCloud: TagCloud,
    "article-list-cell": ArticleListCell,
  },
  data(){
    return{
      isEnd: false,
      loading: false,
      articleList: [],
      currentPage: 1,
      pageSize: 5,
      total: 0,
    }
  },
  mounted() {
      // 注册scroll事件并监听
      this.loading = false;
    },
  created(){
    this.getList()
    this.getAesKey()
  },
  methods:{
    getList(){
      const params = new URLSearchParams();
      params.append("currentPage", this.currentPage);
      params.append("pageSize", this.pageSize);
      getNewBlog(params).then((res) => {
        console.log(res.data.code)
        this.articleList = res.data.data.article;
        this.total = res.data.data.total;
      })
    },
    getAesKey(){
      let aesKey = getAes();
    },
    loadContent() {
      this.loading = true;
      this.currentPage++;
      const params = new URLSearchParams();
      params.append("currentPage", this.currentPage);
      params.append("pageSize", this.pageSize);
      // 使用 getNewBlog 方法替代原有的逻辑
      getNewBlog(params).then((response) => {
        if ( response.data.code === 200 ) {
          this.isEnd = false;
          // 追加新加载的博客数据
          this.articleList = this.articleList.concat(response.data.data.article);
          this.total = response.data.data.total;
          // this.pageSize = response.data.size;
          // this.currentPage = response.data.current;

          // 判断是否加载完所有数据
          if (this.articleList.length >= this.total) {
            this.isEnd = true;
          }
        } else {
          this.isEnd = true;
        }
        this.loading = false;
      });
    },
  }
}
</script>


<style scoped>
.ivu-modal-footer {
  display: none !important;
}

.home-content {
  width: auto;
  min-height: calc(100vh - 108px);
}

@media only screen and (max-width: 768px) {
  .home-content {
    margin: 5px 5px 0 5px;
  }
}

@media screen and (min-width: 768px) {
  .home-content {
    margin: 10px 10px 0 10px;
  }
}

@media screen and (min-width: 992px) {
  .home-content {
    margin: 15px 35px 0 35px;
  }
}

@media screen and (min-width: 1200px) {
  .home-content {
    width: 1200px;
    margin: 15px auto 0;
    margin-bottom: 200px;
  }
}

.layout-left, .layout-right {
  padding: 0;
}

@media only screen and (max-width: 768px) {
  .layout-left, .layout-right {
    padding: 0;
  }
}

@media screen and (min-width: 768px) {
  .layout-left, .layout-right {
    padding: 0;
  }
}

@media screen and (min-width: 992px) {
  .layout-left, .layout-right {
    padding: 0 10px;
  }
}

@media screen and (min-width: 1200px) {
  .layout-left, .layout-right {
    padding: 0 10px;
  }
}

.el-card {
  padding-top: 10px;
}

.loadContent {
  width: 120px;
  height: 30px;
  line-height: 30px;
  font-size: 16px;
  margin: 0 auto;
  color: aliceblue;
  cursor: pointer;
  background: rgba(0, 0, 0, 0.8);
}
</style>