<template>
  <el-button @click="preStep">Prev</el-button>
</template>

<script>
  import {mapState} from 'vuex'
  import {STEPS_DECRE} from '@/store/mutations'

  export default {
    name: "PreStepBtn",
    computed: {
      ...mapState({
        active: 'stepsActive'
      })
    },
    methods: {
      // 上一步
      preStep() {
        this.$store.commit(STEPS_DECRE);
        this.$router.push(
          {path: `/config/${this.active + 1}`}
        )
      }
    }
  }
</script>

<style scoped>

</style>
