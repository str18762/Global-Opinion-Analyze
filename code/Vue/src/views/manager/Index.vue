<template>
  <div style="height: 100%">
    <el-card style="height: 100%;width: 100%;border-radius: 20px;background: rgba(255, 255, 255, 0.6);">
      <div style="text-align: center">
        <el-select class="search-input" @change="filterChara(selectedWho)"
                   v-model="selectedWho" filterable placeholder="请选择人物">
          <el-option
              v-for="who in whoList"
              :key="who"
              :label="who"
              :value="who">
          </el-option>
        </el-select>
      </div>
      <div style="margin: 0 5%">
        <div style="position: relative;height: 100px">
          <el-button circle @click="prevPage" :disabled="currentPage <= 1"
                     style="width: 60px;height: 60px;position: absolute;left: 10px;top: 20px;
                     box-shadow: 0 6px 26px 6px rgba(0, 0, 0, 0.25);">Prev</el-button>
          <el-button circle @click="nextPage" :disabled="currentPage >= totalPages"
                     style="width: 60px;height: 60px;position: absolute;right: 10px;top: 20px;
                     box-shadow: 0 6px 26px 6px rgba(0, 0, 0, 0.25);">Next</el-button>
        </div>

        <div style="padding: 0 40px">
          <div style="display: flex;flex-wrap: nowrap">
            <div class="items" v-for="(character, index) in currentItems" :key="index" @click="viewDetails(character.name_zh)"
                 style="position: relative;z-index: 20;flex: 1;margin: 0 10px;padding: 0 30px;" >
              <div class="item-bg"></div>
              <div  style="position: relative;z-index: 20;height: 400px;overflow: hidden">
                <div  class="news__title">{{ character.name_zh }}</div>
                <p class="news__txt">{{ character.introduction_zh }}</p>
                <p style="margin:10px 0">{{ character.location_zh }}</p>
                <p class="field__txt">{{ character.field }}</p>
                <div>
                  <img class="news__img" :src="character.avatar || 'default-avatar.png'" alt="news">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      selectedWho: "",
      whoList: [],

      paginatedChara: [],  // 存储所有的数据
      currentPage: 1,      // 当前页码
      selectedIndex:1,
      pageSize: 3,         // 每页显示条数
      totalPages: 0,       // 总页数
      currentItems: [],    // 当前页显示的条目
      username: '',
      characters: [],
      selectedCharacter: {},
    }
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      this.$request.get(this.Global.select_AllChara).then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.whoList=res.data;
          this.whoList = [...new Set(this.whoList.map(character => character.username))];
          this.paginatedChara = res.data; // 存储所有数据
          this.totalPages = Math.ceil(this.paginatedChara.length / this.pageSize); // 计算总页数
          this.updatePagination();  // 更新当前页数据
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    filterChara(selectedWho){
      this.$request.get(this.Global.select_CharaByUsername + selectedWho).then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          const selectedCharacter = res.data; // 接口返回的选中人物
          console.log('选中人物数据:', selectedCharacter);
          // 查找选中人物在 paginatedChara 中的索引
          const selectedIndex = this.paginatedChara.findIndex(
              (character) => character.id === selectedCharacter.id
          );
          if (selectedIndex !== -1) {
            // 计算选中人物所在页码
            this.currentPage = Math.floor(selectedIndex / this.pageSize) + 1;
            console.log('计算的页码:', this.currentPage);
            // 更新分页显示
            this.updatePagination();
          } else {
            console.error('选中人物未在 paginatedChara 中找到:', selectedCharacter);
          }
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    // 点击人物卡片时跳转
    viewDetails(name) {
      const url = this.setWebPageUrl(name);
      if (url) {
        window.open(url, '_blank'); // 在新标签页中打开链接
      } else {
        console.error('未找到该人物对应的链接:', name);
      }
    },
    // 跳转到对应网页链接
    setWebPageUrl(name) {
      // 定义人物名称与网页链接的映射
      const webPageMap = {
        "elonmusk": "https://baike.baidu.com/item/%E5%9F%83%E9%9A%86%C2%B7%E9%A9%AC%E6%96%AF%E5%85%8B?fromtitle=%E9%A9%AC%E6%96%AF%E5%85%8B&fromid=17200149&fromModule=lemma_search-box",
        "巴拉克 · 奥巴马": "https://baike.baidu.com/item/%E8%B4%9D%E6%8B%89%E5%85%8B%C2%B7%E4%BE%AF%E8%B5%9B%E5%9B%A0%C2%B7%E5%A5%A5%E5%B7%B4%E9%A9%AC/190467?fromtitle=%E5%A5%A5%E5%B7%B4%E9%A9%AC&fromid=3073",
        "justinbieber": "https://baike.baidu.com/item/%E8%B4%BE%E6%96%AF%E6%B1%80%C2%B7%E6%AF%94%E4%BC%AF?fromtitle=JustinBieber&fromid=11257588&fromModule=lemma_search-box",
        // 添加更多人物和对应的网页链接
      };
      // 根据名称获取对应链接
      return webPageMap[name] || null;
    },
    updatePagination() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      this.currentItems = this.paginatedChara.slice(start, end);
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.updatePagination();
    },

    // 上一页
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.updatePagination(); // 更新当前页数据
      }
    },
    // 下一页
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.updatePagination(); // 更新当前页数据
      }
    }
  }
}
</script>



<style scoped>
.search-input {
  font-size: 13px;
  width: 30%;
  border-radius: 5px;
}
.news__item {
  padding: 40px;
  color: #222222;
  border-radius: 10px;
  display: block;
  transition: all 0.3s;
}

.news__title {
  font-size: 25px;
  font-weight: 500;
  opacity: 0.7;
  margin-top: 10px;
  margin-bottom: 15px;
  transition: opacity 0.3s;
}
.news__txt {
  height: 50px;
  margin: 10px 0;
  line-height: 1.6em;
  font-size: 15px;
  opacity: 0.7;
  transition: opacity 0.3s;
  overflow-y: hidden;
}
.news__img {
  border-radius: 10px;
  box-shadow: 0 6px 16px 6px rgba(0, 0, 0, 0.25);
  margin-top: 30px;
  width: 100%;
  transition: all 0.3s;
  transform-origin: 0% 0%;
}

.item-bg {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 30px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 6px 26px 6px rgba(0, 0, 0, 0.25);
  opacity: 0;
  transition: all 0.3s;
  left: -30px;
  z-index: 0; /* 保持在底层 */
}
.items:hover .item-bg {
  left: 0;
  top: 0;
  opacity: 1; /* 显示背景 */
  z-index: 0; /* 保持在底层 */
}
</style>