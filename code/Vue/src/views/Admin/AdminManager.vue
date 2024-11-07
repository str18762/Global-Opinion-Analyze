<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>管理员管理</span>
                <div class="extra">
                    <el-button icon="el-icon-delete" type="danger" @click="handleDelete()">批量删除</el-button>
                </div>
            </div>
        </template>
        <!-- 管理员列表 -->
        <el-table :data="admins" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column label="管理员账号" property="username"></el-table-column>
            <el-table-column label="管理员名称" property="name"></el-table-column>
            <el-table-column label="管理员头像" property="avatar">
                <template #default="scope">
                    <img :src="scope.row.avatar" style="width: 100px;">
                </template>
            </el-table-column>
            <el-table-column label="管理员邮箱" property="email"></el-table-column>
            <el-table-column label="管理员手机" property="phone"></el-table-column>
            <el-table-column label="操作" width="240" fixed="right">
                <template #default="{ row }">
                    <el-button icon="el-icon-delete" round  type="danger" @click="handleDelete(row.id)">删除</el-button>
                    <el-button plain type="primary" round @click="resetPassword(row.id)">重置密码</el-button>
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
<script>
// import {
//     Edit,
//     Delete,
//     Plus,
//     Search
// } from '@element-plus/icons-vue'

// import { ref } from 'vue'
// import { ElMessageBox } from 'element-plus'
// import { ElMessage } from 'element-plus'

export default {
  data() {
    return {
      admins: [],
      pageNum: 1,    //当前页
      total: 20,     //总条数
      pageSize: 10,   //每页条数
      ids: [],
    };
  },
  mounted() {
    this.adminList();
  },
  methods: {
    onSizeChange(size) {
      this.pageSize = size
      this.adminList()
    },
    onCurrentChange(num) {
      this.pageNum = num
      this.adminList()
    },
    handleSelectionChange(admins) {
      this.ids = admins.map(admin => admin.id)
    },
    handleDelete(adminId) {
      this.$confirm('你确认要删除该管理员吗?', '温馨提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (adminId) {
          this.$request.delete("/admin/deleteUser?id="+adminId).then(res => {
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
              this.adminList();
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
        this.adminList();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消删除'
        });
      });
    },
    async adminList() {
      let params = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      }

      this.$request.get("/admin/listAdmin", { params: params }).then(res => {
        if (!res) {
          this.$message.info("后台未启动！");
          return;
        }
        if (res.code === '200') {
          this.total = res.data.total;
          this.admins = res.data.items;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    resetPassword(id) {
      this.$confirm('你确认要重置该管理员的密码吗?', '温馨提示', {
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
        this.adminList();
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