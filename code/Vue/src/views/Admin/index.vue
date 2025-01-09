<template>
    <div style="display: flex;justify-content: center;flex-wrap: wrap">
      <el-card style="width: 60%">
        欢迎进入后台管理系统
      </el-card>
      <el-card style="width: 80%;margin-top: 20px">
        <div style="display: flex;flex-wrap: nowrap">
          <div style="width: 50%">
            <div>已分析人物总数：{{characters.length}}</div>
            <div>
              <PieChart :getData="field_data" :title="title"/>
            </div>
          </div>
          <div style="width: 50%">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="积极情感人物" name="first">
                <el-table :data="paginatedData" border stripe: header-cell-class-name="headerBg">
                  <el-table-column prop="name" label="姓名" width="200">
                  </el-table-column>
                  <el-table-column prop="follow" label="关注数">
                  </el-table-column>
                  <el-table-column prop="followers" label="粉丝数">
                  </el-table-column>
                  <el-table-column prop="field" label="领域">
                  </el-table-column>
                </el-table>
                <div style="padding: 10px 0">
                  <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      :current-page="currentPage"
                      :page-size="pageSize"
                      layout="total, prev, pager, next, jumper"
                      :total="totalItems">
                  </el-pagination>
                </div>
              </el-tab-pane>
              <el-tab-pane label="中性情感人物" name="second">
                <el-table :data="paginatedData" border stripe: header-cell-class-name="headerBg">
                  <el-table-column prop="name" label="姓名" width="200">
                  </el-table-column>
                  <el-table-column prop="follow" label="关注数">
                  </el-table-column>
                  <el-table-column prop="followers" label="粉丝数">
                  </el-table-column>
                  <el-table-column prop="field" label="领域">
                  </el-table-column>
                </el-table>
                <div style="padding: 10px 0">
                  <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      :current-page="currentPage"
                      :page-size="pageSize"
                      layout="total, prev, pager, next, jumper"
                      :total="totalItems">
                  </el-pagination>
                </div>
              </el-tab-pane>
              <el-tab-pane label="消极情感人物" name="third">
                <el-table :data="paginatedData" border stripe: header-cell-class-name="headerBg">
                  <el-table-column prop="name" label="姓名" width="200">
                  </el-table-column>
                  <el-table-column prop="follow" label="关注数">
                  </el-table-column>
                  <el-table-column prop="followers" label="粉丝数">
                  </el-table-column>
                  <el-table-column prop="field" label="领域">
                  </el-table-column>
                </el-table>
                <div style="padding: 10px 0">
                  <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      :current-page="currentPage"
                      :page-size="pageSize"
                      layout="total, prev, pager, next, jumper"
                      :total="totalItems">
                  </el-pagination>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </div>
      </el-card>
    </div>
</template>

<script>
import PieChart from "@/components/echarts/PieChart.vue";

export default {
  name: "Index",
  components: {
    PieChart,
  },
  data(){
    return{
      characters:[],
      field_data: [],
      title:'分析人物领域分布图',
      fields:['政治学家','总统','作家','经济学家','运动员','外交学家','其他'],
      activeName:'first',

      allItems:[],
      allItems1:[],
      allItems2:[],
      allItems3:[],
      // 分页后的数据
      paginatedData: [],
      // 当前页码
      currentPage: 1,
      // 每页显示的条目数
      pageSize: 6,
      // 总条目数
      totalItems: 0,

    }
  },
  mounted(){
    this.getAllChara();
    this.getCharaByEmotion();
    this.getFieldChara();
  },
  methods:{
    getAllChara(){
      this.$request.get(this.Global.select_AllChara).then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if(res.code === '200') {
          this.characters=res.data;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    getCharaByEmotion(){
      this.$request.get("/api/character/selectCharaByEmotion?emotion=正面情感").then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if(res.code === '200') {
          this.allItems1=res.data;
          this.allItems=this.allItems1;
          this.totalItems=this.allItems.length;
          this.fetchData()
        } else {
          this.$message.error(res.msg);
        }
      })

      this.$request.get("/api/character/selectCharaByEmotion?emotion=中性情感").then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if(res.code === '200') {
          this.allItems2=res.data;
        } else {
          this.$message.error(res.msg);
        }
      })

      this.$request.get("/api/character/selectCharaByEmotion?emotion=负面情感").then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if(res.code === '200') {
          this.allItems3=res.data;
        } else {
          this.$message.error(res.msg);
        }
      })
    },

    async getFieldChara(){
      for(let i=0;i<this.fields.length;i++) {
        try {
          const data = await this.requestFieldChara(i); // 等待fetchDataFromAPI的Promise解决
          var t ='{"name":"'+this.fields[i]+'","value":"'+data+'"}';
          this.field_data.push(JSON.parse(t))
        } catch (error) {
          console.error('获取数据失败:', error);
        }
      }
    },

    async requestFieldChara(index) {
      return new Promise(resolve => {
        this.$request.get("/api/character/selectByField" + "?field=" + this.fields[index]).then(res => {
          if (!res) {
            this.$message.info("后台未启动！");
            return;
          }
          if (res.code === '200') {
            resolve(res.data.length)
          } else {
            this.$message.error(res.msg);
          }
        })
      });



    },
    handleClick(tab, event) {
      switch (this.activeName){
        case "first":
          this.allItems=this.allItems1;
          break;
        case "second":
          this.allItems=this.allItems2;
          break;
        case "third":
          this.allItems=this.allItems3;
          break;
      }
      this.totalItems=this.allItems.length;
      this.fetchData()
    },

    fetchData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      this.paginatedData = this.allItems.slice(start, end);
    },
    // 处理每页数量变化
    handleSizeChange(val) {
      this.pageSize = val;
      this.fetchData();
    },
    // 处理当前页码变化
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchData();
    }

  },

}
</script>

<style>

</style>