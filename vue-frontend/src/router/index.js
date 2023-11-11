import Vue from 'vue'
import Router from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Index from '@/components/index/Index.vue'
//头和尾
import SimpleHeader from '@/components/SimpleHeader.vue'
import CommonFooter from '@/components/CommonFooter.vue'
import HomeContent from '@/components/HomeContent.vue'
import LoginContentVue from '@/components/LoginContent.vue'
import RegistContent from '@/components/RegistContent.vue'
Vue.use(Router)
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
      ]
    }]

})
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
]


