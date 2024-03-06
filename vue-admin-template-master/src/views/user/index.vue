<template>
  <div>
    <el-card class="box-card">
      <div class="top">
        <el-select v-model="value" clearable placeholder="用户类型" style="width: 200px; margin-right: 10px;">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="value" clearable placeholder="用户状态" style="width: 200px; margin-right: 10px;">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <el-input placeholder="用户名 / 手机号" v-model="input" clearable style="width: 200px; margin-right: 10px;">
        </el-input>
        <el-button type="primary" icon="el-icon-search">搜索</el-button>
      </div>
      <div class="table">
        <el-table :data="tableData" border style="width: 100%">
          <!-- <el-table :data="tableData.slice((currentPage - 1) * pagesize, currentPage * pagesize)" border style="width: 100%">纯前端分页长这样 -->
          <el-table-column prop="id" label="序号" width="180"></el-table-column>
          <el-table-column prop="userName" label="用户名" width="180"></el-table-column>
          <el-table-column label="头像" width="100px">
            <template v-slot="scope">
              <img :src="scope.row.photo" width="60px" height="60px">
            </template>
          </el-table-column>

          <el-table-column prop="userRole" label="权限">
            <template slot-scope="scope">
              {{ scope.row.userRole === 0 ? '普通用户' : (scope.row.userRole === 1 ? '管理员' : '其他') }}
            </template>
          </el-table-column>
          <el-table-column prop="email" label="邮箱"></el-table-column>
          <el-table-column prop="phone" label="手机号"></el-table-column>
          <el-table-column label="状态">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.status" @change="blogRecommendChanged(scope.row)"></el-switch>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="block">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
          :page-sizes="[10, 20, 30, 40]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getUserList, updateUserForStatus } from '../../api/user'
export default {
  data() {
    return {
      value:'',
      options:[],
      input:'',
      tableData: [{
        id: '',
        userName: '',
        photo: '',
        userRole: '',
        email: '',
        phone: '',
        status: '1',
      }],
      total: 0,
      pagesize: 10,
      currentPage: 1
    }
  },
  created() {
    this.getList();
  },
  methods: {
    blogRecommendChanged(row) {
      updateUserForStatus(row).then(res=>{
        this.getList()
      })

    },
    getList() {
      const params = {
        pageSize: this.pagesize,
        pageNum: this.currentPage,
      }
      getUserList(params)
        .then(response => {
          this.tableData = response.data.list
          this.total = response.data.total
        })
    },
    //监听 pageSize 改变事件
    handleSizeChange(val) {
      this.pagesize = val
      this.getList()
    },
    //监听页码改变事件
    handleCurrentChange(val) {
      this.currentPage = val
      this.getList()
    },
  }

}
</script>

<style>
.table {
  margin-top: 20px;
}
</style>
