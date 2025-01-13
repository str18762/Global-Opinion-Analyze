<template>
  <div class="out_box" style="color: #00ffff;height: 100%">
    <div id="alertOverlay" class="alert-active">
      <div></div>
    </div>
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
              <h2>负面情感分领域趋势</h2>
              <div style="height: 80%">
                <WarnField style="height: 100%;" />
              </div>
            </div>
          </div>

          <div style="height: 40%;padding: 5px">
            <div class="box">
              <h2>舆情监测信息</h2>
              <div class="alert-container">
                <div v-for="(item, index) in alertData" :key="index" class="alert-item">
                  <div class="alert-icon"></div>
                  <div class="alert-text">{{ item }}</div>
                </div>
              </div>
            </div>
          </div>

        </el-col>
        <!--中间-->
        <el-col :span="12" style="height: 100%">

          <div style="height: 20%;padding: 5px">
            <div class="box">
              <div style="height: 50%;display: flex;justify-content: center">
                <el-select v-model="selectedEvent" filterable placeholder="选择话题" @change="changeEvent"
                  style="width: 50%">
                  <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
                <div style="position: absolute;right: 5%">
                  <div style="display: flex;justify-content: center;">
                    <el-link :underline="false" type="danger" style="font-size: 20px"
                      @click="$router.push('/homePage')">
                      返回首页<i class="el-icon-d-arrow-right" />
                    </el-link>
                  </div>
                </div>
              </div>
              <div style="height: 50%;display: flex;">
                <el-button class="trans_button" style="flex: 1"
                  @click="$router.push('/hotTopics/analysis')">舆情分析</el-button>
                <el-button class="trans_button" style="flex: 1"
                  @click="$router.push('/hotTopics/prediction')">舆情预测</el-button>
                <el-button class="trans_button" style="flex: 1;text-decoration: underline"
                  @click="$router.push('/hotTopics/forewarn')">舆情预警</el-button>
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

          <div style="height: 40%;padding: 5px">
            <div class="box">
              <h2>舆情事件相关新闻</h2>
              <div style="height: 80%">
                <InfluenceLevel :chartData="level" />
              </div>
            </div>
          </div>
          <div style="height: 40%;padding: 5px">
            <div class="box">
              <h2>热度图</h2>
              <div style="height: 80%">
                <HotLineChart :xData="xData" :obj="obj" />
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
                  <h2>不同国家舆情风险等级</h2>
                  <div class="table-container">
                    <div class="table-header">
                      <div class="header-cell">序号</div>
                      <div class="header-cell">名称</div>
                      <div class="header-cell">风险</div>
                      <div class="double-header-cell">舆情趋势</div>
                    </div>
                    <div class="table-body" ref="tableBody">
                      <div v-for="(item, index) in currentData" :key="index"
                        :class="['table-row', item.index % 2 === 0 ? 'even-row' : '']">
                        <div class="cell">{{ item.index }}</div>
                        <div class="cell">{{ item.name }}</div>
                        <div class="cell">{{ item.risk }}</div>
                        <div class="double-cell">{{ item.neg }}</div>
                      </div>
                    </div>
                    <div class="next-table-body" ref="nextTableBody">
                      <div v-for="(item, index) in nextData" :key="index"
                        :class="['table-row', item.index % 2 === 0 ? 'even-row' : '']">
                        <div class="cell">{{ item.index }}</div>
                        <div class="cell">{{ item.name }}</div>
                        <div class="cell">{{ item.risk }}</div>
                        <div class="double-cell">{{ item.neg }}</div>
                      </div>
                    </div>
                  </div>
                </div>
                <div style="height: 50%;margin-top: 20px">
                  <h2>舆情处理与应对方案</h2>
                  <div style="height: 100%;display: flex;justify-content: center;">
                    <div class="measure-content">{{ measure }}</div>
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
import WarnField from "@/components/echarts/HotTopicsChart/WarnField.vue";
import HotLineChart from "@/components/echarts/HotTopicsChart/HotLineChart.vue";
import InfluenceLevel from "@/components/echarts/HotPersonChart/InfluenceLevel.vue";

export default {
  name: 'HotTopicsForewarn',
  components: {
    WarnField: WarnField,
    HotLineChart: HotLineChart,
    InfluenceLevel,
  },
  data() {
    return {
      selectedEvent: '美国大选',
      options: [
        {
          label: '美国大选',
          value: '美国大选',
        },
        {
          label: '韩国总统尹锡悦被捕',
          value: '韩国总统尹锡悦被捕',
        },
        {
          label: '以色列空袭黎巴嫩',
          value: '以色列空袭黎巴嫩',
        },
        {
          label: 'spaceX完成首次“筷子夹火箭”',
          value: 'spaceX完成首次“筷子夹火箭”',
        },
        {
          label: '法国四换总理',
          value: '法国四换总理',
        },
      ],
      taskList: [
        { index: 1, name: '中国', risk: '低', neg: '平稳', },
        { index: 2, name: '美国', risk: '高', neg: '上升', },
        { index: 3, name: '英国', risk: '中', neg: '平稳', },
        { index: 4, name: '法国', risk: '高', neg: '上升', },
        { index: 5, name: '日本', risk: '中', neg: '下降', },
        { index: 6, name: '印度', risk: '高', neg: '上升', },
        { index: 7, name: '德国', risk: '低', neg: '下降', },
        { index: 8, name: '澳大利亚', risk: '中', neg: '上升', },
        { index: 9, name: '西班牙', risk: '低', neg: '平稳', },
        { index: 10, name: '巴西', risk: '中', neg: '上升', },
        { index: 11, name: '俄罗斯', risk: '中', neg: '上升', },
        { index: 12, name: '南非', risk: '中', neg: '平稳', },
        { index: 13, name: '叙利亚', risk: '高', neg: '上升', },
        { index: 14, name: '乌克兰', risk: '高', neg: '上升', },
        { index: 15, name: '以色列', risk: '高', neg: '上升', },
        { index: 16, name: ' 加拿大 ', risk: ' 中 ', neg: ' 平稳 ', },
        { index: 17, name: ' 意大利 ', risk: ' 中 ', neg: ' 上升 ', },
        { index: 18, name: ' 韩国 ', risk: ' 中 ', neg: ' 平稳 ', },
        { index: 19, name: ' 土耳其 ', risk: ' 高 ', neg: ' 上升 ', },
        { index: 20, name: ' 墨西哥 ', risk: ' 中 ', neg: ' 平稳 ', },
        { index: 21, name: ' 阿根廷 ', risk: ' 中 ', neg: ' 下降 ', },
        { index: 22, name: ' 马来西亚 ', risk: ' 低 ', neg: ' 平稳 ', },
        { index: 23, name: ' 新加坡 ', risk: ' 低 ', neg: ' 下降 ', },
        { index: 24, name: ' 泰国 ', risk: ' 中 ', neg: ' 平稳 ', },
        { index: 25, name: ' 菲律宾 ', risk: ' 中 ', neg: ' 上升 ', },
      ],
      xData: ["2024-09", "2024-10", "2024-11", "2024-12"],
      eventHotData: {
        '美国大选': {
          '中国': [0, 13, 1374, 241],
          '美国': [56, 1023, 9173, 3012],
          '英国': [0, 24, 1597, 98],
          '印度': [0, 12, 342, 63],
          '日本': [0, 3, 525, 42],
          '法国': [0, 6, 1374, 241],
          '德国': [0, 34, 321, 53],
          '澳大利亚': [0, 2, 229, 37],
          '西班牙': [0, 0, 194, 15],
        },
        '韩国总统尹锡悦被捕': {
          '中国': [0, 0, 0, 1523],
          '美国': [0, 0, 0, 432],
          '英国': [0, 0, 0, 183],
          '印度': [0, 0, 0, 628],
          '日本': [0, 0, 0, 1035],
          '法国': [0, 0, 0, 23],
          '德国': [0, 0, 0, 18],
          '澳大利亚': [0, 0, 0, 9],
          '西班牙': [0, 0, 0, 5],
        },
        '以色列空袭黎巴嫩': {
          '中国': [0, 48, 194, 32],
          '美国': [182, 137, 243, 85],
          '英国': [45, 15, 57, 35],
          '印度': [24, 15, 65, 21],
          '日本': [0, 3, 24, 6],
          '法国': [0, 6, 25, 14],
          '德国': [142, 34, 52, 34],
          '澳大利亚': [0, 24, 143, 52],
          '西班牙': [14, 0, 53, 15],
        },
        'spaceX完成首次“筷子夹火箭”': {
          '中国': [0, 217, 24, 0],
          '美国': [0, 721, 32, 12],
          '英国': [0, 210, 24, 0],
          '印度': [0, 183, 9, 0],
          '日本': [0, 103, 5, 0],
          '法国': [0, 97, 23, 0],
          '德国': [0, 45, 0, 0],
          '澳大利亚': [65, 0, 3, 0],
          '西班牙': [0, 75, 2, 0],
        },
        '法国四换总理': {
          '中国': [132, 37, 0, 232],
          '美国': [57, 41, 0, 170],
          '英国': [83, 0, 0, 315],
          '印度': [23, 0, 0, 162],
          '日本': [45, 0, 0, 210],
          '法国': [302, 32, 0, 428],
          '德国': [84, 0, 0, 142],
          '澳大利亚': [54, 0, 0, 22],
          '西班牙': [49, 0, 0, 54],
        },
      },
      obj: {},
      currentIndex: 0,
      currentData: [],
      nextIndex: 8,
      nextData: [],
      intervalId: null,
      timeId: null,
      timeId2: null,
      timeId3: null,
      HotLineData: [],
      level: 0.75,
      eventRisk: [0.75, 0.80, 0.85, 0.45, 0.67],
      alertData: ['2024年9月公众负面情绪较高，其中悲伤情绪占比最高',
        '2024年11月公众负面情绪较高，其中惊讶情绪占比最高',
        '2024年12月公众负面情绪较高，其中厌恶情绪居多',
      ],
      measureData: ['拓展多元市场：降低对美国单一市场的依赖，积极加强与欧盟、东盟、“一带一路” 沿线国家等的贸易合作，签署更多自由贸易协定，如《区域全面经济伙伴关系协定》（RCEP）等，开辟新的贸易渠道，分散贸易风险。\n强化产业链韧性：加大对关键核心技术和零部件的研发投入，实现自主可控，减少对美国及其他国家的进口依赖。同时，推动产业链供应链的数字化、智能化升级，提高产业链的协同效率和抗风险能力。\n深化两岸经济文化交流：在坚持一个中国原则的前提下，继续加强两岸经济合作、文化交流和人员往来，增进两岸同胞的感情和认同，削弱 “台独” 势力的生存空间。',
        '外交层面：秉持不干涉他国内政原则，密切关注局势发展，通过外交渠道与韩国保持沟通，表达对韩国政治局势稳定的期望，鼓励韩方通过合法、和平的方式解决内部问题。\n经济合作方面：持续关注韩国国内政策变化和经济形势，对中韩之间的经贸往来进行评估和调整，对于可能影响到中国在韩企业利益的情况，及时提供必要的支持和帮助，确保双边经济合作的平稳进行。安全领域：加强对朝鲜半岛局势的监测和评估，与相关各方保持沟通协调，防止因韩国总统被捕事件引发地区安全形势的不稳定。同时，做好应对可能出现的突发情况的准备，确保中国在该地区的安全利益不受损害。',
        '外交途径：在联合国等国际组织中积极发声，强烈谴责以色列的空袭行为，呼吁双方保持克制，通过和平谈判解决争端。利用与中东地区国家的友好关系，积极斡旋，推动双方回到谈判桌前。\n维和与安全保护：加强对中国在黎巴嫩维和部队的安全保护，提供必要的军事支持和物资保障，确保维和人员的生命安全和任务的顺利进行。同时，密切关注中国在中东地区其他利益相关区域的安全形势，采取相应的防范措施。\n人道主义援助：根据黎巴嫩的实际需求，提供必要的人道主义援助，帮助黎巴嫩应对空袭造成的人员伤亡和基础设施破坏等问题，展现中国作为负责任大国的担当。',
        '技术研发层面：鼓励中国航天企业和科研机构加大对火箭回收技术的研发投入，借鉴 SpaceX 的成功经验，结合中国国情，探索适合中国的火箭回收方案，如继续优化长征八号火箭的部分可重复使用技术，加快可重复使用运载火箭的研制进程。\n人才培养与创新激励：加强航天领域的人才培养，建立完善的人才培养体系，培养一批既懂技术又有创新能力的航天人才。同时，鼓励科研人员勇于创新，营造良好的创新环境，为航天技术的突破提供支持。\n产业发展与合作：推动中国航天产业的商业化发展，加强航天企业之间的合作与交流，整合资源，提高产业的整体竞争力。鼓励商业航天企业参与火箭回收技术的研发和应用，促进航天技术与经济社会的深度融合。',
        '外交与政治合作：保持与法国的高层交往和政治对话，了解法国政治局势变化的原因和趋势，在相互尊重的基础上，就共同关心的国际问题和双边合作进行沟通与协调，加强政治互信。\n经济合作调整：密切关注法国国内经济政策的调整和变化，对中法之间的贸易、投资等经济合作进行评估和优化。针对法国可能出现的市场需求变化和产业结构调整，及时调整中国企业在法的投资和经营策略，降低风险，寻找新的合作机遇。\n文化与人文交流：加强中法之间的文化、教育、科技等人文领域的交流与合作，增进两国人民之间的相互了解和友谊，为中法关系的长期稳定发展奠定坚实的民意基础。通过人文交流，促进法国社会对中国的全面认识，减少误解和偏见，为双边合作创造良好的社会氛围。'
      ],
      measure: '',
    }
  },
  mounted() {
    this.getData(this.selectedEvent);
    this.updateData();
    this.intervalId = setInterval(() => {
      this.scrollData();
    }, 5000);
  },
  beforeDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId)
    }
    if (this.timeId) {
      clearTimeout(this.timeId)
    }
    if (this.timeId2) {
      clearTimeout(this.timeId2)
    }
    if (this.timeId3) {
      clearTimeout(this.timeId3)
    }
  },
  methods: {
    getData(event) {
      this.obj = this.eventHotData[event]
      const index = this.options.map((item, index) => {
        if (item.value === event) {
          return index;
        }
        return -1
      }).filter(index => index !== -1);
      this.level = this.eventRisk[index]
      this.measure = this.measureData[index]
      console.log('index:', index, 'level:', this.level)
      console.log(this.obj)
    },
    changeEvent() {
      this.getData(this.selectedEvent)
    },
    drawChart() {

    },
    updateData() {
      this.currentData = this.taskList.slice(this.currentIndex, this.currentIndex + 8);
      this.nextData = this.taskList.slice(this.nextIndex, this.nextIndex + 8);
    },
    scrollData() {
      this.currentIndex = this.nextIndex;
      this.nextIndex += 8
      if (this.nextIndex >= this.taskList.length) {
        this.nextIndex = 0;
      }

      this.$nextTick(() => {
        this.$refs.tableBody.classList.add('scroll-animation');
        this.timeId = setTimeout(() => {
          this.$refs.tableBody.classList.remove('scroll-animation');
        }, 2000);
        this.$refs.nextTableBody.classList.add('scroll-animation');
        this.timeId2 = setTimeout(() => {
          this.$refs.nextTableBody.classList.remove('scroll-animation');
        }, 2000);
        this.timeId3 = setTimeout(() => {
          this.updateData();
        }, 2000);
      });
    },
    goToIndex3() {
      console.log('goToIndex3 called');  // 确认是否被调用
      this.$nextTick(() => {
        window.location.href = '/index3.html'; // 跳转到 index3.html
      });
    },
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

.out_box {
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

.trans_button {
  background-color: transparent;
  color: #00ffff;
  border: 1px solid #00ffff;
  border-radius: 5px;
  padding: 5px 10px;
  margin-right: 10px;
  cursor: pointer;
  font-size: 20px;
}

.trans_button:hover {
  background-color: #00ffff;
  color: #0d0d0d;
}

.table-container {
  height: 90%;
  /* padding: 20px; */
  overflow-y: hidden;
}

.table-header {
  height: 40px;
  position: relative;
  display: flex;
  background-color: rgb(45, 52, 54);
  padding: 10px;
  z-index: 2;
}

.header-cell {
  flex: 1;
  width: 20%;
  text-align: center;
  font-weight: bold;
}

.double-header-cell {
  flex: 1;
  width: 40%;
  text-align: center;
  font-weight: bold;
}

.table-body {
  height: 320px;
  overflow-y: hidden;
}

.new-table-body {
  height: 320px;
  overflow-y: hidden;
}

.table-row {
  height: 40px;
  display: flex;
  padding: 10px;
}

.cell {
  flex: 1;
  width: 20%;
  text-align: center;
}

.double-cell {
  flex: 1;
  width: 40%;
  text-align: center;
}

.even-row {
  background-color: rgba(54, 54, 65, 0.829);
  border-radius: 5px;
}

.scroll-animation {
  animation: scrollUp 2s ease-in-out;
}

@keyframes scrollUp {
  0% {
    transform: translateY(0%);
  }

  100% {
    transform: translateY(-100%);
  }
}

#alertOverlay::before,
#alertOverlay::after {
  content: "";
  position: absolute;
  width: 120px;
  /*var(--alert-size);*/
  height: 100%;
}

#alertOverlay>div::before,
#alertOverlay>div::after {
  content: "";
  position: absolute;
  width: 100%;
  height: 120px;
  /*var(--alert-size);*/
}

#alertOverlay::before {
  background: linear-gradient(to right, red, transparent);
  top: 0;
  left: 0;
  transform: rotate(0deg);
}

#alertOverlay::after {
  background: linear-gradient(to left, red, transparent);
  top: 0%;
  left: 100%;
  transform: rotate(0deg) translate(calc(-1 * 120px), 0px);
  /*var(--alert-size)*/
}

#alertOverlay>div::before {
  background: linear-gradient(to top, red, transparent);
  top: 0;
  left: 0;
  transform: rotate(180deg);
}

#alertOverlay>div::after {
  background: linear-gradient(to top, red, transparent);
  top: 100%;
  left: 0;
  transform: rotate(0deg) translate(0px, calc(-1 * 120px));
  /*var(--alert-size)*/
}

#alertOverlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  opacity: 1;
  transition: opacity 0.5s;
  pointer-events: none;
  z-index: 1;
}

.alert-active {
  animation: blink 1s infinite;
}

@keyframes blink {
  0% {
    opacity: 0;
  }

  50% {
    opacity: 0.5;
  }

  100% {
    opacity: 0;
  }
}

.alert-container {
  display: flex;
  flex-direction: column;
  gap: 5px;
  padding: 10px;
  align-items: center;
  overflow: auto;
  max-height: 90%;
}

.alert-item {
  width: 90%;
  height: 25%;
  display: flex;
  gap: 5px;
  padding: 10px;
  border: 2px solid rgba(255, 0, 0, 0.5);
  border-radius: 3px;
  background:
    linear-gradient(to bottom, rgba(255, 0, 0) 0%, rgba(255, 0, 0, 0) 20px) top,
    linear-gradient(to top, rgba(255, 0, 0) 0%, rgba(255, 0, 0, 0) 20px) bottom,
    linear-gradient(to right, rgba(255, 0, 0) 0%, rgba(255, 0, 0, 0) 20px) left,
    linear-gradient(to left, rgba(255, 0, 0) 0%, rgba(255, 0, 0, 0) 20px) right;
}


.alert-icon {
  align-self: center;
  width: 30px;
  height: 30px;
  background-image: url("@/assets/Images/警告图标svg.png");
}

.alert-text {
  width: 90%;
  color: red;
}

.measure-content {
  width: 90%;
  max-height: 80%;
  overflow-y: auto;
}

/* 滚动条整体样式 */
::-webkit-scrollbar {
  width: 8px;
  background-color: transparent;
}

/* 滚动条轨道（未滚动部分）样式 */
::-webkit-scrollbar-track {
  background-color: transparent;
}

/* 滚动条滑块样式 */
::-webkit-scrollbar-thumb {
  background-color: #888;
  border-radius: 4px;
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
