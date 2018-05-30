<template>
  <el-row>
    <el-col :span="2" :offset="3">
      <span style="font-size: 25px">MSConfig</span>
    </el-col>
    <el-col :span="9" :offset="1" v-if="isLogin">
      <el-menu
        :default-active="activeIndex"
        mode="horizontal"
        @select="handleSelect"
        background-color="#000000"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="1">Config</el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="4" :offset="5" v-if="isLogin">
      <span>{{ user.username }}, </span>
      <el-button type="text" size="large" style="color: #fff;" @click="logout">Logout</el-button>
    </el-col>
  </el-row>
</template>

<script>

  import {mapState} from 'vuex'
  import {LOGOUT} from "../store/mutations";

  export default {
    name: "MyHeader",
    data() {
      return {
        activeIndex: '1',

        user: {
          username: "admin"
        }
      }
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      logout() {
        this.$store.commit(LOGOUT);
        this.$router.push('/login');
      }
    },
    computed: {
      ...mapState([
        'isLogin'
      ])
    }
  }
</script>

<style scoped>
</style>
