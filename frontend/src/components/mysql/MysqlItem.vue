<template>
  <el-collapse-item
    :title="itemName"
    :name="service.serviceName"
    :key="service.serviceName"
  >
    <el-row class="component-wrapper">
      <el-row>
        <el-col :span="12">
          <div class="input-item">
            <el-col :span="10">Project Name:</el-col>
            <el-col :span="12">
              <el-input
                placeholder="project name"
                v-model="mysqlInfo.projectName">
              </el-input>
            </el-col>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="input-item">
            <el-col :span="10">Database Name:</el-col>
            <el-col :span="12">
              <el-input
                placeholder="database name"
                v-model="mysqlInfo.database">
              </el-input>
            </el-col>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <div class="input-item">
            <el-col :span="10">Username:</el-col>
            <el-col :span="12">
              <el-input
                placeholder="username"
                v-model="mysqlInfo.user">
              </el-input>
            </el-col>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="input-item">
            <el-col :span="10">Password:</el-col>
            <el-col :span="12">
              <el-input
                type="password"
                placeholder="password"
                v-model="mysqlInfo.password">
              </el-input>
            </el-col>
          </div>
        </el-col>
      </el-row>
      <div style="margin-top: 2%">
        <el-row v-if="isAddTable">
          <el-col :span="7" :offset="10">
            <el-input v-model="tableName" placeholder="table name" clearable></el-input>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-button
              size="mini"
              @click="addTable">Confirm
            </el-button>
            <el-button
              size="mini"
              @click="clearTableInput">Cancel
            </el-button>
          </el-col>
        </el-row>
        <el-row v-if="!isAddTable">
          <el-col :span="4" :offset="20">
            <el-button size="small" @click="isAddTable = true">Add Table</el-button>
          </el-col>
        </el-row>
      </div>
      <el-collapse v-model="activeNames" v-if="mysqlInfo.tables.length !== 0" style="margin-top: 4%">
        <table-item
          v-for="(table, index) in mysqlInfo.tables"
          :table="table"
          :mysqlInfo="mysqlInfo"
          :key="index"
        >
        </table-item>
      </el-collapse>
    </el-row>
  </el-collapse-item>
</template>

<script>

  import {mapState, mapMutations} from 'vuex'

  import TableItem from "./TableItem"

  export default {
    name: "MysqlItem",
    components: {
      "table-item": TableItem
    },
    props: ['service', 'mysqlInfo'],
    data() {
      return {
        activeNames: [],
        isAddTable: false,
        tableName: ""
      }
    },
    computed: {
      ...mapState([
        "services"
      ]),
      itemName() {
        return "Service Name: " + this.service.serviceName
      }
    },
    methods: {
      // 提交添加的表
      addTable() {
        let tableInfo = {
          tableName: this.tableName,
          columnList: [],
          primary_key: ""
        };
        this.mysqlInfo.tables.push(tableInfo);
        this.clearTableInput();
      },
      clearTableInput() {
        this.tableName = "";
        this.isAddTable = false;
      }
    }
  }
</script>

<style scoped>

</style>
