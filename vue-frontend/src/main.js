import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import iView from 'view-design'
import 'view-design/dist/styles/iview.css';
import axios from 'axios'
import APlayer from '@moefe/vue-aplayer'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.devtools = true
Vue.config.productionTip = false
Vue.prototype.axios = axios;

Vue.use(APlayer, {
  defaultCover: 'https://github.com/u3u.png',
  productionTip: true,
});
Vue.use(ElementUI)
Vue.use(iView)
new Vue({
  router,
  store,
  render: h => h(App),
  beforeCreate(){                 //在初始化阶段前
		Vue.prototype.$bus = this   //配置全局总线，bus有总线的意思
	}
}).$mount('#app')
