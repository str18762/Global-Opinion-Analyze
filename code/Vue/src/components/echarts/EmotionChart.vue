<template>
  <div>
    <div ref="chart" style="width: 100%; height: 400px;"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "EmotionChart",
  props: {
    nowYearData:{
      type: Array,
      required: true,
      default: () => [],
    },
    lastYearData:{
      type: Array,
      required: true,
      default: () => [],
    },
  },
  data(){
    return{

    }
  },
  mounted() {
    this.drawChart()
  },
  methods:{
    drawChart(){
      const chart = echarts.init(this.$refs.chart);
      const colors = ['#5470C6', '#EE6666'];
      const nowYear = new Date().getFullYear();
      const lastYear = (parseInt(nowYear)-1).toString();
      const bottomX="情感("+nowYear+")";
      const topX="情感("+lastYear+")";
      let bottomXData = [];
      let topXData = [];
      for(let i=1;i<=12;i++){
        bottomXData.push(nowYear+'-'+i.toString());
        topXData.push(lastYear+'-'+i.toString());
      }
      const option = {
        color: colors,
        tooltip: {
          trigger: 'none',
          axisPointer: {
            type: 'cross'
          }
        },
        legend: {},
        grid: {
          top: 70,
          bottom: 50
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            axisLine: {
              onZero: false,
              lineStyle: {
                color: colors[1]
              }
            },
            axisPointer: {
              label: {
                formatter: function (params) {
                  return (
                      '情感  ' +
                      params.value +
                      (params.seriesData.length ? '：' + params.seriesData[0].data : '')
                  );
                }
              }
            },
            // prettier-ignore
            data: bottomXData
          },
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            axisLine: {
              onZero: false,
              lineStyle: {
                color: colors[0]
              }
            },
            axisPointer: {
              label: {
                formatter: function (params) {
                  return (
                      '情感  ' +
                      params.value +
                      (params.seriesData.length ? '：' + params.seriesData[0].data : '')
                  );
                }
              }
            },
            // prettier-ignore
            data: topXData
          }
        ],
        yAxis: [
          {
            type: 'category',
            data: ['消极','中性','积极'],
          }
        ],
        series: [
          {
            name: topX,
            type: 'line',
            xAxisIndex: 1,
            smooth: true,
            emphasis: {
              focus: 'series'
            },
            data: this.lastYearData
          },
          {
            name: bottomX,
            type: 'line',
            smooth: true,
            emphasis: {
              focus: 'series'
            },
            data: this.nowYearData
          }
        ]
      };
      chart.setOption(option);
    }
  },
  watch: {
    nowYearData(newVal) {
      this.drawChart();
    },
    lastYearData(newVal) {
      this.drawChart();
    },
  },
}
</script>

<style scoped>

</style>