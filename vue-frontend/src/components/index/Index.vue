<template>
  <div class="main-wrapper">
    <router-view name="header"></router-view>
    <router-view name="content"></router-view>
    <router-view name="footer"></router-view>
    <div class="backTop" v-show="btnFlag" @click="backTop"></div>
    <!-- <div>
      <aplayer autoplay :audio="audio" :lrcType="1" fixed />
      <div id="weather-v2-plugin-simple"></div>
    </div> -->
  </div>
</template>

<script>
import Cookie from "js-cookie";

import { getKey } from "@/api/key";
import { setAesKey, getAes } from "@/utils/auth";
export default {
  data() {
    return {
      //加密后请求服务器的参数
      res: {
        requestData: "",
      },
      btnFlag: false,
      loading: false,
      audio: [],
    };
  },
  beforeCreate() {
    
    // const getkey = getAes()
    // console.log("cookie中的key值："+getkey)
    if (getAes() == undefined) {
      //获取aes密钥
      getKey().then((response) => {
        const key = response.data.key
        // console.log("返回中的key值："+key)
        //设置到cookie中
        setAesKey(key);
      });
    }
  },
  created() {
    this.$nextTick(function () {
      if (Cookie.get("loginToken") != undefined) {

        let data = JSON.parse(Cookie.get("userInfo"));
        if (data.username == undefined || data.username == "") {
          this.modal = true;
        }
      }
    });
    // vue的两个生命钩子，这里不多解释。
    // window对象，所有浏览器都支持window对象。它表示浏览器窗口，监听滚动事件
    window.addEventListener("scroll", this.scrollToTop);

  },

  destroyed() {
    window.removeEventListener("scroll", this.scrollToTop);
  },

  methods: {
    // 点击图片回到顶部方法，加计时器是为了过渡顺滑
    backTop() {
      const that = this;
      let timer = setInterval(() => {
        let ispeed = Math.floor(-that.scrollTop / 5);
        document.documentElement.scrollTop = document.body.scrollTop =
          that.scrollTop + ispeed;
        if (that.scrollTop === 0) {
          clearInterval(timer);
        }
      }, 16);
    },

    // 为了计算距离顶部的高度，当高度大于60显示回顶部图标，小于60则隐藏
    scrollToTop() {
      const that = this;
      let scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop;
      that.scrollTop = scrollTop;
      if (that.scrollTop > 0) {
        that.btnFlag = true;
      } else {
        that.btnFlag = false;
      }
    },


  },
};
</script>
<style scoped>
#snowbox {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  z-index: -1;
}
.backTop {
  position: fixed;
  bottom: 20px;
  right: 60px;
  width: 23px;
  height: 23px;
  background: url("../../assets/toTop.png");
  cursor: pointer;
}
</style>

