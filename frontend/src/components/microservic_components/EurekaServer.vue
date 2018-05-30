<template>
  <div>
    <el-row>
      <el-col>
        <div class="service-item">
          <h2>Eureka Server</h2>
          <el-checkbox checked disabled>Required</el-checkbox>
        </div>
        <hr/>
      </el-col>
      <el-row>
        <el-col>
          <div class="input-item">
            <el-col :span="4">GroupId: </el-col>
            <el-col :span="6">
              <el-input
                placeholder="groupId"
                v-model="eurekaServerInfo.groupId"
              >
              </el-input>
            </el-col>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <div class="input-item">
            <el-col :span="4">ArtifactId: </el-col>
            <el-col :span="6">
              <el-input
                placeholder="artifactId"
                v-model="eurekaServerInfo.artifactId"
              >
              </el-input>
            </el-col>
          </div>
        </el-col>
      </el-row>
    </el-row>
  </div>
</template>

<script>

  import {mapState} from 'vuex'

  export default {
    name: "EurekaServer",
    computed: {
      ...mapState([
        'eurekaServerInfo',
        'checkedEurekaServer'
      ])
    }
  }
</script>

<style>

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
