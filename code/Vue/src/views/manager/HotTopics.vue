<template>
  <div class="out_box" style="color: #00ffff;height: 100%">
    <!-- 第一行 -->
    <div class="module_box" style="height: 10vh;margin: 0">
      <div style="flex: 0 1 30%;">
        <dv-decoration-10 style="width:100%;height:50px;" />
      </div>
      <div style="flex: 0 1 40%;">
        <div style="display: flex;width: 100%;">
          <dv-decoration-8 style="width:300px;height:50px;flex:1;transform: rotateY(180deg);" />
          <dv-decoration-7 style="width:200px;height:60px;flex:1">热评话题</dv-decoration-7>
          <dv-decoration-8 style="width:300px;height:50px;flex:1" />
        </div>
      </div>
      <div style="flex: 0 1 30%;">
        <dv-decoration-10 style="width: 100%;height:50px;transform: rotateY(180deg);" />
      </div>
    </div>

    <div style="margin-bottom: 20px">
      <div style="display: flex;justify-content: center">
        <el-select v-model="selectedEvent" filterable  placeholder="选择话题" @change="changeEvent" style="width: 400px">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
        <div style="position: absolute;right: 20%">
          <div style="display: flex;justify-content: center;">
            <el-link :underline="false" type="danger" style="font-size: 20px" @click="$router.push('/homePage')">
              返回首页<i class="el-icon-d-arrow-right"/>
            </el-link>
          </div>
        </div>
      </div>
    </div>
    <div style="margin: 0 10px">
      <el-row :gutter="10" style="margin-bottom: 10px">
        <el-col :span="6">
          <div class="box">
            <h2>情感分析结果占比</h2>
            <div style="height: 300px">
              <HotTopics_charts1 :chartData="pieData"/>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="box">
            <h2>总体情感指标</h2>
            <div style="height: 300px">
              <HotTopics_charts2 :chartData="gaugeData"/>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="box">
            <div style="display: flex">
              <div style="flex: 1">
                <h2>选择情感人物</h2>
                <div style="height: 300px">
                  <div style="display: flex;color: white">
                    <h2 class="emotion_change" style="flex: 1" @click="emotionChange">正面</h2>
                    <h2 class="emotion_change" style="flex: 1" @click="emotionChange">中性</h2>
                    <h2 class="emotion_change" style="flex: 1" @click="emotionChange">负面</h2>
                  </div>
                  <div class="emotion_box">
                    <el-row v-for="(item,index) in emotions" :key="index" v-if="item.emotion===selectEmotion"
                      style="font-size: 15px;margin-left: 20px;margin-bottom: 5px">
                      <span class="emotion_inner_name">{{item.Username}}</span>
                      <el-divider/>
                    </el-row>
                  </div>
                </div>
              </div>
              <div style="flex: 1">
                <h2>情感人物详情</h2>
                <div style="height: 300px">

                </div>
              </div>
            </div>

          </div>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="12">
          <div class="box">
            <h2>舆情事件讨论热度变化图</h2>
            <div style="height: 300px">
              <HotTopics_charts3 :chartData="lineData"/>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="box">
            <h2>词云图</h2>
            <div style="height: 300px">
              <HotTopics_charts4 :chartData="wordCloudData"/>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import HotTopics_charts1 from "@/components/echarts/HotTopics/HotTopics_charts1.vue";
import HotTopics_charts2 from "@/components/echarts/HotTopics/HotTopics_charts2.vue";
import HotTopics_charts3 from "@/components/echarts/HotTopics/HotTopics_charts3.vue";
import HotTopics_charts4 from "@/components/echarts/HotTopics/HotTopics_charts4.vue";

export default {
  name: 'HotTopics',
  components: {
    HotTopics_charts1,
    HotTopics_charts2,
    HotTopics_charts3,
    HotTopics_charts4,
  },
  data() {
    return {
      datas:[],
      pieData:[],
      gaugeData:[],
      lineData:[],
      wordCloudData:[],

      selectedEvent:'modiElection',
      options: [
        {
          label:'拜登:宣布退出美国总统大选',
          value:'bidenElection',
        },
        {
          label:'拜登:确诊新冠病毒',
          value:'bidenCovid',
        },
        {
          label:'特朗普:美国总统候选人辩论日期敲定',
          value:'debateDate',
        },
        {
          label:'特朗普:遇刺未遂',
          value:'trumpAssassination',
        },
        {
          label:'普京:乌克兰战争两周年',
          value:'ukraineAnniversary',
        },
        {
          label:'普京:俄罗斯总统普京访华会晤习近平',
          value:'putinVisit',
        },
        {
          label:'莫迪:连任印度总理',
          value:'modiElection',
        },
        {
          label:'莫迪:俄乌开战以来印度总理莫迪首度访问基辅',
          value:'visit',
        },
        {
          label:'马斯克:收购推特完成',
          value:'muskTwitter',
        },
        {
          label:'马斯克:SpaceX成功发射载人飞船',
          value:'muskSpaceX',
        },
      ],
      activeTabPane:1,
      emotions:[
        {
          "Username": "安努帕姆·凯尔",
          "emotion": "正面"
        },
        {
          "Username": "比尔·盖茨",
          "emotion": "正面"
        },
        {
          "Username": "北方邦首席部长办公室",
          "emotion": "正面"
        },
        {
          "Username": "德夫·法德纳维斯",
          "emotion": "中性"
        },
        {
          "Username": "巴塞罗那俱乐部",
          "emotion": "中性"
        },
        {
          "Username": "苏雷什·雷纳",
          "emotion": "正面"
        },
        {
          "Username": "罗希特·沙玛",
          "emotion": "正面"
        },
        {
          "Username": "卡皮尔·夏尔马",
          "emotion": "中性"
        },
        {
          "Username": "金·卡戴珊",
          "emotion": "正面"
        },
        {
          "Username": "莱昂纳多·迪卡普里奥",
          "emotion": "正面"
        },
        {
          "Username": "刘易斯·汉密尔顿",
          "emotion": "正面"
        },
        {
          "Username": "曼彻斯特城",
          "emotion": "中性"
        },
        {
          "Username": "麦莉·赛勒斯",
          "emotion": "负面"
        },
        {
          "Username": "美国职业篮球联赛",
          "emotion": "中性"
        },
        {
          "Username": "塞娜·内瓦尔",
          "emotion": "正面"
        },
        {
          "Username": "国家地理旅游",
          "emotion": "正面"
        },
        {
          "Username": "奥普拉·温弗瑞",
          "emotion": "中性"
        },
        {
          "Username": "佩雷兹·希尔顿",
          "emotion": "正面"
        },
        {
          "Username": "佩雷兹·希尔顿",
          "emotion": "正面"
        },
        {
          "Username": "塞斯·罗根",
          "emotion": "正面"
        },
        {
          "Username": "西德哈特·马尔霍特拉",
          "emotion": "正面"
        },
        {
          "Username": "阿米塔布·巴强",
          "emotion": "正面"
        },
        {
          "Username": "时报电视台",
          "emotion": "正面"
        },
        {
          "Username": "阿贾伊·德乌干",
          "emotion": "正面"
        },
        {
          "Username": "阿琼·卡普尔",
          "emotion": "负面"
        },
        {
          "Username": "卡洛斯·维夫斯",
          "emotion": "中性"
        },
        {
          "Username": "吉妮莉亚·德索扎",
          "emotion": "正面"
        },
        {
          "Username": "孟买印第安人队（印度板球队）",
          "emotion": "正面"
        },
        {
          "Username": "内马尔",
          "emotion": "中性"
        },
        {
          "Username": "皮普保罗",
          "emotion": "正面"
        },
        {
          "Username": "皇家马德里",
          "emotion": "负面"
        },
        {
          "Username": "皇家马德里英文账号",
          "emotion": "中性"
        },
        {
          "Username": "赛琳娜·戈麦斯",
          "emotion": "正面"
        },
        {
          "Username": "夏奇拉",
          "emotion": "正面"
        }
      ],
      selectEmotion:'正面'
    }
  },
  mounted() {
    this.getData("modiElection");
    this.drawChart()
  },
  methods: {
    getData(event) {
      const eventSentimentData = {
        "muskSpaceX": {
          "pieData": [0.05, 0.17, 0.05, 0.7, 0, 0],  // 愤怒, 中立, 惊讶, 喜悦, 悲伤, 恐惧
          "lineData": [2300, 2500, 2800, 2600, 2400, 2700],  // 各日期的讨论热度值
          "gaugeData": [75,25],  // 正面情感和负面情感的百分比
          "wordCloudData": [
            {
              "name": "SpaceX",
              "value": 10
            },
            {
              "name": "马斯克",
              "value": 9
            },
            {
              "name": "载人飞船",
              "value": 8
            },
            {
              "name": "成功",
              "value": 7
            },
            {
              "name": "发射",
              "value": 6
            },
            {
              "name": "火星",
              "value": 5
            },
            {
              "name": "太空探索",
              "value": 5
            },
            {
              "name": "未来",
              "value": 4
            },
            {
              "name": "技术",
              "value": 4
            },
            {
              "name": "创新",
              "value": 4
            },
            {
              "name": "NASA",
              "value": 3
            },
            {
              "name": "太空旅行",
              "value": 3
            },
            {
              "name": "人类历史",
              "value": 3
            },
            {
              "name": "科学突破",
              "value": 3
            },
            {
              "name": "国际合作",
              "value": 2
            },
            {
              "name": "航天",
              "value": 3
            },
            {
              "name": "星际",
              "value": 2
            },
            {
              "name": "梦想",
              "value": 2
            },
            {
              "name": "冒险",
              "value": 2
            },
            {
              "name": "进步",
              "value": 2
            },
            {
              "name": "太空站",
              "value": 2
            },
            {
              "name": "宇航员",
              "value": 2
            },
            {
              "name": "技术革新",
              "value": 2
            },
            {
              "name": "成功发射",
              "value": 2
            },
            {
              "name": "地球",
              "value": 1
            },
            {
              "name": "太空使命",
              "value": 2
            },
            {
              "name": "星际飞行",
              "value": 1
            },
            {
              "name": "探索未来",
              "value": 2
            },
            {
              "name": "航天科技",
              "value": 2
            },
            {
              "name": "人类梦想",
              "value": 2
            }
          ]
        },
        "muskTwitter": {
          "pieData": [0, 0.2, 0, 0.8, 0, 0],  // 愤怒, 中立, 惊讶, 喜悦, 悲伤, 恐惧
          "lineData": [2100, 2400, 2600, 2300, 2500, 2800],  // 各日期的讨论热度值
          "gaugeData": [80, 20],  // 正面情感和负面情感的百分比
          "wordCloudData": [
            {
              "name": "马斯克",
              "value": 10
            },
            {
              "name": "推特",
              "value": 9
            },
            {
              "name": "收购",
              "value": 8
            },
            {
              "name": "社交媒体",
              "value": 7
            },
            {
              "name": "科技",
              "value": 6
            },
            {
              "name": "创新",
              "value": 5
            },
            {
              "name": "言论自由",
              "value": 5
            },
            {
              "name": "影响力",
              "value": 4
            },
            {
              "name": "企业家",
              "value": 4
            },
            {
              "name": "股价",
              "value": 3
            },
            {
              "name": "科技行业",
              "value": 3
            },
            {
              "name": "市场波动",
              "value": 3
            },
            {
              "name": "科技巨头",
              "value": 2
            },
            {
              "name": "交易",
              "value": 3
            },
            {
              "name": "商业",
              "value": 2
            },
            {
              "name": "未来发展",
              "value": 2
            },
            {
              "name": "平台改革",
              "value": 3
            },
            {
              "name": "推特变革",
              "value": 2
            },
            {
              "name": "社交网络",
              "value": 2
            },
            {
              "name": "投资",
              "value": 2
            },
            {
              "name": "市场反应",
              "value": 2
            },
            {
              "name": "技术变革",
              "value": 2
            },
            {
              "name": "用户增长",
              "value": 2
            },
            {
              "name": "股东",
              "value": 1
            },
            {
              "name": "马斯克政策",
              "value": 1
            },
            {
              "name": "推特管理",
              "value": 1
            },
            {
              "name": "商业战略",
              "value": 1
            },
            {
              "name": "全球市场",
              "value": 1
            },
            {
              "name": "用户隐私",
              "value": 1
            },
            {
              "name": "数据安全",
              "value": 1
            }
          ]

        },
        'visit': {
          pieData: [0, 0.625, 0.25, 0.125, 0, 0],
          lineData: [2200, 2400, 2700, 2600, 2800, 2900],
          gaugeData: [37.5, 0],
          "wordCloudData": [
            {
              "name": "莫迪",
              "value": 10
            },
            {
              "name": "基辅",
              "value": 9
            },
            {
              "name": "印度总理",
              "value": 8
            },
            {
              "name": "乌克兰战争",
              "value": 7
            },
            {
              "name": "俄乌冲突",
              "value": 6
            },
            {
              "name": "外交访问",
              "value": 5
            },
            {
              "name": "国际关系",
              "value": 5
            },
            {
              "name": "和平努力",
              "value": 4
            },
            {
              "name": "外交政策",
              "value": 4
            },
            {
              "name": "全球反应",
              "value": 3
            },
            {
              "name": "普京",
              "value": 3
            },
            {
              "name": "拜登",
              "value": 3
            },
            {
              "name": "泽连斯基",
              "value": 3
            },
            {
              "name": "政治局势",
              "value": 3
            },
            {
              "name": "地缘政治",
              "value": 2
            },
            {
              "name": "印度",
              "value": 2
            },
            {
              "name": "军事合作",
              "value": 2
            },
            {
              "name": "经济影响",
              "value": 2
            },
            {
              "name": "人道主义",
              "value": 1
            },
            {
              "name": "国际合作",
              "value": 2
            },
            {
              "name": "莫迪外交",
              "value": 1
            },
            {
              "name": "印度外交",
              "value": 1
            },
            {
              "name": "冲突调停",
              "value": 2
            },
            {
              "name": "欧洲安全",
              "value": 1
            }
          ]
        },
        'putinVisit': {
          pieData: [0.05, 0.52, 0.05, 0.35, 0, 0],
          lineData: [2400, 2600, 2800, 2700, 2500, 2900],
          gaugeData: [40, 5],
          "wordCloudData": [
            {
              "name": "普京",
              "value": 10
            },
            {
              "name": "访华",
              "value": 9
            },
            {
              "name": "习近平",
              "value": 8
            },
            {
              "name": "中俄关系",
              "value": 7
            },
            {
              "name": "国际合作",
              "value": 6
            },
            {
              "name": "地缘政治",
              "value": 5
            },
            {
              "name": "外交访问",
              "value": 5
            },
            {
              "name": "经济合作",
              "value": 4
            },
            {
              "name": "安全合作",
              "value": 4
            },
            {
              "name": "乌克兰战争",
              "value": 4
            },
            {
              "name": "全球战略",
              "value": 3
            },
            {
              "name": "多边合作",
              "value": 3
            },
            {
              "name": "中国",
              "value": 3
            },
            {
              "name": "俄罗斯",
              "value": 3
            },
            {
              "name": "国际社会",
              "value": 2
            },
            {
              "name": "和平倡议",
              "value": 2
            },
            {
              "name": "能源合作",
              "value": 2
            },
            {
              "name": "军事合作",
              "value": 2
            },
            {
              "name": "联合声明",
              "value": 2
            },
            {
              "name": "全球影响",
              "value": 2
            },
            {
              "name": "国家安全",
              "value": 1
            },
            {
              "name": "领导人会晤",
              "value": 1
            },
            {
              "name": "外交政策",
              "value": 1
            },
            {
              "name": "中俄友谊",
              "value": 1
            }
          ]

        },
        'ukraineAnniversary': {
          pieData: [0.12, 0.5, 0.31, 0.06, 0, 0],
          lineData: [2600, 2800, 2900, 2700, 2500, 3000],
          gaugeData: [37, 12],
          "wordCloudData": [
            {
              "name": "普京",
              "value": 10
            },
            {
              "name": "乌克兰战争",
              "value": 9
            },
            {
              "name": "两周年",
              "value": 8
            },
            {
              "name": "战争影响",
              "value": 7
            },
            {
              "name": "地缘政治",
              "value": 6
            },
            {
              "name": "国际制裁",
              "value": 5
            },
            {
              "name": "军事行动",
              "value": 5
            },
            {
              "name": "经济制裁",
              "value": 4
            },
            {
              "name": "俄罗斯",
              "value": 4
            },
            {
              "name": "乌克兰",
              "value": 4
            },
            {
              "name": "和平谈判",
              "value": 3
            },
            {
              "name": "国际关系",
              "value": 3
            },
            {
              "name": "全球反应",
              "value": 3
            },
            {
              "name": "普京演讲",
              "value": 3
            },
            {
              "name": "军事合作",
              "value": 2
            },
            {
              "name": "外交压力",
              "value": 2
            },
            {
              "name": "战争升级",
              "value": 2
            },
            {
              "name": "北约",
              "value": 2
            },
            {
              "name": "全球影响",
              "value": 2
            },
            {
              "name": "能源危机",
              "value": 1
            },
            {
              "name": "核威胁",
              "value": 1
            },
            {
              "name": "人道主义",
              "value": 1
            },
            {
              "name": "西方制裁",
              "value": 1
            },
            {
              "name": "欧洲安全",
              "value": 1
            }
          ]

        },
        'trumpAssassination': {
          pieData: [0.08, 0.28, 0.16, 0.44, 0, 0.04],
          lineData: [2700, 2900, 3000, 2800, 2600, 2500],
          gaugeData: [60, 12],
          "wordCloudData": [
            {
              "name": "特朗普",
              "value": 10
            },
            {
              "name": "遇刺未遂",
              "value": 9
            },
            {
              "name": "美国政治",
              "value": 8
            },
            {
              "name": "安全威胁",
              "value": 7
            },
            {
              "name": "总统保护",
              "value": 6
            },
            {
              "name": "政治阴谋",
              "value": 5
            },
            {
              "name": "国际反应",
              "value": 5
            },
            {
              "name": "保守派",
              "value": 4
            },
            {
              "name": "拜登",
              "value": 4
            },
            {
              "name": "国家安全",
              "value": 3
            },
            {
              "name": "媒体报道",
              "value": 3
            },
            {
              "name": "政治分歧",
              "value": 3
            },
            {
              "name": "社会不安",
              "value": 3
            },
            {
              "name": "法治",
              "value": 2
            },
            {
              "name": "公共安全",
              "value": 2
            },
            {
              "name": "选举影响",
              "value": 2
            },
            {
              "name": "民众反应",
              "value": 2
            },
            {
              "name": "警察行动",
              "value": 2
            },
            {
              "name": "国家保护",
              "value": 2
            },
            {
              "name": "刺杀企图",
              "value": 1
            },
            {
              "name": "政府应对",
              "value": 1
            },
            {
              "name": "法律措施",
              "value": 1
            },
            {
              "name": "社会反响",
              "value": 1
            }
          ]

        },
        'debateDate': {
          pieData: [0, 0.6, 0.1, 0.3, 0, 0],
          lineData: [2300, 2500, 2700, 2400, 2600, 2800],
          gaugeData: [40, 0],
          "wordCloudData": [
            {
              "name": "特朗普",
              "value": 10
            },
            {
              "name": "总统候选人",
              "value": 9
            },
            {
              "name": "辩论日期",
              "value": 8
            },
            {
              "name": "美国大选",
              "value": 7
            },
            {
              "name": "政治辩论",
              "value": 6
            },
            {
              "name": "选举",
              "value": 5
            },
            {
              "name": "拜登",
              "value": 5
            },
            {
              "name": "选民关注",
              "value": 4
            },
            {
              "name": "候选人立场",
              "value": 4
            },
            {
              "name": "媒体报道",
              "value": 3
            },
            {
              "name": "选举策略",
              "value": 3
            },
            {
              "name": "选民影响",
              "value": 3
            },
            {
              "name": "竞选活动",
              "value": 3
            },
            {
              "name": "政治分歧",
              "value": 2
            },
            {
              "name": "公众舆论",
              "value": 2
            },
            {
              "name": "选举焦点",
              "value": 2
            },
            {
              "name": "政策讨论",
              "value": 2
            },
            {
              "name": "辩论准备",
              "value": 2
            },
            {
              "name": "民意调查",
              "value": 2
            },
            {
              "name": "候选人表现",
              "value": 2
            },
            {
              "name": "选举结果",
              "value": 1
            },
            {
              "name": "总统竞选",
              "value": 1
            },
            {
              "name": "选举争议",
              "value": 1
            },
            {
              "name": "党派斗争",
              "value": 1
            }
          ]

        },
        'bidenElection': {
          pieData: [0.07, 0.46, 0.15, 0.23, 0.07, 0],
          lineData: [2500, 2700, 2900, 2600, 2800, 3000],
          gaugeData: [38, 14],
          "wordCloudData": [
            {
              "name": "拜登",
              "value": 10
            },
            {
              "name": "退出大选",
              "value": 9
            },
            {
              "name": "美国总统",
              "value": 8
            },
            {
              "name": "大选",
              "value": 7
            },
            {
              "name": "特朗普",
              "value": 6
            },
            {
              "name": "政治局势",
              "value": 5
            },
            {
              "name": "选民",
              "value": 5
            },
            {
              "name": "民主党",
              "value": 4
            },
            {
              "name": "选举策略",
              "value": 4
            },
            {
              "name": "竞选活动",
              "value": 3
            },
            {
              "name": "总统竞选",
              "value": 3
            },
            {
              "name": "政党斗争",
              "value": 3
            },
            {
              "name": "媒体反应",
              "value": 3
            },
            {
              "name": "选民反应",
              "value": 3
            },
            {
              "name": "大选争议",
              "value": 2
            },
            {
              "name": "政坛变动",
              "value": 2
            },
            {
              "name": "竞选团队",
              "value": 2
            },
            {
              "name": "副总统候选人",
              "value": 2
            },
            {
              "name": "党内竞争",
              "value": 2
            },
            {
              "name": "选举投票",
              "value": 2
            },
            {
              "name": "总统辩论",
              "value": 2
            },
            {
              "name": "选民焦虑",
              "value": 1
            },
            {
              "name": "政党策略",
              "value": 1
            }
          ]

        },
        'bidenCovid': {
          pieData: [0, 0.8, 0, 0.2, 0, 0],
          lineData: [2100, 2400, 2600, 2300, 2500, 2700],
          gaugeData: [20, 0],
          "wordCloudData": [
            {
              "name": "拜登",
              "value": 10
            },
            {
              "name": "确诊新冠",
              "value": 9
            },
            {
              "name": "总统健康",
              "value": 8
            },
            {
              "name": "新冠疫情",
              "value": 7
            },
            {
              "name": "疫情影响",
              "value": 6
            },
            {
              "name": "公众反应",
              "value": 5
            },
            {
              "name": "医疗",
              "value": 5
            },
            {
              "name": "白宫",
              "value": 4
            },
            {
              "name": "政府回应",
              "value": 4
            },
            {
              "name": "媒体报道",
              "value": 3
            },
            {
              "name": "疫苗",
              "value": 3
            },
            {
              "name": "隔离",
              "value": 3
            },
            {
              "name": "康复",
              "value": 3
            },
            {
              "name": "政治影响",
              "value": 3
            },
            {
              "name": "公众健康",
              "value": 2
            },
            {
              "name": "疫情控制",
              "value": 2
            },
            {
              "name": "总统工作",
              "value": 2
            },
            {
              "name": "副总统",
              "value": 2
            },
            {
              "name": "医疗团队",
              "value": 2
            },
            {
              "name": "公共卫生",
              "value": 1
            },
            {
              "name": "舆论反应",
              "value": 1
            },
            {
              "name": "病情",
              "value": 1
            },
            {
              "name": "国家安全",
              "value": 1
            }
          ]

        },
        "modiElection": {
          "pieData": [0.15, 0.35, 0.05, 0.25, 0.10, 0.10],  // 愤怒, 中立, 惊讶, 喜悦, 悲伤, 恐惧
          "lineData": [1200, 1500, 1800, 1600, 1900, 2000],  // 各日期的讨论热度值
          "gaugeData": [55, 45],  // 正面情感和负面情感的百分比
          "wordCloudData": [
            {
              "name": "莫迪",
              "value": 10
            },
            {
              "name": "连任",
              "value": 8
            },
            {
              "name": "印度",
              "value": 9
            },
            {
              "name": "选举",
              "value": 7
            },
            {
              "name": "共产主义",
              "value": 6
            },
            {
              "name": "独裁者",
              "value": 5
            },
            {
              "name": "强人",
              "value": 5
            },
            {
              "name": "分享",
              "value": 4
            },
            {
              "name": "政治",
              "value": 4
            },
            {
              "name": "支持",
              "value": 4
            },
            {
              "name": "民主",
              "value": 4
            },
            {
              "name": "总理",
              "value": 3
            },
            {
              "name": "拜登",
              "value": 3
            },
            {
              "name": "特朗普",
              "value": 3
            },
            {
              "name": "经济",
              "value": 3
            },
            {
              "name": "政府",
              "value": 3
            },
            {
              "name": "胜利",
              "value": 3
            },
            {
              "name": "社会",
              "value": 3
            },
            {
              "name": "演讲",
              "value": 2
            },
            {
              "name": "抗议",
              "value": 2
            },
            {
              "name": "疫情",
              "value": 2
            },
            {
              "name": "投票",
              "value": 2
            },
            {
              "name": "结果",
              "value": 2
            },
            {
              "name": "改革",
              "value": 2
            },
            {
              "name": "民众",
              "value": 2
            },
            {
              "name": "强权",
              "value": 2
            },
            {
              "name": "权力",
              "value": 2
            },
            {
              "name": "分歧",
              "value": 2
            },
            {
              "name": "国际关系",
              "value": 1
            },
            {
              "name": "影响",
              "value": 1
            },
            {
              "name": "反对派",
              "value": 1
            },
            {
              "name": "未来",
              "value": 1
            },
            {
              "name": "稳定",
              "value": 1
            }
          ]
        },
      };
      this.datas = eventSentimentData[event]
      this.pieData=this.datas.pieData
      this.lineData=this.datas.lineData
      this.gaugeData=this.datas.gaugeData
      this.wordCloudData=this.datas.wordCloudData
    },
    drawChart(){

    },
    changeEvent(){
      this.getData(this.selectedEvent)
    },
    emotionChange(param){
      console.log("param",param.target.innerText)
      this.selectEmotion=param.target.innerText
    },

    getActiveTabColor(){
      if(this.activeTabPane===1){
        return "red"
      }
      else if(this.activeTabPane===2){
        return "blue"
      }
      else if(this.activeTabPane===3){
        return "blue"
      }
      else {
        return "white"
      }
    }
  },

}
</script>

<style scoped>
body {
  margin: 0;
  padding: 0;
  background-color: #0d0d0d;
  color: #00ffff;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
.out_box{
  background-image: url("@/assets/Images/bk.png");
}
.box {
  background-color: transparent;
  border: 1px solid #00ffff;
  padding: 10px;
  position: relative;
  box-shadow: 0 0 15px #00ffff;
  border-radius: 10px;
  overflow: hidden;
}
.box h2 {
  margin: 0;
  font-size: 18px;
  text-align: center;
  padding: 10px;
}


/deep/ .el-input__inner{
  background-color: white;
}

.emotion_change:hover{
  color: darkorange;
  cursor: pointer;
}
.emotion_inner_name:hover{
  color: darkorange;
  cursor: pointer;
}
.emotion_box{
  overflow-y: auto; /* 启用垂直滚动条 */
  height: 256px;
}
/* 滚动条整体部分 */
.emotion_box::-webkit-scrollbar {
  width: 0; /* 滚动条的宽度 */
}
/deep/.el-divider{
  margin: 0 !important;
  color: lightgrey !important;
  opacity: 0.3 !important;
}
</style>
