<template>
    <div>
        <div class="navbarBox" style="min-height: 48px;">
            <div class="navbar">
                <!-- 导航栏容器 -->
                <div class="navbar-container">
                    <!-- 左侧 -->
                    <div class="navbar-container-left">

                        <!-- 网页logo -->
                        <div class="logo">
                            <img src="../../assets/logo.png"
                                alt="">
                        </div>

                        <!-- 左侧导航栏 -->
                        <ul class="left-ul">
                            <li title=""><router-link active-class="active" to="/">首页</router-link></li>
                            <!-- <li title=""><router-link active-class="active" to="/">资源</router-link></li> -->
                            <li title=""><router-link active-class="active" to="/news">新闻</router-link></li>
                            <li title=""><router-link active-class="active" to="/aimodel">AI</router-link></li>
                        </ul>
                    </div>

                    <!-- 中间搜索框 -->
                    <div class="navbar-container-middle">

                        <el-autocomplete v-model="queryString" :fetch-suggestions="debounceQuery" placeholder="Search..."
                            class="right item m-search" 
                            :class="{ 'm-mobile-hide': mobileHide }"
                            popper-class="m-search-item"
                             @select="handleSelect">
                            <i class="search icon el-input__icon" slot="suffix"></i>
                            <template slot-scope="{ item }">
                                <div class="title">{{ item.title }}</div>
                            </template>
                            <el-button slot="append" icon="el-icon-search"></el-button>
                        </el-autocomplete>
                        
                    </div>

                    <!-- 右侧导航栏 -->
                    <div class="navbar-container-right">
                        <div class="navbar-btns-User">
                            <!--用户登录: 展示用户的头像以及其他信息-->
                            <div class="userPhoto" @mouseover="mouseOver" @mouseleave="mouseLeave"
                                v-show="userInfo.userName != undefined">
                                <!-- 用户头像 -->
                                <a class="hasAvatar" :style="{ opacity }" href="/center">
                                    <img :src="userInfo.photo">
                                </a>
                                <!-- 用信息弹出框 初始display:none-->
                                <div class="navbar-profile" :style="{ display }">
                                    <div class="profile-user">
                                        <!-- 用户简介的头像 -->
                                        <a class="profile-avatar" href="/">
                                            <img :src="userInfo.photo">
                                        </a>
                                        <p class="profile-nickName">{{ userInfo.userName }}</p>
                                    </div>
                                    <div class="center">
                                        <ul class="profile">
                                            <li title=""><router-link active-class="active" :to="{name:'user-center',params: { id: userInfo.id } }">个人中心</router-link></li>
                                            <li title=""><span @click="logout">退出登录</span></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <!--用户未登录: 提示提示信息-->
                            <div class="userPhoto" v-show="userInfo.userName == undefined" >
                                <a href="/login">登录</a><!--替换成圆形登录 => 类似CSDN的样式-->
                                <span>|</span>
                                <a href="/regist">注册</a><!--替换成圆形登录 => 类似CSDN的样式-->
                            </div>
                            <!-- 消息 -->
                            <div class="navbar-btn navbar-btn-news navbar-fl">
                                <a href="/notification">消息</a>
                            </div>
                            <!-- 发布 -->
                            <div class="navbar-btn navbar-btn-write navbar-fl">
                                <a class="write" href="/edit" target="_blank">
                                    <i></i>发布
                                    <i></i></a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div>
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import { getToken, getUserInfo } from '@/utils/auth'
import Cookie from "js-cookie";
import { queryArticleSearchKey } from '@/api/blog'
export default {
    name: "CSDNHeader",
    data() {
        return {
            mobileHide: true,
            queryResult: [],
            opacity: 1,
            display: 'none',
            isLogin: false,
            keys: 'java',
            timer: null,
            queryString: '',
            userInfo: {
                //     // id:0,
                //     // userName:'aaa',
                //     // email:'',
                //     // phone:'',
                //     // status:false,
                //     // photo:'',
                //     // userRole:0
            },
        }
    },
    methods: {
        logout() {
            this.$store.dispatch("LogOut")
            location.reload();
            //退出登录
        },
        toggle() {
            this.mobileHide = !this.mobileHide
        },
        mouseOver() {
            this.opacity = 0
            this.display = 'block'
        },
        mouseLeave() {
            this.opacity = 1
            this.display = 'none'
        },
        transIndex() {
            this.$router.push('/index')
        },
       
        // searchBykey() {
        //     var key = document.getElementById("search").value
        //     if (key == '') {
        //         key = this.keys
        //     }
        //     this.$router.push({
        //         path: '/search',
        //         query: {
        //             key: key
        //         }
        //     });

        // },
        getUserInfobyCreated() {

            if (Cookie.get("userInfo") != undefined) {
                const userinfo = getUserInfo()
                const userInfoJson = JSON.parse(userinfo);
                const userName = userInfoJson.userName;
                // console.log("用户名:" + userName)
            }
            this.userInfo = JSON.parse(Cookie.get("userInfo"))
        },
        debounceQuery(queryString, callback) {
            this.timer && clearTimeout(this.timer)
            this.timer = setTimeout(() => this.querySearchAsync(queryString, callback), 1000)
        },
        querySearchAsync(queryString, callback) {
            if (queryString == null
                || queryString.trim() === ''
                || queryString.indexOf('%') !== -1
                || queryString.indexOf('_') !== -1
                || queryString.indexOf('[') !== -1
                || queryString.indexOf('#') !== -1
                || queryString.indexOf('*') !== -1
                || queryString.trim().length > 20) {
                return
            }
            queryArticleSearchKey(queryString).then(res => {
                // console.log(res.data.code)
                if (res.data.code === 200) {

                    this.queryResult = res.data.data.article
                    if (this.queryResult.length === 0) {
                        this.queryResult.push({ title: '无相关搜索结果' })
                    }
                    callback(this.queryResult)
                }
            }).catch(() => {
                console.log("请求失败")
            })
        },
        handleSelect(item) {
            if (item.id) {
                this.$router.push(`/article/${item.id}`)
            }
        }
    },
    created() {
        this.getUserInfobyCreated()
    },
    
watch:{
  $route(to,from){
    location.reload()
  }
},
    mounted() {
         
        this.$bus.$on('isLogin', (data) => {
            this.isLogin = data
        })
        //此处要判断token是否存在，存在则不展示 登录/注册 选项
        if (getToken() != null || getToken != undefined) {

            this.isLogin = true
            // console.log(isLogin)
        }
    }
}
</script>

<style scoped>
/* 导航栏样式 */
.navbar {
    position: fixed;
    /* 固定定位，使导航栏在页面滚动时保持在顶部*/
    z-index: 2001;
    /*  设置层级，确保导航栏在其他元素之上显示 */
    top: 0px;
    /* 导航栏距离顶部的距离为0像素*/
    width: 100%;
    /* 导航栏宽度为100%，占据整个页面宽度*/
    left: 0px;
    /* 导航栏距离左侧的距离为0像素*/
    font-size: 14px;
    /*字体大小为14像素*/
    /* 字体粗细，400相当于normal */
    font-weight: 400;
    color: #222226;
    /*字体颜色为深灰色*/
    background-color: #fff;
    /* 背景颜色为白色*/
    /* 阴影  水平阴影距离，垂直阴影距离， 模糊尺寸， 阴影尺寸 颜色*/
    box-shadow: 0 2px 4px 0 rgb(0, 0, 0, 5%);
    /* 给导航栏添加阴影效果*/
}

.navbar-container {
    width: 80%;
    /* 导航栏容器宽度为页面宽度的80% */
    /* 最小宽度，窗口缩小之后到1280px不会再缩小 */
    min-width: 1280px;
    box-sizing: border-box;
    /* 使用边框盒模型，使元素的宽度和高度包括内边距和边框 */
    padding: 0 24px;
    /* 导航栏容器左右两侧的内边距为24像素 */
    margin: 0 auto;
    /* 导航栏容器水平居中 */
    height: 48px;
    /* 导航栏容器高度为48像素 */
    line-height: 48px;
    /* 导航栏容器内的文本行高为48像素 */
    display: flex;
    /* 使用弹性盒子布局，使子元素在容器内自动调整大小和位置 */
    justify-content: space-between;
    /* 子元素在水平方向上均匀分布，两端对齐 */
    -webkit-box-pack: justify;
    /* 兼容WebKit内核的浏览器使用justify属性进行布局 */
}

/* .toolbar-container-left{
        /* 元素会根据自身宽高来设置尺寸。它是完全非弹性的：既不会缩短，也不会伸长来适应 flex 容器 
        flex: none;
    } */
/* 定义logo样式 */
.logo {
    position: relative;
    /* 相对定位 */
    float: left;
    /* 浮动到左边 */
    margin-right: 8px;
    /* 右边距为8px */
    /* 当箭头鼠标移到会变成手式鼠标 */
    cursor: pointer;
}

/* 定义logo图片样式 */
.logo img {
    width: 80px;
    /* 宽度为80px */
    max-width: 80px;
    /* 最大宽度为80px */
    height: 44px;
    /* 高度为44px */
    display: block;
    /* 块级元素 */
    margin-top: calc((48px - 44px)/ 2);
    /* 上边距为(48px - 44px) / 2 */
}

/* 定义左侧导航栏样式 */
.left-ul {
    width: auto;
    /* 宽度自适应 */
    height: 100%;
    /* 高度为100% */
    /* 去除li中的间隙，在li中设置font-size,否则没有内容 */
    font-size: 0px;
    float: left;
    /* 浮动到左边 */
}

/* 定义左侧导航栏列表项样式 */
.left-ul li {
    position: relative;
    /* 相对定位 */
    display: inline-block;
    /* 内联块级元素 */
    font-size: 14px;
    /* 字体大小为14px */
    height: 100%;
    /* 高度为100% */
    line-height: 48px;
    /* 行高为48px */
}

/* 定义链接样式 */
a {
    color: #000;
    /* 颜色为黑色 */
    padding: 0 10px;
    /* 内边距为0 10px */
    /* 删除a标签的下划线 */
    text-decoration: none;
}

/* 定义鼠标停留时的样式 */
.left-ul li:hover {
    background-color: #eee;
    /* 背景色为灰色 */
}

/* 定义用户头像样式 */
.userPhoto {
    height: 100%;
    /* 高度为100% */
    float: right;
    /* 浮动到左边 */
    position: relative;
    /* 相对定位 */
    line-height: 48px;
    /* 行高为48px */
    text-align: center;
    /* 文本居中 */
    padding: 0 8px;
    /* 内边距为0 8px */
}

/* 定义用户头像图片样式 */
.hasAvatar {
    display: block;
    /* 块级元素 */
    line-height: 48px;
    /* 行高为48px */
    color: #222226;
    /* 颜色为深灰色 */
    margin-top: calc((48px - 32px)/ 2);
    /* 上边距为(48px - 32px) / 2 */
    margin-right: 16px;
    /* 右边距为16px */
    opacity: 1;
    /* 透明度为1 */
}

/* 定义用户头像图片样式 */
.hasAvatar img {
    width: 32px;
    /* 宽度为32px */
    height: 32px;
    /* 高度为32px */
    border-radius: 50%;
    /* 圆角为50% */
}

/* 定义个人资料样式 */
.navbar-profile {
    width: 248px;
    /* 宽度为248px */
    height: auto;
    color: #222226;
    /* 颜色为深灰色 */
    background: #fff;
    /* 背景色为白色 */
    position: absolute;
    /* 绝对定位 */
    min-height: 200px;
    /* 最小高度为200px */
    top: 48px;
    /* 距离顶部48px */
    left: 50%;
    /* 距离左边50% */
    margin-left: -132px;
    /* 左边距为-132px */
    z-index: 9999999;
    /* z-index为9999999 */
    border-radius: 4px;
    /* 圆角为4px */
    box-shadow: 0 0 10px 2px rgb(0 0 0 / 6%);
    /* 阴影效果 */
}

/* 定义个人资料中的用户信息样式 */
.navbar-profile .profile-user {
    text-align: center;
    /* 文本居中 */
    padding: 20px 0 12px 0;
    /* 内边距为20px 0 12px 0 */
    border-bottom: 1px solid #e8e8ed;
    /* 底部边框为1px实线，颜色为#e8e8ed */
}

/* 定义个人资料中的用户头像样式 */
.navbar-profile .profile-user .profile-avatar {
    position: absolute;
    /* 绝对定位 */
    width: 48px;
    /* 宽度为48px */
    height: 48px;
    /* 高度为48px */
    padding: 0px;
    /* 内边距为0px */
    top: -32px;
    /* 距离顶部-32px */
    left: 40%;
    /* 距离左边50% */
    -webkit-transform: translateX(-50%, 0);
    /* Chrome和Safari浏览器的转换效果 */
    transform: translateX(-50%, 0);
    /* IE浏览器的转换效果 */
    border-radius: 50%;
    /* 圆角为50% */
    cursor: pointer;
    /* 鼠标样式为手形 */
    z-index: 9999;
    /* z-index为9999 */
    border: 1px solid #e8e8ed;
    /* 边框为1px实线，颜色为#e8e8ed */
}

/* 定义个人资料中的用户昵称样式 */
.profile-user .profile-avatar img {
    width: 100%;
    /* 宽度为100% */
    height: 100%;
    /* 高度为100% */
    border-radius: 50%;
    /* 圆角为50% */
}

/* 定义个人资料中的用户昵称样式 */
.navbar-profile .profile-user .profile-nickName {
    width: 100%;
    /* 宽度为100% */
    box-sizing: border-box;
    /* 内边距和边框不增加总宽度 */
    padding: 0 16px;
    /* 内边距为0 16px */
    font-size: 16px;
    /* 字体大小为16px */
    color: #222226;
    /* 颜色为深灰色 */
    font-weight: 500;
    /* 字体粗细为500 */
    height: 40px;
    /* 高度为40px */
    line-height: 40px;
    /* 行高为40px */
    text-overflow: ellipsis;
    /* 文本溢出隐藏 */
    white-space: nowrap;
    /* 文本不换行 */
    overflow: hidden;
    /* 超出部分隐藏 */
}

/* 定义导航按钮样式 */
.navbar-btn {
    position: relative;
    /* 相对定位 */
    height: 48px;
    /* 高度为48px */
    line-height: 48px;
    /* 行高为48px */
    color: inherit;
    /* 继承颜色 */
    text-align: center;
    /* 文本居中 */
    padding: 0 4px;
    /* 内边距为0 4px */
}

/* 定义浮动样式 */
.navbar-fl {
    float: right;
    /* 浮动到左边 */
}

/* 定义导航按钮写入样式 */
.navbar-btn-write>a i:first-child {
    display: inline-block;
    /* 显示块级元素 */
    width: 20px;
    /* 宽度为20px */
    height: 20px;
    /* 高度为20px */
    vertical-align: middle;
    /* 垂直对齐方式为居中 */
    /* background: url("../assets/write.png"); */
    /* 背景图片地址 */
    background-size: 100%;
    /* 背景图片大小为100% */
    margin-right: 2px;
    /* 右边距为2px */
}

/* 定义导航按钮写入样式 */
.navbar-btn-write>a i:last-child {
    display: inline-block;
    /* 显示块级元素 */
    background: 0 0;
    /* 背景图片地址为空 */
    width: 10px;
    /* 宽度为10px */
    height: 7px;
    /* 高度为7px */
    margin-left: 7px;
    /* 左边距为7px */
    vertical-align: middle;
    /* 垂直对齐方式为居中 */
    background: no-repeat center center;
    /* 背景图片地址 */
    background-size: 100%;
    /* 背景图片大小为100% */
}

/* 定义写入按钮样式 */
.write {
    display: block;
    /* 显示块级元素 */
    min-width: 88px;
    /* 最小宽度为88px */
    height: 32px;
    /* 高度为32px */
    line-height: 32px;
    /* 行高为32px */
    text-align: center;
    /* 文本居中 */
    color: #fff;
    /* 颜色为白色 */
    background: #4e6072;
    /* 背景色为#fc5531 */
    border-radius: 20px;
    /* 圆角为20px */
    margin-top: calc((48px - 32px)/ 2);
    /* 上边距为(48px - 32px) / 2 */
}

.profile {
    width: auto;
    /* 宽度自适应 */
    height: 48px;
    /* 高度为100% */
    /* 去除li中的间隙，在li中设置font-size,否则没有内容 */
    font-size: 0px;
}

.profile li {
    /* position: relative; 相对定位 */
    /* display: inline-block; 内联块级元素 */
    font-size: 14px;
    /* 字体大小为14px */
    height: 100%;
    /* 高度为100% */
    line-height: 48px;
    /* 行高为48px */
    list-style-type: none
}

/* 定义鼠标停留时的样式 */
.profile li:hover {
    background-color: #eee;
    /* 背景色为灰色 */
}

</style>

