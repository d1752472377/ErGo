<template>
    <div class="articleSearch">
        <Row>
            <Col :xs="24" :sm="24" :md="24" :lg="17">
            <div class="layout-left">
                <article-list-cell v-for="article in articleList" :article="article" :key="article.id"></article-list-cell>
                <Page class="mt-10 text-right" :total="total" :current="param.pageNo" :page-size="param.pageSize"
                    @on-change="changePage" @on-page-size-change="changeSize" :show-elevator="isShow" :show-total="isShow"
                    :show-sizer="isShow" :page-size-opts="[5, 10, 15, 20]" />
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

import Recommend from "@/components/Recommend.vue";
import TitleMenuFilter from '@/components/TitleMenuFilter.vue'
import ArticleListCell from '@/components/Article/ArticleListCell.vue'
import { queryArticleSearchKey } from "@/api/blog";

export default {
    components: {
        recommend: Recommend,

        "title-menu-filter": TitleMenuFilter,
        "article-list-cell": ArticleListCell,
    },
    data() {
        return {
            //加密后请求服务器的参数
            res: {
                requestData: "",
            },
            isShow: true,
            total: 0,
            articleList: [],
            // defaultFilterList: DefaultFilterList,
            param: {
                key: "",
            },
        }
    },
    created(){
        this.getList();
    },
    methods:{
        getList(){
            this.param.key = this.$route.query.key
            console.log(this.$route.query.key)
            // const paramss = {
            //     key:'java'
            // }
            queryArticleSearchKey(this.param).then(responese=>{
                this.articleList = responese.data.data.article;
                        // this.total = responese.data.data;
            })
        },
        changePage(page) {
            this.param.pageNo = page;
            this.getList();
        },
        changeSize(size) {
            this.param.pageSize = size;
            this.param.pageNo = 1;
            this.getList();
        },
    }
}
</script>

<style></style>