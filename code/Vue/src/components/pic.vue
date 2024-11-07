<template>
  <div>
    <h2>{{ who }} 的情感变化图</h2>
    <div ref="chart" style="width: 100%; height: 400px;"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'EmotionLineChart',
  props: {
    who: {
      type: String,
      required: true,
    },
    emotions: {
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
      const chart = echarts.init(this.$refs.chart);
      const option = {
        tooltip: {
          trigger: 'axis',
        },
        xAxis: {
          type: 'category',
          data: [
            '1月', '2月', '3月', '4月', '5月', '6月', '7月',
            '8月', '9月', '10月', '11月', '12月',
          ],
        },
        yAxis: {
          type: 'category',
          data: ['负面情感', '中性情感', '正面情感'],
        },
        series: [
          {
            data: this.emotions.map((emotion) => {
              switch (emotion) {
                case '正面情感':
                  return 2;
                case '中性情感':
                  return 1;
                case '负面情感':
                  return 0;
                default:
                  return null;
              }
            }),
            type: 'line',
            smooth: true,
            label: {
              show: true,
              formatter: (params) => {
                switch (params.value) {
                  case 2:
                    return '正面情感';
                  case 1:
                    return '中性情感';
                  case 0:
                    return '负面情感';
                  default:
                    return '';
                }
              },
            },
          },
        ],
      };

      chart.setOption(option);
    },
  },
};
</script>

<style scoped>
/* 你可以在这里添加样式 */
</style>
