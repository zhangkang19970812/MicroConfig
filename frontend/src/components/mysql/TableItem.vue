<template>
  <el-collapse-item
    :title="itemName"
    :name="table.tableName"
    :key="table.tableName"
  >
    <h4>Column</h4>
    <el-table
      :data="table.columnList"
    >
      <el-table-column
        label="Column"
        width="300">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Type"
        width="300">
        <template slot-scope="scope">
          <span>{{ scope.row.type }}</span>
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
      <el-row v-if="isAddColumn">
        <el-col :span="8">
          <el-input v-model="columnName" placeholder="Please input the column name" clearable></el-input>
        </el-col>
        <el-col :span="8" :offset="1">
          <el-input v-model="columnType" placeholder="Please input the column type" clearable></el-input>
        </el-col>
        <el-col :span="6" :offset="1">
          <el-button
            size="mini"
            @click="addColumn">Confirm
          </el-button>
          <el-button
            size="mini"
            @click="clearColumnInput">Cancel
          </el-button>
        </el-col>
      </el-row>
      <el-row v-if="!isAddColumn">
        <el-col :span="12" :offset="4" v-if="table.columnList.length > 0">
          Primary Key：
          <el-select
            v-model="table.primary_key"
            placeholder="Please select a primary key">
            <el-option
              v-for="(column, index) in table.columnList"
              :key="index"
              :label="column.name"
              :value="column.name"
            >
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="8" :offset="16" v-if="table.columnList.length === 0">
          <el-button @click="isAddColumn = true">Add Column</el-button>
          <el-button @click="deleteTable">Delete Table</el-button>
        </el-col>
        <el-col :span="8" v-if="table.columnList.length > 0">
          <el-button @click="isAddColumn = true">Add Column</el-button>
          <el-button @click="deleteTable">Delete Table</el-button>
        </el-col>
      </el-row>
    </div>
  </el-collapse-item>
</template>

<script>

  export default {
    name: "TableItem",
    props: ["table", "mysqlInfo"],
    data() {
      return {
        isAddColumn: false,
        columnName: "",
        columnType: ""
      }
    },
    methods: {
      handleDelete(index, row) {
        this.table.columnList.splice(index, 1);
        console.log(index, row);
      },
      deleteTable() {
        let deleteIndex = 0;
        let deleteTableName = this.table.tableName;
        console.log(this.mysqlInfo.tables);
        this.mysqlInfo.tables.forEach(function (t, index) {
          if (deleteTableName === t.tableName) {
            deleteIndex = index;
            return false;
          }
        });
        console.log(deleteIndex);
        this.mysqlInfo.tables.splice(deleteIndex, 1);
      },
      // 提交添加的列
      addColumn() {
        this.table.columnList.push(
          {
            name: this.columnName,
            type: this.columnType
          }
        );
        this.clearColumnInput();
      },
      // 取消添加配置
      clearColumnInput() {
        this.columnName = "";
        this.columnType = "";
        this.isAddColumn = false;
      }
    },
    computed: {
      itemName() {
        return "Table: " + this.table.tableName
      }
    }
  }
</script>

<style scoped>
  .input-item {
    display: -webkit-flex; /* Safari */
    display: flex;
    /*主轴对齐方式*/
    justify-content: flex-start;
    /*纵轴对齐方式*/
    align-items: center
  }
</style>
