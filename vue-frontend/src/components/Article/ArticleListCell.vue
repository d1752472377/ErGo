<template>
    <div>
        <a @click="getContent(article)">
            <Card class="ivu">
                <div class="ivu-row">
                    <div class="ivu-col ivu-col-span-7">
                        <img class="live-cover" :src="article.picture" style="width: 100%; height: 100%" />
                    </div>
                    <div class="ivu-col ivu-col-span-17">
                        <div class="live-info">
                            <div class="live-title">
                                <!-- <div>{{ article.title }}</div> -->
                                <div  title="article.title" style="width:200px;white-space: nowrap;text-overflow:ellipsis; overflow:hidden;">{{article.title}}</div>
                                <div>
                                    <Tag v-if="article.officalStat == true" checkable color="success">官方</Tag>
                                    <Tag v-if="article.creamStat == true" checkable color="error">精品</Tag>
                                    <Tag v-if="article.good == '1'" checkable color="error">好文</Tag>
                                    <Tag v-if="article.recommend == '1'" checkable color="warning">推荐</Tag>
                                    <Tag v-if="article.toppingStat == true" checkable color="primary">置顶</Tag>
                                </div>
                            </div>
                            <div class="live-desc" style="width:400px;
                            display: block;
                            display: -webkit-box;
                            -webkit-line-clamp: 3;
                            -webkit-box-orient: vertical;
                            overflow: hidden;
                            text-overflow: ellipsis;">{{ article.summary }}</div>
                        </div>
                        <div class="live-down">
                            <div class="live-down-left">
                                <div class="live-for" v-for="(item, index) in article.TagList" :key="item" :name="item">
                                    <Icon type="ios-pricetag" :color="index | mapTagColor" />
                                    {{ item }}
                                </div>

                                <div class="live-for like">
                                    <span class="iconfont icon-liulan" style="color: #d4237a"><b style="margin-left: 5px">{{
                                        article.readCount
                                    }}</b></span>
                                    <span class="iconfont icon-dianzan" style="margin-left: 5px; color: #d81e06"><b
                                            style="margin-left: 5px">{{
                                                article.starCount
                                            }}</b></span>
                                    <span style="margin-left: 5px; color: #3cc2a8"
                                        class="iconfont icon-pinglun_huabanfuben"><b style="margin-left: 5px">{{
                                            article.conCount
                                        }}</b></span>
                                    <span v-if="article.downloadCount != null" style="margin-left: 5px; color: #1296db"
                                        class="iconfont icon-download"><b style="margin-left: 5px">{{
                                            article.downloadCount
                                        }}</b></span>
                                </div>
                            </div>
                            <div class="live-down-right">
                                <div class="live-name">
                                    <Icon type="ios-contact" class="icon" style="color: #1296db" />{{ article.userName }}
                                </div>
                                <div class="live-time">
                                    <Icon type="ios-timer-outline" style="color: #d81e06" class="icon" />{{
                                        article.createTime | formatDate
                                    }}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Card>
        </a>
    </div>

</template>
  
<script >
import { mixin } from "@/utils";

export default {
  
    props: {
        article: {
            Type: Object,
        },
        type: "",
    },
    filters: {
        formatDate(value) {
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
    mixins: [mixin],
    // computed: {
    //   themeClass: function () {
    //     if (this.article.coverType === 1) {
    //       return "big-image";
    //     } else {
    //       return "";
    //     }
    //   },
    // },
    methods: {
        getContent(article) {
            //判断是博客内容还是源码内容
             
                this.$router.push({
                    name: "article",
                    params: { articleId: article.id },
                });
            
        },
    },
};
</script>
<style scoped>
.ivu-tag:nth-child(1) {
  margin-left: 10px;
}

.like {
  position: absolute;
  bottom: 0px;
  left: 0px;
}

.iconfont {
  font-size: 14px !important;
}

.iconfont a {
  display: block;
  cursor: default;
  border: 1px solid  #222a32; /* 替换为实际的边框颜色值 border: 1px solid #your-color-border; */
}

.iconfont a:hover {
  border: 1px solid #2d8cf0; /* 替换为实际的 hover 边框颜色值 border: 1px solid #your-color-border; */
  box-shadow: 2px 2px 3px #10161c; /* 替换为实际的 box-shadow 颜色值 border: 1px solid #your-color-border; */
}


.text-wrapper {
  padding: 20px 20px 0 20px;
  text-align: left;
}

@media only screen and (max-width: 768px) {
  .text-wrapper {
    padding: 15px 15px 0 15px;
  }
}

.title {
  font-size: 23px;
  font-weight: 100;
  line-height: 27px;
}

.title span.special {
  border-radius: 5px; /* 替换为你期望的边框半径值 */
  font-size: 15px;
  font-weight: 100;
  padding: 3px 5px;
  margin-left: 1px;
  vertical-align: top;
  color: #ffffff; /* 替换为你期望的文本颜色值 */
  background: #ff0000; /* 替换为你期望的背景颜色值 */
  cursor: pointer;
}


.title a {
  color: #d9d9d9; /* 替换为你期望的颜色值 */
  cursor: pointer;
}

.title a:hover {
  color: #d9d9d9; /* 替换为你期望的颜色值 */
  text-decoration: underline;
}

.info {
  margin-top: 10px;
  font-size: 14px;
  line-height: 18px;
  font-weight: 200;
}

.info a {
  color: #777;
  cursor: pointer;
}

.info a:hover {
  color: #ff0000; /* 替换为你想要的颜色值 */
  text-decoration: underline;
}

.info .publish-time {
  margin-left: 20px;
}

.line {
  width: 50px;
  margin-top: 30px;
  border: 1px solid rgba(7, 17, 27, 0.4);
  background: rgba(7, 17, 27, 0.4);
}

.line::after {
  margin-bottom: 15px;
}

.tags {
  margin: 8px 0;
}

.desc {
  color: #666;
  font-size: 14px;
  line-height: 20px;
  font-weight: 200;
}

.desc a {
  color: #ff0000; /* 替换为你想要的颜色值 */
  font-weight: 500;
  cursor: pointer;
}

.desc a:hover {
  text-decoration: underline;
}

.operate_info {
  font-size: 14px;
  margin: 15px 0 20px;
}

.operate_info span {
  margin-right: 8px;
}

.operate_info span + span {
  margin-left: 8px;
}

.operate_info span a {
  cursor: default;
}

.operate_info span a:hover {
  color: #ff0000; /* 替换为你想要的颜色值 */
}

.operate_info span img {
  width: 100%;
}

.before {
  box-sizing: border-box;
}

.ivu {
  background-color: #fff;
  margin-bottom: 10px;
  cursor: pointer;
}

.live-cover {
  height: 150px !important;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 50%;
  border-radius: 4px;
}

.live-info {
  height: 110px;
  padding: 10px;
  overflow: hidden;
}

.live-title {
  font-size: 16px;
  font-weight: 700;
  display: inline-flex;
  justify-content: center;
  align-items: center;
}

.live-desc {
  color: #9ea7b4;
  margin-top: 18px;
  line-height: 18px;
}

.live-down-left {
  float: left;
  width: 340px;
}

.live-down {
  margin-top: -35px;
}

.live-for {
  float: left;
  margin-left: 10px;
}

.live-down-right {
  width: 50px;
  margin-left: 400px;
  margin-top: 20px;
}

.live-name {
  width: 200px;
  font-size: 15px;
}

.live-time {
  color: #9ea7b4;
  width: 200px;
  margin-top: 5px;
  font-size: 10px;
  line-height: 20px;
}

.ivu-col:hover {
  color: #2d8cf0;
}

.ivu-col {
  color: #798788;
}

.icon {
  margin-right: 10px;
}

@media (max-width: 768px) {
  .live-cover {
    height: 100px;
  }

  .live-info {
    height: 100px;
  }

  .live-down {
    display: none;
  }

  .live-title {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
  }

  .ivu-tag:nth-child(1) {
    margin-left: 0px;
  }

  .ivu-col-span-7 {
    width: 45%;
  }

  .ivu-col-span-17 {
    width: 55%;
  }

  .ivu-tag {
    margin: 0px;
  }

  .live-info {
    height: 100%;
    padding: 10px 0px 10px 10px;
  }
}

</style>