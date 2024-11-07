<template>
  <div ref="pieChart" style="width: 600px;height:400px;"></div>
</template>

<script>
// 引入 ECharts 主模块
import * as echarts from 'echarts';

export default {
  name: 'PieChart',
  props:{
    getData: {
      type: Array,
      required: true,
      default: () => [],
    },
    title:{
      type:String,
      required: true,
      default:()=>"ECharts 饼状图title",
    },
  },
  watch: {
    getData(newVal) {
      this.initChart();
    },
  },
  mounted() {
    this.initChart();
  },
  methods: {
    initChart() {
      const myChart = echarts.init(this.$refs.pieChart);

      // 指定图表的配置项和数据
      const option = {
        title: {
          text: this.title,
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '情感分析',
            type: 'pie',
            radius: '50%',
            data: this.getData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart.setOption(option);
    }
  }
};
</script>

<style scoped>

</style>