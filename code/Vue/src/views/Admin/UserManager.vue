<script>

export default {
    data() {
        return {
            users: [],
            pageNum: 1,    //当前页
            total: 20,     //总条数
            pageSize: 10,   //每页条数
            userName:'',
            ids: [],
        };
    },
    mounted() {
        this.userList();
    },
    methods: {
        onSizeChange(size) {
            this.pageSize = size
            this.userList()
        },
        onCurrentChange(num) {
            this.pageNum = num
            this.userList()
        },
        handleSelectionChange(users) {
            this.ids = users.map(users => users.id)
        },
        handleDelete(userId) {
            this.$confirm('你确认要删除该用户吗?', '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (userId) {
                    this.$request.delete("/admin/deleteUser?id="+userId).then(res => {
                        if (!res) {
                            this.$message.info("后台未启动！");
                            return;
                        }
                        if (res.code === '200') {
                            console.log(res.data)
                        } else {
                            this.$message.error(res.msg);
                        }
                    })
                }
                else {
                    this.$request.delete(`/admin/batchDeleteUser/${this.ids}`).then(res => {
                        if (!res) {
                            this.$message.info("后台未启动！");
                            return;
                        }
                        if (res.code === '200') {
                            this.userList();
                        } else {
                            this.$message.error(res.msg);
                        }
                    })
                }
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                this.userList();
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消删除'
                });
            });
        },
        async userList() {
            let params = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                userName: this.userName,
            }

            this.$request.get("/admin/listUser", { params: params }).then(res => {
                if (!res) {
                    this.$message.info("后台未启动！");
                    return;
                }
                if (res.code === '200') {
                    this.total = res.data.total;
                    this.users = res.data.items;
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        resetPassword(id) {
            this.$confirm('你确认要重置该用户的密码吗?', '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$request.patch("/admin/resetPassword?id="+id).then(res => {
                    if (!res) {
                        this.$message.info("后台未启动！");
                        return;
                    }
                    if (res.code === '200') {
                        console.log(res.data)
                    } else {
                        this.$message.error(res.msg);
                    }
                })
                this.$message({
                    type: 'success',
                    message: '重置成功!'
                });
                this.userList();
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消重置'
                });
            });
        }
    }
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>用户管理</span>
                <div class="extra">
                    <el-button icon="el-icon-delete" type="danger" @click="handleDelete()">批量删除</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="用户名称：">
                <el-input prefix-icon="el-icon-search" placeholder="请输入用户名称" v-model="userName"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="userList">搜索</el-button>
                <el-button @click="userName='' ">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 用户列表 -->
        <el-table :data="users" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column label="用户账号" property="username"></el-table-column>
            <el-table-column label="用户名称" property="name"></el-table-column>
            <el-table-column label="用户头像" property="avatar">
                <template #default="scope">
                    <img :src="scope.row.avatar" style="width: 100px;">
                </template>
            </el-table-column>
            <el-table-column label="用户邮箱" property="email"></el-table-column>
            <el-table-column label="用户手机号" property="phone"></el-table-column>
            <el-table-column label="操作" width="200" fixed="right">
                <template #default="{ row }">
                    <el-button icon="el-icon-delete" round plain type="danger" @click="handleDelete(row.id)">删除</el-button>
                    <el-button plain type="primary" round @click="resetPassword(row.id)">重置密码</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 20, 50]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

        
    </el-card>
</template>
<style scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}


</style>