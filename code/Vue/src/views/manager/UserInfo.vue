<template>
<div style="background-color: #f0f1f5;height: 100vh">
  <div style="margin: 30px 100px">
    <el-card shadow="never">
      <div style="display: flex;flex-wrap: wrap">
        <div style="flex: 2;display: flex">
          <el-avatar :src="avatar" :size="120"></el-avatar>
          <div style="font-size: 28px;margin-left: 20px;padding-top: 20px">
            <div>
              昵称：{{name}}
            </div>
            <div style="font-size: 20px;color: grey">
              注册时间：{{user.register_time}}
            </div>
          </div>
        </div>
        <div style="flex: 1">
          <div style="text-align: center">
            <p>{{user.focus_num}}</p>
            <p>关注人物</p>
          </div>
        </div>
      </div>
    </el-card>
    <div style="display: flex;flex-wrap: nowrap;margin-top: 20px">
      <!--左侧菜单卡片-->
      <el-card id="left_card" style="width: 20%;margin-right: 20px">
        <div style="text-align: center;padding:20px 0px">
          <i class="el-icon-house"></i>个人中心
        </div>
        <el-menu mode="vertical" @select="select_menu" default-active="1-1">
          <el-menu-item index="1-1"><i class="el-icon-user"></i>个人资料</el-menu-item>
          <el-menu-item index="1-2"><i class="el-icon-star-on"></i>关注人物</el-menu-item>
        </el-menu>
      </el-card>
      <!--右侧个人信息修改卡片-->
      <el-card v-if="current_menu==='1-1'" style="width: 80%">
        <div><strong>修改个人信息</strong></div>
        <div style="display: flex;flex-wrap: nowrap;margin-top: 20px">
          <div style="width: 30%;height: 100%">
            <div style="text-align: center">
              <el-upload class="img_uploader" :action=this.Global.change_UserImage
                         :show-file-list="false" :on-success="handleAvatarSuccess" :limit="1">
                <el-avatar :src="user.avatar" :key="user.avatar" :size="200" ></el-avatar>
              </el-upload>
            </div>
            <div style="width: 100%;text-align: center">点击替换头像</div>
          </div>
          <div style="width: 70%">
            <el-form label-position="right" label-width="80px" :model="user" :rules="rules" ref="dataForm">
              <el-form-item label="用户编号">
                <el-input v-model="user.id" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="用户名" prop="username">
                <el-input v-model="user.username" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="昵称" prop="name">
                <el-input v-model="user.name"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input v-model="user.password" show-password></el-input>
              </el-form-item>
              <el-form-item label="描述" prop="description">
                <el-input v-model="user.description"></el-input>
              </el-form-item>
              <el-form-item label="注册时间" prop="date">
                <div style="display: flex;flex-wrap: nowrap">
                  <div style="width:60%;color: grey;font-size: 15px">{{user.register_time}}</div>
                  <div style="width: 40%;display: flex;flex-direction: row-reverse">
                    <div>
                      <el-button type="primary" style="text-align: center;" @click="change">确 认</el-button>
                    </div>
                  </div>
                </div>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-card>

      <!--右侧关注人物卡片-->
      <el-card id="chara_card" v-if="current_menu==='1-2'" style="width: 80%">
        <div>
          <el-card v-for="(item,index) in characters" :key="index">
            <div style="padding: 20px;display: flex;flex-wrap: nowrap">
              <div style="width: 80%;display: flex;">
                <div @click="goChara"><el-avatar :src="item.avatar"  :size="80"></el-avatar></div>
                <div style="margin-left: 20px">
                  <div><el-link :underline="false" style="font-size: 25px" @click="goChara">{{item.name}}</el-link></div>
                  <div class="custom-cell"><span style="font-size: 15px">{{item.introduction}}</span></div>
                </div>
              </div>
              <div style="width: 20%;display: flex;justify-content: center;align-items: center">
                <div>
                  <button   @click="favor(item)" class="my_button" :style="collect_style(item)">
                    <span v-if="item.collect">已关注</span>
                    <span v-else>+关注</span>
                  </button>
                </div>
              </div>
            </div>
          </el-card>
        </div>
      </el-card>
    </div>

  </div>
</div>
</template>

<script>
export default {
  name: "UserInfo",
  data(){
    return{
      user: JSON.parse(localStorage.getItem('user_logined') || '{}'),
      name:'',
      avatar:'',
      characters:[
        {
          name:'test',
          collect:true,
        }
      ],
      current_menu:'1-1',
      rules:{
        password: [{ required: true, message: '不能为空', trigger: 'blur' }],
      }

    }
  },
  mounted(){
    this.name = JSON.parse(JSON.stringify(this.user.name))
    this.avatar =JSON.parse(JSON.stringify(this.user.avatar))
    this.getCharacters();
    this.getCollectNum();
  },
  methods:{
    getCollectNum(){
      this.$request.get(this.Global.get_CollecNum+'?id='+this.user.id).then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.user.focus_num=res.data;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    getCharacters(){
      this.$request.get(this.Global.select_CollectChara+'?username='+this.user.username).then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.characters=res.data;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    change(){
      if(this.user.password===''||this.user.username===''){
        this.$message.error("用户名或密码不能为空")
        return;
      }
      this.$request.post(this.Global.change_UserInfo,this.user).then(res=>{
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          localStorage.setItem("user_logined",JSON.stringify(res.data));
          location.reload()
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    handleAvatarSuccess(response,file,fileList){
      this.user.avatar=response.data
    },
    select_menu(index,indexPatth){
      switch (index){
        case index='1-1':
          this.current_menu='1-1';
          break;
        case index='1-2':
          this.current_menu='1-2';
          break;
      }
    },
    goChara(item){
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
            this.user.focus_num+=1;
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
            this.user.focus_num-=1;
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
      //未收藏
      else
      {
        return{
          backgroundColor:"#fef0f0",
          color:"#f56c6c"
        }
      }
    },


  },
}
</script>

<style scoped>
/deep/#left_card .el-card__body{
  padding: 0;
}
/deep/#chara_card .el-card__body{
  padding: 0;
}

.my_button{
  color: #f56c6c;
  background: #fef0f0;
  border: #fbc4c4 solid;
  border-radius: 20px;
  padding: 12px 23px;
  text-align: center;
  font-size: 16px;
  -webkit-transform: scale(0.7);
}
</style>