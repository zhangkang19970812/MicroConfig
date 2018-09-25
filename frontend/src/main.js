import Vue from 'vue'
// 完整引入Element
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App'
import 'es6-promise/auto'
import locale from 'element-ui/lib/locale/lang/en'

import 'normalize.css'

// 路由
import router from './router'

// qs
import qs from 'qs'

// axios
import axios from 'axios'

// store
import store from './store'

Vue.prototype.$qs = qs

axios.defaults.baseURL = 'http://localhost:8000/'
Vue.prototype.$axios = axios

Vue.prototype.$store = store

Vue.config.productionTip = false

// 配置组件默认尺寸均为small，语言为英文
Vue.use(Element, {size: 'small', locale})

/* eslint-disable no-new */
// 创建根实例
new Vue({
  // 根实例特有的el
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App},
  render: h => h(App)
}).$mount('#app')
