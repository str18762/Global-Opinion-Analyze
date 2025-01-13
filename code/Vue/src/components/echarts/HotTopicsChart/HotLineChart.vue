<template>
  <div ref="chart" style="height: 100%;width: 100%;"></div>
</template>

<script>
import * as echarts from "echarts";


export default {
  name: "HotLineChart",
  data(){
    return{
      xData : ["2020", "2021"],
      colorData : [
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
      obj : {
        '1月': [10, 11],
        '2月': [12, 13],
        '3月': [8, 10],
        '4月': [9, 14],
        '5月': [10, 17],
        '6月': [9, 13],
        '7月': [5, 12],
        '8月': [5, 12],
        '9月': [5, 12],
        '10月': [5, 12],
        '11月': [5, 12],
        '12月': [5, 12],
      },
      testData : [],
      seriesArr : [],
      sumValue:[],
    }
  },
  mounted() {
    this.drawChart()
  },
  methods: {
    drawChart(){
      const myEcharts = echarts.init(this.$refs.chart);

      for (let item in this.obj) {
        this.testData.push(item)
        this.seriesArr.push(this.obj[item])
      }
      let lastYear=0;
      let thisYear=0;
      this.seriesArr.map((i)=>{
        lastYear += parseInt(i[0]);
        thisYear += parseInt(i[1]);
      })
      this.sumValue = [lastYear,thisYear];
      let option = {
        backgroundColor: "#1a2439", //背景色
        tooltip: {
          show: false,
        },
        textStyle: {
          color: "#C9C9C9",
        },
        color: ["#74AF2E", "#74AF2E", "#2EAE9C", "#0096C7", "#0279CD", "#1949BF", "#1949BF", '#6942B0', '#9B40CA',
          '#E18439', '#DF9A37', '#D2CB04', '#9FE04B', '#9FE04B'
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
      let count = 0;
      let count_1 = 0;
      let testObj = [];
      data.map((item, index) => {
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
              colorStops: [{
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
            offset: [55, 0]
          },
          labelLine: {
            show: true,
          }
        });
        countArr = [];
        count += item[0];
        count_1 += item[1];
        countArr.push(count, count_1);
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
                color:'#fff',
                position: ['18',-25],       //  位置设为top
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

  }
}
</script>

<style scoped>

</style>