import Vue from 'vue'

// 0. 使用模块化机制编程，导入Vue和Vuex，要调用 Vue.use(VueRouter)
import Vuex from 'vuex'

import {LOGIN, LOGOUT, STEPS_SET} from './mutations'
import {STEPS_DECRE, STEPS_INCRE} from './mutations'
import {SERVICE_ADD, SERVICE_DELETE} from "./mutations"
import {RIBBON_ADD, RIBBON_DELETE} from "./mutations";
import {COMPOSE_SERVICE_ADD, COMPOSE_SERVICE_DELETE} from "./mutations";

Vue.use(Vuex)

const store = new Vuex.Store({

  // 严格模式，对于v-model有限制，故不使用
  // strict: true,

  // 应用状态的数据，各组件的共享数据
  state: {

    // login
    isLogin: false,

    // 当前激活的steps
    stepsActive: 0,

    // 所有服务，服务名称及本机地址
    services: [],
    // 保存被选择的微服务组件
    componentCheck: {
      checkedEurekaServer: true,
      checkedEurekaClient: true,
      checkedRibbon: false,
      checkedHystrix: false,
      checkedZuul: false,
    },
    // eureka server的配置
    eurekaServerInfo: {
      groupId: "",
      artifactId: ""
    },
    // 网关
    ribbon: {
      consumer: "",
      providers: []
    },

    // zuul 的配置
    zuulInfo: {
      groupId: "",
      artifactId: ""
    },

    // compose
    composeInfo: []
  },

  // store的计算属性，state的一些派生状态
  getters: {},

  // 唯一允许更新应用状态的地方
  mutations: {

    [LOGIN](state) {
      state.isLogin = true;
    },
    [LOGOUT](state) {
      state.isLogin = false;
    },

    // 这里怎么使用解构，给payload一个默认值
    [STEPS_INCRE](state) {
      state.stepsActive++;
    },
    [STEPS_DECRE](state) {
      state.stepsActive--;
    },
    [STEPS_SET](state, num) {
      state.stepsActive = num;
    },

    [SERVICE_ADD](state, service) {
      state.services.push(service);
    },
    [SERVICE_DELETE](state, index) {
      state.services.splice(index, 1);
    },

    [RIBBON_ADD](state, providerName) {
      state.ribbon.providers.push(providerName);
    },
    [RIBBON_DELETE](state, index) {
      state.ribbon.providers.splice(index, 1);
    },


    // compose
    [COMPOSE_SERVICE_ADD](state, service) {
      state.composeInfo.push(service);
    },
    [COMPOSE_SERVICE_DELETE](state, index) {
      state.composeInfo.splice(index, 1);
    }
  },
  // 定义提交触发更改信息的描述，常见的例子是从服务端获取数据
  // Action 提交的是 mutation，而不是直接变更状态。
  // Action 可以包含任意异步操作。
  actions: {},
  // 允许将单一的 Store 拆分为多个 Store 的同时保存在单一的状态树中。随着应用复杂度的增加，这种拆分能够更好地组织代码
  modules: {}
})
export default store
