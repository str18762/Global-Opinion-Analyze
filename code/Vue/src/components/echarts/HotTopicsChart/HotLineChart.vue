<template>
  <div ref="chart" style="height: 100%;width: 100%;"></div>
</template>

<script>
import * as echarts from "echarts";


export default {
  name: "HotLineChart",
  props: {
    xData: {
      type: Array,
      required: true,
      default: () => [],
    },
    obj: {
      type: Object,
      required: false,
      default: () => { },
    }
  },
  data() {
    return {
      colorData: [
        ['#87C53E', '#97D44F'],
        ['#40CDB9', '#2EAE9C'],
        ['#2CB8E6', '#0096C7'],
        ['#2C8CE6', '#1949BF'],
        ['#1949BF', '#2C61E6'],
        ['#341EA9', '#4829E4'],
        ['#6942B0', '#9162DE'],
        ['#9B40CA', '#BE5CDE'],
        ['#E18439', '#FFA127'],
        ['#DF9A37', '#FFC946'],
        ['#D2CB04', '#FFF95A'],
        ['#9FE04B', '#CAFF86']
      ],
      testData: [],
      seriesArr: [],
      sumValue: [],
    }
  },
  mounted() {
    this.drawChart()
  },
  methods: {
    drawChart() {
      const myEcharts = echarts.init(this.$refs.chart);

      this.testData = []
      this.seriesArr = []
      for (let item in this.obj) {
        this.testData.push(item)
        this.seriesArr.push(this.obj[item])
      }
      let month_list = [];
      for (let i = 0; i < this.xData.length; i++) {
        month_list.push(0)
      }
      this.seriesArr.map((i) => {
        for (let index = 0; index < this.xData.length; index++) {
          month_list[index] += parseInt(i[index])
        }
      })
      this.sumValue = month_list
      let option = {
        backgroundColor: "#1a2439", //背景色
        tooltip: {
          show: true,
          trigger: 'axis',
          confine:true,
          formatter: function (params) {
            let res = params[0].name + '<br/>';
            for (let i = 0; i < params.length / 2; i++) {
              res += params[i].seriesName + ':' + params[i].value + '<br/>';
            }
            return res;
          }
        },
        textStyle: {
          color: "#C9C9C9",
        },
        color: ["#74AF2E", "#74AF2E", "#2EAE9C", "#0096C7", "#0279CD", "#1949BF", "#1949BF", '#6942B0', '#9B40CA',
          '#E18439', '#DF9A37', '#D2CB04', '#9FE04B', '#9FE04B'
        ],
        dataZoom: [
          {
            type: 'inside', // 内置型数据区域缩放组件
            start: 0, // 数据窗口范围的起始百分比
            end: 100 // 数据窗口范围的结束百分比
          }
        ],
        legend: {
          type: "scroll",
          orient: 'vertical',
          selectedMode: false, //图例点击失效
          right: "10%",
          top: "15%",
          textStyle: {
            color: "#ffffff",
            fontSize: 14,
          },
        },
        grid: {
          containLabel: true,
          left: "10%",
          top: "20%",
          bottom: "10%",
          right: "30%",
        },
        xAxis: {
          type: "category",
          data: this.xData,
          axisLine: {
            show: false,
            lineStyle: {
              color: "#B5B5B5",
            },
          },
          axisTick: {
            show: false,
          },
          axisLabel: {
            margin: 20, //刻度标签与轴线之间的距离。
            textStyle: {
              fontFamily: "Microsoft YaHei",
              color: "#18BBFF",
            },
            fontSize: 16,
            fontStyle: "bold"
          },
        },
        yAxis: {
          type: "value",
          axisLine: {
            show: false,
            lineStyle: {
              color: "#B5B5B5",
            },
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: '#466ABA'
            }

          },
          axisLabel: {
            textStyle: {
              fontFamily: "Microsoft YaHei",
              color: "#18BBFF",
            },
            fontSize: 16,
          },
        },
        series: this.seriesData(this.seriesArr)
      };
      console.log(option)
      myEcharts.setOption(option);
    },
    seriesData(data) {
      let barData = [];
      let pictorialBarData = [];
      let countArr = [];
      let count_list = [];
      for (let i = 0; i < this.xData.length; i++) {
        count_list.push(0);
      }
      data.map((item, index) => {
        console.log("index:", index)
        barData.push({
          "name": this.testData[index],
          data: item,
          stack: "zs",
          type: "bar",
          barMaxWidth: "auto",
          barWidth: 50,
          itemStyle: {
            color: {
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              type: "linear",
              global: false,
              colorStops: [{  // smys
                offset: 0,
                color: this.colorData[index][0],
              },
              {
                offset: 1,
                color: this.colorData[index][1],
              },

              ],
            },
          },
          label: {
            show: true,
            offset: [55, 0],
          },
          labelLine: {
            show: true,
          }
        });
        countArr = [];
        for (let idx = 0; idx < this.xData.length; idx++) {
          count_list[idx] += item[idx]
        }
        countArr = count_list;
        pictorialBarData.push({
          data: countArr,
          type: "pictorialBar",
          barMaxWidth: "20",
          symbolPosition: "end",
          symbol: "diamond",
          symbolOffset: [0, "-50%"],
          symbolSize: [50, 20],
          zlevel: 2,
        })
      })
      let btArr = [{
        data: [1, 1],
        type: "pictorialBar",
        barMaxWidth: "20",
        symbol: "diamond",
        symbolOffset: [0, "50%"],
        symbolSize: [50, 20],
        zlevel: 2,
      }, {
        data: data[0],
        type: "pictorialBar",
        barMaxWidth: "20",
        symbol: "diamond",
        symbolOffset: [0, "50%"],
        symbolSize: [50, 20],
        zlevel: 2,
      }];
      barData.unshift(
        {
          name: '总数',
          type: 'bar',
          barGap: '-100%',         // 左移100%，stack不再与上面两个在一列
          label: {
            normal: {
              show: true,
              color: '#fff',
              position: ['18', -25],       //  位置设为top
              formatter: '{c}',
              textStyle: { color: '#fff' }
            }
          },
          barWidth: 50,
          itemStyle: {
            normal: {
              color: 'rgba(128, 128, 128, 0.3)'    // 仍为透明
            }
          },
          data: this.sumValue,
        })
      let arr = [...barData, ...btArr, ...pictorialBarData];
      console.log('arr', arr)
      return arr;
    },

  },
  watch: {
    'obj': function (val) {
      this.drawChart()
    },
  },
}
</script>

<style scoped></style>