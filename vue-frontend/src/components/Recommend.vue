<template>
    <div class="recommend">
        <panel :title="'推荐阅读'">
            <div slot="content" class="content">
                <div class="top" v-for="recommend in recommendList" :key="recommend.id">
                    <router-link :to="{ name: 'article', params: { articleId: recommend.id } }">
                        <p class="title">{{ recommend.title }}</p>
                        <div class="tags">
                            <Tag v-for="(item, index) in recommend.tagList" :key="index" :color="index | mapTagColor">{{
                                item }}</Tag>
                        </div>
                        <p class="info">
                            <span class="time">{{ recommend.createTime | socialDate }}</span>
                            <span class="iconfont icon-dianzan" style="margin-left: 5px; color: #d81e06"><b
                                    style="margin-left: 5px">{{ recommend.starCount }}</b></span>
                            <span style="margin-left: 5px; color: #3cc2a8" class="iconfont icon-pinglun_huabanfuben"><b
                                    style="margin-left: 5px">{{ recommend.conCount }}</b></span>
                            <span class="iconfont icon-liulan" style="color: #d4237a"><b style="margin-left: 5px">{{
                                recommend.readCount }}</b></span>
                        </p>
                    </router-link>
                </div>
            </div>
        </panel>
    </div>
</template>
  
<script>
import Panel from "@/components/utils/Panel";
import { list,getNewBlog } from "@/api/blog";
import { mixin } from "@/utils/index";
import { getAes } from "@/utils/auth";
import { AESEncrypt } from "@/api/aes";
export default {
    mixins: [mixin],
    data() {
        return {
            recommendList: [],
            currentPage: 1,
            pageSize: 5,
        };
    },
    filters: {
        socialDate(value) {
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
    mounted() {
        //开启粒子特效
        if (document.getElementById("particles-js") != null) {
            document.getElementById("particles-js").style.display = "block";
        }
    },
    created() {
        this.getList();
    },
    methods: {
        getList(){
          const params = new URLSearchParams();
          params.append("currentPage", this.currentPage);
          params.append("pageSize", this.pageSize);
          getNewBlog(params).then((res) => {
        console.log(res.data.code)
        this.recommendList = res.data.data.article;
        
      })
        }
    },
    components: {
        panel: Panel,
    },
};
</script>
<style scoped>
.recommend .content {
  padding: 5px 20px;
}

.recommend .top a,
.recommend .others a {
  display: block;
  overflow: hidden;
}

.recommend .tags {
  margin-bottom: 10px;
}

.recommend .title {
  text-align: justify;
  color: #888;
  font-size: 16px;
  line-height: 23px;
  margin-bottom: 5px;
}

.recommend .info {
  margin: 5px 0 0px;
}

.recommend .info span {
  font-size: 13px;
  line-height: 18px;
  font-weight: 100;
  color: #2885f7;
}

.recommend .info span + span {
  float: right;
  margin-left: 10px;
}

.recommend .info a {
  display: inline-block;
  color: #777;
  cursor: pointer;
}

.recommend .info a:hover {
  color: #888;
  text-decoration: underline;
}

.recommend .img {
  padding-bottom: 40%;
  width: 100%;
  height: 0;
  margin: 5px 0;
  overflow: hidden;
}

.recommend .img img {
  width: 100%;
  transition: All 0.4s ease-in-out;
  transform: scale(1);
  zoom: 1;
}

.recommend .desc {
  text-align: justify;
  color: #888;
  font-size: 13px;
  line-height: 20px;
  margin: 5px 0 0;
}

.recommend .top:hover .title,
.recommend .top:hover img {
  color: #888;
  transition: All 0.4s ease-in-out;
  transform: scale(1.05);
  zoom: 1.05;
}

.recommend .others li {
  list-style-type: none;
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px solid #777;
}

</style>
