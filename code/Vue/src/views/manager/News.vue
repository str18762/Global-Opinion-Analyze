<template>
  <div style="background-color: white">
    <div style="background-color: black;height: 50px">
      <div style="display: flex;flex-wrap: wrap">
        <el-image :src="require('@/assets/Images/cnn.png')" style="height: 50px;margin-left: 40px"></el-image>
        <div style="color: white;height: 50px;display: flex;align-items: center;margin-left: 5px"><strong>Politics</strong></div>
      </div>
    </div>
    <div>
      <div style="padding: 10px 50px">
        <el-divider></el-divider>
      </div>
      <div style="display: flex;flex-wrap: wrap;justify-content: center">
        <Video :src="news.video" :key="news.video"/>
        <div style="width: 100%;margin-top: 32px;display: flex;justify-content: center">
          <div style="width: 900px">
            <div v-if="isOrigin===false">
              <div><h2 style="font-size: 40px">{{news.title_zh}}</h2></div>
              <div>{{news.content_zh}}</div>
              <div><el-link :underline="false" @click="switchOrigin">点击查看原文</el-link></div>
            </div>
            <div v-else>
              <div><h2 style="font-size: 40px">{{news.title}}</h2></div>
              <div>{{news.content}}</div>
              <div><el-link :underline="false" @click="switchOrigin">点击查看中文译文</el-link></div>
            </div>

            <div style="height: 200px;display: flex;margin-top: 20px">
              <div >Source:<el-link target="_blank" href="https://edition.cnn.com/specials/politics/the-point-with-chris-cillizza">CNN</el-link></div>
              <div style="margin-left: 50px"><el-link target="_blank" :href="news.url">点击跳转源网址</el-link></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Video from "@/components/views/Video.vue";

export default {
  name: "News",
  components: {
    Video,
  },
  data(){
    return{
      news_id:'',
      news:{},
      isOrigin:false,
    }
  },
  mounted() {
    this.news_id = this.$route.query.news_id;
    this.getNewsData();
  },
  methods:{
    getNewsData(){
      this.$request.get(this.Global.select_NewsById +'?id='+this.news_id).then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.news = res.data;
          console.log(this.news)
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    switchOrigin(){
      this.isOrigin=!this.isOrigin;
    },
  },
}
</script>

<style scoped>

</style>