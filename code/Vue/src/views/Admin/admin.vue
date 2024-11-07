<template>
    <!-- element-plus中的容器 -->
    <el-container class="layout-container">
        <el-aside id="ttt" width="200px">
            <div class="title">控制台</div>
            <el-menu active-text-color="orange" text-color="white"
                router>
                <el-menu-item index="/admin/index">
                    <el-icon>
                        <House />
                    </el-icon>
                    <span>首页</span>
                </el-menu-item>
                <el-submenu index="1">
                    <template #title>
                        <el-icon>
                            <Promotion />
                        </el-icon>
                        <span>系统管理</span>
                    </template>
                    <el-menu-item index="/admin/adminManage">
                        <!-- <el-icon>
                            <Management />
                        </el-icon> -->
                        <span>管理员管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/userManage">
                        <!-- <el-icon>
                            <Calendar />
                        </el-icon> -->
                        <span>用户管理</span>
                    </el-menu-item>
                </el-submenu>
                <el-submenu index="2">
                    <template #title>
                        <el-icon>
                            <Platform />
                        </el-icon>
                        <span>传播监测管理</span>
                    </template>
                    <el-menu-item index="/admin/influencialPersonManage">
                        <!-- <el-icon>
                            <ChatRound />
                        </el-icon> -->
                        <span>高影响力人物信息管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/newsManage">
                        <!-- <el-icon>
                            <Promotion />
                        </el-icon> -->
                        <span>人物新闻管理</span>
                    </el-menu-item>
                </el-submenu>
                <el-submenu index="3">
                    <template #title>
                        <el-icon>
                            <TrendCharts />
                        </el-icon>
                        <span>传播分析管理</span>
                    </template>
                    <el-menu-item index="/admin/emotionAnalysisManage">
                        <!-- <el-icon>
                            <User />
                        </el-icon> -->
                        <span>情感分析管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/emotionExtraAnalysisManage">
                        <!-- <el-icon>
                            <Avatar />
                        </el-icon> -->
                        <span>情感极性分析管理</span>
                    </el-menu-item>
                </el-submenu>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container style="background-color: lightgrey">
            <!-- 头部区域 -->
            <el-header>
                <div>欢迎你：<strong></strong></div>
                <!-- 下拉菜单 -->
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        管理员
                        <pre> </pre><el-avatar :src="user.avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="logout" >退出</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>

            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <router-view></router-view>
            </el-main>
            <!-- 底部区域 -->
            <el-footer>舆情分析系统 ©2024 Created by 师大程序员</el-footer>
        </el-container>
    </el-container>
</template>

<script>
// import {
//     House,
//     Crop,
//     Promotion,
//     Management,
//     SwitchButton,
//     TrendCharts,
//     User,
//     EditPen,
//     Platform,
// } from '@element-plus/icons-vue'
//条目被点击后,调用的函数
import router from '@/router'

export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user_logined') || '{}'),
    };
  },
  methods: {
    handleCommand(command) {
      //判断指令
      if (command === 'logout') {
        //退出登录
        this.$confirm('您确认要退出吗?', '温馨提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          router.push('/')
          this.$message({
            type: 'success',
            message: '退出成功!'
          });
          this.adminList();
        }).catch(() => {
        });
      } else {
        //路由
        router.push('/' + command)
      }
    }
  }

}
</script>

<style scoped>
.icon {
    color: #fff;
}
#ttt{
  background: linear-gradient(135deg, #2c3e50, #ecf0f1);
}

.layout-container {
    height: 100vh;

    .title {
        font-size: 50px;
        -webkit-text-fill-color: #ffffff;
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #088820;
    }
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
}

/deep/ .el-menu-item:hover, .el-menu-item.is-active {
  background-color: black;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-bottom: none;
}

/deep/ .el-submenu__title:hover{
  background-color: black;
}
/deep/ .el-submenu .el-menu-item{
  background-color: #555555;
}
/deep/ .el-submenu .el-menu-item:hover{
  background-color: #333333;
}
</style>
