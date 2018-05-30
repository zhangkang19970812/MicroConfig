<template>
  <div>
    <el-col>
      <div class="service-item">
        <h2>Compose File</h2>
        <el-checkbox checked disabled>Required</el-checkbox>
      </div>
      <hr/>
    </el-col>
    <el-table
      :data="composeInfo"
    >
      <el-table-column
        label="Service Name"
        width="300">
        <template slot-scope="scope">
          <span>{{ scope.row.serviceName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="port"
        width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.port }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="mysql"
        width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.mysql }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operation">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="deleteService(scope.row, scope.$index)">Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 2%">
      <el-row v-if="isAddService">
        <el-col :span="4">
          <el-input v-model="serviceName" placeholder="service" clearable></el-input>
        </el-col>
        <el-col :span="3" :offset="1">
          <el-input v-model="port" placeholder="port" clearable></el-input>
        </el-col>
        <el-col :span="3" :offset="1">
          <el-checkbox v-model="mysql">isMysql</el-checkbox>
        </el-col>
        <el-col :span="5" :offset="1">
          <el-col :span="11">
            <el-button
              @click="addService">Submit
            </el-button>
          </el-col>
          <el-col :span="11" :offset="2">
            <el-button
              @click="clearInput">Cancel
            </el-button>
          </el-col>
        </el-col>
      </el-row>
      <el-row v-if="!isAddService">
        <el-col :span="4" :offset="20">
          <el-button size="small" @click="isAddService = true">Add</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>

  import {mapState, mapMutations} from 'vuex'
  import {COMPOSE_SERVICE_ADD, COMPOSE_SERVICE_DELETE} from "../store/mutations";

  export default {
    name: "ComposeComponent",
    data() {
      return {
        // 表示当前是否正在增加服务
        isAddService: false,
        serviceName: "",
        image: "",
        port: "",
        mysql: false
      }
    },
    computed: {
      ...mapState({
        composeInfo: 'composeInfo'
      })
    },
    methods: {
      // 确认提交
      addService() {
        let newService =
          {
            serviceName: this.serviceName,
            image: this.image,
            port: this.port,
            mysql: this.mysql
          };

        this.$store.commit(COMPOSE_SERVICE_ADD, newService);
        this.clearInput();
      },
      deleteService(row, index) {
        this.$store.commit(COMPOSE_SERVICE_DELETE, index);
      },
      clearInput() {
        this.serviceName = "";
        this.image = "";
        this.port = "";
        this.mysql = false;
        this.isAddService = false;
      }
    }
  }
</script>

<style scoped>

</style>
