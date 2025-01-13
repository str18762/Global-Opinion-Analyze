<template>
    <div ref="chart" style="height: 100%"></div>
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
        legend: {
          show: true, // 显示图例
          orient: 'horizontal', // 图例排列方向，可选 'horizontal'（水平）或 'vertical'（垂直）
          left: 'left', // 图例组件离容器左侧的距离
          top: 0, // 图例组件离容器上侧的距离
          itemWidth: 10, // 图例图形宽度
          itemHeight: 10, // 图例图形高度
          textStyle: {
            fontSize: 13, // 图例文字大小
            color: '#0ff', // 图例文字颜色
          },
        },
        series: [
          {
            type: 'pie',
            stillShowZeroSum: true,
            radius: ['40%', '70%'], // 饼图的半径，支持设置成百分比或者数值，百分比为容器宽度的百分比
            center: ['50%', '60%'], // 饼图的位置，支持设置成百分比或者数值，百分比为容器宽度的百分比

            label: {
              show: true,
              position: 'outside', // 数据标签的位置
              fontSize: '13', // 字体大小，注意这里使用的是字符串形式
              color: '#00ffff', // 字体颜色
            },
            data: [
              {
                value: data[0],
                name: '愤怒',
                itemStyle: {
                  color: '#FF4500', // 图形颜色
                }
              },
              {
                value: data[1],
                name: '厌恶',
                itemStyle: {
                  color: '#808000', // 图形颜色
                }
              },
              {
                value: data[2],
                name: '恐惧',
                itemStyle: {
                  color: '#0074D9', // 图形颜色
                }
              },
              {
                value: data[3],
                name: '喜悦',
                itemStyle: {
                  color: '#FFD700', // 图形颜色
                }
              },
              {
                value: data[4],
                name: '悲伤',
                itemStyle: {
                  color: '#A9A9A9', // 图形颜色
                }
              },
              {
                value: data[5],
                name: '惊讶',
                itemStyle: {
                  color: '#FFA500  ', // 图形颜色
                }
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