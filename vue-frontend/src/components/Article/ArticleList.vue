<template>
    <div class="article-list-content">
        <Row>
            <Col :xs="24" :sm="24" :md="24" :lg="17">
            <div class="layout-left">
                <section-title :mainTitle="'文章分享'" :btnFlag="true" :tipText="'文章'" :tipHref="'/postArticle'">
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
import { getAes } from '@/utils/auth';
import { AESEncrypt } from '@/api/aes';
import { list } from '@/api/blog';
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
        //获取类型
        let type = this.$route.query.type;
        if (type != undefined) {
            this.param.type = type;
        }
        this.getList();
        if (document.body.offsetWidth <= 678) {
            this.isShow = false;
        }

    },
    methods: {
        getList(param) {
            if (param != undefined) {
                this.param.categoryld = param.categoryld;
            }
            //获取保存在cookie的AES密钥
            let aesKey = getAes();
            //为了处理aes还没有写入cookie就调接口了
            const timer = setInterval(() => {
                if (aesKey != undefined) {
                    //进行参数加密,必须把对象转换json字符串，不然加密不了
                    let dataJson = JSON.stringify(this.param);
                    //数据进行加密
                    this.res.requestData = AESEncrypt(dataJson, aesKey);
                    console.log(this.res);
                    list(this.res).then((res) => {
                        this.articleList = res.data.data.article;
                        this.total = res.data.data.total;
                    });
                    clearInterval(timer);
                    return;
                }
                aesKey = getAes();
            }, 50);
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

<style lang="stylus" scoped rel="stylesheet/stylus">
.ivu-modal-footer {
  display: none !important;
}

.article-list-content {
  width: auto;
  min-height: calc(100vh - 308px);

  @media only screen and (max-width: 768px) {
    margin: 5px 5px 0 5px;
  }

  @media screen and (min-width: 768px) {
    margin: 10px 10px 0 10px;
  }

  @media screen and (min-width: 992px) {
    margin: 15px 35px 0 35px;
  }

  @media screen and (min-width: 1200px) {
    width: 1200px;
    margin: 15px auto 0;
    margin-bottom: 200px;
  }

  .layout-left, .layout-right {
    padding: 0;

    @media only screen and (max-width: 768px) {
      padding: 0;
    }

    @media screen and (min-width: 768px) {
      padding: 0;
    }

    @media screen and (min-width: 992px) {
      padding: 0 10px;
    }

    @media screen and (min-width: 1200px) {
      padding: 0 10px;
    }
  }
}
</style>