<template>
  <el-container>
    <el-aside width="200px">
      <el-menu default-active="1" @select="handleMenuSelect" style="border-radius: 10px;">
        <el-menu-item index="4" v-if="isListMode">切换至卡片形式</el-menu-item>
        <el-menu-item index="2" v-else>切换至列表形式</el-menu-item>
        <el-menu-item index="3">进入影响力分析</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
<!--      头部区域-->
      <el-header>
        <div style="width: 100%;display: flex;flex-direction: row-reverse">
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
        </div>
      </el-header>
<!--      人物卡片-->
      <el-main v-if="isListMode===false">
        <div>
          <div>
            <el-row :gutter="20" type="flex" style="flex-wrap:wrap">
              <el-col :span="12" v-for="(character, index) in paginatedChara" :key="index">
                <el-card class="person-card news-item" v-if="character.username" style="height: 280px">
                  <div class="card-content">
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
                        </div>

                        <div class="custom-cell" style="width: 100%;" @click="viewDetails(character)">
                          <p><strong>个人简介：</strong>{{ character.introduction_zh }}</p>
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
      </el-main>

<!--      人物列表-->
      <el-main v-if="isListMode===true">
        <div>
          <el-row>
            <el-col :span="24" v-for="(character, index) in paginatedChara" :key="index">
              <el-card shadow="never">
                <div style="display: flex;flex-wrap: wrap;height: 80px">
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
                        <div style="display: flex;flex-wrap: wrap">
                          <div style="height: 80px;display: flex;justify-content: center;align-items: center;margin:0 10px">
                            <el-avatar :src="character.avatar" :size="60"></el-avatar>
                          </div>
                          <div>
                            <el-link :underline="false" style="font-size: 20px;margin: 10px">{{character.name_zh}}</el-link>
                            <div style="margin-left: 20px;font-size: 15px">领域：{{character.field}}</div>
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
      </el-main>
    </el-container>
  </el-container>
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
      pageSize: 16,
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
    handleImageError(event, character) {
      // 确保图片加载失败时替换为默认图片
      character.displayPhoto = this.defaultAvatar;
      event.target.src = this.defaultAvatar;
    },
    handleSearch() {
      this.filterCharacters();
    },
    handleClear() {
      this.searchQuery = '';
      this.filterCharacters();
    },
    handleMenuSelect(index) {
      if(index==='3'){
        this.$router.push({ path: '/influenceAnalysis' });
      }
      else if (index === '2') {
        this.isListMode = true
        this.handlePageChange(1)
      }
      else if (index === '4'){
        this.isListMode = false
        this.handlePageChange(1)
      }
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
.news-item {
  flex: 1 1 calc(33.333% - 20px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  background: linear-gradient(135deg, #bdc3c7, #ecf0f1);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  perspective: 500px;
  cursor: pointer;
  position: relative;
  margin: 20px 0;
  border-radius: 15px;
  overflow: hidden;
  color: #333;
}

.news-item:hover {
  transform: perspective(500px) scale(1.07);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.el-aside {
  background: linear-gradient(135deg, #bdc3c7, #ecf0f1);
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 15px;
}

.person-card {
  cursor: pointer;
  transition: transform 0.3s;
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
.el-menu .el-menu-item {
  min-width: 160px!important;
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
  -webkit-line-clamp: 1; /* 限制在一个块元素显示的文本的行数 */
  -webkit-box-orient: vertical; /* 设置或检索伸缩盒对象的子元素的排列方式 */
}

</style>
