<template>
<div class="out_box" style="padding: 10px">
  <div style="height: 100%;width: 100%;display: flex;flex-wrap: nowrap">
    <div style="flex:1;height: 100%;">
      <dv-border-box-9 class="dv_content">
        <div style="height: 100%;width: 100%">
          <div style="display: flex;">
            <h2 style="flex: 1">选择人物</h2>
            <div style="flex: 1;align-self: flex-end">
              <el-link :underline="false" type="danger" style="font-size: 20px" @click="$router.push('/hotPerson')">
                返回<i class="el-icon-d-arrow-right"/>
              </el-link>
            </div>
          </div>
          <el-input v-model="searchChara" prefix-icon="el-icon-search" placeholder="搜索人物" @input="filterChara()" />
          <div class="left_menu" style="height: 100vh;max-height: 500px">
            <el-menu class="el-menu-vertical-demo" style="background-color: transparent;">
              <el-menu-item v-for="chara in filteredCharas" :key="chara" @click="selectChara(chara)">
                {{ chara }}
              </el-menu-item>
            </el-menu>
          </div>
        </div>
      </dv-border-box-9>
    </div>
    <div style="flex: 3;height: 100%;">
      <dv-border-box-9 class="dv_content">
        <div style="height: 100%;width: 100%">
          <div style="height: 100%;width: 100%">
            <div id="chart" ref="chart" style="min-height: 550px;height: 100%;width: 100%"></div>
          </div>
          <!-- 圆形菜单 -->
          <div v-if="showMenu" :style="menuStyle" class="circle-menu">
            <div class="menu-item" @click="executeAction('extend')">扩展</div>
            <div class="menu-item" @click="executeAction('hide')">隐藏</div>
            <div class="menu-item" @click="executeAction('cancel')">取消</div>
            <div class="menu-item" @click="executeAction('explain')">解释</div>
            <span class="menu-center">{{ selectedNode?.name }}</span>
          </div>
        </div>

      </dv-border-box-9>
    </div>
    <div style="flex: 1;height: 100%;">
      <dv-border-box-9 class="dv_content">
        <el-card class="box-card" style="color: #0ff">
          <div slot="header" class="clearfix">
            <span>人物互动动态</span>
          </div>
          <div class="left_menu">
            <el-timeline :reverse="false" style="margin-left: 10px;color: #0ff">
              <el-timeline-item
                  v-for="(activity, index) in activities"
                  type="success"
                  :key="index"
                  :timestamp="activity.timestamp">
                <span style="color: #0ff">{{activity.content}}</span>
              </el-timeline-item>
            </el-timeline>
          </div>
        </el-card>
      </dv-border-box-9>
    </div>
  </div>

</div>
</template>

<script>
import * as echarts from 'echarts';
import neo4j from "neo4j-driver";

export default {
  name: "Correlation",
  data() {
    return {
      charas:[],
      chart:null,
      chartData:{
        nodes:[],
        links:[],
      },
      initQuery:'MATCH (n:chara)-[r:关注]->(m:chara) RETURN n,r,m limit 25',
      selectedNodeId:null,
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
          content:'加拿大总理特鲁多和印度总理莫迪 谴责 发生暴力事件 在加拿大大多伦多地区布兰普顿市一座印度教寺庙',
          timestamp:'2024-11-4',
        },
        {
          content:'俄罗斯总统普京 致慰问电 塞总统武契奇 塞尔维亚火车站雨棚倒塌事故',
          timestamp:'2024-11-2',
        },
        {
          content:'欧盟外交与安全政策高级代表博雷利 访问 日本',
          timestamp:'2024-11-1',
        },
      ],
      searchChara:'',
      filteredCharas:[],

      // 圆形菜单相关
      showMenu: false,
      selectedNode: null,
      menuStyle: {
        position: 'absolute',
        width: '200px',
        height: '200px',
        borderRadius: '50%',
        backgroundColor: 'lightgray',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        top: '0px',
        left: '0px',
      },
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
        if (data.length === 0) {
          console.warn("没有获取到角色数据"); // 可以记录警告
          return; // 如果没有数据，直接返回
        }
        for(const name of data){
          this.charas.push(name._fields[0])
        }
      }).catch(error=>{
        console.error("获取角色数据失败",error)
        this.$message.error("获取角色数据失败")
      })
    },
    async runCypherQuery (query,params){
      const driver = neo4j.driver(
          process.env.VUE_APP_NEO4J_URI,
          neo4j.auth.basic('neo4j','12345678')
      );
      const session = driver.session()
      try {
        const result = await session.run(query,params)
        return result.records;
      }catch (error){
        console.log("数据库连接失败",error)
        this.$message.error("数据库连接失败")
        return [];
      }finally {
        session.close()
      }
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
            roam:false,
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
                  shadowBlur: 20,
                  shadowColor: '#0ff'
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
                  fontSize: 20,
                  color: '#0ff'
                }
              }
            },
            lineStyle: {
              width: 3,
              color: '#fff'
            },
            edgeLabel: {
              normal: {
                show: true,
                textStyle: {
                  color: '#0ff'
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
      // 检查是否点击的是节点
      if (node.componentType === 'series' && node.dataType === 'node') {
        // 使用 echarts api 获取节点的坐标
        const pointInPixel = [node.event.offsetX, node.event.offsetY];
        console.log('点击位置的像素坐标:', pointInPixel);

        // 如果需要得到图表内逻辑坐标（x, y轴对应的值）
        const pointInGrid = this.chart.convertFromPixel({ seriesIndex: node.seriesIndex }, pointInPixel);
        console.log('点击位置的逻辑坐标:', pointInGrid);

        // 或者直接从节点的数据中获取预定义的坐标
        const nodePosition = node.data.itemStyle.origin ? node.data.itemStyle.origin : node.data;
        console.log('节点预定义的位置:', nodePosition.x, nodePosition.y);
      }
      // 确保 node 和 node.data 存在
      if (!node || !node.data ||!node.data.id) {
        console.error('点击的节点数据无效', node);
        return;
      }
      console.log('Handling click for node:', node.data);

      //菜单
      this.selectedNode = node.data;
      const pointInPixel = [node.event.offsetX, node.event.offsetY];
      const point = this.chart.convertFromPixel({ seriesIndex: node.seriesIndex }, pointInPixel);
      console.log("点击的节点point",point)
      if(point){
        console.log("dodo")
        this.menuStyle.top = `${point[1] -100 }px`; // 菜单居中
        this.menuStyle.left = `${point[0] +100 }px`; // 菜单居中
        this.showMenu = true; // 显示菜单
      }

      this.selectedNodeId = node.data.id;
    },
    executeAction(actionType) {
      console.log("执行操作:", actionType);
      this.showMenu = false; // 隐藏菜单
      // 根据选项类型执行不同的操作
      switch (actionType){
        case 'extend':
          console.log("扩展")
          const query = 'match(n:chara)-[r:`关注`]->(m:chara) where id(n)=' + this.selectedNodeId +' return n,r,m'
          this.updateChart(query);
          break;
        case 'hide':
          console.log("隐藏")
          this.hideNode(this.selectedNodeId);
          break;
        case 'cancel':
          console.log("取消")
          break;
        case 'explain':
          console.log("解释")
          // TODO:
          break;
        default:
          console.log("未知操作")
      }
    },

    hideNode(nodeId) {
      // 存储与当前节点直接相连的边
      const connectedLinks = this.chartData.links.filter(link => {
        return link.source === nodeId || link.target === nodeId;
      });

      // 获取与被删除节点相连的节点 ID
      const connectedNodeIds = new Set();

      connectedLinks.forEach(link => {
        if (link.source === nodeId) {
          connectedNodeIds.add(link.target);
        } else {
          connectedNodeIds.add(link.source);
        }
      });

      // 过滤链接数据，删除与当前节点直接相连的边
      this.chartData.links = this.chartData.links.filter(link => {
        return link.source !== nodeId && link.target !== nodeId;
      });

      // 检查连接的节点，判断是否只与被删除的节点相连
      connectedNodeIds.forEach(id => {
        const isOnlyConnectedToDeletedNode = this.chartData.links.every(link => {
          return link.source !== id && link.target !== id; // 如果该节点不会出现在任何边中
        });

        if (isOnlyConnectedToDeletedNode) {
          // 删除相连的节点，因为它只与被删除的节点相连
          this.chartData.nodes = this.chartData.nodes.filter(node => {
            return node.id !== id;
          });
        }
      });

      // 删除被删除节点
      this.chartData.nodes = this.chartData.nodes.filter(node => {
        return node.id !== nodeId;
      });

      // 更新图表
      this.setChartOption();
    },


    // 定义一个函数来根据 type 返回节点大小
    getNodeSize(type) {
      switch (type) {
        case 'Government Certified':
          return 60;
        case 'Media Certified':
          return 50;
        case 'Personal Certified':
          return 40;
        case 'Unclassified':
          return 30
        default:
          return 25; // 默认大小
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
  },
}
</script>

<style scoped>
.out_box{
  background-image: url("@/assets/Images/bk.png");
  color: #00ffff;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

  width: 100vw;
  height: 100vh;
}

.outer-card {
  background: transparent;
}

.box-card {
  background: transparent;
  border: none;
}

.menu-card {
  height: 100%;
}

.chart-container {
  width: 100%;
  height: 650px;
}W

.el-menu-item {
  background-color: transparent !important;
}

.el-menu-item:hover {
  background-color: #666666 !important;
  color: rgba(255, 255, 255, 0.8) ;
}

.el-menu-item-group__title {
  padding-left: 20px;
}
.left_menu{
  overflow-y: auto; /* 启用垂直滚动条 */
  height: 100%;
  width:100%;
}
/* 滚动条整体部分 */
.left_menu::-webkit-scrollbar {
  width: 0; /* 滚动条的宽度 */
}

.circle-menu {
  position: absolute;
  border-radius: 50%;
  background-color: lightgray; /* 圆形菜单背景色 */
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.3s ease-in-out; /* 动画效果 */
}

.menu-item {
  position: absolute;
  background-color: white;
  color: black;
  border: 1px solid gray; /* 按钮样式 */
  padding: 5px 10px;
  cursor: pointer;
}

.menu-item:nth-child(1) { top: 0; left: 50%; transform: translate(-50%, -50%); } /* 上侧 */
.menu-item:nth-child(2) { top: 50%; right: 0; transform: translate(50%, -50%); } /* 右侧 */
.menu-item:nth-child(3) { bottom: 0; left: 50%; transform: translate(-50%, 50%); } /* 下侧 */
.menu-item:nth-child(4) { top: 50%; left: 0; transform: translate(-50%, -50%); } /* 左侧 */

.menu-center {
  position: relative; /* 中间文字样式 */
  color: blue;
  font-weight: bold;
}

.dv_content{
  padding: 15px;
}

</style>