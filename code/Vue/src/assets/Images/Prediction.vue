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
          <dv-decoration-8 style="width:300px;height:50px;flex:1"/>
        </div>

      </div>
      <div style="flex: 0 1 30%;">
        <dv-decoration-10 style="width: 100%;height:50px;transform: rotateY(180deg);" />
      </div>
    </div>

    <!--body-->
    <div style="height: 90vh">
      <el-row style="height: 100%">
        <el-col :span="18" style="height: 100%">
<!--          左侧顶部-->
          <div style="height: 20%;width: 100%;display: flex;flex-wrap: nowrap">
<!--            效果文字-->
            <div style="flex:1;height: 100%;padding: 5px">
              <div style="height: 100%;width: 100%;">
                <TextChart :chartData="'舆情预测Predict'"/>
              </div>
            </div>
<!--            跳转导航-->
            <div style="flex: 2;height: 100%;padding: 5px">
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
                  <el-button class="trans_button" style="flex: 1" @click="$router.push('/hotTopics/analysis')">舆情分析</el-button>
                  <el-button class="trans_button" style="flex: 1;text-decoration: underline" @click="$router.push('/hotTopics/prediction')">舆情预测</el-button>
                  <el-button class="trans_button" style="flex: 1" @click="$router.push('/hotTopics/forewarn')">舆情预警</el-button>
                  <el-dropdown class="trans_button" style="flex: 1; color: orangered;">
                  <span style="width: 100%;height: 100%;display: flex;align-items: center;justify-content: center">
                    中国特色传播
                    <i class="el-icon-arrow-down"></i>
                  </span>
                    <el-dropdown-menu slot="dropdown" class="custom-dropdown-menu">
                      <el-dropdown-item>
                        <div @click="goToIndex3" >科技</div>
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
          </div>
<!--          左侧下部-->
          <div style="height: 80%;width: 100%;display: flex;flex-wrap: nowrap">
            <div style="height: 100%;flex:2">
              <div style="height: 50%;width: 100%;padding: 5px">
                <div class="box">
                  <h2>情感变化预测图（10天）</h2>
                  <div style="height: 100%;">
                    <PredictEmotion style="height: 100%" :xData="emotionXData" :yData="emotionYData"/>
                  </div>
                </div>
              </div>
              <div style="height: 50%;width: 100%;padding: 5px">
                <div class="box">
                  <h2>舆情事件讨论热度预测图</h2>
                  <div style="height: 80%;">
                    <PredictionLine style="height: 100%" :xData="hotXData" :yData="hotYData"/>
                  </div>
                </div>
              </div>
            </div>
            <div style="height: 100%;flex: 1;padding: 5px">
              <div class="box">
                <h2>影响舆情关键人物</h2>
                <div style="height: 20%">
                  以下人物有着巨大的影响力，他们的话语或行为往往能引导或者改变公众对该事件的看法，甚至在一定程度上左右着该事件的社会趋势或决策，对舆情如何发展有着关键作用。
                </div>
                <div style="height: 70%">
                  <EventKeyChara :event="selectedEvent" @nodeClick="charaNodeClick"/>
                </div>
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
                  <h2>该人物已经造成的影响</h2>
                  <div style="height: 80%">
                    <div class="key_chara_eventList">
                      <el-timeline :reverse="false" style="margin-left: 10px;color: #0ff">
                        <el-timeline-item
                            v-for="(item, index) in keyCharaData.event"
                            type="success"
                            :key="index">
                          <span style="color: #0ff">{{item}}</span>
                        </el-timeline-item>
                      </el-timeline>
                    </div>
                  </div>
                </div>
                <div style="height: 50%;margin-top: 20px">
                  <h2>预测该人物可能对后续舆情的影响</h2>
                  <div style="height: 100%">
                    {{keyCharaData.predict}}
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

import TextChart from "@/components/echarts/TextChart.vue";
import PredictHotLine from "@/components/echarts/HotTopicsChart/PredictHotLine.vue";
import PredictEmotion from "@/components/echarts/HotTopicsChart/PredictEmotion.vue";
import PredictEventNode from "@/components/echarts/HotTopicsChart/PredictEventNode.vue";
import EventKeyChara from "@/components/echarts/HotTopicsChart/EventKeyChara.vue";

export default {
  name: 'HotTopicsPrediction',
  components: {
    TextChart,
    PredictionLine: PredictHotLine,
    PredictEmotion,
    PredictEventNode,
    EventKeyChara,
  },
  data() {
    return {
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

      selectedChara: null,

      dataset: {
        '美国大选':{
          emotionData:[
            [32.00, 1.89, 1.89, 4.28, 4.28, 3.50, 0.00, 1.89, 1.89, 7.81],  //# anger
            [0.00, 4.28, 0.00, 4.28, 30.00, 3.00, 0.00, 0.00, 0.00, 0.00],  //# disgust
            [0.00, 4.28, 0.00, 4.28, 0.00, 0.00, 0.00, 3.20, 0.00, 0.04],  //# fear
            [50.00, 52.00, 56.00, 30.00, 28.30, 34.36, 24.50, 27.20, 0.54, 78.15],  //# joy
            [0.00, 4.28, 4.28, 4.28, 0.00, 0.02, 4.28, 0.01, 0.00, 0.00],  //# sadness
            [0.04, 10.00, 0.00, 0.00, 15.30, 0.03, 0.03, 0.34, 3.20, 0.01],  //# surprise
            [18.00, 23.27, 37.83, 52.88, 22.12, 59.09, 71.19, 65.36, 90.09, 12.24]  //# neutral
          ],
          hotData:{
            "time": ["2025/1/1", "2025/1/2", "2025/1/3", "2025/1/4", "2025/1/5", "2025/1/6", "2025/1/7", "2025/1/8",
              "2025/1/9", "2025/1/10", "2025/1/11", "2025/1/12", "2025/1/13", "2025/1/14", "2025/1/15", "2025/1/16",
              "2025/1/17", "2025/1/18", "2025/1/19", "2025/1/20"
            ],
            "popularity": [549, 303, 635, 10, 356, 89, 976, 814, 934, 678, 298, 78,],
            "pre_popularity": [200, 167, 743, 34, 265, 176, 786, 658, 1034, 468, 135,
              46, 331, 643, 98, 45, 293, 12, 88, 456]
          },
          keyCharaData: {
            "泰勒・斯威夫特":{
              event: [
                "美国大选时，号召粉丝为拜登投票，发帖后一小时内参与投票人数增加 12 倍多。",
                "因其巨大的影响力和 “霉霉经济学” 效应登上美国《时代》周刊封面，，让她在大选中也备受关注。",
                "特朗普团队把其视作可能影响选情的因素，拜登也寻求其在大选前为自己 “背书”。",
              ],
              predict:"若为拜登 “背书”，可能会吸引大量年轻粉丝支持拜登，增加拜登的选民支持率；若明确表达对特朗普的反对，也可能影响部分摇摆选民的态度"
            },
            "福克斯电视台":{
              event:[
                "作为特朗普的“忠粉”，福克斯电视台在报道中多倾向于支持特朗普的观点和政策，影响了部分选民对特朗普的看法和态度，为其争取了一定的选民基础。",
                "在大选期间，福克斯电视台对特朗普的竞选活动进行了大量报道，提升了特朗普的曝光度和影响力，同时也引发了其他媒体和公众对特朗普竞选策略的关注和讨论。",
                "其报道风格和内容选择在一定程度上影响了美国右翼选民的信息接收和认知，加剧了美国社会在大选问题上的政治极化现象。"
              ],
              predict:"福克斯电视台可能会继续支持特朗普的竞选活动，进一步巩固其在右翼选民中的影响力，影响选民的投票决策"
            }
          }
        },
        '韩国总统尹锡悦被逮捕':{
          emotionData:[
            [2.30, 0.02, 25.37, 2.10, 2.30, 1.55, 6.67, 2.11, 29.71, 2.61], // # anger
            [4.55, 6.55, 3.20, 33.10, 77.20, 2.30, 2.30, 26.30, 0.17, 0.45], // # disgust
            [33.15, 4.22, 2.30, 6.60, 1.20, 3.22, 2.10, 9.88, 5.57, 45.33],  //# fear
            [9.20, 29.30, 1.60, 5.20, 0.67, 5.69, 3.00, 5.78, 0.02, 15.59],  //# joy
            [3.22, 2.55, 0.05, 4.60, 9.44, 4.76, 4.30, 3.22, 0.22, 0.48], // # sadness
            [1.56, 9.60, 7.10, 7.20, 5.90, 0.01, 9.11, 0.00, 6.70, 31.93],  //# surprise
            [46.02, 47.76, 60.38, 41.20, 3.29, 82.47, 48.52, 63.71, 57.61, 3.61] // # neutral
          ],
          hotData:{
            "time": ["2025/1/1", "2025/1/2", "2025/1/3", "2025/1/4", "2025/1/5", "2025/1/6", "2025/1/7", "2025/1/8",
              "2025/1/9", "2025/1/10", "2025/1/11", "2025/1/12", "2025/1/13", "2025/1/14", "2025/1/15", "2025/1/16",
              "2025/1/17", "2025/1/18", "2025/1/19", "2025/1/20"
            ],
            "popularity": [345, 75, 945, 6, 578, 288, 178, 323, 193, 36, 421, 123,],
            "pre_popularity": [210, 195, 789, 35, 410, 408, 78, 310, 313, 82,
              239, 212, 56, 378, 32, 765, 88, 13, 987, 1082]
          },
          keyCharaData: {
            "尹锡悦":{
              event: [
                "尹锡悦的支持率在弹劾案深入后逆转，上升到40%左右",
                "尹锡悦的政治生涯一直充满争议，近年来其政府在处理社会问题、经济复苏等方面的表现都遭到了不同程度的批评",
                "尹锡悦或被拘留事件对韩国经济、民众情绪以及国际关系产生深远影响"
              ],
              predict:"后续的法律程序和判决可能会持续吸引公众关注，可能会引发对政治腐败和权力滥用的讨论"
            },
            "朴钟俊":{
              event:[
                "作为尹锡悦的重要支持者，其掌管的总统警卫处多次逼退韩国公调处的搜查和逮捕，如 1 月 3 日韩国公调处对尹锡悦执行逮捕令时，双方一度爆发肢体冲突。",
                "因强硬保护尹锡悦的立场，遭到在野党起诉和举报。",
                "1 月 10 日辞职，被视为尹锡悦核心支持团队进一步瓦解的标志，影响尹锡悦的 “安全” 形势。"
              ],
              predict:"其辞职可能使尹锡悦失去重要保护力量，舆论可能更加倾向于认为尹锡悦被逮捕的可能性增加，也可能引发对韩国政治局势稳定性的更多讨论。"
            },
            "李在明":{
              event:[
                "作为韩国在野党共同民主党党首，公开表示尹锡悦被逮捕是“民主的胜利”，引发国际上一些支持民主理念的人士的共鸣，带动了部分大众对尹锡悦被逮捕事件的积极评价。",
                "在社交媒体上呼吁民众关注尹锡悦涉嫌内乱等罪行的调查过程，提高了公众对事件的关注度和参与度。",
                "对尹锡悦政府的政策和行为进行批评，引导舆论对尹锡悦政府的负面评价，影响了大众对韩国政治局势的整体认知。"
              ],
              predict:"可能会继续利用尹锡悦被逮捕事件，推动对尹锡悦及其盟友的进一步调查和追究，引发更多关于韩国政治腐败和权力斗争的讨论，使舆情持续聚焦于韩国政坛的动荡和改革"
            },
            "权宁世":{
              event:[
                "作为韩国国民力量党紧急对策委员会委员长，权宁世表示公调处对总统执行逮捕令是“极其不公正的越权、不当行为”，认为“公调处通过与法官交易获得了逮捕令”，这一表态引发了韩国国内对逮捕令合法性和公正性的广泛讨论，影响了公众对此次逮捕行动的看法。",
                "他的言论也得到了部分支持尹锡悦的民众和政治势力的呼应，使得韩国国内支持和反对尹锡悦的两派民众之间的对立情绪进一步加剧，影响了韩国社会的稳定和舆论氛围。"
              ],
              predict:"权宁世可能会继续推动对尹锡悦的支持和声援活动，进一步加剧韩国国内的政治对立，影响公众对尹锡悦案件的持续关注"
            }
          }
        },
        '以色列空袭黎巴嫩':{
          emotionData:[
            [2.36, 1.24, 6.74, 7.98, 3.20, 7.98, 8.50, 4.22, 2.36, 33.40],  //# anger
            [8.10, 6.40, 21.30, 2.36, 5.10, 8.34, 2.36, 4.35, 2.51, 2.36],  //# disgust
            [2.51, 21.30, 9.67, 3.22, 33.40, 4.33, 6.44, 4.35, 2.51, 1.24],  //# fear
            [2.51, 7.98, 2.83, 5.21, 6.40, 7.94, 0.23, 5.44, 0.44, 19.93],  //# joy
            [2.51, 2.36, 3.11, 7.98, 2.51, 4.50, 0.74, 4.66, 6.99, 0.07],  //# sadness
            [2.51, 2.36, 2.36, 7.98, 2.51, 2.43, 0.15, 2.36, 4.20, 0.01],  //# surprise
            [79.50, 58.36, 53.99, 65.27, 46.88, 64.48, 81.58, 74.62, 80.99, 42.99]  //# neutral
          ],
          hotData: {
            "time": ["2025/1/1", "2025/1/2", "2025/1/3", "2025/1/4", "2025/1/5", "2025/1/6", "2025/1/7", "2025/1/8",
              "2025/1/9", "2025/1/10", "2025/1/11", "2025/1/12", "2025/1/13", "2025/1/14", "2025/1/15", "2025/1/16",
              "2025/1/17", "2025/1/18", "2025/1/19", "2025/1/20"
            ],
            "popularity": [
              200,
              198,
              638,
              10,
              356,
              176,
              976,
              814,
              536,
              299,
              542,
              78,
            ],
            "pre_popularity": [
              594,
              298,
              743,
              34,
              265,
              89,
              786,
              658,
              873,
              468,
              671,
              46,
              331,
              643,
              198,
              452,
              293,
              126,
              88,
              456
            ]
          },
          keyCharaData: {
            "约瑟夫・奥恩":{
              event:[
                "作为黎巴嫩军队总司令，自去年 11 月下旬以来，一直在监督黎巴嫩军方在黎南部的部署工作，执行黎以停火协议。",
                "于 1 月 9 日就任黎巴嫩第十四任总统，结束了黎总统职位空缺两年多的政治真空局面，改变了黎巴嫩的政治格局。",
                "其当选被认为反映出黎巴嫩真主党影响力已被削弱，同时沙特在黎巴嫩的影响力正在复苏。"
              ],
              predict:"可能推动黎巴嫩与以色列之间的停火协议继续执行，也可能在处理与真主党关系问题上引发新的舆论焦点，还可能影响黎巴嫩与周边国家及国际社会的关系走向，进而引发相关舆情讨论。"
            },
            "安东尼奥·古特雷斯":{
              event:[
                "作为联合国秘书长，强烈谴责以色列的空袭行为，呼吁各方保持克制，避免局势进一步升级，引发了国际社会对以色列空袭黎巴嫩事件的高度关注。",
                "呼吁国际社会采取行动，推动以色列和黎巴嫩之间的停火谈判，影响了大众对解决该地区冲突的期望和方向。",
                "对以色列空袭造成的平民伤亡表示哀悼，强调保护平民的重要性，引导舆论对以色列军事行动的道德审视，使更多人关注战争对无辜民众的影响。"
              ],
              predict:"可能会继续推动国际社会对以色列和黎巴嫩冲突的调解工作，促使各方回到谈判桌前，其相关表态和行动将继续影响国际舆论对此次冲突的关注焦点和解决路径的探讨，也可能对以色列的后续军事行动产生一定的舆论压力。"
            },
            "黎巴嫩真主党总书记纳斯鲁拉":{
              event:[
                "纳斯鲁拉在以色列空袭黎巴嫩期间的言行和领导行动，对黎巴嫩国内民众和真主党的士气产生了重要影响，其号召民众抵抗以色列侵略的言论激发了黎巴嫩民众的民族主义情绪和抵抗意志。",
                "纳斯鲁拉的领导使得真主党在与以色列的冲突中展现出较强的抵抗能力，其军事行动和战术安排对以色列的军事行动产生了一定的牵制作用，影响了国际舆论对黎以冲突双方力量对比的判断。",
                "纳斯鲁拉的死亡或生存情况成为国际舆论关注的焦点之一，其个人命运与黎巴嫩真主党的未来以及地区局势紧密相连，其相关动态引发了国际社会对黎巴嫩局势和中东地区和平稳定的广泛关注。"
              ],
              predict:"如果纳斯鲁拉能够继续领导真主党进行抵抗，可能会进一步激发黎巴嫩民众的抵抗情绪，使黎以冲突持续升级，引发更多的国际关注和干预；若纳斯鲁拉出现意外，可能会导致真主党内部出现权力真空或分裂，引发黎巴嫩国内政治局势的动荡，同时也会影响地区力量格局和国际舆论对黎巴嫩局势的预期。"
            },
          }
        },
        'SpaceX完成首次“筷子夹火箭”':{
          emotionData:[
            [11.00, 4.22, 0.32, 21.20, 1.28, 39.82, 0.33, 0.65, 0.79, 4.12],  //# anger
            [3.22, 2.33, 4.22, 3.22, 0.01, 4.50, 0.15, 12.00, 0.13, 5.20], // # disgust
            [6.57, 0.60, 3.44, 1.54, 0.02, 1.94, 1.12, 0.04, 0.48, 2.33],  //# fear
            [72.08, 4.86, 79.87, 1.51, 94.57, 39.46, 0.45, 8.00, 1.73, 0.96], // # joy
            [4.22, 0.43, 4.55, 0.66, 3.30, 6.60, 0.31, 8.32, 0.78, 5.40], // # sadness
            [1.11, 0.23, 6.55, 9.30, 0.05, 4.28, 97.27, 1.82, 0.01, 6.30], // # surprise
            [1.80, 87.33, 1.05, 62.57, 0.77, 3.40, 0.37, 69.17, 44.76, 75.69]  //# neutral
          ],
          hotData: {
            "time": ["2025/1/1", "2025/1/2", "2025/1/3", "2025/1/4", "2025/1/5", "2025/1/6", "2025/1/7", "2025/1/8",
              "2025/1/9", "2025/1/10", "2025/1/11", "2025/1/12", "2025/1/13", "2025/1/14", "2025/1/15", "2025/1/16",
              "2025/1/17", "2025/1/18", "2025/1/19", "2025/1/20"
            ],
            "popularity": [
              356,
              89,
              635,
              10,
              321,
              77,
              976,
              814,
              876,
              678,
              298,
              78,
            ],
            "pre_popularity": [
              200,
              167,
              743,
              34,
              265,
              176,
              786,
              658,
              756,
              468,
              135,
              46,
              331,
              643,
              12,
              88,
              456,
              98,
              45,
              293
            ]
          },
        },
        '法国四换总理':{
          emotionData:[
            [3.22, 1.72, 1.09, 0.31, 1.55, 6.67, 0.26, 0.23, 2.34, 76.17],  //# anger
            [2.10, 2.56, 0.11, 0.04, 2.30, 26.30, 0.05, 6.10, 2.66, 7.60],  //# disgust
            [9.80, 2.07, 5.55, 0.12, 3.22, 2.10, 0.07, 23.00, 8.00, 1.25],  //# fear
            [6.40, 23.86, 56.82, 72.69, 5.69, 3.00, 0.12, 63.43, 0.11, 0.18],  //# joy
            [9.44, 18.72, 0.12, 0.04, 4.76, 4.30, 0.06, 1.30, 12.20, 0.97],  //# sadness
            [4.20, 49.67, 6.98, 19.44, 0.01, 9.11, 50.05, 5.56, 7.30, 7.98],  //# surprise
            [64.84, 1.40, 29.34, 7.36, 82.47, 48.52, 49.39, 0.38, 67.39, 5.85]  //# neutral
          ],
          hotData: {
            "time": ["2025/1/1", "2025/1/2", "2025/1/3", "2025/1/4", "2025/1/5", "2025/1/6", "2025/1/7", "2025/1/8",
              "2025/1/9", "2025/1/10", "2025/1/11", "2025/1/12", "2025/1/13", "2025/1/14", "2025/1/15", "2025/1/16",
              "2025/1/17", "2025/1/18", "2025/1/19", "2025/1/20"
            ],
            "popularity": [356, 89, 635, 46, 321, 77, 976, 276, 876, 322, 198, 78,],
            "pre_popularity": [200, 167, 743, 12, 265, 176, 786, 122, 756, 468,
              234, 46, 187, 653, 122, 88, 763, 98, 259, 345]
          },
        }
      },
      emotionXData:[],
      emotionYData:[],
      hotXData:[],
      hotYData:[],
      keyCharaData:{},
    }
  },
  mounted() {
    this.getData(this.selectedEvent)
    console.log(this.getNextTenDays())
  },
  methods: {
    goToIndex3() {
      console.log('goToIndex3 called');  // 确认是否被调用
      this.$nextTick(() => {
        window.location.href = '/index3.html'; // 跳转到 index3.html
      });
    },
    getData(event) {
      this.emotionXData = this.getNextTenDays()
      this.emotionYData = this.dataset[event].emotionData
      this.hotXData = this.dataset[event].hotData.time
      this.hotYData = []
      this.hotYData.push(this.dataset[event].hotData.popularity)
      this.hotYData.push(this.dataset[event].hotData.pre_popularity)
      console.log("hotYData",this.hotYData)
    },
    changeEvent(){
      this.getData(this.selectedEvent)
    },
    drawChart(){

    },
    getNextTenDays(){
      const today = new Date();
      const dateList = [];

      for (let i = 1; i <= 10; i++) {
        const nextDate = new Date(today);
        nextDate.setDate(today.getDate() + i); // 设置为今天加 i 天
        const year = nextDate.getFullYear();
        const month = String(nextDate.getMonth() + 1).padStart(2, '0'); // 月份从 0 开始，需加 1
        const day = String(nextDate.getDate()).padStart(2, '0');

        dateList.push(`${year}-${month}-${day}`); // 组织成 YYYY-mm-dd
      }
      return dateList;
    },
    charaNodeClick(data){
      this.selectedChara = data;
      this.keyCharaData = this.dataset[this.selectedEvent].keyCharaData[data];
      console.log(this.keyCharaData)
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

.key_chara_eventList{
  overflow-y: auto; /* 启用垂直滚动条 */
  height: 100%;
  width:100%;
}
/* 滚动条整体部分 */
.key_chara_eventList::-webkit-scrollbar {
  width: 0; /* 滚动条的宽度 */
}
/deep/.el-dropdown-menu__item{
  padding: 0 !important;
}
/* 自定义下拉菜单的宽度 */
.custom-dropdown-menu {
  min-width: 150px;  /* 设置最小宽度 */
  width: 150px;      /* 设置固定宽度，也可以调整为适合你的值 */
  text-align: center;
}

</style>
