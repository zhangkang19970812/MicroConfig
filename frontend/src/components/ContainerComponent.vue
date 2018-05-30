<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="17" :offset="4">
          <el-row>
            <el-col>
              <h2>Containers</h2>
              <hr/>
            </el-col>
          </el-row>
          <el-table
            :data="containers"
          >
            <el-table-column
              label="Name"
              width="200">
              <template slot-scope="scope">
                <span>{{ scope.row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="Image"
              width="200">
              <template slot-scope="scope">
                <span>{{ scope.row.image }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="Create Time"
              width="200">
              <template slot-scope="scope">
                <span>{{ scope.row.created }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="Status"
              width="200">
              <template slot-scope="scope">
                <span>{{ scope.row.status }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="Port"
              width="200">
              <template slot-scope="scope">
                <span>{{ scope.row.port }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>

  export default {
    name: "ContainerComponent",
    data() {
      return {
        containers: [
          {
            name: "container",
            image: "image",
            created: "createTime",
            status: "Running",
            port: 8888
          }
        ]
      }
    },
    mounted() {
      // this.$axios
      //   .get("http://localhost:8000/container/showAllContainerInfo")
      //   .then(response => (this.containers = JSON.parse(response)));
    }
  }
</script>

