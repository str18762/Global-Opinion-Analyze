<template>
  <div ref="chart" style="height: 100%"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "PredictEmotion",
  props: {
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
        show: false,
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
          type:'inside',
          start: 0,
          end: 40,
        },
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: this.xData,
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
            data: this.yData[0]
          },
          {
            name: '厌恶',
            type: 'bar',
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: this.yData[1]
          },
          {
            name: '恐惧',
            type: 'bar',
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data:this.yData[2]
          },
          {
            name: '喜悦',
            type: 'bar',
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: this.yData[3]
          },
          {
            name: '悲伤',
            type: 'bar',
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: this.yData[4]
          },
          {
            name: '惊讶',
            type: 'bar',
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: this.yData[5]
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
            data: this.yData[6]
          },
        ]
      };
      chart.setOption(option);
    }
  },
  watch: {
    xData: function(newVal, oldVal) {
      this.drawChart();
    },
    yData: function(newVal, oldVal) {
      this.drawChart();
    }
  }
}
</script>


<style scoped>

</style>