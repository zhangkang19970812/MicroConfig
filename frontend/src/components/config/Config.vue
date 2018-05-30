<template>
  <div>
    <el-row>
      <el-col>
        <div>
          <h2>Service Config</h2>
        </div>
        <hr/>
      </el-col>
    </el-row>
    <el-row>
      <el-collapse v-model="activeNames">
        <config-item
          v-for="(service, index) in services"
          :service="service"
          :key="index"
        >
        </config-item>
      </el-collapse>
    </el-row>
  </div>
</template>

<script>

  import {mapState} from 'vuex'

  import ConfigItem from './ConfigItem'

  export default {
    name: "Config",
    components: {
      "config-item": ConfigItem
    },
    data() {
      return {
        activeNames: ['1']
      }
    },
    computed: {
      ...mapState([
        'services'
      ])
    }
  }
</script>

<style scoped>

</style>
