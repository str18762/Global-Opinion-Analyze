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
        <!--左侧-->
        <el-col :span="6" style="height: 100%">
          <div style="height: 60%;padding: 5px">
            <div class="box">
              <h2>情感分析结果占比</h2>
              <div style="height: 80%">
              </div>
            </div>
          </div>

          <div style="height: 40%;padding: 5px">
            <div class="box">
              <h2>舆情事件讨论热度变化图</h2>
              <div style="height: 100%">
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
                <el-button class="trans_button" style="flex: 1" @click="$router.push('/hotTopics/analysis')">舆情分析</el-button>
                <el-button class="trans_button" style="flex: 1" @click="$router.push('/hotTopics/prediction')">舆情预测</el-button>
                <el-button class="trans_button" style="flex: 1" @click="$router.push('/hotTopics/forewarn')">舆情预警</el-button>
                <el-button class="trans_button" style="flex: 1;color: orangered" @click="$router.push('/hotTopics/spread')">中国特色传播</el-button>
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

export default {
  name: 'HotTopicsSpread',
  components: {

  },
  data() {
    return {
    }
  },
  mounted() {
  },
  methods: {
    getData(event) {

    },
    drawChart(){

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

</style>
