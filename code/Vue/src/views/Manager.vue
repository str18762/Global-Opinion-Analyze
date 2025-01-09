<template>
  <div>
    <el-container style="height: 100vh;">
      <!-- 顶部栏 -->
      <el-header class="header">
        <div class="header-left">
          <strong class="system-title">基于高影响力人物追踪的国际舆论分析系统</strong>
          <div style="font-size: 14px">International public opinion analysis system based on the tracking of
            high-influencers</div>
        </div>
        <div class="header-right">
          <div style="display: flex;justify-content: center;align-items: center">
            <el-button v-if="!user.username" type="text" @click="showLogin" class="header-button">登录</el-button>
            <el-button v-if="!user.username" type="text" @click="showRegister" class="header-button">注册</el-button>
            <el-dropdown style="margin-left: 20px">
              <div style="display: flex;align-items: center;justify-content: flex-end;cursor: default">
                <span style="color: white;margin-right: 10px">{{ user.name }}</span>
                <el-avatar :src="user.avatar ? user.avatar : require('@/assets/Images/unlogin.jpg')"
                  class="avatar"></el-avatar>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-if="user.role === '管理员'" @click.native="backVue">后台管理</el-dropdown-item>
                <el-dropdown-item @click.native="personnal">个人中心</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </el-header>

      <!-- 导航栏 -->
      <div style="position:relative">
        <div class="nav-container" style="padding-bottom: 0">
          <el-menu mode="horizontal" class="nav-menu" @select="handleSelect" :default-active="$route.path">
            <el-menu-item index="/homePage"><i class="el-icon-house"></i>首页</el-menu-item>
            <el-menu-item index="/personTracking"><i class="el-icon-user"></i>国际人物</el-menu-item>
            <el-menu-item index="/hotTopics"><i class="el-icon-news"></i>热评话题</el-menu-item>
            <el-menu-item index="/hotPerson"><i class="el-icon-s-data"></i>热评人物</el-menu-item>
            <el-menu-item index="/helpCenter"><i class="el-icon-question"></i>FAQ</el-menu-item>
            <el-menu-item index="/background"><i class="el-icon-question"></i>Background</el-menu-item>
            <el-menu-item index="/systemProcess"><i class="el-icon-question"></i>SystemProcess</el-menu-item>
          </el-menu>
        </div>
      </div>

      <!-- 页面内容 -->
      <el-main>
        <router-view />
      </el-main>

      <!-- 悬浮按钮 -->
      <div class="snap">
        <el-tooltip class="item" effect="dark" content="快照保存" placement="top-start">
          <el-button icon="el-icon-share" class="floating-button" @click="snapshot"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="查看快照" placement="top-start">
          <el-button icon="el-icon-view" class="floating-button" @click="selectSnapshot"></el-button>
        </el-tooltip>
      </div>

      <!-- 遮罩层 -->
      <div class="mask" v-show="loginDialogVisible"></div>
      <!-- 新登录弹窗 -->
      <div class="login-container" v-show="loginDialogVisible">
        <div class="login-left">
          <h2>登录</h2>
          <p>欢迎!请输入账号密码！</p>
          <form>
            <div class="form-group">
              <input type="text" v-model="loginForm.username" placeholder="用户名" required />
            </div>
            <div class="form-group">
              <input type="password" v-model="loginForm.password" placeholder="密码" required />
            </div>

            <div style="align-items: center;display: flex;justify-content: center;">
              <button @click="handleLogin" class="btn-login">登录</button>
              <span style="width: 60px;"></span>
              <button class="btn-login" @click="loginDialogVisible = false">取消</button>
            </div>
          </form>
        </div>
        <div class="login-right">
          <p>如果你没有账号，你想要现在注册一个吗？</p>
          <button class="btn-register" @click="() => { registerDialogVisible = true;loginDialogVisible = false; }">注册</button>
        </div>
      </div>

      <!-- 遮罩层 -->
      <div class="mask" v-show="registerDialogVisible"></div>
      <!-- 新注册弹窗 -->
      <div class="login-container" v-show="registerDialogVisible">
        <div class="login-left">
          <h2>注册</h2>
          <p>欢迎!请填写注册信息！</p>
          <form>
            <div class="form-group">
              <input type="text" v-model="registerForm.username" placeholder="用户名" required />
            </div>
            <div class="form-group">
              <input type="password" v-model="registerForm.password" placeholder="密码" required />
            </div>
            <div class="form-group">
              <input type="password" v-model="registerForm.confirmPassword" placeholder="确认密码" required />
            </div>
            <div style="align-items: center;display: flex;justify-content: center;">
              <button @click="handleRegister" class="btn-login">注册</button>
              <span style="width: 60px;"></span>
              <button class="btn-login" @click="registerDialogVisible = false">取消</button>
            </div>
          </form>
        </div>
        <div class="login-right">
          <p>已经有账号了？</p>
          <button class="btn-register" @click="() => { loginDialogVisible = true;registerDialogVisible = false; }">登录</button>
        </div>
      </div>

    </el-container>
  </div>
</template>

<script>
import router from "@/router";
import html2canvas from 'html2canvas';

export default {
  name: 'Manager',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user_logined') || '{}'),
      loginDialogVisible: false,
      registerDialogVisible: false,
      loginForm: {
        username: '',
        password: '',
      },
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
      },
      currentView: 'HomePage',
    };
  },
  methods: {
    showLogin() {
      this.loginDialogVisible = true;
    },
    showRegister() {
      this.registerDialogVisible = true;
    },
    handleLogin() {
      if (this.loginForm.username === "" || this.loginForm.password === "") {
        this.$message.error("用户名或密码不能为空")
        return;
      }
      this.$request.post(this.Global.login, this.loginForm).then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.user = res.data;
          localStorage.setItem("user_logined", JSON.stringify(this.user));
          location.reload();
        } else {
          this.$message.error(res.msg);
        }
      })
      this.loginDialogVisible = false;
    },
    handleRegister() {
      if (this.registerForm.username === "" || this.registerForm.password === "" || this.registerForm.confirmPassword === "") {
        this.$message.error("输入框不能为空")
        return;
      }
      if (this.registerForm.password !== this.registerForm.confirmPassword) {
        this.$message.error("确认密码不一致")
        return;
      }
      this.$request.post(this.Global.register, this.registerForm).then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.user = res.data;
          localStorage.setItem("user_logined", JSON.stringify(this.user));
          location.reload();
        } else {
          this.$message.error(res.msg);
        }
      })
      this.registerDialogVisible = false;
    },
    handleSelect(key) {
      switch (key) {
        case '/homePage':
          router.push('/homePage');
          break;
        case '/personTracking':
          router.push('/personTracking');
          break;
        case '/hotTopics':
          router.push('/hotTopics/analysis');
          break;
        case '/hotPerson':
          router.push('/hotPerson');
          break;
        case '/reportGeneration':
          router.push('/reportGeneration');
          break;
        case '/helpCenter':
          router.push('/helpCenter');
          break;
        case '/background':
          router.push('/background');
          break;
        case '/systemProcess':
          router.push('/systemProcess');
          break;
        default:
          router.push('/homePage');
      }
    },
    backVue() {
      router.push('/admin/index');
    },
    personnal() {
      if (!this.user.username) {
        this.$message.info("请先登录")
        return;
      }
      router.push('/userInfo');
    },
    logout() {
      if (!this.user.username) {
        this.$message.info("请先登录")
        return;
      }
      this.user = {};
      localStorage.removeItem('user_logined');
      location.reload()
    },
    // 空点击函数，点击按钮不执行任何操作
    emptyClick() {
      // Do nothing
    },
    b64ToUint8Array(b64Image) {
      var img = atob(b64Image.split(',')[1]);
      var img_buffer = [];
      var i = 0;
      while (i < img.length) {
        img_buffer.push(img.charCodeAt(i));
        i++;
      }
      return new Uint8Array(img_buffer);
    },
    snapshot() {
      const element = document.getElementById('app') // 截图区域
      html2canvas(element, { backgroundColor: '#fff', allowTaint: false, useCORS: true })
        .then(canvas => {
          var b64Image = canvas.toDataURL('image/png');
          var u8Image = this.b64ToUint8Array(b64Image);
          var formData = new FormData();
          // formData.append("image", new Blob([u8Image], { type: "image/png" }));
          var file = new File([new Blob([u8Image], { type: "image/png" })], `snapshot_${Date.now().toString()}.png`, {
            type: 'application/json',
            lastModified: Date.now()
          });
          formData.append("image", file);
          this.$request.post("/file/snapshot", formData).then(res => {
            if (!res) {
              this.$message.info("后台未启动！");
              return;
            }
            if (res.code === '200') {
              this.$message.success(res.msg ? res.msg : '上传成功')
              console.log('快照地址', res.data)
            } else {
              this.$message.error(res.msg ? res.msg : '上传失败');
            }
          })
        })
    },
    selectSnapshot() {
      this.$message.success('查看成功')
      //跳转至快照查看界面
      this.$router.push('/snapshot')
    },
  }
};
</script>

<style scoped>
.floating-button {
  background-color: #5a6977;
  /* 按钮背景色 */
  color: white;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  line-height: 0;
  /* 确保图标居中 */
}

.snap {
  position: fixed;
  bottom: 20px;
  /* 距离屏幕底部 */
  right: 20px;
  /* 距离屏幕右侧 */
  display: flex;
  flex-direction: row;
  /* 水平排列按钮 */
  align-items: center;
  /* 水平对齐 */
  gap: 10px;
  /* 按钮之间的间距 */
  z-index: 1000;
  /* 确保在其他元素之上 */
}

.floating-button:hover {
  background-color: #34495e;
  /* 悬停时的背景色 */
}

body {
  margin: 0;
  padding: 0;
}

.el-container {
  background-image: url("@/assets/Images/sys_bg.png");
}

.el-main {
  padding: 0;
  background-size: cover;
  background-position: center;
}

.el-header {
  background: url('@/assets/bigbg.gif') no-repeat center center;
  background-size: cover;
  box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);
  display: flex;
  align-items: center;
  padding: 0 20px;
  height: 200px;
  /* 增加高度以展示更多背景图片 */
  color: white;
  margin: 0;
  position: relative;
}

.system-title {
  font-size: 28px;
  /* 增大字体以配合更大的背景区域 */
  font-weight: bold;
  color: white;
  margin-left: 20px;
  z-index: 1;
  /* 确保文本在渐变层之上 */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
  /* 文本阴影增加可读性 */
}

.header-right {
  margin-left: auto;
  z-index: 1;
  /* 确保按钮在渐变层之上 */
}

.login-btn {
  font-size: 18px;
  font-weight: bold;
  color: #ffffff;
  background-color: transparent;
}

.nav-container {
  background: rgba(255, 255, 255, 0.9);
  /* 半透明白色背景 */
  padding: 10px 0;
  box-shadow: 0 2px 6px rgba(0, 21, 41, 0.35);
  margin-top: -10px;
  /* 将导航栏上移，使其与背景图片接上 */
  clip-path: ellipse(100% 80% at 50% 100%);
  /* 下方添加椭圆形弧度设计 */
  display: flex;
  justify-content: center;
  border-bottom-left-radius: 25px;
  /* 圆角设计 */
  border-bottom-right-radius: 25px;
  /* 圆角设计 */
  position: relative;
  z-index: 2;


  transition: height 0.3s ease;
  /* 高度变化的过渡效果 */
  height: 60px;
  /* 初始高度 */
  max-height: 80px;
  /* 最大高度 */
  min-height: 60px;
  /* 最小高度 */
  overflow: hidden;
  /* 防止内容溢出 */
}

/* 鼠标悬停时增加高度 */
.nav-container:hover {
  height: 80px;
  /* 悬停时的高度 */
}

.el-menu-item:hover {
  transform: translateY(10px);
  /* 悬停时向下移动 */
}

.el-menu {
  background-color: transparent;
  color: #34495e;
  font-size: 16px;
  border-bottom: none;
}

.el-menu-item {
  color: #34495e;
  padding: 0 35px;
  border-bottom: none;
  height: 90px;

  transition: background-color 0.3s ease, transform 0.3s ease;
  /* 背景色和缩放过渡效果 */
}

.el-menu-item:hover,
.el-menu-item.is-active {
  background-color: #5a6977;
  color: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-bottom: none;
}




.login-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  height: 60%;
  width: 60%;
  font-family: Arial, sans-serif;
  z-index: 888;
  background-color: rgba(0, 0, 0, 0);
  background-image: url('@/assets/login_bg.gif');
  background-size: cover;
  /* 背景全屏铺满 */
  background-position: center;
  /* 居中显示 */
  background-repeat: no-repeat;
  /* 防止重复 */
  overflow: hidden;
  /* 防止内容溢出 */
}

.login-left {
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  flex: 3;
  padding: 40px;
}

.login-right {
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
  flex: 2;
  padding: 40px;
}

.login-left {
  background-color: rgba(255, 255, 255, 0.9);
  display: flex;
  flex-direction: column;
  justify-content: center;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}

h2,
h3 {
  margin: 0 0 20px;
}

p {
  margin: 0 0 10px;
}

form {
  margin: 20px 0;
}

.form-group {
  margin-bottom: 20px;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 10px 15px;
  border: 1px solid #ccc;
  border-radius: 20px;
  font-size: 16px;
}

input[type="checkbox"] {
  margin-right: 10px;
}

label {
  font-size: 14px;
}

.btn-login {
  background: black;
  color: white;
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  cursor: pointer;
}

.btn-login:hover {
  background: #333;
}

.forgot-password {
  margin-top: 10px;
  font-size: 14px;
}

.forgot-password a {
  color: #007bff;
  text-decoration: none;
}

.forgot-password a:hover {
  text-decoration: underline;
}

.third-party-login {
  margin-top: 20px;
}

.icons {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.login-right {
  background-color: rgba(245, 245, 245, 0.7);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 40px;
}

.btn-register {
  background: black;
  color: white;
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  cursor: pointer;
  margin-top: 20px;
}

.btn-register:hover {
  background: #333;
}

.mask {
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: 777;
}
</style>
