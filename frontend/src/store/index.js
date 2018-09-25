import Vue from 'vue'

// 0. 使用模块化机制编程，导入Vue和Vuex，要调用 Vue.use(VueRouter)
import Vuex from 'vuex'

Vue.use(Vuex)
const store = new Vuex.Store({

  // 应用状态的数据，各组件的共享数据
  state: {

    // login
    isLogin: false,

    // 当前激活的steps
    stepsActive: 0,

    // 所有服务，服务名称及本机地址
    services: [],
    // 是否从Git上拉取服务
    fromGit: true,
    // 是否将生成的服务push到git
    toGit: true,

    // 保存被选择的微服务组件
    componentCheck: {
      checkedEurekaServer: true,
      checkedEurekaClient: true,
      checkedRibbon: false,
      checkedHystrix: false,
      checkedZuul: false
    },
    // eureka server的配置
    eurekaServerInfo: {
      groupId: '',
      artifactId: ''
    },

    // ribbon 的配置
    ribbonGroup: [],

    // 指定hystrix的服务、Controller及其方法
    hystrixMethods: [],

    // zuul 的配置
    zuulInfo: {
      groupId: '',
      artifactId: ''
    }
  },

  // store的计算属性，state的一些派生状态
  getters: {},

  // 唯一允许更新应用状态的地方
  mutations: {

    login (state) {
      state.isLogin = true
    },
    logout (state) {
      state.isLogin = false
    },

    // 这里怎么使用解构，给payload一个默认值
    increStep (state) {
      state.stepsActive++
    },
    decreStep (state) {
      state.stepsActive--
    },
    setStep (state, num) {
      state.stepsActive = num
    },

    addService (state, service) {
      state.services.push(service)
    },
    deleteService (state, index) {
      state.services.splice(index, 1)
    },

    switchFromGit (state, status) {
      state.fromGit = status
    },
    switchToGit (state, status) {
      state.toGit = status
    },

    addRibbonConsumer (state, consumerName) {
      state.ribbonGroup.push(
        {
          'consumer': consumerName,
          'providers': []
        }
      )
    },
    deleteRibbonConsumer (state, consumerName) {
      let deleteIndex = 0
      state.ribbonGroup.some(function (ribbonItem, index) {
        if (ribbonItem.consumer === consumerName) {
          deleteIndex = index
          return true
        }
      })

      state.ribbonGroup.splice(deleteIndex, 1)
    }
  },
  // 定义提交触发更改信息的描述，常见的例子是从服务端获取数据
  // Action 提交的是 mutation，而不是直接变更状态。
  // Action 可以包含任意异步操作。
  actions: {
    // 拉取服务
    pullFromGit ({state, commit}, gitPath) {

    }
  },
  // 允许将单一的 Store 拆分为多个 Store 的同时保存在单一的状态树中。随着应用复杂度的增加，这种拆分能够更好地组织代码
  modules: {}
})
export default store
