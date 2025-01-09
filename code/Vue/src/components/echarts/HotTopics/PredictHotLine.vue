<template>
  <div ref="chart" style="height: 100%"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "PredictionLine",
  props: {
    data: {

    }
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
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
            shadowStyle: {
              color: 'rgba(14,34,68,.5)',
              shadowColor: 'rgba(14,34,68,.1)',
              shadowBlur: 5
            }
          },
        },
        legend: {
          left: '0',
          align: 'left',
          top: '0',
          textStyle: {
            color: '#0ff'
          },
        },
        dataZoom:{
          type:'inside'
        },
        grid: {
          top: '20%',
          left: '0',
          right: '3%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          axisLine: {
            show: true,
            lineStyle: {
              type: 'solid',
              color: '#1aaed7',
              width: '2'
            }
          },
          axisTick: {
            "show": false
          },
          axisLabel: {
            color: '#1aaed7',
            width: 100
          },
          splitLine: {
            show: false
          },
          boundaryGap: false,
          data: ["周一", "周二", "周三", "周四", "周五"]
        }],
        yAxis: [{
          type: 'value',
          min: 0,
          max:120,
          splitNumber: 6,
          splitLine: {
            show: true,
            lineStyle: {
              color: '#0e2244'
            }
          },
          axisLine: {
            show: false,
          },
          axisLabel: {
            show: true,
            margin: 20,
            textStyle: {
              color: '#1aaed7',
            },
          },
          axisTick: {
            show: false,
          },
        }],
        series: [{
          name: '实际',
          type: 'line',
          showAllSymbol: true,
          symbol: 'circle',
          symbolSize: 10,
          lineStyle: {
            normal: {
              color: "#A582EA",
            },
          },
          label: {
            show: false
          },
          itemStyle: {
            color: "rgba(89,47,180,1)",
            borderColor: "rgba(89,47,180,.3)",
            borderWidth: 8
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(89,47,180,0.8)'
              },
                {
                  offset: 1,
                  color: 'rgba(89,47,180,0)'
                }
              ], false),
            }
          },
          data: [40, 30, 60, 70, 50]
        },
          {
            name: '预测',
            type: 'line',
            showAllSymbol: true,
            symbol: 'circle',
            symbolSize: 10,
            lineStyle: {
              normal: {
                color: "#2CABE3",
              },
            },
            label: {
              show: false
            },
            itemStyle: {
              color: "rgba(53,108,255,1)",
              borderColor: "rgba(53,108,255,.3)",
              borderWidth: 8
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(43,58,155,0.8)'
                },
                  {
                    offset: 1,
                    color: 'rgba(43,58,155,0)'
                  }
                ], false),
              }
            },
            data: [80, 60, 40, 90, 75]
          },
        ]
      }
      chart.setOption(option);
    },
  },
}
</script>



<style scoped>

</style>