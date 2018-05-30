<template>
  <el-container>
    <el-aside width="550px">
      <steps-bar/>
    </el-aside>
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="20">
            <router-view/>
          </el-col>
        </el-row>
      </el-main>
      <el-footer>
        <!--<el-button type="primary" @click="promise">Promise Test</el-button>-->
        <el-row>
          <el-col :span="4" :offset="19" v-if="active === 0">
            <next-step-btn v-if="active < 8"/>
          </el-col>
          <el-col :span="6" :offset="18" v-if="active !== 0">
            <pre-step-btn v-if="active > 0 && active < 8"/>
            <next-step-btn v-if="active < 7"/>
            <submit-btn v-if="active === 7"/>
          </el-col>
        </el-row>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>

  import StepsBar from './StepsBar'

  // footer的三个btn
  import PreStepBtn from './PreStepBtn'
  import NextStepBtn from './NextStepBtn'
  import SubmitBtn from './SubmitBtn'

  // vuex
  import {mapState} from 'vuex'

  export default {
    // 组件名称
    name: 'ProcessComponent',
    components: {
      'steps-bar': StepsBar,

      // footer
      'pre-step-btn': PreStepBtn,
      'next-step-btn': NextStepBtn,
      'submit-btn': SubmitBtn
    },
    computed: {
      ...mapState({
        // 表示当前执行到的步骤
        active: 'stepsActive'
      })
    },
    methods: {
      // async 等待传输完毕
      async promise() {
        function timeout(ms) {
          // promise 在创建的时候就会开始调用
          return new Promise((resolve, reject) => {
            // setTimeout是异步的
            setTimeout(resolve, ms, 'done');
          });
        }

        // promise的状态变为resolved之后才会触发then
        await timeout(5000).then((value) => {
          console.log(value);
        });

        //
        let promise = new Promise(function (resolve, reject) {
          console.log('Promise');
          resolve();
        });

        promise.then(function () {
          console.log('Resolved');
        });

        console.log('Hi');

        let p1 = new Promise(function (resolve, reject) {
          setTimeout(() => reject(new Error('fail')), 3000)
        });
        let p2 = new Promise(function (resolve, reject) {
          setTimeout(() => resolve(p1), 1000)
        });
        p2.then(result => console.log(result))
        p2.catch(error => console.log(error))
// Error: fail

      }
    }
  }

</script>
