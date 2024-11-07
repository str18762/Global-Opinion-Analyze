<template>
  <div ref="chart" style="height: 300px"></div>
</template>

<script>
import * as echarts from "echarts";
import 'echarts-wordcloud';

export default {
  name: "HotTopics_charts4",
  props: {
    chartData:{
      type: Array,
      required: true,
      default: () => [],
    },
  },
  data(){
    return{
    }
  },
  mounted(){
    this.drawChart() //调用定义词云图方法
  },
  methods:{
    //定义词云图并插入容器内
    drawChart(){
      let myChart2 = echarts.init(this.$refs.chart);
      const option= {
        backgroundColor: "transparent",
        tooltip: {
          show: false
        },
        series: [{
          type: "wordCloud",
          //用来调整词之间的距离
          gridSize: 10,
          //用来调整字的大小范围
          sizeRange: [14, 60],
          //用来调整词的旋转方向，，[0,0]--代表着没有角度，也就是词为水平方向，需要设置角度参考注释内容
          rotationRange: [-45, 0, 45, 90],
          // rotationRange: [ 0,90],
          //rotationRange: [0, 0],
          //随机生成字体颜色
          textStyle: {
            normal: {
              color: function() {
                return `rgb(${[
                  Math.round(Math.random() * 200 + 55),
                  Math.round(Math.random() * 200 + 55),
                  Math.round(Math.random() * 200 + 55)
                ].join(',')})`
              }
            }
          },
          //位置相关设置
          left: "center",
          top: "center",
          right: null,
          bottom: null,
          width: "200%",
          height: "200%",
          //数据
          data: this.chartData
        }]
      }
      myChart2.setOption(option)
    }
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