<template>
  <div style="display: flex;flex-wrap: nowrap;padding: 20px;">
    <div style="width: 100%;display: flex;flex-wrap: nowrap" >
      <div style="width: 20%; margin-left: 16px;" class="side-left">
        <el-card shadow="hover" style="width: 20%; position: fixed;">
          <div style="padding: 20px;">
            <!-- 美国 -->
            <el-progress :stroke-width="15" :percentage="100" :text-inside="true" status="success" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>美国</h5>
              <span>121</span>
            </div>

            <!-- 英国 -->
            <el-progress :stroke-width="15" :percentage="45" :text-inside="true" status="warning" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>英国</h5>
              <span>45</span>
            </div>

            <!-- 澳大利亚 -->
            <el-progress :stroke-width="15" :percentage="14" :text-inside="true" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>澳大利亚</h5>
              <span>14</span>
            </div>

            <!-- 法国 -->
            <el-progress :stroke-width="15" :percentage="7" :text-inside="true" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>法国</h5>
              <span>7</span>
            </div>

            <!-- 加拿大 -->
            <el-progress :stroke-width="15" :percentage="5" :text-inside="true" status="success" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>加拿大</h5>
              <span>5</span>
            </div>

            <!-- 印度 -->
            <el-progress :stroke-width="15" :percentage="3" :text-inside="true" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>印度</h5>
              <span>3</span>
            </div>

            <!-- 新增国家：德国 -->
            <el-progress :stroke-width="15" :percentage="35" :text-inside="true" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>德国</h5>
              <span>35</span>
            </div>

            <!-- 新增国家：日本 -->
            <el-progress :stroke-width="15" :percentage="50" :text-inside="true" status="success" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>日本</h5>
              <span>50</span>
            </div>

            <!-- 新增国家：俄罗斯 -->
            <el-progress :stroke-width="15" :percentage="15" :text-inside="true" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>俄罗斯</h5>
              <span>15</span>
            </div>

            <!-- 新增国家：意大利 -->
            <el-progress :stroke-width="15" :percentage="25" :text-inside="true" status="success" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>意大利</h5>
              <span>25</span>
            </div>

            <!-- 新增国家：西班牙 -->
            <el-progress :stroke-width="15" :percentage="40" :text-inside="true" status="warning" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>西班牙</h5>
              <span>40</span>
            </div>

            <!-- 新增国家：阿根廷 -->
            <el-progress :stroke-width="15" :percentage="50" :text-inside="true" status="success" style="margin-bottom: 0px;"></el-progress>
            <div style="display: flex; height: 30px; justify-content: space-between; font-size: 14px;">
              <h5>阿根廷</h5>
              <span>50</span>
            </div>
          </div>
        </el-card>
      </div>
      <div style="width: 80%;padding: 40px;border-radius: 10px; " >
        <!--      头部区域-->
        <div>
          <div style="width: 100%;display: flex;flex-direction: row-reverse" class="aoe">
            <el-popover placement="bottom-start" trigger="hover" style="margin-left: 20px;display: flex;align-items: center">
              点击向管理员申请
              <div slot="reference" @click="applyForChara()">
                <el-link :underline="false" >没有人物？</el-link>
              </div>
            </el-popover>
            <el-input placeholder="请输入人物姓名或关键词" v-model="searchQuery"
                      clearable @clear="handleClear" @input="handleSearch"
                      style="width: 40%;">
              <el-select slot="append" v-model="rank_mode" placeholder="默认排序" @change="order" style="width: 120px;">
                <el-option v-for="item in ranks" :label="item" :value="item" :key="item">
                </el-option>
              </el-select>
            </el-input>

            <el-popover placement="bottom-start" width="300" trigger="hover">
              <el-row :gutter="10" type="flex" style="flex-wrap:wrap">
                <el-col :span="12" v-for="(item, index) in fields" :key="index" style="height: 30px">
                  <el-link :underline="false" @click="SwitchField(item)">{{item}} </el-link>
                </el-col>
              </el-row>
              <el-button slot="reference" style="width: 100px">{{selectedCategory}}<i class="el-icon-arrow-down"/></el-button>
            </el-popover>
            <div id="switch_mode" style="width: 40%">
              <div>
                <el-button v-if="isListMode" @click="handleModeChange">切换至卡片形式</el-button>
                <el-button v-else @click="handleModeChange">切换至卡片形式</el-button>
              </div>
            </div>
          </div>
        </div>
        <br>
        <!--      人物卡片-->
        <div v-if="isListMode===false" >
          <div>
            <div>
              <el-row :gutter="20" type="flex" style="flex-wrap:wrap">
                <el-col :span="8" v-for="(character, index) in paginatedChara" :key="index">
                  <el-card class="person-card news-item" v-if="character.username" style="height: 220px">
                    <div>
                      <div class="info">
                        <div style="display: flex;flex-wrap: wrap">
                          <div style="width: 40%;display: flex;flex-wrap: wrap;justify-content: center">
                            <!--左半部分 头像区域-->
                            <div style="width: 100%;height: 120px;display: flex;justify-content: center">
                              <el-avatar :src="character.avatar" :size="120"></el-avatar>
                            </div>
                            <div>
                              <div>
                                <h3 style="display: flex;justify-content: center">{{ character.name_zh }}</h3>
                              </div>
                              <div style="display: flex;justify-content: center">
                                <button   @click="favor(character)" class="my_button" :style="collect_style(character)">
                                  <span v-if="character.collect">已关注</span>
                                  <span v-else>+关注</span>
                                </button>
                              </div>
                            </div>
                          </div>
                          <!--有半部分 信息区域-->
                          <div style="width: 60%" @click="viewDetails(character)">
                            <p class="custom-cell"><strong>账户名：</strong>{{ character.username_zh }}</p>
                            <p><strong>地理位置：</strong>{{ character.location_zh }}</p>
                            <p><strong>正在关注：</strong>{{ character.follow }}</p>
                            <p><strong>关注者：</strong>{{ character.followers }}</p>
                            <p><strong>所在领域：</strong>{{ character.field }}</p>
                            <p class="custom-cell" style="margin-top: 20px"><strong>个人简介：</strong>{{ character.introduction_zh }}</p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
            </div>
            <div style="padding: 10px 0;display: flex;justify-content: center">
              <el-pagination
                  :background="true"
                  @current-change="handlePageChange"
                  :current-page="currentPage"
                  :page-sizes="[5, 10, 15, 20]"
                  :page-size="pageSize"
                  layout="total, prev, pager, next, jumper"
                  :total="filteredCharacters.length">
              </el-pagination>
            </div>
          </div>
        </div>

        <!--      人物列表-->
        <div v-if="isListMode===true">
          <div>
            <el-row>
              <el-col :span="24" v-for="(character, index) in paginatedChara" :key="index">
                <el-card shadow="never" class="aoe">
                  <div style="display: flex;flex-wrap: wrap;height: 90px">
                    <div style="width: 60%">
                      <el-popover placement="right-start" trigger="hover">
                        <div style="border-radius: 10px;margin: 0">
                          <div style="display: flex;flex-wrap: wrap">
                            <div style="margin: 10px"><el-avatar :src="character.avatar" :size="60"></el-avatar></div>
                            <div><div>
                              <p><strong>账户名：</strong>{{ character.name_zh }}</p>
                              <p><strong>正在关注：</strong>{{ character.follow }}</p>
                              <p><strong>关注者：</strong>{{ character.followers }}</p>
                            </div></div>
                          </div>
                        </div>
                        <div slot="reference" style="display: inline-block" @click="viewDetails(character)">
                          <div style="display: flex;flex-wrap: nowrap">
                            <div style="height: 80px;display: flex;justify-content: center;align-items: center;margin:0 10px">
                              <el-avatar :src="character.avatar" :size="60" style="cursor: pointer"></el-avatar>
                            </div>
                            <div>
                              <el-link :underline="false" style="font-size: 20px;margin: 10px">{{character.name_zh}}</el-link>
                              <div style="margin-left: 20px;font-size: 15px">领域：{{character.field}}</div>
                              <div class="custom-cell2" style="margin-left: 20px;font-size: 15px">简介：{{character.introduction_zh}}</div>
                            </div>
                          </div>
                        </div>
                      </el-popover>
                    </div>
                    <div style="width: 40%;display: flex;justify-content: end;align-items: center">
                      <button   @click="favor(character)" class="my_button" :style="collect_style(character)">
                        <span v-if="character.collect">已关注</span>
                        <span v-else>+关注</span>
                      </button>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
          <div style="padding: 10px 0;display: flex;justify-content: center">
            <el-pagination
                :background="true"
                @current-change="handlePageChange"
                :current-page="currentPage"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="pageSize2"
                layout="total, prev, pager, next, jumper"
                :total="filteredCharacters.length">
            </el-pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PersonTracking',
  components:{
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user_logined') || '{}'),
      searchQuery: '',
      selectedCategory: '全部领域',
      characters: [],
      filteredCharacters: [],
      selectedCharacter: null,
      dialogVisible: false,
      defaultAvatar: 'path_to_default_image',  // 默认占位图片路径

      liked:false,
      content:'+关注',
      bg_color:"#fef0f0",
      ft_color:"#f56c6c",

      fields:['全部领域','政治','科技','音乐','体育','新闻','时尚','娱乐','电影'],
      rank_mode:'默认排序',
      ranks:['默认排序','粉丝数降序','粉丝数升序'],
      currentPage: 1,
      pageSize: 30,
      pageSize2:30,
      paginatedChara: [],
      isListMode:false,
    };
  },
  mounted() {
    this.selectAllCharacters();
  },
  methods: {
    selectAllCharacters(){
      this.$request.get(this.Global.select_AllChara+'?username='+this.user.username).then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if(res.code === '200') {
          this.characters=res.data;
          this.filteredCharacters = this.characters;
          this.handlePageChange(1)
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    handleSearch() {
      this.filterCharacters();
    },
    handleClear() {
      this.searchQuery = '';
      this.filterCharacters();
    },
    handleModeChange() {
      this.isListMode = !this.isListMode;
      this.handlePageChange(1)
    },
    filterCharacters() {
      this.filteredCharacters = this.characters.filter(character => {
        let matchesCategory = this.selectedCategory === '全部领域' || character.field === this.selectedCategory;
        let matchesQuery = character.name.includes(this.searchQuery) ||
            character.username.includes(this.searchQuery) ||
            character.introduction.includes(this.searchQuery) ||
            character.location.includes(this.searchQuery);
        return matchesCategory && matchesQuery;
      });
      this.handlePageChange(1)
    },
    viewDetails(character) {
      this.$router.push({
        path:'/personTracking/personDetail',
        query:{
          character_name: character.name
        }
      });
    },
    SwitchField(item){
      this.selectedCategory=item;
      this.filterCharacters();
    },
    order(){
      this.$message.info("排序，开发中")
    },
    favor (item) {
      if(!this.user.username){
        this.$message.info("请先登录")
        return;
      }
      if(!item.collect){//应该要收藏
        this.$request.get(this.Global.collect_Chara+'?name='+item.name+'&username='+this.user.username).then(res=>{
          if(!res){
            this.$message.info('后台未启动')
            return;
          }
          if(res.code=='200'){
            item.collect=!item.collect;
            this.content="已关注";
            this.bg_color="#f56c6c";
            this.ft_color="#fef0f0";
            this.$message.success('收藏成功')
          }else{
            this.$message.error(res.msg)
          }
        })
      }
      else{//应该取消收藏
        this.$request.get(this.Global.cancelCollect_Chara+'?name='+item.name+'&username='+this.user.username).then(res=>{
          if(!res){
            this.$message.info('后台未启动')
            return;
          }
          if(res.code=='200'){
            item.collect=!item.collect
            this.content="+关注"
            this.bg_color="#fef0f0";
            this.ft_color="#f56c6c";
            this.$message.success('取消收藏成功')
          }else{
            this.$message.error(res.msg)
          }
        })
      }
    },
    collect_style(item){
      //已收藏
      if(item.collect)
      {
        return {
          backgroundColor:"#f56c6c",
          color: "#fef0f0",
        }
      }
      //为收藏
      else
      {
        return{
          backgroundColor:"#fef0f0",
          color:"#f56c6c"
        }
      }
    },
    updatePagination() {
      let pageSize = 0
      if(this.isListMode===false){
        pageSize=this.pageSize
      }
      else {
        pageSize=this.pageSize2
      }
      const start = (this.currentPage - 1) * pageSize;
      const end = start + pageSize;
      this.paginatedChara = this.filteredCharacters.slice(start, end);
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.updatePagination();
    },
    applyForChara(){
      this.$message.info("开发中")
    }
  }
};
</script>

<style scoped>
.aoe{
  background-image: url("@/assets/Images/7DB12A5D7CF536CB23C8636FE90D59E8.png");
  border: 3px solid #32789b56;
  box-shadow: 0 0 8px rgba(18, 91, 237, 0.772);
  margin-bottom: 6px;
}
.news-item {
  flex: 1 1 calc(33.333% - 20px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 10px;
  background: linear-gradient(135deg, #bdc3c7, #ecf0f1);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  perspective: 500px;
  cursor: pointer;
  position: relative;
  margin: 5px 0;
  border-radius: 15px;
  overflow: hidden;
  color: #333;
}

.news-item:hover {
  transform: perspective(500px) scale(1.07);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.person-card {
  cursor: pointer;
  transition: transform 0.3s;
  background-image: url("@/assets/Images/7DB12A5D7CF536CB23C8636FE90D59E8.png");
  border: 3px solid #32789b56;
  box-shadow: 0 0 8px rgba(18, 91, 237, 0.772);
}
.person-card:hover {
  transform: translateY(-5px);
}
.card-content {
  display: flex;
  align-items: center;
}
.avatar {
  margin-right: 20px;
  width: 60px;
  height: 60px;
}
.info h3 {
  margin: 0 0 10px;
  font-size: 18px;
}
.info p {
  margin: 5px 0;
  font-size: 14px;
  color: #666;
}
.dialog-footer {
  text-align: right;
}
.el-header {
  background-color: #fff;
  padding: 20px;
  border-bottom: 1px solid #eaeaea;
}
.el-main {
  background-color: #f9f9f9;
  padding: 20px;
}


button {
  outline: none;
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

/deep/.el-menu-item{
  border-radius: 10px;
}
/deep/ .el-card__body{
  padding: 0;
}

.custom-cell {
  overflow: hidden; /* 隐藏超出部分 */
  text-overflow: ellipsis; /* 显示省略号 */
  display: -webkit-box; /* 必需 */
  -webkit-line-clamp: 2; /* 限制在一个块元素显示的文本的行数 */
  -webkit-box-orient: vertical; /* 设置或检索伸缩盒对象的子元素的排列方式 */
}

.custom-cell2 {
  overflow: hidden; /* 隐藏超出部分 */
  text-overflow: ellipsis; /* 显示省略号 */
  display: -webkit-box; /* 必需 */
  -webkit-line-clamp: 1; /* 限制在一个块元素显示的文本的行数 */
  -webkit-box-orient: vertical; /* 设置或检索伸缩盒对象的子元素的排列方式 */
}
</style>
