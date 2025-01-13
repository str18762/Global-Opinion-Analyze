<template>
  <div ref="chart2" style="width: 100%;height: 250px"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "HotTopics_charts2",
  props: {
    chartData:{
      type: Array,
      required: true,
      default: () => [],
    },
  },
  mounted() {
    this.drawChart();
  },
  methods: {
    drawChart() {
      const chart2 = echarts.init(this.$refs.chart2);

      // 指定图表的配置项和数据
      const option = {
        tooltip: {
          trigger: 'item'
        },
        label: {
          fontSize: '15px',
          fontWeight: 'bold',
          color:'#00ffff'
        },
        series: [
          {
            emphasis: {
              label: {
                show: true,
                fontSize: '20',
                fontWeight: 'bold',
              },
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
              }
            },
            name: '情感指标',
            type: 'pie',
            radius: ['70%', '100%'],
            center: ['50%', '70%'],
            // adjust the start and end angle
            startAngle: 180,
            endAngle: 360,
            data: [
              { value: this.chartData[0], name: '积极情感',itemStyle: { normal: { color: 'green' } } },
              { value: this.chartData[1], name: '消极情感',itemStyle: { normal: { color: 'red' } } },
            ]
          }
        ]
      };
      chart2.setOption(option);
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