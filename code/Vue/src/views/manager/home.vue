<template>

</template>

<script>
import collapse from "element-ui/packages/collapse";
import router from "@/router";
import request from "@/utils/request";

export default {
  name: 'Home',
  data(){
    return{
      user: JSON.parse(localStorage.getItem('honey-user')||'{}'),
    }
  },
  mounted(){
  },
}
</script>

<style scoped>

</style>