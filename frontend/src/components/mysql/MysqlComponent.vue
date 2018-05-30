<template>
  <el-row class="component-wrapper">
    <el-row>
      <el-col>
        <div class="service-item">
          <h2>Service Database</h2>
          <el-checkbox checked disabled>Required</el-checkbox>
        </div>
        <hr/>
      </el-col>
    </el-row>
    <el-row>
      <el-collapse v-model="activeNames">
        <mysql-item
          v-for="(service, index) in services"
          :service="service"
          :mysqlInfo="service.mysqlInfo"
          :key="index"
        >
        </mysql-item>
      </el-collapse>
    </el-row>
  </el-row>
</template>

<script>

  import {mapState, mapMutations} from 'vuex'

  import MysqlItem from './MysqlItem'

  export default {
    name: "MysqlComponent",
    components: {
      "mysql-item": MysqlItem
    },
    data() {
      return {
        activeNames: ['1']
      }
    },
    computed: {
      ...mapState([
        "services"
      ])
    }
  }
</script>

<style scoped>
  .service-item {
    display: -webkit-flex; /* Safari */
    display: flex;
    /*主轴对齐方式*/
    justify-content: space-between;
    /*纵轴对齐方式*/
    align-items: center
  }

  .input-item {
    margin-top: 4%;

    display: -webkit-flex; /* Safari */
    display: flex;
    /*主轴对齐方式*/
    justify-content: flex-start;
    /*纵轴对齐方式*/
    align-items: center
  }
</style>
