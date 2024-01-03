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
Vue.use(Router)
const originalPush = Router.prototype.push
      Router.prototype.push = function push(location) {
        return originalPush.call(this, location).catch(err => err)
      }
export default new Router({
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
    //登录页
    {
      path: '/login',
      name: 'login',
      components: {
        header: SimpleHeader,
        content: LoginContentVue,
        footer: CommonFooter
      }
    },
    //注册页
    {
      path: '/regist',
      name: 'regist',
      components: {
        header: SimpleHeader,
        content: RegistContent,
        footer: CommonFooter
      }
    },
    //修改密码
    {
      path: '/recover',
      name: 'recover',
      components: {
        header: SimpleHeader,
        content: RetrievePassword,
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
      path: '/article',
      name: 'article',
      components: {
        header: SimpleHeader,
        content: ArticleContent,
        footer: CommonFooter
      }
    },
    {
      path: '/search/',
      name: 'search',
      components: {
        header: SimpleHeader,
        content: searchContent,
        footer: CommonFooter
      }
    }
    ]
  }]

})


