<template>
  <div class="component-wrapper">
    <el-row>
      <el-col>
        <div class="service-item">
          <h2>Ribbon</h2>
          <!-- `checked` 为 true 或 false -->
          <el-checkbox v-model="componentCheck.checkedRibbon">Select</el-checkbox>
        </div>
        <hr/>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div style="margin-top: 2%">
          Consumer：
          <el-select
            :disabled=!componentCheck.checkedRibbon
            v-model="ribbon.consumer"
            placeholder="Consumer">
            <el-option
              v-for="(item, index) in services"
              :key="index"
              :label="item.serviceName"
              :value="item.serviceName"
            >
            </el-option>
          </el-select>
        </div>
      </el-col>
    </el-row>
    <h4>Ribbon Providers</h4>
    <!-- Ribbon Providers表格 -->
    <el-table
      :data="ribbon.providers"
    >
      <el-table-column
        label="Provider"
        width="350"
      >
        <template slot-scope="scope">
          <span>{{ scope.row }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Operation"
        width="400"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="deleteRibbonProvider(scope.$index)">Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 2%">
      <el-row v-if="isAddRibbonProvider">
        <el-col :span="12">
          <div>
            Provider：
            <el-select
              v-model="ribbonProviderName"
              placeholder="Provider">
              <el-option
                v-for="(item, index) in services"
                :key="index"
                :label="item.serviceName"
                :value="item.serviceName"
              >
              </el-option>
            </el-select>
          </div>
        </el-col>
        <el-col :span="6" :offset="1">
          <el-button
            size="mini"
            @click="submitAdd">Confirm
          </el-button>
          <el-button
            size="mini"
            @click="clearRibbonProviderInput">Cancel
          </el-button>
        </el-col>
      </el-row>
      <el-row v-if="!isAddRibbonProvider">
        <el-button size="small" @click="isAddRibbonProvider = true" :disabled="!componentCheck.checkedRibbon">Add</el-button>
      </el-row>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import {RIBBON_ADD, RIBBON_DELETE} from "@/store/mutations";

  export default {
    name: "RibbonComponent",
    data() {
      return {
        isAddRibbonProvider: false,
        ribbonProviderName: ""
      }
    },
    computed: {
      ...mapState([
        'componentCheck',
        'services',
        'ribbon'
      ])
    },
    methods: {
      // 确认提交
      submitAdd() {
        this.$store.commit(RIBBON_ADD, this.ribbonProviderName);
        this.clearRibbonProviderInput();
      },
      deleteRibbonProvider(index) {
        this.$store.commit(RIBBON_DELETE, index);
      },
      // 取消提交
      clearRibbonProviderInput() {
        this.ribbonProviderName = "";
        this.isAddRibbonProvider = false;
      }
    }
  }
</script>

<style scoped>

</style>
