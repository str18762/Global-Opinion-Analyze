<script>

export default {
    data() {
        return {
            emotions: [],
            pageNum: 1,    //当前页
            total: 20,     //总条数
            pageSize: 10,   //每页条数
            detail:'',
            name:'',
            selectedEmotion: [],
        };
    },
    mounted() {
        this.emotionList();
    },
    methods: {
        onSizeChange(size) {
            this.pageSize = size
            this.emotionList()
        },
        onCurrentChange(num) {
            this.pageNum = num
            this.emotionList()
        },
        handleSelectionChange(emotions) {
            this.selectedEmotion = emotions
        },
        handleDelete(emotion) {
            this.$confirm('你确认要删除该条情感吗?', '温馨提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (emotion) {
                    this.$request.delete("/admin/deleteEmotion",{params:emotion}).then(res => {
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
                    if(this.selectedEmotion.length==0){
                        this.$message.error('请至少选择一条情感!')
                        return
                    }
                    this.$request.post(`/admin/batchDeleteEmotion`,this.selectedEmotion).then(res => {
                        if (!res) {
                            this.$message.info("后台未启动！");
                            return;
                        }
                        if (res.code === '200') {
                            this.emotionList();
                            console.log(res.data)
                        } else {
                            this.$message.error(res.msg);
                        }
                    })
                }
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                this.emotionList();
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消删除'
                });
            });
        },
        async emotionList() {
            let params = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                detail:this.detail,
                name:this.name,
            }

            this.$request.get("/admin/listEmotion", { params: params }).then(res => {
                if (!res) {
                    this.$message.info("后台未启动！");
                    return;
                }
                if (res.code === '200') {
                    this.total = res.data.total;
                    this.emotions = res.data.items;
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
                <span>情感分析管理</span>
                <div class="extra">
                    <el-button icon="el-icon-delete" type="danger" @click="handleDelete()">批量删除</el-button>
                </div>
            </div>
        </template>
        <el-form inline>
            <el-form-item label="情感相关人：">
                <el-input prefix-icon="el-icon-search" placeholder="请输入相关人" v-model="name"></el-input>
            </el-form-item>
            <el-form-item label="情感细节：">
                <el-input prefix-icon="el-icon-search" placeholder="请输入细节" v-model="detail"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="emotionList">搜索</el-button>
                <el-button @click="name = '';detail='' ">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 情感列表 -->
        <el-table :data="emotions" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column label="情感相关人" property="name"></el-table-column>
            <el-table-column label="年份" property="year"></el-table-column>
            <el-table-column label="月份" property="month"></el-table-column>
            <el-table-column label="情感细节" property="detailEmotion"></el-table-column>
            <el-table-column label="操作" width="240" fixed="right">
                <template #default="{ row }">
                    <el-button icon="el-icon-delete" round  type="danger" @click="handleDelete(row)">删除</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 20]"
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