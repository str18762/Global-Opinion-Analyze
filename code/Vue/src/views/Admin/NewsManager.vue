<script>

export default {
    data() {
        return {
            news: [],
            pageNum: 1,    //当前页
            total: 20,     //总条数
            pageSize: 10,   //每页条数
            wheres: [],
            newWhat:'',
        };
    },
    mounted() {
        this.newList();
    },
    methods: {
        onSizeChange(size) {
            this.pageSize = size
            this.newList()
        },
        onCurrentChange(num) {
            this.pageNum = num
            this.newList()
        },
        handleSelectionChange(news) {
            this.wheres = news.map(news => news.where)
        },
        handleDelete(newWhere) {
            this.$confirm('你确认要删除该新闻吗?', '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (newWhere) {
                    this.$request.delete("/api/admin/deleteNew?where="+newWhere).then(res => {
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
                    this.$request.delete(`/api/admin/batchDeleteNew/${this.wheres}`).then(res => {
                        if (!res) {
                            this.$message.info("后台未启动！");
                            return;
                        }
                        if (res.code === '200') {
                            this.newList();
                        } else {
                            this.$message.error(res.msg);
                        }
                    })
                }
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                this.newList();
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消删除'
                });
            });
        },
        async newList() {
            let params = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                newWhat: this.newWhat,
            }

            this.$request.get("/api/admin/listNew", { params: params }).then(res => {
                if (!res) {
                    this.$message.info("后台未启动！");
                    return;
                }
                if (res.code === '200') {
                    this.total = res.data.total;
                    this.news = res.data.items;
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
    }
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>新闻管理</span>
                <div class="extra">
                    <el-button icon="el-icon-delete" type="danger" @click="handleDelete()">批量删除</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="新闻内容/关键词：">
                <el-input prefix-icon="el-icon-search" placeholder="请输入新闻关键词" v-model="newWhat"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="newList">搜索</el-button>
                <el-button @click="newWhat='' ">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 新闻列表 -->
        <el-table :data="news" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" wwhereth="55" />
            <el-table-column label="新闻相关人" property="who"></el-table-column>
            <el-table-column label="新闻内容" property="what"></el-table-column>
            <el-table-column label="新闻地址" property="where"></el-table-column>
            <el-table-column label="操作" width="200" fixed="right">
                <template #default="{ row }">
                    <el-button icon="el-icon-delete" round plain type="danger" @click="handleDelete(row.where)">删除</el-button>
                    <el-button icon="el-icon-share" plain type="primary" round><a :href="row.where">详情</a></el-button>
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