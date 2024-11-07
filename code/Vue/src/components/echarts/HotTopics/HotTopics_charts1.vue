<template>
  <div>
    <div ref="chart" style="height: 250px"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'HotTopics_charts1',
  props: {
    chartData:{
      type: Array,
      required: true,
      default: () => [],
    },
  },
  data() {
    return {}
  },
  mounted() {
    this.drawChart()
  },
  methods: {
    drawChart() {
      // 基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(this.$refs.chart);
      const data = this.chartData;

      const option = {
        tooltip: {
          trigger: 'item',

        },

        series: [
          {
            type: 'pie',
            stillShowZeroSum: false,
            label: {
              show: true,
              position: 'outside', // 数据标签的位置
              fontSize: '15', // 字体大小，注意这里使用的是字符串形式
              color: '#00ffff', // 字体颜色
            },
            data: [{
                value: data[0],
                name: '愤怒'
              },
              {
                value: data[1],
                name: '中立'
              },
              {
                value: data[2],
                name: '惊讶'
              },
              {
                value: data[3],
                name: '喜悦'
              },
              {
                value: data[4],
                name: '悲伤'
              },
              {
                value: data[5],
                name: '恐惧'
              },
            ],
          }
        ]
      };
      myChart.setOption(option)
    },
  },
  watch: {
    'chartData': function (val) {
       this.drawChart()
    },
  },
}
</script>

<style>
</style>