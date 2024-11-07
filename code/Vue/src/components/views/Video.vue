<template>
  <div>
    <video controls width="900">
      <source :src="src" :key="src" type="video/mp4">
    </video>
  </div>
</template>

<script>
export default {
  name: "Video",
  props: {
    src:{
      type: String,
      required: true,
      default:"",

    },
  },
  data(){
    return{

    }
  },
  watch:{
  }

}
</script>

<style scoped>

</style>