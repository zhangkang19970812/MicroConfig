<template>
  <el-row class="component-wrapper">
    <el-col>
      <div class="service-item">
        <h2>Hystrix</h2>
        <el-checkbox v-model="componentCheck.checkedHystrix">Select</el-checkbox>
      </div>
      <hr/>
    </el-col>
  </el-row>
</template>

<script>
  import {mapState} from 'vuex'

  export default {
    name: "HystrixComponent",
    computed: {
      ...mapState({
        componentCheck() {
          return this.$store.state.componentCheck
        }
      })
    }
  }
</script>

<style scoped>

</style>
