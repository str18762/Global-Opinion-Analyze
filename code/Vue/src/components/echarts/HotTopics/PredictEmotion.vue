<template>
  <div ref="chart" style="height: 100%"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "PredictEmotion",
  props: {
    chartData: {
    },
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
  data() {
    return {

    }
  },
  mounted() {
    this.drawChart();
  },
  methods: {
    drawChart() {
      const chart = echarts.init(this.$refs.chart);
      const labelOption = {
        show: true,
        formatter: '{name|{a}}',
        fontSize: 12,
        rich: {
          name: {

          }
        }
      };
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          show: true,
          data: ['愤怒', '厌恶', '恐惧', '喜悦','悲伤','惊讶','中立'],
          textStyle: {
            color: '#0ff',
            fontSize: 12,
          }
        },
        dataZoom:{
          type:'inside'
        },
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: ['2024-8-1', '2024-9-2', '2024-10-1', '2024-11-1', '2024-12-2','2024-8-1', '2024-9-2', '2024-10-1', '2024-11-1', '2024-12-2']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '愤怒',
            type: 'bar',
            barGap: 0,
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: [320, 332, 301, 334, 390,320, 332, 301, 334, 390]
          },
          {
            name: '厌恶',
            type: 'bar',
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: [220, 182, 191, 234, 290,320, 332, 301, 334, 390]
          },
          {
            name: '恐惧',
            type: 'bar',
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: [150, 232, 201, 154, 190,320, 332, 301, 334, 390]
          },
          {
            name: '喜悦',
            type: 'bar',
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: [98, 77, 101, 99, 40,320, 332, 301, 334, 390]
          },
          {
            name: '悲伤',
            type: 'bar',
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: [98, 77, 101, 99, 40,320, 332, 301, 334, 390]
          },
          {
            name: '惊讶',
            type: 'bar',
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: [150, 232, 201, 154, 190,320, 332, 301, 334, 390]
          },
          {
            name: '中立',
            type: 'bar',
            show: false,
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            itemStyle: {
              opacity: 0
            },
            data: [150, 232, 201, 154, 190,320, 332, 301, 334, 390]
          },
        ]
      };
      chart.setOption(option);
    }
  },
}
</script>


<style scoped>

</style>