<template>
  <div id="app">
    <EmotionLineChart
        who="AmbRice46"
        :emotions="[
        '中性情感', '中性情感', '中性情感', '负面情感',
        '中性情感', '正面情感', '中性情感', '', '', '', '', ''
      ]"
    />
  </div>
</template>

<script>
import EmotionLineChart from '/src/components/pic.vue';

export default {
  name: 'App',
  components: {
    EmotionLineChart,
  },
  data(){
    return{
      character:'',
    }
  },
  mounted(){
    this.selectEmotionMonthByWho();
  },
  methods:{
    selectEmotionMonthByWho(){
      this.$request.get("/character/selectEmotionMonthByWho?who="+"@1843mag").then(res=>{
        if(!res){
          this.$message.info("后台未启动");
          return
        }
        if(res.code==="200"){
          this.character=res.data;
          console.log(this.character);
        }else {
          this.$message.error(res.msg);
        }
      })
    }
  }
};
</script>

<style>
/* 你可以在这里添加全局样式 */
</style>
