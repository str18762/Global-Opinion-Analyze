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
          <dv-decoration-7 style="width:200px;height:60px;flex:1;font-size: 20px">热评话题</dv-decoration-7>
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
                <HotTopics_charts3 :xData="lineTime" :yData="lineData"/>
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
                <el-button class="trans_button" style="flex: 1;text-decoration: underline" @click="$router.push('/hotTopics/analysis')" >舆情分析</el-button>
                <el-button class="trans_button" style="flex: 1" @click="$router.push('/hotTopics/prediction')">舆情预测</el-button>
                <el-button class="trans_button" style="flex: 1" @click="$router.push('/hotTopics/forewarn')">舆情预警</el-button>
                <el-dropdown class="trans_button" style="flex: 1; color: orangered;">
                  <span style="width: 100%;height: 100%;display: flex;align-items: center;justify-content: center">
                    中国特色传播
                    <i class="el-icon-arrow-down"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown" class="custom-dropdown-menu">
                    <el-dropdown-item>
                      <div class="custom-item" @click="goToIndex3" >科技</div>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <div @click="goToIndex3" >文化</div>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <div @click="goToIndex3" >医疗</div>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <div @click="goToIndex3" >教育</div>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <div @click="goToIndex3" >军事</div>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
            </div>
          </div>

          <div style="height: 30%;padding: 5px">
            <div class="box">
              <h2>舆情事件相关新闻</h2>
              <div style="height: 80%;display: flex;flex-wrap: nowrap">
                <div style="height: 100%;width: auto">
                  <img :src="newsData[currentIndex].img" style="height: 100%"/>
                </div>
                <div style="height: 100%;width: 100%">
                  <div class="emotion_inner_name" @click="newsDetail(newsData[currentIndex])">
                    {{newsData[currentIndex].caption}}
                  </div>
                  <div style="text-align: right">
                    --{{newsData[currentIndex].source}}
                  </div>
                  <div style="text-align: center;position: absolute;bottom: 20px;right: 20px">
                    <button @click="news_prev" :disabled="currentIndex === 0"><i class="el-icon-caret-left"/>上一个</button>
                    <button @click="news_next" :disabled="currentIndex === newsData.length - 1">下一个<i class="el-icon-caret-right" /></button>
                  </div>
                </div>
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
                      <h2 class="emotion_change" style="flex: 1" @click="emotionChange">正面情感</h2>
                      <h2 class="emotion_change" style="flex: 1" @click="emotionChange">中性情感</h2>
                      <h2 class="emotion_change" style="flex: 1" @click="emotionChange">负面情感</h2>
                    </div>
                    <div class="emotion_box">
                      <el-row v-for="(item,index) in charaData" :key="index" v-show="item.emotion===selectEmotion"
                              style="font-size: 15px;margin-bottom: 5px">
                        <span class="emotion_inner_name" @click="selectCharaInfo=item.description">{{item.username}}</span>
                        <el-divider/>
                      </el-row>
                    </div>
                  </div>
                </div>
                <div style="height: 50%;margin-top: 20px">
                  <h2>情感人物详情</h2>
                  <div style="height: 100%">
                    {{selectCharaInfo}}
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
import EmotionPie from "@/components/echarts/HotTopicsChart/EmotionPie.vue";
import LineChange from "@/components/echarts/HotTopicsChart/LineChange.vue";
import WordCloud from "@/components/echarts/HotTopicsChart/WordCloud.vue";

export default {
  name: 'HotTopicsAnalysis',
  components: {
    HotTopics_charts1: EmotionPie,
    HotTopics_charts3: LineChange,
    HotTopics_charts4: WordCloud,
  },
  data() {
    return {
      datas:[],
      pieData:[],
      gaugeData:[],
      lineData:[],
      lineTime:[],
      wordCloudData:[],
      newsData:[
        {
          id:null,
          img:null,
        },
      ],
      currentIndex:0,

      selectedEvent:'美国大选',
      options: [
        {
          label:'美国大选',
          value:'美国大选',
        },
        {
          label:'韩国总统尹锡悦被逮捕',
          value:'韩国总统尹锡悦被逮捕',
        },
        {
          label:'以色列空袭黎巴嫩',
          value:'以色列空袭黎巴嫩',
        },
        {
          label:'SpaceX完成首次“筷子夹火箭”',
          value:'SpaceX完成首次“筷子夹火箭”',
        },
        {
          label:'法国四换总理',
          value:'法国四换总理',
        },
      ],
      activeTabPane:1,
      charaData:[],
      selectEmotion:'正面情感',
      selectCharaInfo:'',
    }
  },
  mounted() {
    this.getData(this.selectedEvent);
    this.drawChart()
  },
  methods: {
    goToIndex3() {
      console.log('goToIndex3 called');  // 确认是否被调用
      this.$nextTick(() => {
        window.location.href = '/index3.html'; // 跳转到 index3.html
      });
    },

    getData(event) {
      this.currentIndex=0;
      this.$request.get(this.Global.select_BBCNewsByRelativity+'?relativity='+event).then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.newsData=res.data;
        } else {
          this.$message.error(res.msg);
        }
      })

      const eventSentimentData = {
        '美国大选': {
          pieData: [31, 1, 0, 184, 9, 73],
          lineData: [
            ["Jan-24", "Feb-24", "Mar-24", "Apr-24", "May-24", "Jun-24", "Jul-24", "Aug-24", "Sep-24", "Oct-24", "Nov-24", "Dec-24"],
            [549, 303, 635, 10, 356, 89, 1217, 814, 934, 1494, 2000, 0]
          ],
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
          ],
          charaData:[
            {
              "username": "泽琳娜・麦克斯韦",
              "emotion": "正面情感",
              "description": "政治分析员，倡导社会正义、性别平等和种族平等，常支持进步的政策和候选人"
            },
            {
              "username": "巴拉克・奥巴马",
              "emotion": "正面情感",
              "description": "美国前总统，通常支持进步候选人并强调民主和选民权利"
            },
            {
              "username": "乔・拜登",
              "emotion": "正面情感",
              "description": "支持自己的竞选活动及相关政策"
            },
            {
              "username": "卡马拉・哈里斯",
              "emotion": "正面情感",
              "description": "美国副总统，支持本党的政策并强调选民参与的重要性"
            },
            {
              "username": "贝托・奥’鲁克",
              "emotion": "正面情感",
              "description": "政治活动家和前国会议员，支持进步政治运动"
            },
            {
              "username": "杰克・塔珀",
              "emotion": "中性情感",
              "description": "CNN主持人，倾向于提供平衡的报道，政治立场中立"
            },
            {
              "username": "比尔・奥'赖利",
              "emotion": "中性情感",
              "description": "政治评论员，虽然偏向保守，但也会批评两党的政策，强调独立立场"
            },
            {
              "username": "查尔梅恩・塔・戈德",
              "emotion": "中性情感",
              "description": "媒体人物，常常就政治问题进行平衡讨论，特别关注影响少数族裔的议题"
            },
            {
              "username": "塔克・卡尔森",
              "emotion": "中性情感",
              "description": "电视节目主持人，尽管倾向保守，但其评论有时涉及多元议题"
            },
            {
              "username": "安德森・库珀",
              "emotion": "中性情感",
              "description": "NN新闻主持人，以公平和中立的报道为特点，强调事实和多元视角"
            },
            {
              "username": "肖恩・汉尼提",
              "emotion": "负面情感",
              "description": "保守派评论员，一直对民主党及其政策持批评态度，支持右翼观点"
            },
            {
              "username": "本・夏皮罗",
              "emotion": "负面情感",
              "description": "政治评论员，倾向于保守立场，常批评进步候选人和政策"
            },
            {
              "username": "坎迪斯・欧文斯",
              "emotion": "负面情感",
              "description": "保守派评论员，以强烈批评民主党和进步政策闻名，尤其在选举期间"
            },
            {
              "username": "劳拉・英格拉汉",
              "emotion": "负面情感",
              "description": "福克斯新闻主持人，倡导保守派政策，常对民主党候选人和进步议程进行批评"
            },
            {
              "username": "克雷・特拉维斯",
              "emotion": "负面情感",
              "description": "体育记者转型的政治评论员，支持保守派价值观，并批评自由派政策"
            }
          ]
        },
        '韩国总统尹锡悦被逮捕': {
          pieData: [9, 0, 2, 27, 4, 35],
          lineData: [
            ["Jan-24", "Feb-24", "Mar-24", "Apr-24", "May-24", "Jun-24", "Jul-24", "Aug-24", "Sep-24", "Oct-24", "Nov-24", "Dec-24"],
            [0, 75, 1945, 6, 5, 2, 0, 323, 193, 36, 7, 2000]
          ],
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
          ],
          charaData:[
            {
              "username": "乔・拜登",
              "emotion": "正面情感",
              "description": "美国总统，虽然没有直接评论尹锡悦，但他通常支持民主国家的领导，并赞扬领导人的法治和稳定"
            },
            {
              "username": "安倍晋三",
              "emotion": "正面情感",
              "description": "日本前首相，通常支持与韩国的稳定外交关系，曾强调与韩国的合作"
            },
            {
              "username": "玛丽娜・赫什曼",
              "emotion": "正面情感",
              "description": "英国《卫报》专栏作家，时常支持全球政治中的民主力量"
            },
            {
              "username": "梅拉尼娅・特朗普",
              "emotion": "正面情感",
              "description": "美国前第一夫人，倾向于支持对外政策中的稳定性和国际合作"
            },
            {
              "username": "马尔科・鲁比奥",
              "emotion": "正面情感",
              "description": "美国参议员，支持亚洲盟友，通常批评政治动荡"
            },
            {
              "username": "杰克・塔珀",
              "emotion": "中性情感",
              "description": "CNN记者，偏向报道事实而非参与政治立场，因此在尹锡悦事件中的态度较为中立"
            },
            {
              "username": "安德森・库珀",
              "emotion": "中性情感",
              "description": "CNN新闻主播，通常提供平衡的报道，不偏向任何一方"
            },
            {
              "username": "比尔・奥'赖利",
              "emotion": "中性情感",
              "description": "保守派评论员，虽然偏向美国国内政治，但对于外部事务通常保持中立"
            },
            {
              "username": "塔克・卡尔森",
              "emotion": "中性情感",
              "description": "福克斯新闻主持人，倾向于关注美国政治，但对于国际领导人通常维持一定的中立立场"
            },
            {
              "username": "加里・尤基",
              "emotion": "中性情感",
              "description": "日本著名记者，经常报道东亚政治，态度较为客观"
            },
            {
              "username": "克雷・特拉维斯",
              "emotion": "负面情感",
              "description": "美国保守派评论员，支持对民主党及左翼政府的批评，倾向于反对此类政治动荡"
            },
            {
              "username": "肖恩・汉尼提",
              "emotion": "负面情感",
              "description": "保守派电视主持人，对类似尹锡悦政治危机事件持负面评价，通常支持强硬政治立场"
            },
            {
              "username": "本・夏皮罗",
              "emotion": "负面情感",
              "description": "保守派政治评论员，倾向于批评民主党和进步派的政治行为"
            },
            {
              "username": "劳拉・英格拉汉",
              "emotion": "负面情感",
              "description": "保守派主持人，经常对左派政治和领导人表达强烈反对意见"
            },
            {
              "username": "坎迪斯・欧文斯",
              "emotion": "负面情感",
              "description": "保守派评论员，通常对具有争议的领导人及其行为持批评态度，特别是涉及权力滥用和政治冲突时"
            }
          ]
        },
        '以色列空袭黎巴嫩': {
          pieData: [15, 3, 6, 58, 38, 31],
          lineData: [
            ["Jan-24", "Feb-24", "Mar-24", "Apr-24", "May-24", "Jun-24", "Jul-24", "Aug-24", "Sep-24", "Oct-24", "Nov-24", "Dec-24"],
            [2, 131, 24, 6, 0, 11, 336, 59, 2000, 822, 1443, 791]
          ],
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
          ],
          charaData:[
            {
              "username": "本雅明・内塔尼亚胡",
              "emotion": "正面情感",
              "description": "支持以色列的防卫行动，强调以色列有权保护其公民免受袭击。"
            },
            {
              "username": "马克・雷格夫",
              "emotion": "正面情感",
              "description": "强调以色列的自卫权，并支持打击恐怖组织如真主党。"
            },
            {
              "username": "本尼・甘茨",
              "emotion": "正面情感",
              "description": "强调对所有威胁以色列安全的目标采取行动的必要性。"
            },
            {
              "username": "以色列国防军",
              "emotion": "正面情感",
              "description": "通过推特发布与空袭相关的声明，支持以色列的军事行动。"
            },
            {
              "username": "雅伊尔・拉皮德",
              "emotion": "正面情感",
              "description": "表达以色列采取自卫措施的正当性。"
            },
            {
              "username": "安东尼奥・古特雷斯",
              "emotion": "中性情感",
              "description": "呼吁各方冷静，并倡导通过对话解决冲突。"
            },
            {
              "username": "延斯・斯托尔滕贝格",
              "emotion": "中性情感",
              "description": "强调北约对中东地区和平稳定的关切。"
            },
            {
              "username": "约翰・克里",
              "emotion": "中性情感",
              "description": "强调需要通过外交途径解决冲突，但承认以色列的自卫权。"
            },
            {
              "username": "费德里卡・莫盖里尼",
              "emotion": "中性情感",
              "description": "发表中立声明，呼吁双方保持克制。"
            },
            {
              "username": "联合国难民署",
              "emotion": "中性情感",
              "description": "提出对因冲突而流离失所的民众表示关切，强调人道主义援助。"
            },
            {
              "username": "扎赫尔・哈提卜",
              "emotion": "负面情感",
              "description": "强烈批评以色列的空袭行动，指责以色列侵犯黎巴嫩主权。"
            },
            {
              "username": "哈桑・纳斯鲁拉",
              "emotion": "负面情感",
              "description": "强烈反对以色列的空袭，呼吁反抗和报复。"
            },
            {
              "username": "国际特赦组织",
              "emotion": "负面情感",
              "description": "报告空袭可能违反国际人道法，呼吁对事件进行独立调查。"
            },
            {
              "username": "人权观察",
              "emotion": "负面情感",
              "description": "表示以色列的空袭可能构成战争罪，尤其是对平民和记者的伤害。"
            },
            {
              "username": "马哈茂德・阿巴斯",
              "emotion": "负面情感",
              "description": "强烈谴责以色列空袭，并呼吁国际社会采取行动制止以色列的攻击行为。"
            }
          ]
        },
        'SpaceX完成首次“筷子夹火箭”': {
          pieData: [4, 0, 1, 46, 1, 21],
          lineData:  [
            ["Jan-24", "Feb-24", "Mar-24", "Apr-24", "May-24", "Jun-24", "Jul-24", "Aug-24", "Sep-24", "Oct-24", "Nov-24", "Dec-24"],
            [0, 10, 0, 46, 16, 117, 41, 24, 29, 2000, 77, 38]
          ],
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
          ],
          charaData:[
            {
              "username": "埃隆・马斯克",
              "emotion": "正面情感",
              "description": "SpaceX创始人，他是这项技术的推动者，并且在事件后称其为“向多星球生活迈出的巨大一步”"
            },
            {
              "username": "布莱恩・梅",
              "emotion": "正面情感",
              "description": "英国著名音乐家和天文学家，支持SpaceX的探索精神，关注太空技术创新"
            },
            {
              "username": "杰里米・科特曼",
              "emotion": "正面情感",
              "description": "天文学家和太空科技专家，对这一突破表示高度赞赏，认为这是空间探索的新纪元"
            },
            {
              "username": "哈维尔・博尔格",
              "emotion": "正面情感",
              "description": "资深航空工程师，赞扬SpaceX重新定义了火箭发射和回收技术"
            },
            {
              "username": "本・福德",
              "emotion": "正面情感",
              "description": "太空科技记者，认为这一技术将改变太空旅行的商业模式"
            },
            {
              "username": "皮特・比尤尔",
              "emotion": "中性情感",
              "description": "太空工程师，他认为这是一个令人印象深刻的技术展示，但需要更多的测试和验证"
            },
            {
              "username": "阿尔弗雷德・费尔南德斯",
              "emotion": "中性情感",
              "description": "资深技术记者，他认为这一突破性进展虽然令人兴奋，但仍面临很多挑战"
            },
            {
              "username": "艾玛・罗杰斯",
              "emotion": "中性情感",
              "description": "NASA科学家，表示虽然技术展示非常令人鼓舞，但必须在更复杂的环境中进行更多验证"
            },
            {
              "username": "约瑟夫・罗斯",
              "emotion": "中性情感",
              "description": "航天工程师，认为这项技术仍需更多试飞和改进才能确保可靠性"
            },
            {
              "username": "凯瑟琳・杨",
              "emotion": "中性情感",
              "description": "科技评论员，保持中立态度，认为SpaceX的每一步都值得关注，但对未来表现持谨慎态度"
            },
            {
              "username": "罗杰・西尔顿",
              "emotion": "负面情感",
              "description": "航天工程师，批评“筷子夹火箭”的技术存在过度复杂化的风险，可能影响回收的稳定性"
            },
            {
              "username": "威廉・凯恩",
              "emotion": "负面情感",
              "description": "前航天顾问，对这一创新表示质疑，认为其可靠性和安全性未得到充分验证"
            },
            {
              "username": "莉莉・格林",
              "emotion": "负面情感",
              "description": "航天学者，认为SpaceX过于强调速度和创新，而忽视了技术的深度和长期可持续性"
            },
            {
              "username": "奥斯卡・霍普金斯",
              "emotion": "负面情感",
              "description": "航天评论员，警告该技术在面对极端环境时可能会失败"
            },
            {
              "username": "海尔・摩根",
              "emotion": "负面情感",
              "description": "专业工程师，批评SpaceX的技术过于冒险，认为他们可能低估了环境和操作复杂性"
            }
          ]
        },
        '法国四换总理': {
          pieData: [0, 0, 0, 3, 4, 11],
          lineData:  [
            ["Jan-24", "Feb-24", "Mar-24", "Apr-24", "May-24", "Jun-24", "Jul-24", "Aug-24", "Sep-24", "Oct-24", "Nov-24", "Dec-24"],
            [276, 18, 74, 36, 0, 2000, 971, 235, 489, 126, 7, 609]
          ],
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
      this.lineTime=this.datas.lineData[0]
      this.lineData=this.datas.lineData[1]
      this.gaugeData=this.datas.gaugeData
      this.wordCloudData=this.datas.wordCloudData
      this.charaData=this.datas.charaData
      this.selectCharaInfo=''
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
    newsDetail(news) {
      this.$router.push({
        path:'/news_bbc',
        query:{
          bbc_newsId: news.id
        }
      });
    },

    news_prev(){
      if (this.currentIndex > 0) {
        this.currentIndex--;
      }
    },
    news_next(){
      if (this.currentIndex < this.newsData.length - 1) {
        this.currentIndex++;
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
/deep/.el-dropdown-menu__item{
  padding: 0 !important;
}
/* 自定义下拉菜单的宽度 */
.custom-dropdown-menu {
  min-width: 150px;  /* 设置最小宽度 */
  width: 150px;      /* 设置固定宽度，也可以调整为适合你的值 */
  text-align: center;
  padding: 0;
}
.custom-item:hover{
  background-image: url("@/assets/Images/sys_bg.png");
}
</style>
