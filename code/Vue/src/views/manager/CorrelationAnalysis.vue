<template>
  <el-container>
    <el-main style="padding:0;">
      <el-card class="outer-card" :style="defaultHeight">
        <el-row :gutter="20">
          <el-col :span="5">
            <el-card class="box-card menu-card">
              <!-- 用 el-scrollbar 包裹 el-menu -->
              <h2>选择人物</h2>
              <el-input v-model="searchChara" prefix-icon="el-icon-search" placeholder="搜索人物" @input="filterChara()"></el-input>
              <div class="left_menu" style="height: 100vh;max-height: 500px">
                <el-menu default-active="1" class="el-menu-vertical-demo">
                  <el-menu-item v-for="chara in filteredCharas" :key="chara" @click="selectChara(chara)">
                    {{ chara }}
                  </el-menu-item>
                </el-menu>
              </div>
            </el-card>
          </el-col>

          <el-col :span="14">
            <el-card  class="box-card main-card">
              <div>
                <div id="chart" ref="chart" style="min-height: 550px"></div>
              </div>
            </el-card>
          </el-col>


          <el-col :span="5">
            <el-card class="box-card detail-card">
              <div slot="header" class="clearfix">
                <span>人物互动动态</span>
              </div>
              <div class="left_menu">
                <el-timeline :reverse="false" style="margin-left: 10px">
                  <el-timeline-item
                      v-for="(activity, index) in activities"
                      type="success"
                      :key="index"
                      :timestamp="activity.timestamp">
                    {{activity.content}}
                  </el-timeline-item>
                </el-timeline>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
import * as echarts from 'echarts';
import neo4j from "neo4j-driver";

export default {
  name: 'CorrelationAnalysis',
  components:{
  },
  data() {
    return {
      charas:[],
      chart:null,
      chartData:{
        nodes:[],
        links:[],
      },
      initQuery:'MATCH (n:chara)-[r:关注]->(m:chara) RETURN n,r,m limit 25',
      defaultHeight: {
        height: ""
      },
      activities:[
        {
          content:'韩国总统尹锡悦 祝贺 特朗普 当选美国总统',
          timestamp:'2024-11-6',
        },
        {
          content:'欧盟委员会主席冯德莱恩 祝贺 特朗普 当选美国总统',
          timestamp:'2024-11-6',
        },
        {
          content:'澳大利亚总理阿尔巴尼斯 祝贺 特朗普 当选美国总统',
          timestamp:'2024-11-6',
        },
        {
          content:'北约秘书长吕特 祝贺 特朗普 当选美国总统',
          timestamp:'2024-11-6',
        },
        {
          content:'加拿大总理特鲁多和印度总理莫迪发声，对上周日（3日）发生在加拿大大多伦多地区布兰普顿市一座印度教寺庙的暴力事件表示谴责',
          timestamp:'2024-11-4',
        },
        {
          content:'俄罗斯总统普京就塞尔维亚火车站雨棚倒塌事故向塞总统武契奇致慰问电',
          timestamp:'2024-11-2',
        },
        {
          content:'欧盟外交与安全政策高级代表博雷利访问日本',
          timestamp:'2024-11-1',
        },
      ],
      searchChara:'',
      filteredCharas:[],
    };
  },
  mounted() {
    this.chart = echarts.init(this.$refs.chart);
    this.chart.on('click', this.handleNodeClick);
    this.updateChart(this.initQuery);
    this.getCharaName()
    this.filteredCharas=this.charas
  },
  methods: {
    getCharaName(){
      const getChara = 'MATCH (n:chara) RETURN n.name'
      this.runCypherQuery(getChara,{}).then(data=>{
        for(const name of data){
          this.charas.push(name._fields[0])
        }
      })
      console.log("charas",this.charas)
    },
    async runCypherQuery (query,params){
      const driver = neo4j.driver(
          'bolt://localhost:7687',
          neo4j.auth.basic('neo4j','12345678')
      );
      const session = driver.session()
      const result = await session.run(query,params)
      session.close()
      return result.records;
    },
    selectChara(chara){
      console.log("chara",chara)
      const query = "match(n:chara)-[r:`关注`]->(m:chara) where n.name='"+chara+"' return n,r,m"
      this.updateChart2(query);
    },
    async queryAndTransformData(query) {
      const result = await this.runCypherQuery(query, {});
      const nodes = [];
      const links = [];
      for (const record of result) {
        const node1 = record.get('n');
        const node2 = record.get('m');
        const link = record.get('r');

        const node1Index = nodes.findIndex(node => node.id === node1.identity.toString());
        const node2Index = nodes.findIndex(node => node.id === node2.identity.toString());
        if (node1Index === -1) {
          nodes.push({
            id: node1.identity.toString(),
            name: node1.properties.name,
            emotion:node1.properties.emotion,
            followers:node1.properties.followers,
            type: node1.properties.chara_type,
          });
        }
        if (node2Index === -1) {
          nodes.push({
            id: node2.identity.toString(),
            name: node2.properties.name,
            emotion:node2.properties.emotion,
            followers:node2.properties.followers,
            type: node2.properties.chara_type,
          });
        }
        links.push({
          source: node1.identity.toString(),
          target: node2.identity.toString(),
          name: link.type
        });
      }
      return {
        nodes,
        links
      };
    },
    async updateChart2(query) {
      let data = await this.queryAndTransformData(query);
      this.chartData = data;
      this.setChartOption()
    },
    async updateChart(query) {
      let data = await this.queryAndTransformData(query);
      let addData = {
        nodes: [...this.chartData.nodes, ...data.nodes],
        links: [...this.chartData.links, ...data.links]
      };

      // 去重
      addData.nodes = addData.nodes.filter((node, index, self) =>
          index === self.findIndex(t => t.id === node.id)
      );
      addData.links = addData.links.filter((link, index, self) =>
          index === self.findIndex(t => t.source === link.source && t.target === link.target)
      );
      this.chartData = addData;
      this.setChartOption()
    },
    setChartOption(){
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: function (params) {
            // 自定义提示框内容
            if (params.dataType==='node') {
              return `<strong>人物名:${params.data.name}</strong><br/>
                        关注数: ${params.data.followers}<br/>
                        情感:${params.data.emotion}<br/>
                        认证:${params.data.type}`;
            }
            else if (params.dataType==='edge') {
              return `<strong>关系类型:${params.data.name}</strong>`
            }
          }
        },
        series: [
          {
            type: 'graph',
            roam:true,
            layout: 'force',
            cursor: 'pointer',
            force: {
              repulsion: 450,
              edgeLength: 150
            },
            data: this.chartData.nodes.map(node => ({
              id:node.id,
              name: node.name,
              followers:node.followers,
              emotion:node.emotion,
              type:node.type,
              draggable: true,
              symbolSize: this.getNodeSize(node.type), // 根据 type 设置节点大小
              itemStyle: {
                color: this.getNodeColor(node.type) // 根据 type 设置节点颜色
              },
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            })),
            links: this.chartData.links.map(link => ({
              source: link.source,
              target: link.target,
              name: link.name
            })),
            label: {
              show: true,

              normal: {
                // 是否显示标签
                show: true,
                // 标签位置:'top''left''right''bottom''inside''insideLeft''insideRight''insideTop''insideBottom''insideTopLeft''insideBottomLeft''insideTopRight''insideBottomRight'
                position: "top",
                // 文本样式
                textStyle: {
                  fontSize: 16,
                  color: 'black'
                }
              }
            },
            lineStyle: {
              width: 3,
              color: '#ccc'
            },
            edgeLabel: {
              normal: {
                show: true,
                textStyle: {
                  color: '#000'
                  // fontSize: 14
                },
                // 标签内容
                formatter: function(param) {
                  return param.data.name;
                }
              }
            },
          }
        ]
      };
      this.chart.setOption(option);
    },

    handleNodeClick(node) {
      console.log('Handling click for node:', node.data);
      if(!node.data.id){
        return;
      }
      const query = 'match(n:chara)-[r:`关注`]->(m:chara) where id(n)=' + node.data.id +' return n,r,m'
      console.log("更新query",query)
      // 更新节点数据
      this.updateChart(query);
    },

    // 定义一个函数来根据 type 返回节点大小
    getNodeSize(type) {
      switch (type) {
        case 'Government Certified':
          return 38;
        case 'Media Certified':
          return 33;
        case 'Personal Certified':
          return 24;
        case 'Unclassified':
          return 18
        default:
          return 15; // 默认大小
      }
    },

    // 定义一个函数来根据 type 返回节点颜色
    getNodeColor(type) {
      switch (type) {
        case 'Government Certified':
          return 'red'; // 橙色
        case 'Media Certified':
          return 'orange'; // 天蓝色
        case 'Personal Certified':
          return 'blue'; // 紫罗兰色
        case 'Unclassified':
          return '#696969'; //
        default:
          return '#cccccc'; // 默认颜色
      }
    },

    filterChara(){
      this.filteredCharas = this.charas.filter(character => {
        character = character.toLowerCase();
        let searchQuery = this.searchChara.toLowerCase();
        let matchesQuery = character.includes(searchQuery)
        return matchesQuery;
      });
    },
    getHeight() {
      this.defaultHeight.height = window.innerHeight - 110 + "px";
    }
  },
  created() {
    //页面创建时执行一次getHeight进行赋值，顺道绑定resize事件
    window.addEventListener("resize", this.getHeight);
    this.getHeight();
  }
};
</script>

<style scoped>
body {
  background-color: #f0f0f0;
}

.outer-card {
  background: linear-gradient(135deg, #bdc3c7, #ecf0f1);
  padding: 0px;
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.box-card {
  background: linear-gradient(315deg, #7f8c8d, #bdc3c7); /* 从右下到左上的灰色渐变 */
  color: #000;
  border-radius: 10px;
}

.menu-card {
  height: 100%;
}

.chart-container {
  width: 100%;
  height: 650px;
}

.user-details p {
  margin: 0 0 10px 0;
}

.user-list {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
  max-width: 100%;
}

.el-menu-item {
  background-color: #bdc3c7 !important;
  color: #000;
}

.el-menu-item:hover {
  background-color: #666666 !important;
  color: rgba(255, 255, 255, 0.8) ;
}

.el-menu-item-group__title {
  color: #ffffff !important;
  padding-left: 20px;
}
.left_menu{
  overflow-y: auto; /* 启用垂直滚动条 */
  height: 500px;
}
/* 滚动条整体部分 */
.left_menu::-webkit-scrollbar {
  width: 0; /* 滚动条的宽度 */
}

/deep/.el-timeline-item__timestamp{
  color: black;
}
</style>
