<template>
    <div class="article-list-content">
        <Row>
            <Col :xs="24" :sm="24" :md="24" :lg="17">
            <div class="layout-left">
                <section-title :mainTitle="'技术分享'" :btnFlag="true" :tipText="'文章'" :tipHref="'/postArticle'">
                    <!-- <title-menu-filter @filterByMenu="getList" slot="menu" :downloadType="'download_count'" :title="'文章'"
            :menu-filter-list="defaultFilterList" :show="true"></title-menu-filter> -->
                </section-title>
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
import SectionTitle from '../SectionTitle.vue';
import Recommend from "@/components/Recommend.vue";
import TitleMenuFilter from '@/components/TitleMenuFilter.vue'
import ArticleListCell from '@/components/Article/ArticleListCell.vue'
export default {
    components: {
        recommend: Recommend,
        "section-title": SectionTitle,
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
                pageNo: 1,
                pageSize: 5,
                //默认是以创建时间倒叙排序
                sortField: "create_time",
                type: "",
            },
        }
    },
    created() {

    },
    methods: {
        getList() {

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