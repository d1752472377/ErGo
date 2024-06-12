import Vue from 'vue'
import Router from 'vue-router'

import Index from '@/components/index/Index.vue'
//头和尾
import SimpleHeader from '@/components/header/SimpleHeader.vue'
import CommonFooter from '@/components/CommonFooter.vue'
//首页
import HomeContent from '@/components/HomeContent.vue'
import LoginContentVue from '@/components/LoginContent.vue'
import RegistContent from '@/components/RegistContent.vue'
import RetrievePassword from '@/components/RetrievePassword.vue'
import ArticleList from '@/components/Article/ArticleList.vue'
import ArticleContent from '@/components/Article/ArticleContent.vue'
import searchContent from '@/components/searchContent.vue'
import UserCenter from '@/components/UserCenter/UserCenter.vue'
import EditArticle from '@/components/EditArticle.vue'
import FOUR from '../views/404.vue'
import News from '@/components/News.vue'
import notification from '@/components/Notification.vue'
import VueRouter from 'vue-router'
import { getToken } from '@/utils/auth'
import Aimodel from '@/components/AIModel.vue'
Vue.use(Router)
const originalPush = Router.prototype.push
      Router.prototype.push = function push(location) {
        return originalPush.call(this, location).catch(err => err)
      }

const router = new Router({
  mode: 'history',
  base: __dirname,
  scrollBehavior: () => ({ y: 0 }),
  routes: [{
    path: '/',
    name: 'Home',
    redirect: "home",
    component: Index,
    children: [{
      path: '/',
      name: 'home',
      components: {
        header: SimpleHeader,
        content: HomeContent,
        footer: CommonFooter
      }
    },
    //全部文章列表
    {
      path: '/articleList',
      name: 'articleList',
      components: {
        header: SimpleHeader,
        content: ArticleList,
        footer: CommonFooter
      }
    },
    {
      path: '/category/:name',
      name: 'category',
      components: {
        header: SimpleHeader,
        content: ArticleList,
        footer: CommonFooter
      },
      meta: {title: '分类'}
    },
    //文章详情页
    {
      path: '/article/:articleId',
      name: 'article',
      components: {
        header: SimpleHeader,
        content: ArticleContent,
        footer: CommonFooter,
      },
    },
    {
      path: '/search/',
      name: 'search',
      components: {
        header: SimpleHeader,
        content: searchContent,
        footer: CommonFooter
      }
    },
    {
      path:'/user-center/:id',
      name:'user-center',
      components:{
        header: SimpleHeader,
        content: UserCenter,
        footer: CommonFooter
      }
    },
    {
      path:'/news',
      name:'news',
      components:{
        header: SimpleHeader,
        content: News,
        footer: CommonFooter
      }
    },
    //消息通知
  {
    path: '/notification',
    name: 'notification',
    components:{
      header: SimpleHeader,
      content: notification,
      footer: CommonFooter
    }
  },
  {
    path: '/aimodel',
    name: 'aimodel',
    components:{
      header: SimpleHeader,
      content: Aimodel,
      footer: CommonFooter
    }
  },
    
    ],
  },{
    path: '*',
    name: '404',
    component: FOUR
  },
  {
    path:'/edit',
    name:'edit',
    component: EditArticle
  },
  //登录页
  {
    path: '/login',
    name: 'login',
    component: LoginContentVue,
    meta: {requireAuth: true}
  },
  //注册页
  {
    path: '/regist',
    name: 'regist',
    component: RegistContent,
  },
  //修改密码
  {
    path: '/recover',
    name: 'recover',
    component: RetrievePassword,
  },
  
]

})


router.beforeEach(function(to,from,next){
  if (to.matched.some((r) => r.meta.requireAuth)) {
    let token = getToken()
    if (token) {   //判断是否已经登录
      console.log('这是通过拦截后到处理',from);
      next();
    } else {
      if(to.path == '/login'){
        next()
      }else{
        next({
          path: '/login',
          query: {redirect: to.fullPath}   //登录成功后重定向到当前页面
        });
      }  
    }
  } else {
    console.log('这是拦截');
    next();
  }

  if(to.fullPath === "/login"){
    let token = getToken()
    // console.log("token"+token)
    if(token){
      next({
        path:from.fullPath
      })
    }else{
      next()
    }
  }
})

export default router