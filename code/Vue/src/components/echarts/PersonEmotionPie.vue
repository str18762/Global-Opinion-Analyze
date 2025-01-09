<template>
  <div ref="chart" style="height: 100%"></div>
</template>


<script>
import * as echarts from "echarts";

export default {
  name: "PersonEmotionPie",
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
        legend: {
          show: true, // 显示图例
          orient: 'vertical', // 图例排列方向，可选 'horizontal'（水平）或 'vertical'（垂直）
          left: 200, // 图例组件离容器左侧的距离
          top: 0, // 图例组件离容器上侧的距离
          itemWidth: 20, // 图例图形宽度
          itemHeight: 20, // 图例图形高度
          textStyle: {
            fontSize: 13, // 图例文字大小
          },
        },
        series: [
          {
            type: 'pie',
            stillShowZeroSum: false,
            label: {
              show: true,
              position: 'outside', // 数据标签的位置
              fontSize: '20', // 字体大小，注意这里使用的是字符串形式
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


<style scoped>

</style>