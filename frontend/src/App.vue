<template>
  <div id="app">
    <el-container>
      <el-header class="header">
        <my-header/>
      </el-header>
    </el-container>
    <router-view/>
  </div>
</template>

<script>

import MyHeader from './components/config/MyHeader'

export default {
  // 组件名称
  name: 'app',
  components: {
    'my-header': MyHeader
  }
}

</script>

<style scoped>
  .header {
    background-color: #000000;
    color: #fff;
    line-height: 60px;
  }
</style>
