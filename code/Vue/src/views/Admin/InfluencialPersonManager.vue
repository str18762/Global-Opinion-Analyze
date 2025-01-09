<script>

export default {
    data() {
        return {
            users: [],
            pageNum: 1,    //当前页
            total: 20,     //总条数
            pageSize: 10,   //每页条数
            userName: '',
            ids: [],
            dialogVisible: false,
            userModel: {
                username:'',
                name:'',
                introduction:'',
            },
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
            this.$confirm('你确认要删除该高影响力人物吗?', '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (userId) {
                    this.$request.delete("/api/admin/deleteInfluence").then(res => {
                        if (!res) {
                            this.$message.info("后台未启动！");
                            return;
                        }
                        if (res.code === '200') {
                            console.log(res)
                        } else {
                            this.$message.error(res.msg);
                        }
                    })
                }
                else {
                    this.$request.delete("/api/admin/batchDeleteInfluence/${this.ids}").then(res => {
                        if (!res) {
                            this.$message.info("后台未启动！");
                            return;
                        }
                        if (res.code === '200') {
                            console.log(res)
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
        userList() {
            let params = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                userName: this.userName,
            }

            this.$request.get("/api/admin/listInfluence", { params: params }).then(res => {
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
        showDialog(row) {
            this.userModel = row;
            this.dialogVisible = true;
        },
        updateuser() {
            this.$request.post("/api/admin/updateInfluence",{params:this.userModel}).then(res => {
                if (!res) {
                    this.$message.info("后台未启动！");
                    return;
                }
                if (res.code === '200') {
                    this.$message.success('修改成功!')
                    this.userList();
                } else {
                    this.$message.error(res.msg);
                }
            })
            this.dialogVisible = false;
        }
    }
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>高影响力人物管理</span>
                <div class="extra">
                    <el-button icon="el-icon-delete" type="danger" @click="handleDelete()">批量删除</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="高影响力人物名称：">
                <el-input prefix-icon="el-icon-search" placeholder="请输入高影响力人物名称" v-model="userName"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="userList">搜索</el-button>
                <el-button @click="userName = ''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 高影响力人物列表 -->
        <el-table :data="users" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column label="高影响力人物账号" property="username"></el-table-column>
            <el-table-column label="高影响力人物名称" property="name"></el-table-column>
            <el-table-column label="高影响力人物头像" property="pic">
                <template #default="scope">
                    <img :src="scope.row.pic" style="width: 100px;">
                </template>
            </el-table-column>
            <el-table-column label="高影响力人物介绍" property="introduction"></el-table-column>
            <el-table-column label="高影响力人物所在地" property="location"></el-table-column>
            <el-table-column label="高影响力人物关注人数" property="follow"></el-table-column>
            <el-table-column label="高影响力人物粉丝数" property="followers"></el-table-column>
            <el-table-column label="高影响力人物所属分类" property="field"></el-table-column>
            <el-table-column label="操作" width="200" fixed="right">
                <template #default="{ row }">
                    <el-button icon="el-icon-edit" round plain type="primary" @click="showDialog(row)">编辑</el-button>
                    <el-button icon="el-icon-delete" round plain type="danger"
                        @click="handleDelete(row.id)">删除</el-button>
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

        <el-dialog :visible.sync="dialogVisible" title="编辑信息" width="30%">
            <el-form :model="userModel" label-width="100px">
                <el-form-item label="高影响力人物账号">
                    <el-input v-model="userModel.username" placeholder="请输入账号"></el-input>
                </el-form-item>
                <el-form-item label="高影响力人物名称" >
                    <el-input v-model="userModel.name" placeholder="请输入名称"></el-input>
                </el-form-item>
                <el-form-item label="高影响力人物简介">
                    <el-input v-model="userModel.introduction" placeholder="请输入简介"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateuser()"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>
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