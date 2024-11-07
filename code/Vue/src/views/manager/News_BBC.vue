<template>
  <div>
    <div style="height: 60px;background-color: #900000;padding: 0 25%;display: flex">
      <el-image :src="require('@/assets/Images/BBC.png')" style="height: 60px"></el-image>
      <div style="display: flex;align-items: center;color: white;font-size: 20px;margin-left: 5px">中文</div>
      <div style="display: flex;align-items: center;margin-left: 50px"><el-link type="success" :href="news.url" target="_blank">原文链接</el-link></div>
    </div>
    <div style="margin:0 25%;">
      <div id="content" v-html="news.article">
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "News_BBC",
  data(){
    return{
      bbcNewsId:0,
      news:{},

    }
  },
  mounted(){
    this.bbcNewsId = this.$route.query.bbc_newsId;
    this.getData()
  },
  methods:{
    getData(){
      this.$request.get(this.Global.select_bbcNewsById +'?id='+this.bbcNewsId).then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.news = res.data;
          this.news.article=this.parsedHtml()
          console.log(this.news.article);
          console.log(this.parsedHtml())
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    parsedHtml() {
      // 正则表达式用于匹配 img 标签
      const imgRegex = /<img[^>]+>/g;
      let content = this.news.article;

      // 替换所有的 img 标签为 el-image 组件
      return content.replace(imgRegex, (match) => {
        // 解析 src 和 alt 属性
        let src = match.match(/src="(.*?)"/)[1];
        let alt = match.match(/alt="(.*?)"/) ? match.match(/alt="(.*?)"/)[1] : '';
        // 返回替换后的 el-image 组件
        return `<img src="${src}" alt="${alt}" style="width: 100%;"/>`;
      });
    },
  },

}
</script>

<style scoped>
/deep/ .bbc-1qn0xuy{
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
/deep/ .bbc-1qn0xuy div{
  margin: 0 !important;
}
/deep/.bbc-j1srjl{
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
/deep/.bbc-j1srjl .img{
  width: 100%;
}
/deep/.bbc-j1srjl .bbc-1276odk{
  width: 100%;
}
/deep/ #content div{
  margin: 15px auto;
}
</style>