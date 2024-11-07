<template>
  <div>
    <div style="color: lightcoral;text-align: right;margin: 0 100px">点击图片即可放大，可在图片上右键选择保存下载</div>
    <div v-for="(item,key) in snapshotList" :key="key">
      <div style="text-align: center;font-size: 30px;margin-top: 20px">
        {{ key.split('-')[0] }}年{{ key.split('-')[1] }}月{{ key.split('-')[2] }}日
      </div>
      <div>
        <el-row :gutter="20" type="flex" style="flex-wrap:wrap">
          <el-col :span="8" v-for="(url,index) in item" :key="index" style="margin-top: 5px">
            <el-image :src="url" :preview-src-list="item" alt="图片不存在" style="width: 100%" ></el-image>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>

</template>
<script>

export default {
  data() {
    return {
      snapshotList:null,
      urlList:['http://localhost:7001/file/download/users/1728366023500_头像1.jpg'],
    }
  },
  mounted(){
    this.getData()
  },
  methods:{
    getData(){
      this.$request.get("/file/select").then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.snapshotList = res.data

          console.log(this.snapshotList)
        } else {
          this.$message.error(res.msg?res.msg:'查看失败');
        }
      })
    }
  }
};
</script>