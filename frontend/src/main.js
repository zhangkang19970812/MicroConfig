import Vue from 'vue'
// 完整引入Element
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import App from './App'
import NormailizeCss from 'normalize.css'
import 'es6-promise/auto'
import locale from 'element-ui/lib/locale/lang/en'

// 文件夹上传
import uploader from 'vue-simple-uploader'

// 路由
import router from './router'

// store
import store from './store'

Vue.prototype.$axios = axios
Vue.prototype.$store = store

Vue.config.productionTip = false;

// 配置组件默认尺寸均为small，语言为英文
Vue.use(Element, {size: 'small', locale});

Vue.use(uploader)

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
}).$mount('#app');
