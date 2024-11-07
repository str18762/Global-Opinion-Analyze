<template>
  <div class="influence-analysis">
    <el-card class="box-card">
      <el-form :inline="true" :model="formInline" class="demo-form-inline analysis-form">
        <el-form-item label="选择人物">
          <el-select v-model="formInline.person" placeholder="请选择人物">
            <el-option v-for="item in filteredCharacters" :key="item.name" :label="item.username"
                       :value="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">分析</el-button>
        </el-form-item>
      </el-form>

      <div v-if="analysisResult" class="analysis-result">
        <h3 class="analysis-title">{{ analysisResult.person }} 的影响力分析结果</h3>
        <p>媒体曝光度：{{ analysisResult.mediaExposure }}</p>
        <p>社交媒体影响力：{{ analysisResult.socialMediaInfluence }}</p>
        <p>公众评价：{{ analysisResult.publicOpinion }}</p>
        <el-progress :percentage="analysisResult.totalInfluence" status="success"></el-progress>
      </div>

      <div class="analysis-graph">
        <div>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>新增粉丝量</span>
            </div>
            <div id="new-fan-chart" class="chart"></div>
          </el-card>
        </div>
        <div>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>新增评论量</span>
            </div>
            <div id="new-comment-chart" class="chart"></div>
          </el-card>
        </div>
      </div>

    </el-card>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return {
      formInline: {
        person: ''
      },
      persons: [
        { value: 'person1', label: '人物1' },
        { value: 'person2', label: '人物2' },
        { value: 'person3', label: '人物3' }
      ],
      characters: [
        {
          id: 'test_id',
          photo: 'test_photo',
          username: 'test_username',
          name: 'test_name',
          introduction: 'test_introduction',
          location: 'test_location',
          follow: 'test_follow',
          followers: 'test_followers',
          pic: 'test_pic',
          website: 'test_website',
        }
      ],
      filteredCharacters: [],
      newFanVo: {
        dateList: [],
        fanList: [],
      },
      newCommentVo: {
        dateList: [],
        commentList: [],
      },
      analysisResult: null
    };
  },
  mounted() {
    this.selectAllCharacters();

    this.newFanChart = echarts.init(document.getElementById('new-fan-chart'));
    this.newCommentChart = echarts.init(document.getElementById('new-comment-chart'));
  },
  methods: {
    selectAllCharacters() {
      this.$request.get("/character/selectAll").then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.characters = res.data;
          this.filteredCharacters = this.characters;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    async submitForm() {
      // 模拟分析结果
      await this.$request.get("/character/influence").then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.analysisResult = {
            person: this.formInline.person,
            mediaExposure: res.data.mediaExposure,
            socialMediaInfluence: res.data.socialMediaInfluence,
            publicOpinion: res.data.publicOpinion,
            totalInfluence: res.data.totalInfluence
          };
        } else {
          this.$message.error(res.msg);
        }
      })



      await this.$request.get("/character/getFans").then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.newFanVo.dateList = res.data.dateList.split(',');
          this.newFanVo.fanList = res.data.dataList.split(',');
          console.log(this.newFanVo.fanList)
        } else {
          this.$message.error(res.msg);
        }
      })
      await this.$request.get("/character/getComment").then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.newCommentVo.dateList = res.data.dateList.split(',');
          this.newCommentVo.commentList = res.data.dataList.split(',');
        } else {
          this.$message.error(res.msg);
        }
      })

      const newFansOption = {
        title: {
          text: '月新增粉丝数',
        },
        tooltip: {},
        xAxis: {
          type: 'category',
          data: this.newFanVo.dateList//['A', 'B', 'C']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.newFanVo.fanList,//[120, 200, 150]
            type: 'line'
          }
        ]
      };
      const newCommentOption = {
        title: {
          text: '月新增评论量',
        },
        tooltip: {},
        xAxis: {
          type: 'category',
          data: this.newCommentVo.dateList//['A', 'B', 'C']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.newCommentVo.commentList,//[120, 200, 150]
            type: 'line'
          }
        ]
      };
      this.newFanChart.setOption(newFansOption);
      this.newCommentChart.setOption(newCommentOption);
    }
  }
};
</script>

<style scoped>
.influence-analysis {
  padding: 0px;
  background: linear-gradient(135deg, #bdc3c7, #ecf0f1); /* 添加层次感的渐变背景 */
  border-radius: 15px; /* 使整体区域有圆角效果 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 添加阴影增加立体感 */
}

.box-card {
  margin-bottom: 0px;
  background: linear-gradient(315deg, #ffffff, #e0e0e0); /* 卡片内部背景的渐变效果 */
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
  color: #2c3e50;
  background: linear-gradient(135deg, #ececec, #ffffff); /* 标题背景的渐变效果 */
  padding: 10px;
  border-radius: 5px;
}

.analysis-form {
  margin-bottom: 0px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.analysis-form .el-form-item {
  margin-right: 20px;
}

.analysis-result {
  margin-top: 0px;
  padding: 20px;
  background: linear-gradient(315deg, #ffffff, #ececec); /* 结果显示区域的渐变背景 */
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.analysis-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 0px;
}

.analysis-graph {
  margin-top: 0px;
}

.chart {
  width: 100%;
  height: 400px;
}

.el-row {
  margin-bottom: 20px;
}

.el-col {
  padding: 0 10px;
}

.el-card {
  background-color: transparent;
}

.el-progress {
  margin-top: 10px;
}
</style>


