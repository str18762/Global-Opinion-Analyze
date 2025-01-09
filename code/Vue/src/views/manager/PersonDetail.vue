<template>
  <div class="main_page" style="padding: 0 200px">
    <div style="width: 100%;">
      <el-card shadow="never" style="display: flex;flex-wrap: wrap;justify-content: center;">
        <div style="width: 100%;display: flex;justify-content: center">
          <!--解决了图片有时候加载错误的问题-->
          <el-image v-if="character.avatar !== undefined" :src="character.avatar" lazy
            style="height: 300px;border-radius: 50%"></el-image>
        </div>
        <div style="width: 100%;min-width: 600px">
          <div style="display: flex;flex-wrap: wrap;justify-content: center;align-items: center;margin-bottom: 10px">
            <div>
              账户名：{{ character.username_zh }}
            </div>
            <div>
              <button @click="favor(character)" class="my_button" :style="collect_style(character)">
                <span v-if="character.collect">已关注</span>
                <span v-else>+关注</span>
              </button>
            </div>
          </div>

          <div class="chara_property">
            个人简介：{{ character.introduction_zh }}
          </div>
          <div style="display: flex;flex-wrap: wrap">
            <div style="width: 50%">
              <div class="chara_property">
                所在领域：{{ character.field }}
              </div>
              <div class="chara_property">
                地理位置：{{ character.location_zh }}
              </div>
            </div>
            <div style="width: 50%">
              <div class="chara_property">
                关注数：{{ character.follow }}
              </div>
              <div class="chara_property">
                粉丝数：{{ character.followers }}
              </div>
            </div>
          </div>
        </div>
      </el-card>
    </div>
    <div style="width: 100%">
      <el-card shadow="never" style="width: 100%;">
        <div>
          <h2 style="text-align: center;font-size: 20px">{{ character_name }} 的情感变化图</h2>
          <EmotionLineChange :xdata="xdata" :ydata="ydata" @xDataHovered="handleXDataHovered" />
        </div>
        <div>
          <h2 style="text-align: center;font-size: 20px">{{ character_name }} 对应时间的情感分布图（点击或悬停上方对应节点）</h2>
          <h2 style="text-align: center;font-size: 14px">{{ currentXData }}</h2>
          <div style="height: 300px;width: 100%;">
            <EmotionPie :chartData="pie_data" />
          </div>

        </div>
        <!--        <div v-if="data===''">-->
        <!--          暂无该人物情感变化数据-->
        <!--          <el-empty>-->
        <!--            <el-button type="success" @click="applyForChara()">向管理员反应</el-button>-->
        <!--          </el-empty>-->
        <!--        </div>-->

        <br>
        <br>
        <div class="block">
          <h2 style="text-align: center;font-size: 20px">{{ character_name }} 近期关注话题</h2>
          <el-timeline>
            <el-timeline-item v-for="item in itemList" :timestamp=item.time placement="top">
              <el-card>
                <h4>{{ item.description }}</h4>
              </el-card>
            </el-timeline-item>

          </el-timeline>
        </div>

      </el-card>
    </div>
  </div>
</template>

<script>
import EmotionLineChange from "@/components/echarts/EmotionLineChange.vue";
import EmotionPie from "@/components/echarts/PersonEmotionPie.vue";

export default {
  name: "PersonDetail",
  components: {
    EmotionLineChange: EmotionLineChange,
    EmotionPie,
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user_logined') || '{}'),
      character_name: '',
      character: {},
      emotions: [],
      itemList: [],

      data: '',

      //暂时只有 按月三分类
      xdata: ['2023-6', '2023-7', '2023-8', '2023-9', '2023-10', '2023-11', '2023-12', '2024-1', '2024-2', '2024-3', '2024-4', '2024-5', '2024-6', '2024-7'],
      ydata: ['消极', '消极', '中性', '中性', '消极', '中性', '积极', '消极', '消极', '中性', '中性', '消极', '中性', '积极'],

      nowYearData: ['积极', '消极', '积极', '中性'],
      lastYearData: [],


      select_type: '按年三分类',
      types: [
        {
          label: '按年三分类',
          value: '按年三分类',
        },
        {
          label: '按月三分类',
          value: '按月三分类',
        },
      ],

      currentXData: '2024-7', // 当前折线图悬停的 xData 值
      pie_dataset: { '2024-7': [15, 15, 15, 30, 50, 45], '2024-6': [45, 45, 20, 30, 50, 10], '2024-5': [2, 50, 10, 60, 20, 70], '2024-4': [70, 12, 12, 56, 10, 10], '2024-3': [20, 20, 15, 30, 20, 10], '2024-2': [15, 15, 15, 30, 50, 45], '2024-1': [15, 15, 15, 30, 50, 45], '2023-12': [15, 15, 15, 30, 50, 45] },
      pie_data: [15, 15, 15, 30, 50, 45],
    }
  },
  mounted() {
    this.character_name = this.$route.query.character_name;
    this.getCharaData();
    this.getEmotionData();
    this.getItemList();
  },
  methods: {
    getItemList() {
      this.$request
        .get('/api/celebrity-tweets/author',{
          params:{
            author:this.character_name
          }
        })
        .then(res => {
          if (!res) {
            this.$message.info("后台未启动！");
            return;
          }
          if (res.code === '200') {
            this.itemList = res.data.filter(item => item.description!== ''&&item.description!==null);
          } else {
            this.$message.error(res.msg);
        }})
    },
    getCharaData() {
      this.$request.get(this.Global.select_CharaByName + this.character_name + '?username=' + this.user.username).then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.character = res.data;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    getEmotionData() {
      let nowYear = new Date().getFullYear();
      this.$request.get(this.Global.select_Chara_Emotion + "?name=" + this.character_name + "&year=" + nowYear).then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          if (res.data[0].length === 0 && res.data[1].length === 0) {
            console.log('月情感图无数据')
            return;
          }
          this.data = res.data;
          this.nowYearData = res.data[0]
          this.lastYearData = res.data[1]
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    handleXDataHovered(xData) {
      this.currentXData = xData; // 更新当前悬停的 xData 值
      console.log("parent", this.pie_dataset[xData])
      this.pie_data = this.pie_dataset[xData]
    },


    favor(item) {
      if (!this.user.username) {
        this.$message.info("请先登录")
        return;
      }
      if (!item.collect) {//应该要收藏
        this.$request.get(this.Global.collect_Chara + '?name=' + item.name + '&username=' + this.user.username).then(res => {
          if (!res) {
            this.$message.info('后台未启动')
            return;
          }
          if (res.code == '200') {
            item.collect = !item.collect;
            this.content = "已关注";
            this.bg_color = "#f56c6c";
            this.ft_color = "#fef0f0";
            this.$message.success('收藏成功')
          } else {
            this.$message.error(res.msg)
          }
        })
      }
      else {//应该取消收藏
        this.$request.get(this.Global.cancelCollect_Chara + '?name=' + item.name + '&username=' + this.user.username).then(res => {
          if (!res) {
            this.$message.info('后台未启动')
            return;
          }
          if (res.code == '200') {
            item.collect = !item.collect
            this.content = "+关注"
            this.bg_color = "#fef0f0";
            this.ft_color = "#f56c6c";
            this.$message.success('取消收藏成功')
          } else {
            this.$message.error(res.msg)
          }
        })
      }
    },
    collect_style(item) {
      //已收藏
      if (item.collect) { return { backgroundColor: "#f56c6c", color: "#fef0f0", } }
      //未收藏
      else { return { backgroundColor: "#fef0f0", color: "#f56c6c" } }
    },
    applyForChara() {
      this.$message.info("开发中")
    },

    changeType() {
      //向后端请求 按年三分类 数据
      if (this.select_type === '按年三分类') {

      }
      //向后端请求 按年六分类 数据
      else if (this.select_type === '按月三分类') {

      }
    }
  },
}
</script>

<style scoped>
.chara_property {
  width: 100%;
  text-align: center;
  margin-bottom: 20px;
}

.el-link:hover {
  color: red;
}

.main_page {
  background: linear-gradient(135deg, #2c3e50, #ecf0f1);
  min-height: 100vh;
}

.my_button {
  color: #f56c6c;
  background: #fef0f0;
  border: #fbc4c4 solid;
  border-radius: 20px;
  padding: 12px 23px;
  text-align: center;
  font-size: 16px;
  -webkit-transform: scale(0.7);
}

.custom-cell {
  overflow: hidden;
  /* 隐藏超出部分 */
  text-overflow: ellipsis;
  /* 显示省略号 */
  display: -webkit-box;
  /* 必需 */
  -webkit-line-clamp: 1;
  /* 限制在一个块元素显示的文本的行数 */
  -webkit-box-orient: vertical;
  /* 设置或检索伸缩盒对象的子元素的排列方式 */
}
</style>
