<template>
  <div>
    <el-row>
      <el-col>
        <div class="service-item">
          <h2>Zuul</h2>
          <el-checkbox v-model="componentCheck.checkedZuul">Select</el-checkbox>
        </div>
        <hr/>
      </el-col>
      <el-row>
        <el-col>
          <div class="input-item">
            <el-col :span="4">GroupId:</el-col>
            <el-col :span="6">
              <el-input
                placeholder="groupId"
                v-model="zuulInfo.groupId"
                :disabled="!componentCheck.checkedZuul"
              >
              </el-input>
            </el-col>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <div class="input-item">
            <el-col :span="4">ArtifactId:</el-col>
            <el-col :span="6">
              <el-input
                placeholder="artifactId"
                v-model="zuulInfo.artifactId"
                :disabled="!componentCheck.checkedZuul"
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

  import {mapState, mapMutations} from 'vuex'

  export default {
    name: "ZuulComponent",
    computed: {
      ...mapState([
        'componentCheck',
        'zuulInfo'
      ])
    }
  }
</script>

<style scoped>

</style>
