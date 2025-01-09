<template>
    <el-row>
        <el-col :span="isCollapsed ? 1 : 3" style="height: 100vh !important;">
            <el-menu :default-active="'/admin/index'" router text-color="#000" :collapse="isCollapsed"
                :unique-opened="true">
                <el-menu-item index="/admin/index">
                    <a class="el-icon-s-home"></a>
                    <span slot="title">首页</span>
                </el-menu-item>

                <el-submenu index="1">
                    <template #title>
                        <a class="el-icon-setting"></a>
                        <span>系统管理</span>
                    </template>
                    <el-menu-item index="/admin/adminManage">
                        <span>管理员管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/userManage">
                        <span>用户管理</span>
                    </el-menu-item>
                </el-submenu>
                <el-submenu index="2">
                    <template #title>
                        <a class="el-icon-s-data"></a>
                        <span>传播监测管理</span>
                    </template>
                    <el-menu-item index="/admin/influencialPersonManage">
                        <span>高影响力人物管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/newsManage">
                        <span>人物新闻管理</span>
                    </el-menu-item>
                </el-submenu>
                <el-submenu index="3">
                    <template #title>
                        <a class="el-icon-connection"></a>
                        <span>传播分析管理</span>
                    </template>
                    <el-menu-item index="/admin/emotionAnalysisManage">
                        <span>情感分析管理</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/emotionExtraAnalysisManage">
                        <span>情感极性分析管理</span>
                    </el-menu-item>
                </el-submenu>
            </el-menu>
        </el-col>

        <el-col :span="isCollapsed ? 23 : 21" style="height: 100vh !important;background-color: rgb(242, 242, 242);">
            <el-row type="flex" justify="start" align="middle">
                <el-col :span="2">
                    <div class="collapse-btn">
                        <i class="el-icon-s-fold" @click="toggleCollapse" style="margin-left: 10px"></i>
                    </div>
                </el-col>
                <el-col :span="9">
                    <div>欢迎你：<strong>{{ user.name }}</strong></div>
                </el-col>
                <el-col :span="11">

                </el-col>
                <el-col :span="2">
                    <!-- 下拉菜单 -->
                    <el-dropdown placement="bottom-end" @command="handleCommand">
                        <span class="el-dropdown__box">
                            <pre> </pre><el-avatar :src="user.avatar" />
                            <el-icon>
                                <CaretBottom />
                            </el-icon>
                        </span>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item command="logout">退出</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </el-col>

            </el-row>
            <router-view></router-view>
        </el-col>
    </el-row>
</template>

<script>
import router from '@/router'

export default {
    data() {
        return {
            isCollapsed: false,
            user: JSON.parse(localStorage.getItem('user_logined') || '{}'),
        }
    },
    methods: {
        toggleCollapse() {
            this.isCollapsed = !this.isCollapsed;
        },
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
</style>