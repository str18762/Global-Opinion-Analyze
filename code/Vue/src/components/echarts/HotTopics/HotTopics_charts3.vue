<template>
  <div ref="chart" style="height: 300px"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "HotTopics_charts3",
  props: {
    chartData:{
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
  },
  methods:{
    drawChart(){
      const chart1 = echarts.init(this.$refs.chart);
      const option={
        xAxis: {
          type: 'category',
          data: ['2024-08-26','2024-08-27','2024-08-28','2024-08-29','2024-08-30','2024-08-31'],
          axisLabel: {
            formatter: function (value) {
              let date = new Date(value);
              let day = date.getDate();
              let month = date.getMonth() + 1;
              return month + '/' + day;
            }
          }
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.chartData,
            type: 'line',
            label: {
              show: false,
              position: 'bottom',
              textStyle: {
                fontSize: 20
              }
            }
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
  },
}
</script>

<style scoped>

</style>