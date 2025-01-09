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
          <dv-decoration-7 style="width:200px;height:60px;flex:1" :style="{'flex': 1,'font-size': Math.round(koiParams.screen.screenWidth/70) + 'px'}">热评话题</dv-decoration-7>
          <dv-decoration-8 style="width:300px;height:50px;flex:1" />
        </div>

      </div>
      <div style="flex: 0 1 30%;">
        <dv-decoration-10 style="width: 100%;height:50px;transform: rotateY(180deg);" />
      </div>
    </div>

    <!--body-->
    <div style="height: 90vh">
      <el-row style="height: 100%">
        <!--左侧-->
        <el-col :span="6" style="height: 100%">
          <div style="height: 60%;padding: 5px">
            <div class="box">
              <h2>情感分析结果占比</h2>
              <div style="height: 80%">
                <HotTopics_charts1 :chartData="pieData"/>
              </div>
            </div>
          </div>

          <div style="height: 40%;padding: 5px">
            <div class="box">
              <h2>舆情事件讨论热度变化图</h2>
              <div style="height: 100%">
                <HotTopics_charts3 :chartData="lineData"/>
              </div>
            </div>
          </div>

        </el-col>
        <!--中间-->
        <el-col :span="12" style="height: 100%">

          <div style="height: 20%;padding: 5px">
            <div class="box">
              <div style="height: 50%;display: flex;justify-content: center">
                <el-select v-model="selectedEvent" filterable  placeholder="选择话题" @change="changeEvent" style="width: 50%">
                  <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
                </el-select>
                <div style="position: absolute;right: 5%">
                  <div style="display: flex;justify-content: center;">
                    <el-link :underline="false" type="danger" style="font-size: 20px" @click="$router.push('/homePage')">
                      返回首页<i class="el-icon-d-arrow-right"/>
                    </el-link>
                  </div>
                </div>
              </div>
              <div style="height: 50%;display: flex;">
                <el-button class="trans_button" style="flex: 1">舆情分析</el-button>
                <el-button class="trans_button" style="flex: 1">舆情预测</el-button>
                <el-button class="trans_button" style="flex: 1">舆情预警</el-button>
                <el-button class="trans_button" style="flex: 1;color: orangered">中国特色传播</el-button>
              </div>
            </div>
          </div>

          <div style="height: 30%;padding: 5px">
            <div class="box">
              <h2>舆情事件相关新闻</h2>
              <div style="height: 100%">
              </div>
            </div>
          </div>
          <div style="height: 50%;padding: 5px">
            <div class="box">
              <h2>词云图</h2>
              <div style="height: 80%">
                <HotTopics_charts4 :chartData="wordCloudData"/>
              </div>
            </div>
          </div>
        </el-col>
        <!--右侧-->
        <el-col :span="6" style="height: 100%">
          <div style="height: 100%;padding: 5px">
            <div class="box">
              <div style="height: 100%">
                <div style="height: 50%">
                  <h2>选择情感人物</h2>
                  <div style="height: 300px">
                    <div style="display: flex;color: white">
                      <h2 class="emotion_change" style="flex: 1" @click="emotionChange">正面</h2>
                      <h2 class="emotion_change" style="flex: 1" @click="emotionChange">中性</h2>
                      <h2 class="emotion_change" style="flex: 1" @click="emotionChange">负面</h2>
                    </div>
                    <div class="emotion_box">
                      <el-row v-for="(item,index) in emotions" :key="index" v-if="item.emotion===selectEmotion"
                              style="font-size: 15px;margin-bottom: 5px">
                        <span class="emotion_inner_name">{{item.Username}}</span>
                        <el-divider/>
                      </el-row>
                    </div>
                  </div>
                </div>
                <div style="height: 50%;margin-top: 20px">
                  <h2>情感人物详情</h2>
                  <div style="height: 100%">

                  </div>
                </div>
              </div>

            </div>

          </div>

        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import EmotionPie from "@/components/echarts/HotTopics/EmotionPie.vue";
import HotTopics_charts2 from "@/components/echarts/HotTopics/HotTopics_charts2.vue";
import LineChange from "@/components/echarts/HotTopics/LineChange.vue";
import WordCloud from "@/components/echarts/HotTopics/WordCloud.vue";

export default {
  name: 'HotTopics',
  components: {
    HotTopics_charts1: EmotionPie,
    HotTopics_charts2,
    HotTopics_charts3: LineChange,
    HotTopics_charts4: WordCloud,
  },
  data() {
    return {
      koiParams :{
        // 定时任务对象
        timer: {
          // 时间
          dateTimer: null,
          // 适应浏览器
          koiTimer: null,
          // Loading定时器
          loadingTimer: null
        },
        // 时间参数对象
        dateParams: {
          dateDay: null,
          dateYear: null,
          dateWeek: null
        },
        screen: {
          // 获取浏览器可视区域高度（包含滚动条）、
          // 获取浏览器可视区域高度（不包含工具栏高度）、
          // 获取body的实际高度  (三个都是相同，兼容性不同的浏览器而设置的)
          screenHeight: window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight,
          screenWidth: window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth
        },
        height: {
          YHLeftOne: null,
          YHLeftTwo: null,
          YHCenterOne: null,
          YHCenterTwo: null,
          YHCenterThree: null,
          YHRightOne: null,
          YHRightTwo: null,
          YHRightThree: null
        }
      },


      datas:[],
      pieData:[],
      gaugeData:[],
      lineData:[],
      wordCloudData:[],

      selectedEvent:'美国大选',
      options: [
        {
          label:'美国大选',
          value:'美国大选',
        },
        {
          label:'韩国总统尹锡悦被捕',
          value:'韩国总统尹锡悦被捕',
        },
        {
          label:'以色列空袭黎巴嫩',
          value:'以色列空袭黎巴嫩',
        },
        {
          label:'spaceX完成首次“筷子夹火箭”',
          value:'spaceX完成首次“筷子夹火箭”',
        },
        {
          label:'法国四换总理',
          value:'法国四换总理',
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
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenHeight, false);
    // 页面大小改变时触发
    window.addEventListener('resize',this.getScreenWidth, false);


    this.getData(this.selectedEvent);
    this.drawChart()
  },
  methods: {
    // 获取浏览器高度进行自适应
    getScreenHeight(){
      let screenHeight = this.koiParams.screen.screenHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
      // 四舍五入取整数
      this.koiParams.height.YHLeftOne = Math.round(screenHeight * 0.46);
      this.koiParams.height.YHLeftTwo = Math.round(screenHeight * 0.46);
      this.koiParams.height.YHCenterOne= Math.round(screenHeight * 0.18);
      this.koiParams.height.YHCenterTwo = Math.round(screenHeight * 0.37);
      this.koiParams.height.YHCenterThree = Math.round(screenHeight * 0.37);
      this.koiParams.height.YHRightOne = Math.round(screenHeight * 0.3);
      this.koiParams.height.YHRightTwo = Math.round(screenHeight * 0.31);
      this.koiParams.height.YHRightThree = Math.round(screenHeight * 0.31);
      // console.log(screenHeight +"-"+ Math.round(percentHThirty) +"-"+ Math.round(percentHForty));
    },
    // 字体大小根据宽度自适应
    getScreenWidth(){
      const screenWidth = this.koiParams.screen.screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
      console.log("hh-"+screenWidth+"-"+this.koiParams.screen.screenHeight);
    },

    getData(event) {
      const eventSentimentData = {
        '美国大选': {
          pieData: [31, 1, 0, 184, 9, 73],
          lineData: [2500, 2700, 2900, 2600, 2800, 3000],
          gaugeData: [38, 14],
          "wordCloudData": [
            {"name": "特朗普", "value": 1},
            {"name": "我们", "value": 0.9705882352941176},
            {"name": "美国", "value": 0.9264705882352941},
            {"name": "唐纳德·特朗普", "value": 0.6764705882352942},
            {"name": "投票", "value": 0.5220588235294118},
            {"name": "他们", "value": 0.49264705882352945},
            {"name": "一个", "value": 0.47058823529411764},
            {"name": "选举", "value": 0.35294117647058826},
            {"name": "美国总统", "value": 0.3235294117647059},
            {"name": "特朗普总统", "value": 0.3014705882352941},
            {"name": "支持", "value": 0.27205882352941173},
            {"name": "没有", "value": 0.25},
            {"name": "realDonaldc", "value": 0.2794117647058822},
            {"name": "结果", "value": 0.21323529411764707},
            {"name": "现在", "value": 0.21323529411764707},
            {"name": "正在", "value": 0.20588235294117647},
            {"name": "trump", "value": 0.20588235294117647},
            {"name": "国家", "value": 0.1838235294117647},
            {"name": "美国大选", "value": 0.1838235294117647},
            {"name": "总统选举", "value": 0.1838235294117647},
            {"name": "影响", "value": 0.17647058823529413},
            {"name": "如果", "value": 0.17647058823529413},
            {"name": "赢得", "value": 0.17647058823529413},
            {"name": "印度", "value": 0.17647058823529413},
            {"name": "投票特朗普", "value": 0.17647058823529413},
            {"name": "胜利", "value": 0.16911764705882353},
            {"name": "这是", "value": 0.16911764705882353},
            {"name": "世界", "value": 0.16911764705882353},
            {"name": "这个", "value": 0.16911764705882353},
            {"name": "成为", "value": 0.16176470588235293},
            {"name": "知道", "value": 0.16176470588235293},
            {"name": "已经", "value": 0.15441176470588235},
            {"name": "获胜", "value": 0.15441176470588235},
            {"name": "因为", "value": 0.14705882352941176},
            {"name": "需要", "value": 0.14705882352941176},
            {"name": "今天", "value": 0.13970588235294117},
            {"name": "未来", "value": 0.13235294117647059},
            {"name": "如何", "value": 0.125},
            {"name": "看到", "value": 0.125},
            {"name": "刚刚", "value": 0.125},
            {"name": "bit.ly", "value": 0.125},
            {"name": "什么", "value": 0.11764705882352941},
            {"name": "不会", "value": 0.11764705882352941},
            {"name": "不是", "value": 0.11764705882352941},
            {"name": "上帝保佑", "value": 0.11764705882352941},
            {"name": "哈里斯", "value": 0.11029411764705882},
            {"name": "这里", "value": 0.11029411764705882},
            {"name": "选择", "value": 0.11029411764705882},
            {"name": "通过", "value": 0.11029411764705882},
            {"name": "可以", "value": 0.11029411764705882}
          ]
        },
        '韩国总统尹锡悦被捕': {
          pieData: [9, 0, 2, 27, 4, 35],
          lineData: [2100, 2400, 2600, 2300, 2500, 2700],
          gaugeData: [20, 0],
          "wordCloudData": [
            {"name": "韩国", "value": 1},
            {"name": "弹劾", "value": 0.3680555555555556},
            {"name": "韩国 总统", "value": 0.3472222222222222},
            {"name": "发出 逮捕", "value": 0.3125},
            {"name": "总统 尹锡悦", "value": 0.2847222222222222},
            {"name": "调查 人员", "value": 0.2638888888888889},
            {"name": "弹劾 总统", "value": 0.2152777777777778},
            {"name": "要求", "value": 0.20833333333333334},
            {"name": "接受 讯问", "value": 0.2013888888888889},
            {"name": "停职 总统", "value": 0.19444444444444445},
            {"name": "尹锡悦 总统", "value": 0.16666666666666666},
            {"name": "总统", "value": 0.15972222222222223},
            {"name": "实施 戒严", "value": 0.15972222222222223},
            {"name": "总统 发出", "value": 0.15277777777777778},
            {"name": "出庭 接受", "value": 0.14583333333333334},
            {"name": "调查", "value": 0.1388888888888889},
            {"name": "视频", "value": 0.1388888888888889},
            {"name": "罢免", "value": 0.13194444444444444},
            {"name": "议会", "value": 0.13194444444444444},
            {"name": "宪法法院", "value": 0.13194444444444444},
            {"name": "计划", "value": 0.125},
            {"name": "投票", "value": 0.125},
            {"name": "逮捕", "value": 0.125},
            {"name": "没有", "value": 0.125},
            {"name": "YouTube", "value": 0.125},
            {"name": "朝鲜", "value": 0.125},
            {"name": "宣布 戒严", "value": 0.125},
            {"name": "韩国 调查", "value": 0.125},
            {"name": "指控", "value": 0.11805555555555556},
            {"name": "通过", "value": 0.11805555555555556},
            {"name": "new", "value": 0.11805555555555556},
            {"name": "逮捕 尹锡悦", "value": 0.11805555555555556},
            {"name": "south korea", "value": 0.11805555555555556},
            {"name": "尹锡悦", "value": 0.1111111111111111},
            {"name": "逮捕令", "value": 0.1111111111111111},
            {"name": "面临", "value": 0.1111111111111111},
            {"name": "youtu", "value": 0.1111111111111111},
            {"name": "联合 调查", "value": 0.1111111111111111},
            {"name": "代理 总统", "value": 0.1111111111111111},
            {"name": "拒绝", "value": 0.10416666666666667},
            {"name": "他们", "value": 0.10416666666666667},
            {"name": "警方", "value": 0.10416666666666667},
            {"name": "dlvr", "value": 0.10416666666666667},
            {"name": "寻求 逮捕", "value": 0.10416666666666667},
            {"name": "法院", "value": 0.09722222222222222},
            {"name": "阅读", "value": 0.09722222222222222},
            {"name": "政治", "value": 0.09722222222222222},
            {"name": "举行", "value": 0.09027777777777778},
            {"name": "戒严令", "value": 0.09027777777777778},
            {"name": "正在", "value": 0.09027777777777778}
          ]
        },
        '以色列空袭黎巴嫩': {
          pieData: [15, 3, 6, 58, 38, 31],
          lineData: [2300, 2500, 2700, 2400, 2600, 2800],
          gaugeData: [40, 0],
          "wordCloudData": [
            {"name": "以色列", "value": 1},
            {"name": "黎巴嫩", "value": 0.627078389261745},
            {"name": "真主党", "value": 0.4655581977502661},
            {"name": "袭击", "value": 0.2969121115251773},
            {"name": "黎巴嫩 南", "value": 0.2921615251773049},
            {"name": "停火", "value": 0.2446558197750266},
            {"name": "他们", "value": 0.21852731092436977},
            {"name": "以色列 黎", "value": 0.2090261282051282},
            {"name": "空袭", "value": 0.15914485564485565},
            {"name": "停火协议", "value": 0.15679958762886597},
            {"name": "正在", "value": 0.1377672222222222},
            {"name": "地区", "value": 0.1377672222222222},
            {"name": "一个", "value": 0.1306413301306413},
            {"name": "加沙", "value": 0.12826603773584906},
            {"name": "叙利亚", "value": 0.12589073446327688},
            {"name": "现在", "value": 0.12351545098039216},
            {"name": "没有", "value": 0.11638955056179775},
            {"name": "贝鲁特", "value": 0.11401425650793493},
            {"name": "继续", "value": 0.11163895505617977},
            {"name": "我们", "value": 0.1021377672222222},
            {"name": "进行", "value": 0.09263655913978495},
            {"name": "袭击 黎巴嫩", "value": 0.09026128205128205},
            {"name": "以色列国防军", "value": 0.08788598574349207},
            {"name": "平民", "value": 0.08788598574349207},
            {"name": "报道", "value": 0.08788598574349207},
            {"name": "战争", "value": 0.08551068702290076},
            {"name": "美国", "value": 0.07838479411764706},
            {"name": "伊朗", "value": 0.07838479411764706},
            {"name": "黎巴嫩 停", "value": 0.07600950413223144},
            {"name": "发生", "value": 0.07363420041237114},
            {"name": "表示", "value": 0.07363420041237114},
            {"name": "以色列 空", "value": 0.07363420041237114},
            {"name": "轰炸", "value": 0.07125890734463277},
            {"name": "目标", "value": 0.06888361052631579},
            {"name": "可能", "value": 0.06413301306413302},
            {"name": "爆炸", "value": 0.06413301306413302},
            {"name": "已经", "value": 0.06413301306413302},
            {"name": "开始", "value": 0.06413301306413302},
            {"name": "进行 空袭", "value": 0.06413301306413302},
            {"name": "违反 停火", "value": 0.06413301306413302},
            {"name": "包括", "value": 0.05938242230466981},
            {"name": "受伤", "value": 0.05938242230466981},
            {"name": "如果", "value": 0.05938242230466981},
            {"name": "军队", "value": 0.05938242230466981},
            {"name": "遭受", "value": 0.05938242230466981},
            {"name": "以色列 军", "value": 0.05938242230466981},
            {"name": "边境", "value": 0.05700711525177305},
            {"name": "巴勒斯坦", "value": 0.05700711525177305},
            {"name": "今天", "value": 0.05700711525177305},
            {"name": "部队", "value": 0.05463182815251773}
          ]
        },
        'spaceX完成首次“筷子夹火箭”': {
          pieData: [4, 0, 1, 46, 1, 21],
          lineData: [2700, 2900, 3000, 2800, 2600, 2500],
          gaugeData: [60, 12],
          "wordCloudData": [
            {"name": "筷子", "value": 1},
            {"name": "SpaceX", "value": 0.38613861386138615},
            {"name": "助推器", "value": 0.35643564356435645},
            {"name": "火箭", "value": 0.3069306930693069},
            {"name": "测试", "value": 0.2673267326732673},
            {"name": "进行", "value": 0.2178217821782178},
            {"name": "LabPadre", "value": 0.1782178217821782},
            {"name": "我们", "value": 0.1683168316831683},
            {"name": "抓住", "value": 0.1485148514851485},
            {"name": "一个", "value": 0.1485148514851485},
            {"name": "已经", "value": 0.1386138613861386},
            {"name": "发射", "value": 0.1287128712871287},
            {"name": "捕获", "value": 0.1188118811881188},
            {"name": "太空 探索", "value": 0.1188118811881188},
            {"name": "可能", "value": 0.1089108910891089},
            {"name": "回收", "value": 0.1089108910891089},
            {"name": "探索 技术", "value": 0.1089108910891089},
            {"name": "技术 公司", "value": 0.1089108910891089},
            {"name": "超重型 助推", "value": 0.1089108910891089},
            {"name": "Mechazille", "value": 0.09900990099009901},
            {"name": "太空", "value": 0.09900990099009901},
            {"name": "他们", "value": 0.09900990099009901},
            {"name": "正在", "value": 0.09900990099009901},
            {"name": "火箭 回收", "value": 0.09900990099009901},
            {"name": "星舰", "value": 0.0891089108910891},
            {"name": "飞行", "value": 0.0891089108910891},
            {"name": "巨大", "value": 0.0891089108910891},
            {"name": "今天", "value": 0.0891089108910891},
            {"name": "星际 飞船", "value": 0.0891089108910891},
            {"name": "成功", "value": 0.07920792079207921},
            {"name": "发射塔", "value": 0.07920792079207921},
            {"name": "着陆", "value": 0.07920792079207921},
            {"name": "再次", "value": 0.07920792079207921},
            {"name": "这是", "value": 0.07920792079207921},
            {"name": "看到", "value": 0.07920792079207921},
            {"name": "系统", "value": 0.07920792079207921},
            {"name": "机械", "value": 0.07920792079207921},
            {"name": "第一次", "value": 0.06930693069306931},
            {"name": "埃隆", "value": 0.06930693069306931},
            {"name": "没有", "value": 0.06930693069306931},
            {"name": "两个", "value": 0.06930693069306931},
            {"name": "筷子 夹住", "value": 0.06930693069306931},
            {"name": "NASASpace10", "value": 0.05940594059405941},
            {"name": "准备", "value": 0.05940594059405941},
            {"name": "live", "value": 0.05940594059405941},
            {"name": "手臂", "value": 0.05940594059405941},
            {"name": "之间", "value": 0.05940594059405941},
            {"name": "一些", "value": 0.05940594059405941},
            {"name": "尝试", "value": 0.05940594059405941},
            {"name": "使用", "value": 0.05940594059405941}
          ]
        },
        '法国四换总理': {
          pieData: [0, 0, 0, 3, 4, 11],
          lineData: [2600, 2800, 2900, 2700, 2500, 3000],
          gaugeData: [37, 12],
          "wordCloudData": [
            {"name": "马克", "value": 1},
            {"name": "法国", "value": 0.8085106382978723},
            {"name": "总理", "value": 0.7659574468085106},
            {"name": "选举", "value": 0.4680851063829787},
            {"name": "勒庞", "value": 0.2978723404255319},
            {"name": "极右翼", "value": 0.2978723404255319},
            {"name": "政府", "value": 0.2765957446808511},
            {"name": "左翼", "value": 0.2765957446808511},
            {"name": "政党", "value": 0.2553191489361702},
            {"name": "任命", "value": 0.2553191489361702},
            {"name": "埃马 纽埃", "value": 0.2553191489361702},
            {"name": "纽埃 马克", "value": 0.2553191489361702},
            {"name": "纽埃", "value": 0.2340425531914894},
            {"name": "法国 总统", "value": 0.21276595744680852},
            {"name": "辞职", "value": 0.21276595744680852},
            {"name": "右翼", "value": 0.19148936170212765},
            {"name": "法国总统", "value": 0.19148936170212765},
            {"name": "马琳 勒庞", "value": 0.19148936170212765},
            {"name": "马克 任命", "value": 0.19148936170212765},
            {"name": "欧盟", "value": 0.1702127659574468},
            {"name": "人民阵线", "value": 0.1702127659574468},
            {"name": "政治", "value": 0.1702127659574468},
            {"name": "候选人", "value": 0.1702127659574468},
            {"name": "中间派", "value": 0.1702127659574468},
            {"name": "总统 埃马", "value": 0.1702127659574468},
            {"name": "解散 议会", "value": 0.1702127659574468},
            {"name": "法国政府", "value": 0.14893617021276595},
            {"name": "可能", "value": 0.14893617021276595},
            {"name": "没有", "value": 0.14893617021276595},
            {"name": "联盟", "value": 0.14893617021276595},
            {"name": "RN", "value": 0.14893617021276595},
            {"name": "选择", "value": 0.14893617021276595},
            {"name": "今天", "value": 0.14893617021276595},
            {"name": "米歇尔 巴", "value": 0.14893617021276595},
            {"name": "担任 总理", "value": 0.14893617021276595},
            {"name": "议会", "value": 0.1276595744680851},
            {"name": "一个", "value": 0.1276595744680851},
            {"name": "巴尼耶", "value": 0.1276595744680851},
            {"name": "这是", "value": 0.1276595744680851},
            {"name": "赢得", "value": 0.1276595744680851},
            {"name": "席位", "value": 0.1276595744680851},
            {"name": "试图", "value": 0.1276595744680851},
            {"name": "他们", "value": 0.1276595744680851},
            {"name": "提前 举行", "value": 0.1276595744680851},
            {"name": "加布里埃尔", "value": 0.1276595744680851},
            {"name": "弗朗索瓦", "value": 0.1276595744680851},
            {"name": "成为", "value": 0.10638297872340425},
            {"name": "解散", "value": 0.10638297872340425},
            {"name": "失败", "value": 0.10638297872340425},
            {"name": "领导", "value": 0.10638297872340425}
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
  height: 100%;
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
.trans_button{
  background-color: transparent;
  color: #00ffff;
  border: 1px solid #00ffff;
  border-radius: 5px;
  padding: 5px 10px;
  margin-right: 10px;
  cursor: pointer;
  font-size: 20px;
}
.trans_button:hover{
  background-color: #00ffff;
  color: #0d0d0d;
}
</style>
