<template>
  <div ref="chart" style="height: 100%"></div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "PredictInfluenceEvent",
  data() {
    return {
      option: {}
    }
  },
  mounted(){
    this.drawChart();
  },
  methods: {
    drawChart() {
      var colorList = [
        [
          '#ff7f50', '#87cefa', '#da70d6', '#32cd32', '#6495ed',
          '#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0',
          '#1e90ff', '#ff6347', '#7b68ee', '#d0648a', '#ffd700',
          '#6b8e23', '#4ea397', '#3cb371', '#b8860b', '#7bd9a5'
        ],
        [
          '#ff7f50', '#87cefa', '#da70d6', '#32cd32', '#6495ed',
          '#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0',
          '#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700',
          '#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0'
        ],
        [
          '#929fff', '#9de0ff', '#ffa897', '#af87fe', '#7dc3fe',
          '#bb60b2', '#433e7c', '#f47a75', '#009db2', '#024b51',
          '#0780cf', '#765005', '#e75840', '#26ccd8', '#3685fe',
          '#9977ef', '#f5616f', '#f7b13f', '#f9e264', '#50c48f'
        ]
      ][2];
      const chart = echarts.init(this.$refs.chart);
      const option = {
        // 图表标题
        title: {
          show:false,
          x: 'center',        // 水平安放位置，默认为左对齐，可选为：
          y: 'bottom',             // 垂直安放位置，默认为全图顶端，可选为：
        },
        series: [{
          type: 'graph',
          layout: 'force',
          cursor: 'pointer',
          force: {
            repulsion: 300,
            edgeLength: 10
          },
          roam: false,
          label: {
            normal: {
              show: true
            }
          },
          data: [
            {
              "name": "马斯克AAA!",
              "value": 2373,
              "symbolSize": 60,
              "draggable": true,
              "itemStyle": {
                "normal": {
                  "shadowBlur": 100,
                  "shadowColor": colorList[0],
                  "color": colorList[6]
                }
              },
              textStyle: {
                color: '#0ff',

              }
            },{
              "name": "马斯克BBB",
              "value": 1952,
              "symbolSize": 55,
              "draggable": true,
              "itemStyle": {
                "normal": {
                  "shadowBlur": 100,
                  "shadowColor": colorList[5],
                  "color": colorList[5]
                }
              }
            }, {
              "name": "马斯克CCC",
              "value": 926,
              "symbolSize": 70,
              "draggable": true,
              "itemStyle": {
                  "shadowBlur": 100,
                  "shadowColor": colorList[6],
                  "color": colorList[6]
              }
            }, {
              "name": "马斯克DDD",
              "value": 4536,
              "symbolSize": 67,
              "draggable": true,
              "itemStyle": {
                "normal": {
                  "shadowBlur": 100,
                  "shadowColor": colorList[7],
                  "color": colorList[7]
                }
              }
            }, {
              "name": "马斯克EEE",
              "value": 750,
              "symbolSize": 47,
              "draggable": true,
              "itemStyle": {
                "normal": {
                  "shadowBlur": 100,
                  "shadowColor": colorList[8],
                  "color": colorList[8]
                }
              }
            }, {
              "name": "马斯克FFF",
              "value": 20000,
              "symbolSize": 80,
              "draggable": true,
              "itemStyle": {
                  "shadowBlur": 100,
                  "shadowColor": colorList[12],
                  "color": colorList[12]
              },
            }]
        }]
      }
      chart.setOption(option);
    }
  }
}
</script>



<style scoped>

</style>