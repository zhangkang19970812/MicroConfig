<template>
  <div>
    <el-col>
      <div>
        <h2>Add Service</h2>
      </div>
      <hr/>
    </el-col>
    <el-table
      :data="services"
    >
      <el-table-column
        label="Service Name"
        width="300">
        <template slot-scope="scope">
          <span>{{ scope.row.serviceName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Folder Name"
        width="300">
        <template slot-scope="scope">
          <span>{{ scope.row.folderName }}</span>
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
        <el-col :span="8">
          <el-input v-model="serviceName" placeholder="Please input the service name" clearable></el-input>
        </el-col>
        <el-col :span="8" :offset="1">
          <form>
            <!-- html5支持选择文件夹上传 -->
            <input id="folderSelect" type="file" @change="handleFilesChange" multiple webkitdirectory>
          </form>
        </el-col>
        <el-col :span="6" :offset="1">
          <el-button
            size="mini"
            @click="addService">Submit
          </el-button>
          <el-button
            size="mini"
            @click="clearInput">Cancel
          </el-button>
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
  import {SERVICE_ADD, SERVICE_DELETE} from "../store/mutations";

  export default {
    data() {
      return {
        // 表示当前是否正在增加服务
        isAddService: false,
        serviceName: "",
        serviceAddress: "",

        // 保存每次选择的文件夹的内容
        folder: [],
        // 保存每次选择的文件夹的名称
        folderName: ''
      }
    },
    computed: {
      ...mapState({
        services: 'services'
      })
    },
    methods: {
      // 确认提交
      addService() {
        let newService =
          {
            serviceName: this.serviceName,

            // folder
            folderName: this.folderName,
            folder: this.folder,

            // config
            config: {
              "spring.application.name": "",
              "server.port": "",
              "eureka.client.serviceUrl.defaultZone": "",
              "eureka.instance.prefer-ip-address": "",
            },
            addedConfigs: [],

            // mysql
            mysqlInfo: {
              projectName: "",
              database: "",
              user: "",
              password: "",
              tables: [],
            }
          };
        this.$store.commit(SERVICE_ADD, newService);
        this.clearInput();
      },
      deleteService(row, index) {
        this.$store.commit(SERVICE_DELETE, index);
      },
      clearInput() {
        this.serviceName = "";
        this.serviceAddress = "";
        this.isAddService = false;
      },

      // 文件夹选择框值改变
      handleFilesChange(event) {
        this.folder = event.currentTarget.files;
        console.log(this.folder);
        let folderName = this.folder[0].webkitRelativePath;
        console.log(folderName.substring(0, folderName.indexOf('/')));
        this.folderName = folderName.substring(0, folderName.indexOf('/'));
      }
    }
  }
</script>
