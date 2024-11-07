<template>
  <div class="home-page">
    <!-- 轮播图 -->

    <el-carousel height="300px">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index" >
        <img @click="bbc_News(item)" :src="item.img" alt="轮播新闻动态" class="carousel-image">
        <!-- 显示提示主题 -->
        <div v-if="item.caption" class="carousel-caption">{{ item.caption }}</div>
      </el-carousel-item>
    </el-carousel>
    <h2>新闻动态</h2>
    <el-divider id="position_divider" content-position="left" style="width: 100%">近期热点</el-divider>
    <div>
      <h2>人物动态</h2>
    </div>

    <div style="display: flex;flex-wrap: wrap">
      <div style="width: 30%">
        <el-card shadow="never" style="border-radius: 10px;margin-top: 20px;height: 80%">
          <h4>选择人物</h4>
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
          <el-divider content-position="left" style="width: 100%">所有人物</el-divider>
          <div style="display: flex;flex-wrap: wrap">
            <el-select v-model="selectedWho" filterable  placeholder="选择人物" @change="filterNews">
              <el-option v-for="who in whoList" :key="who" :label="who" :value="who"/>
            </el-select>
            <el-button @click="resetFilter">重置过滤</el-button>
          </div>

        </el-card>
      </div>

      <div style="width: 70%;height: 600px">
        <el-empty v-if="paginatedNews.length===0">
          <el-button type="success" @click="applyForChara()">向管理员反应</el-button>
        </el-empty>
        <div class="news-container">
          <div class="news-item" v-for="(news, index) in paginatedNews" :key="index">
            <div style="height: 140px">
              <h3>{{ news.title_zh }}</h3>
              <el-link target="_blank" @click="newsDetail(news)">详情</el-link>
              <div class="reflection"></div>
            </div>
          </div>
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
      user: JSON.parse(localStorage.getItem('user_logined') || '{}'),
      collectCharacters:[
        {
          name:'test',
          collect:true,
        }
      ],
      newsList: [],
      filteredNews: [],
      paginatedNews: [],
      whoList: [],
      selectedWho: '',
      currentPage: 1,
      pageSize: 9,
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
    this.getCollectCharacters();
    this.getBBCNews()
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
    }
  },
  mounted() {
    const newsItems = document.querySelectorAll('.news-item');
    newsItems.forEach(item => {
      item.addEventListener('mousemove', this.move);
      item.addEventListener('mouseleave', this.leave);
      item.addEventListener('mouseover', this.over);
    });
  }
};
</script>

<style scoped>
.home-page {
  padding: 20px;
  background: linear-gradient(135deg, #bdc3c7, #ecf0f1);
  min-height: 100vh;
}


.carousel-image {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 15px;
}

.carousel-caption {
  position: absolute;
  bottom: 20px; /* 这里可以调整为 0px 使其靠近正下方 */
  left: 20px; /* 调整为 center 或 right 来改变位置 */
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
  font-size: 1.2rem;
  background-color: rgba(0, 0, 0, 0.5); /* 背景色提高可读性 */
  padding: 5px 10px;
  border-radius: 5px;
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
  flex-wrap: wrap;
  gap: 20px;
  padding: 0 20px;
}

.news-item {
  flex: 1 1 calc(33.333% - 20px);
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

/deep/ .el-pagination {
  padding: 10px;
  border-radius: 20px;
  background-color: #bdc3c7 !important;
}

/deep/ #position_divider .el-divider__text{
  background-color: #bdc3c7;
}
</style>
