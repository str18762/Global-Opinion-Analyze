<template>
  <div ref="chart" style="width: 100%; height: 100%;"></div>
</template>

<script>
import neo4j from "neo4j-driver";
import * as echarts from "echarts";

export default {
  name: "EventKeyChara",
  props: {
    event: {
      type: String,
      required: false,
      default: () => []
    },
  },
  data() {
    return {
      chart: null,
      eventName:'美国大选',
      initQuery:'MATCH (n:event)-[r:effect]->(m:eventKeyChara) where n.name=',
      chartData:{
        nodes:[],
        links:[],
      },
    }
  },
  mounted() {
    this.chart = echarts.init(this.$refs.chart);
    this.chart.on('click', this.handleNodeClick);
    this.eventName = this.event ? this.event : '美国大选';
    this.initQuery=this.initQuery+'"'+this.eventName+'" RETURN n,r,m'
    this.updateChart(this.initQuery);
  },
  methods: {
    async runCypherQuery (query,params){
      const driver = neo4j.driver(
          process.env.VUE_APP_NEO4J_URI,
          neo4j.auth.basic(process.env.VUE_APP_NEO4J_USER,process.env.VUE_APP_NEO4J_PASSWORD)
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
            type: node1.properties.type,
          });
        }
        if (node2Index === -1) {
          console.log("node2Properties",node2.properties)
          nodes.push({
            id: node2.identity.toString(),
            name: node2.properties.name,
            emotion:node2.properties.emotion,
            influence:node2.properties.influence.low,
            type: node2.properties.type,
          });
        }
        links.push({
          source: node1.identity.toString(),
          target: node2.identity.toString(),
          name: link.name
        });
      }
      return {
        nodes,
        links
      };
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
    async updateChart2(query) {
      let data = await this.queryAndTransformData(query);
      this.chartData = data;
      this.setChartOption()
    },
    setChartOption(){
      let itemStyle_event ={
          borderColor: '#b158ff',
          borderWidth: 6,
          shadowBlur: 20,
          shadowColor: '#b158ff',
          color: '#001c43',
      };
      let itemStyle_chara ={
          borderColor: '#83e0ff',
          borderWidth: 6,
          shadowBlur: 20,
          shadowColor: '#83e0ff',
          color: '#001c43',
      };

      const option = {
        tooltip: {
          trigger: 'item',
          formatter: function (params) {
            // 自定义提示框内容
            if (params.dataType==='node') {
              if (params.data.type==='event') {
                return `<strong>事件名:${params.data.name}</strong><br/>`;
              }
              else if (params.data.type==='chara') {
                return `<strong>关键人物名:${params.data.name}</strong><br/>
                        影响力: ${params.data.influence}<br/>
                        情感:${params.data.emotion}<br/>`;
              }

            }
          }
        },
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        color:['#83e0ff','#b158ff'],
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
              influence:node.influence,
              emotion:node.emotion,
              type:node.type,
              draggable: true,
              category: node.type === 'event'? 0 : 1,
              symbolSize: node.type === 'event'? 100 : node.influence/10,
              itemStyle: node.type === 'event'? itemStyle_event : itemStyle_chara,
              emphasis: {
                itemStyle: {
                  shadowBlur: 40,
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
                show: true,
                position: "top",
                textStyle: {
                  fontSize: 20,
                  color: '#0ff'
                }
              }
            },
            lineStyle: {
              normal: {
                opacity: 0.9,
                width: 5,
                curveness: 0
              }
            },
            categories:[
              {name: '0'},
              {name: '1'},
            ],
          }
        ]
      };
      this.chart.setOption(option);
    },
    handleNodeClick(node) {
      if (!node || !node.data ||!node.data.id) {
        console.error('点击的节点数据无效', node);
        return;
      }
      console.log('Handling click for node:', node.data);
      this.$emit("nodeClick", node.data.name);
    },
  },
  watch: {
    event(newVal, oldVal) {
      let query = 'MATCH (n:event)-[r:effect]->(m:eventKeyChara) where n.name="'+this.event+'" RETURN n,r,m'
      this.updateChart2(query)
    },
  }
}
</script>



<style scoped>

</style>