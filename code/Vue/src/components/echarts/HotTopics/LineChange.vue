<template>
  <div ref="chart" style="height: 100%"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "HotTopics_charts3",
  props: {
    xData:{
      type: Array,
      required: true,
      default: () => [],
    },
    yData:{
      type: Array,
      required: true,
      default: () => [],
    },
  },
  data(){
    return{

    }
  },
  mounted(){
    this.drawChart();
    console.log("x",this.xData)
    console.log("y",this.yData)
  },
  methods:{
    drawChart(){
      const chart1 = echarts.init(this.$refs.chart);
      const option={
        xAxis: {
          type: 'category',
          data: this.xData,
          axisLabel: {
            fontSize: 12,
            color: '#0ff'
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            fontSize: 10,
            color: '#0ff'
          }
        },
        dataZoom:{
          type:'inside'
        },
        series: [
          {
            data: this.yData,
            type: 'line',
            label: {
              show: true,
              position: 'top',
              fontSize: 14,
              color: '#0ff'
            },
          }
        ]
      };
      chart1.setOption(option)
    }
  },
  watch: {
    'chartData': function (val) {
      this.drawChart()
    },
    'xData': function (val) {
      this.drawChart()
    },
    'yData': function (val) {
      this.drawChart()
    },
  },
}
</script>

<style scoped>

</style>