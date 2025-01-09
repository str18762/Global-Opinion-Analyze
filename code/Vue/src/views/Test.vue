<template>
  <div class="home-page">
    <div>
      <h2>人物动态</h2>
    </div>

    <div style="display: block;flex-wrap: wrap">
      <div style="width: 100%">
        <el-card shadow="never" style="border-radius: 10px;margin-top: 20px;height: 80%">
          <div style="display: flex;width: 100%;">
            <div class="left_content" style="width: 30%;">
              <h4>选择人物</h4>
              <el-divider content-position="left" style="width: 100%">所有人物</el-divider>
              <div style="display: flex;flex-wrap: wrap">
                <el-select v-model="selectedWho" filterable  placeholder="选择人物" @change="filterNews">
                  <el-option v-for="who in whoList" :key="who" :label="who" :value="who"/>
                </el-select>
                <el-button @click="resetFilter">重置过滤</el-button>
              </div>
            </div>
            <el-divider direction="vertical" style="flex: 1;"></el-divider>
            <div class="right_content" style="width: 70%;">
              <el-divider content-position="left" style="width: 100%">我的关注</el-divider>
              <div style="height: 80px;overflow: hidden;display: flex;flex-wrap: wrap">
                <div v-for="(item, index) in collectCharacters" :key="index" shadow="hover" style="height: 80px;width: 25%">
                  <div style="display: flex;align-items: center;justify-content: center" @click="filterCollect(item.name)">
                    <el-avatar :size="60" :src="item.avatar" style="cursor: pointer"></el-avatar>
                  </div>
                  <div style="display: flex;align-items: center;justify-content: center" @click="filterCollect(item.name)">
                    <el-link :underline="false">
                      {{item.name}}
                    </el-link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </div>

      <div style="width: 100%;height: 600px">
        <el-empty v-if="paginatedNews.length===0">
          <el-button type="success" @click="applyForChara()">向管理员反应</el-button>
        </el-empty>
        <div class="news-container">
          <!-- <transition-group name="slide" tag="div" class="news-item-wrapper"> -->
          <div class="news-item-wrapper" ref="newsItemWrapper">
            <div class="page-news" v-for="(page_news,index) in split_list" :key="index" ref="pageNews">
              <div class="news-item" v-for="(news, index) in page_news" :key="index" ref="newsItem">
                <div style="height: 140px">
                  <h3>{{ news.title_zh }}</h3>
                  <el-link target="_blank" @click="newsDetail(news)">详情</el-link>
                  <div class="reflection"></div>
                </div>
              </div>
            </div>
          </div>
          <!-- </transition-group> -->
        </div>
        <!-- 分页控件 -->
        <div v-if="isOnePage()" style="padding: 10px 0;display: flex;justify-content: center">
          <el-pagination
              :background="true"
              @current-change="handlePageChange"
              :current-page="currentPage"
              :page-sizes="[5, 10, 15, 20]"
              :page-size="pageSize"
              layout="total, prev, pager, next, jumper"
              :total="filteredNews.length">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Video from "@/components/views/Video.vue";

export default {
  name: 'HomePage',
  components:{
    Video,
  },
  data() {
    return {
      intervalId: null ,
      currentIndex: 0,
      itemWidth: 0, // 单个列表项的宽度
      wrapperWidth: 0, // 包裹列表项的容器宽度
      scrollLeftValue: 0, // 记录水平滚动的位置
      isScrolling: false, // 标记是否正在滚动切换中
      user: JSON.parse(localStorage.getItem('user_logined') || '{}'),
      collectCharacters:[
        {
          name:'test',
          collect:true,
        }
      ],
      newsList: [],
      split_list:[],
      filteredNews: [],
      paginatedNews: [],
      whoList: [],
      selectedWho: '',
      currentPage: 1,
      pageSize: 8,
      carouselItems: [
        {
          id:2,
          image: 'https://ichef.bbci.co.uk/ace/ws/800/cpsprodpb/53B0/production/_133542412_gettyimages-1742610750.jpg.webp',
          caption: '富士康出走后再回归中国：是短期行为还是长线规划？',
          link: 'https://www.bbc.com/zhongwen/simp/business-69287181'
        },
        {
          id:3,
          image: 'https://ichef.bbci.co.uk/ace/ws/800/cpsprodpb/3B0A/production/_133541151_74f5bb33-a547-42ac-a8b2-b0003304093c.jpg.webp',
          caption: '气候变化：尼泊尔珠峰地区的夏尔巴人担忧家园可能被洪水冲毁',
          link: 'https://www.bbc.com/zhongwen/simp/science-69284758'
        },
        {
          id:4,
          image: 'https://ichef.bbci.co.uk/ace/ws/800/cpsprodpb/80F6/production/_133541033_e79b224b-1d43-436d-bf20-e1deed30b5e2.jpg.webp',
          caption: '沙特王储萨勒曼崛起内幕：权力、石油和4.5亿美元的油画',
          link: 'https://www.bbc.com/zhongwen/simp/world-69282797'
        },
        {
          id:5,
          image: 'https://ichef.bbci.co.uk/ace/ws/800/cpsprodpb/A0E9/production/_133539114_gettyimages-932783656.jpg.webp',
          caption: '李嘉诚长江基建英国第二上市，意味着什么？',
          link: 'https://www.bbc.com/zhongwen/simp/business-69270721'
        }
      ]
    };
  },
  created() {
    this.fetchNews();
    // this.getCollectCharacters();
    // this.getBBCNews()
  },
  methods: {
    getBBCNews(){
      this.$request.get(this.Global.select_allBBCNews).then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.carouselItems=res.data;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    getCollectCharacters(){
      this.$request.get(this.Global.select_CollectChara+'?username='+this.user.username).then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.collectCharacters=res.data;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    fetchNews() {
      axios.get('/api/news/all')
          .then(response => {
            this.newsList = response.data;
            for (let i = 0; i < this.newsList.length; i += this.pageSize) {
              this.split_list.push(this.newsList.slice(i, i + this.pageSize));
            }
            this.filteredNews = this.newsList;
            this.whoList = [...new Set(this.newsList.map(news => news.name))];
            this.updatePagination(); // 初始化分页
          })
          .catch(error => {
            console.error('Error fetching news:', error);
          });
    },
    filterNews() {
      if (this.selectedWho) {
        this.filteredNews = this.newsList.filter(news => news.name === this.selectedWho);
      } else {
        this.filteredNews = this.newsList;
      }
      this.currentPage = 1; // 重置为第一页
      this.updatePagination();
    },
    resetFilter() {
      this.selectedWho = '';
      this.filterNews();
    },
    filterCollect(name){
      this.selectedWho = name;
      this.filterNews();
    },
    updatePagination() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      this.paginatedNews = this.filteredNews.slice(start, end);
      this.split_list = []
      for (let i = 0; i < this.filteredNews.length; i += this.pageSize) {
        this.split_list.push(this.filteredNews.slice(i, i + this.pageSize));
      }
      this.currentIndex = this.currentPage - 1
      this.slideToIndex(this.currentIndex)
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.updatePagination();
    },
    applyForChara(){
      this.$message.success("请求成功 来源：人物动态")
    },
    newsDetail(news) {
      this.$router.push({
        path:'/news',
        query:{
          news_id: news.id
        }
      });
    },
    isOnePage(){
      return !(this.currentPage === 1 && this.paginatedNews.length < this.pageSize);
    },
    bbc_News(item){
      this.$router.push({
        path:'news_bbc',
        query:{
          bbc_newsId: item.id
        }
      });
    },
    startAutoSlide() {
      this.intervalId = setInterval(() => {
        this.currentIndex++;
        console.log(this.split_list.length)
        if (this.currentIndex >= this.split_list.length) {
          this.currentIndex = 0;
        }
        this.slideToIndex(this.currentIndex);
      }, 3000); // 每3秒切换一次
    },
    slideToIndex(index) {
      const container = this.$refs.newsItemWrapper;
      container.style.transform = `translateX(-${index * (this.itemWidth+40)}px)`;
      this.currentPage = index + 1
    },
  },
  mounted() {
    this.itemWidth = this.$refs.newsItemWrapper.offsetWidth;
    this.startAutoSlide();
    // const newsItems = document.querySelectorAll('.news-item');
    // newsItems.forEach(item => {
    //   item.addEventListener('mousemove', this.move);
    //   item.addEventListener('mouseleave', this.leave);
    //   item.addEventListener('mouseover', this.over);
    // });
  },
  beforeDestroy() {
    this.stopAutoScroll(); // 在组件销毁前停止自动滚动，避免内存泄漏等问题
  },
};
</script>

<style scoped>
.home-page {
  padding: 20px;
  background: linear-gradient(135deg, #bdc3c7, #ecf0f1);
  min-height: 100vh;
}

h2 {
  margin-bottom: 20px;
  text-align: center;
}

.el-select {
  margin-bottom: 20px;
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 300px;
}

.el-button {
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 20px;
}

.news-container {
  display: flex;
  /* flex-wrap: wrap; */
  gap: 20px;
  padding: 0 20px;
  overflow: hidden; /* 隐藏超出部分，实现滑动效果 */
  position: relative;
}

.news-item-wrapper {
  display: flex;
  white-space: nowrap; /* 保证内部元素在一行显示，便于水平滚动 */
  transition: transform 0.5s ease-in-out; /* 用于平滑的滑动切换动画 */
  gap: 40px;
  width: 100%;
  position: relative;
  /* overflow: hidden; 隐藏超出部分，实现滑动效果 */
}
.page-news {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  flex: 1 0 calc(100%);
}

.news-item {
  flex: 0 0 calc(25% - 20px);
  max-height: calc(50% - 50px);
  /* flex-shrink: 0; 避免列表项被压缩 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  background: linear-gradient(315deg, #7f8c8d, #bdc3c7); /* 从右下到左上的灰色渐变 */
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  perspective: 500px;
  cursor: pointer;
  position: relative;
  margin: 20px 0;
  border-radius: 15px;
  overflow: hidden;
  color: #fff;
}

.news-item:hover {
  transform: perspective(500px) scale(1.07);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}


.news-item h3 {
  margin-top: 0;
  color: #fff;
  font-size: 1.5rem;
  font-weight: 600;
  line-height: 1.2;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
}

.news-item p {
  margin: 10px 0 20px 0;
  color: #ddd;
  font-size: 1rem;
  line-height: 1.5;
  text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.7);
}

.news-item el-link {
  color: #ffd700;
  font-weight: bold;
  text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.7);
}

.reflection {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  transition: opacity 0.3s ease;
  pointer-events: none;
  border-radius: 15px;
}

.el-divider--vertical{
  height: 8em;
}

@keyframes slideIn {
  from {
    transform: translateX(100%); /* 从右边滑入，起始位置在屏幕外左侧 */
  }
  to {
    transform: translateX(0); /* 滑入到正常显示位置 */
  }
}

@keyframes slideOut {
  from {
    transform: translateX(0); /* 从正常显示位置开始 */
    opacity: 1;
  }
  to {
    transform: translateX(-100%); /* 滑出到屏幕外左侧 */
    opacity: 0; /* 可以添加透明度变化，让动画更自然，逐渐消失 */
  }
}

.slide-enter-active {
  animation: slideIn 2s ease-in-out forwards; /* 应用滑入动画，且保持最后一帧状态 */
}

.slide-leave-active {
  animation: slideOut 2s ease-in-out forwards; /* 应用滑出动画，并保持最后一帧状态 */
}

/deep/ .el-pagination {
  padding: 10px;
  border-radius: 20px;
  background-color: #bdc3c7 !important;
}

/deep/ #position_divider .el-divider__text{
  background-color: #bdc3c7;
}


</style>
