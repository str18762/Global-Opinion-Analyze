<template>
  <dv-full-screen-container>
    <!-- 第一行 -->
    <div class="module_box" style="height: 10vh;">
      <div style="flex: 0 1 30%;">
        <dv-decoration-10 style="width:100%;height:50px;" />
      </div>
      <div style="flex: 0 1 40%;">
        <div style="display: flex;width: 100%;">
          <dv-decoration-8 style="width:300px;height:50px;flex:1;transform: rotateY(180deg);" />
          <dv-decoration-11 style="width:200px;height:60px;flex:1">热评人物</dv-decoration-11>
          <dv-decoration-8 style="width:300px;height:50px;flex:1" />
        </div>
      </div>
      <div style="flex: 0 1 30%;">
        <dv-decoration-10 style="width: 100%;height:50px;transform: rotateY(180deg);" />
      </div>
    </div>

    <div id="index">
      <div class="koi-body">
        <div class="layoutHome">
          <el-row :gutter="10">
            <el-col :span="6">
              <div style="height: calc(100vh - 5vh); overflow: hidden;">
                <div class="box" style="padding:3px; height: 100%;">
                  <div style="height: 100%;overflow: hidden">
                    <h2 style="text-align: center; margin-top: 20px; margin-bottom: 20px;">总统推文</h2>
                    <el-scrollbar style="height: 90%;" always>
                      <div v-for="(item, index) in presidents" :key="index" style="margin-bottom: 20px; padding: 0 10px;">
                        <div style="font-weight: bold; font-size: 16px; color: #ffffff; text-shadow: 1px 1px 2px rgba(128, 128, 128, 0.7);">{{index+1}}: {{item.content}}</div>
                        <div style="font-style: italic; font-size: 14px; color: #ffcc00; text-shadow: 1px 1px 2px rgba(255, 204, 0, 0.7);">{{item.username}}</div>
                      </div>
                    </el-scrollbar>
                  </div>
                </div>
              </div>
            </el-col>
            <el-col :span="6">
              <div style="height: calc(100vh - 5vh);overflow: hidden;">
                <div class="box" style="padding:3px; height: 100%;">
                  <div style=" height: 100%;overflow: hidden">
                    <h2 style="text-align: center; margin-top: 20px; margin-bottom: 20px;">企业家推文</h2>
                    <el-scrollbar style="height: 90%;" always>
                      <div v-for="(item, index) in politician" :key="index" style="margin-bottom: 20px; padding: 0 10px;">
                        <div style="font-weight: bold; font-size: 16px; color: #ffffff; text-shadow: 1px 1px 2px rgba(128, 128, 128, 0.7);">{{index+1}}: {{item.content}}</div>
                        <div style="font-style: italic; font-size: 14px; color: #ffcc00; text-shadow: 1px 1px 2px rgba(255, 204, 0, 0.7);">{{item.username}}</div>
                      </div>
                    </el-scrollbar>
                  </div>
                </div>
              </div>

            </el-col>
            <el-col :span="6">
              <div style="height: calc(100vh - 5vh);overflow: hidden;">
                <div class="box" style="padding:3px; height: 100%;">
                  <div style="height: 100%;overflow: hidden">
                    <h2 style="text-align: center; margin-top: 20px; margin-bottom: 20px;">新闻推文</h2>
                    <el-scrollbar style="height: 90%;" always>
                      <div v-for="(item, index) in diplomats" :key="index" style="margin-bottom: 20px; padding: 0 10px;">
                        <div style="font-weight: bold; font-size: 16px; color: #ffffff; text-shadow: 1px 1px 2px rgba(128, 128, 128, 0.7);">{{index+1}}: {{item.content}}</div>
                        <div style="font-style: italic; font-size: 14px; color: #ffcc00; text-shadow: 1px 1px 2px rgba(255, 204, 0, 0.7);">{{item.username}}</div>
                      </div>
                    </el-scrollbar>
                  </div>
                </div>
              </div>
            </el-col>

            <el-col :span="6">
              <div style="height: calc(100vh - 5vh); overflow: hidden;">
                <div class="box" style="padding:3px; height: 100%;">
                  <div style="height: 100%;overflow: hidden">
                    <h2 style="text-align: center; margin-top: 20px; margin-bottom: 20px;">名人明星推文</h2>
                    <el-scrollbar style="height: 90%;" always>
                      <div v-for="(item, index) in economists" :key="index" style="margin-bottom: 20px; padding: 0 10px;">
                        <div style="font-weight: bold; font-size: 16px; color: #ffffff; text-shadow: 1px 1px 2px rgba(128, 128, 128, 0.7);">{{index+1}}: {{item.content}}</div>
                        <div style="font-style: italic; font-size: 14px; color: #ffcc00; text-shadow: 1px 1px 2px rgba(255, 204, 0, 0.7);">{{item.username}}</div>
                      </div>
                    </el-scrollbar>
                  </div>
                </div>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </dv-full-screen-container>
</template>

<script>

export default {
  name: 'Screen2',
  components: {
  },
  data() {
    return {
      presidents: [],
      politician: [],
      diplomats: [],
      economists:[],
    }
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      //得到总统推文
      this.$request.get("/tweets/president").then(res => {
        if (!res) {
          this.$message.info("后台未启动")
          return
        }
        if (res.code === "200") {
          this.presidents = res.data;
        } else {
          this.$message.error(res.msg);
        }
      });
      //得到政治家推文
      this.$request.get("/tweets/politician").then(res=>{
        if (!res) {
          this.$message.info("后台未启动")
          return
        }
        if (res.code === "200") {
          this.politician = res.data;
        } else {
          this.$message.error(res.msg);
        }
      });
      //得到外交官
      this.$request.get("/tweets/diplomats").then(res=>{
        if (!res) {
          this.$message.info("后台未启动")
          return
        }
        if (res.code === "200") {
          this.diplomats = res.data;
        } else {
          this.$message.error(res.msg);
        }
      });
      //得到经济学家推文
      this.$request.get("/tweets/economist").then(res=>{
        if (!res) {
          this.$message.info("后台未启动")
          return
        }
        if (res.code === "200") {
          this.economists = res.data;
        } else {
          this.$message.error(res.msg);
        }
      });
    }
  }
}
</script>

<style scoped>
#dv-full-screen-container {
  color: #fff;
  background-image: url("@/assets/Images/bk.png");

  width: 100vw;
  height: 100vh;
}
.scroll-content {
  padding: 10px; /* 增加内容内边距 */
}

.dv-border-box-13 {
  padding: 3px;
  height: 100%;
  background-color: rgba(0, 0, 0, 0); /* 半透明背景 */
  border: 1px solid rgba(255, 255, 255, 0.2); /* 半透明边框 */
  border-radius: 10px; /* 圆角效果 */
}

.el-scrollbar__wrap {
  height: 90%;
}

h2 {
  font-size: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  text-align: center;
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
</style>
