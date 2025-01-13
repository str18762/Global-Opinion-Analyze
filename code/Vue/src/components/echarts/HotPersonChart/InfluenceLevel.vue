<template>
<div ref="chart" style="height: 100%"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "InfluenceLevel",
  props: {
    chartData:{
      type: Number,
      required: true,
    },
    name:{
      type: String,
      required: true
    }
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

      const option = {
        series: [
          {
            type: 'gauge',
            startAngle: 180,
            endAngle: 0,
            center: ['50%', '100%'],
            radius: '180%',
            min: 0,
            max: 1,
            splitNumber: 8,
            axisLine: {
              lineStyle: {
                width: 6,
                color: [
                  [0.25, 'grey'],
                  [0.5, 'green'],
                  [0.75, '#FF4500'],
                  [1, 'yellow']
                ]
              }
            },
            pointer: {
              icon: 'path://M12.8,0.7l12,40.1H0.7L12.8,0.7z',
              length: '12%',
              width: 20,
              offsetCenter: [0, '-60%'],
              itemStyle: {
                color: 'auto'
              }
            },
            axisTick: {
              length: 12,
              lineStyle: {
                color: 'auto',
                width: 2
              }
            },
            splitLine: {
              length: 20,
              lineStyle: {
                color: 'auto',
                width: 5
              }
            },
            axisLabel: {
              color: '#0ff',
              fontSize: 20,
              distance: -60,
              rotate: 'tangential',
              formatter: function (value) {
                if (value === 0.875) {
                  return '极高';
                } else if (value === 0.625) {
                  return '高';
                } else if (value === 0.375) {
                  return '中';
                } else if (value === 0.125) {
                  return '弱';
                }
                return '';
              }
            },
            title: {
              offsetCenter: [0, '-10%'],
              fontSize: 20,
              color: '#0ff',
            },
            detail: {
              fontSize: 30,
              offsetCenter: [0, '-35%'],
              valueAnimation: true,
              formatter: function (value) {
                return Math.round(value * 100) + '';
              },
              color: 'inherit'
            },
            data: [
              {
                value: this.chartData,
                name: this.name,
              }
            ]
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

<style scoped>

</style>