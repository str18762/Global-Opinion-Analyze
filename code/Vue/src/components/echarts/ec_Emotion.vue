<template>
  <div>
    <div ref="chart" style="width: 100%; height: 400px;"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'Emotion',//人物具体界面的情感变化趋势图
  props: {
    xdata:{
      type: Array,
      required: true,
      default: () => [],
    },
    ydata:{
      type: Array,
      required: true,
      default: () => [],
    },
    name: {
      type: String,
      required: true,
      default: 'Unknown'
    },
  },
  mounted() {
    this.drawChart()
  },
  methods: {
    drawChart() {
      const chart = echarts.init(this.$refs.chart);
      const option = {
        tooltip: {
          trigger: 'axis',
        },
        dataZoom:{
          type:'inside'
        },
        xAxis: {
          type: 'category',
          data: this.xdata,
        },
        yAxis: {
          type: 'category',
          data: ['消极','中性','积极'],
        },
        series: [
          {
            data: this.ydata,
            type: 'line',
            smooth: true,
            label: {
              show: true,
            },
          },
        ],
      };
      chart.setOption(option);
    },
  },
  watch: {
    xdata(newVal) {
      this.drawChart();
    },
  },
};
</script>


<style scoped>
/* 你可以在这里添加样式 */
</style>
