<template>
  <el-collapse-item
    :title="itemName"
    :name="service.name"
    :key="service.name"
  >
    <h4>Required Configs</h4>
    <div>
      spring.application.name
      <el-input
        v-model="service.config['spring.application.name']">
      </el-input>
    </div>
    <div>
      server.port
      <el-input
        v-model="service.config['server.port']">
      </el-input>
    </div>
    <div>
      eureka.client.serviceUrl.defaultZone
      <el-input
        v-model="service.config['eureka.client.serviceUrl.defaultZone']">
      </el-input>
    </div>
    <div>
      eureka.instance.prefer-ip-address
      <el-input
        v-model="service.config['eureka.instance.prefer-ip-address']">
      </el-input>
    </div>
    <h4>Other Configs</h4>
    <el-table
      :data="service.addedConfigs"
    >
      <el-table-column
        label="Config Key"
        width="300">
        <template slot-scope="scope">
          <span>{{ scope.row.key }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Config Value"
        width="300">
        <template slot-scope="scope">
          <span>{{ scope.row.value }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operation">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 2%">
      <el-row v-if="isAddConfig">
        <el-col :span="7">
          <el-input v-model="configKey" placeholder="Please input a config key" clearable></el-input>
        </el-col>
        <el-col :span="7" :offset="1">
          <el-input v-model="configValue" placeholder="Please input a config value" clearable></el-input>
        </el-col>
        <el-col :span="6" :offset="1">
          <el-button
            size="mini"
            @click="addConfig">Confirm
          </el-button>
          <el-button
            size="mini"
            @click="clearConfigInput">Cancel
          </el-button>
        </el-col>
      </el-row>
      <el-row v-if="!isAddConfig">
        <el-col :span="3" :offset="20">
          <el-button size="small" @click="isAddConfig = true">Add</el-button>
        </el-col>
      </el-row>
    </div>
  </el-collapse-item>
</template>

<script>
  export default {
    name: "ConfigItem",
    props: ["service"],
    data() {
      return {
        isAddConfig: false,
        configKey: "",
        configValue: ""
      }
    },
    methods: {
      handleDelete(index, row) {
        this.service.addedConfigs.splice(index, 1);
        console.log(index, row);
      },
      // 提交添加的配置
      addConfig() {
        this.service.addedConfigs.push(
          {
            key: this.configKey,
            value: this.configValue
          }
        );
        this.clearConfigInput();
      },
      // 取消添加配置
      clearConfigInput() {
        this.configKey = "";
        this.configValue = "";
        this.isAddConfig = false;
      }
    },
    computed: {
      itemName() {
        return "Service Name: " + this.service.serviceName
      }
    }
  }
</script>

<style scoped>

</style>
