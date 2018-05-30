<template>
  <el-button @click="submit($event)">Finish</el-button>
</template>

<script>

  import axios from 'axios'
  import api from '../api'

  import {mapState} from 'vuex'
  import {STEPS_INCRE} from "../store/mutations";

  export default {
    name: "SubmitBtn",
    computed: {
      ...mapState([
        'stepsActive',
        'services',
        'componentCheck',
        'eurekaServerInfo',
        'ribbon',
        'zuulInfo',
        'composeInfo'
      ])
    },
    methods: {
      async submit(event) {

        event.preventDefault();//取消默认行为

        // 上传文件
        let uploadPromises = this.services.map(async function (service) {

          // 创建formData对象
          let formData = new FormData();
          console.log(service.folder);

          let fileNum = service.folder.length;
          for (let i = 0; i < fileNum; i++) {
            let str = i.toString();
            let file = service.folder[str];
            formData.append("folder", file);
          }

          // 上传文件
          // api.upload(formData);
          return axios.post('http://localhost:8000/general/uploadFolder', formData);
        });

        // 服务名称，配置map
        let configurationList = [];

        this.services.forEach(function (service) {

          let configurationItems = [];
          for (let key in service.config) {
            configurationItems.push({
              itemName: key,
              value: service.config[key]
            })
          }

          // 手工添加的配置装上去
          service.addedConfigs.forEach(function (addedConfig) {
            configurationItems.push({
              itemName: addedConfig.key,
              value: addedConfig.value
            });
          });

          // 装载configs
          configurationList.push({
            "projectPath": service.address,
            "list": configurationItems
          });

        });

        let services = [];

        this.services.forEach(function (service, index) {

          services.push({
            serviceName: service.serviceName,

            // folder
            folderName: service.folderName,

            // config
            config: configurationList[index],

            // mysql
            mysqlInfo: service.mysqlInfo
          });

        });

        let general = {
          // 所有微服务的信息
          "services": services,

          // eureka server
          "isEurekaServer": this.componentCheck.checkedEurekaServer,
          "eurekaServerInfo": this.eurekaServerInfo,

          // ribbon
          "isRibbon": this.componentCheck.checkedRibbon,
          "ribbon": this.ribbon,

          // hystrix
          "isHystrix": this.componentCheck.checkedHystrix,

          // zuul
          "isZuul": this.componentCheck.checkedZuul,
          "zuulInfo": this.zuulInfo,

          // docker compose
          "composeInfo":
            {
              serviceList: this.composeInfo
            }
        };

        console.log(JSON.stringify(general));

        let _this = this;

        // upload
        await this.$axios.all(uploadPromises)
          .then(() => alert("Upload Success"));

        // add
        await axios.post('http://localhost:8000/general/add', general)
          .then(() => {
            alert("Add Success");
          });

        alert("Submit Success");
        // 跳转第9步
        this.$store.commit(STEPS_INCRE);
        this.$router.push(
          {path: `/config/${this.stepsActive + 1}`}
        )
      }
    }
  }
</script>

<style scoped>

</style>
