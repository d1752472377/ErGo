<template>
  <div>
    <el-card class="box-card">

      <div class="top">
        <el-row :gutter="10">
          <el-col :span="6">
            <el-button type="primary" size="small" icon="el-icon-plus" @click="addDialogVisible = true">添加分类</el-button>
          </el-col>
        </el-row>
      </div>
      <div class="table">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="id" label="序号"></el-table-column>
          <el-table-column prop="categoryName" label="名称"></el-table-column>
          <el-table-column prop="status" label="状态">
            <template slot-scope="scope">
              {{ scope.row.status === 0 ? '未发布' : '已发布' }}
            </template>
          </el-table-column>
          <!-- <el-table-column prop="rank" label="排序" ></el-table-column> -->
          <!-- <el-table-column prop="deleted" label="是否删除" ></el-table-column> -->
          <!-- <el-table-column prop="createTime" label="创建时间"></el-table-column>
          <el-table-column prop="updateTime" label="更新时间" ></el-table-column> -->
          <el-table-column prop="operate" label="操作" width="200">
            <template v-slot="scope">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">编辑</el-button>
              <el-popconfirm title="确定删除吗？" icon="el-icon-delete" iconColor="red" @onConfirm="deleteById(scope.row.id)">
                <el-button size="mini" type="danger" icon="el-icon-delete" slot="reference">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="block">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
          :page-sizes="[10, 20, 30, 40]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper"
          :total="total" background>
        </el-pagination>

        <!--添加分类对话框-->
        <el-dialog title="添加分类" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false"
          @close="addDialogClosed">
          <!--内容主体-->
          <el-form :model="addForm" :rules="formRules" ref="addFormRef" label-width="80px">
            <el-form-item label="分类名称" prop="categoryName">
              <el-input v-model="addForm.categoryName"></el-input>
            </el-form-item>
          </el-form>
          <!--底部-->
          <span slot="footer">
            <el-button @click="addDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addCategory">确 定</el-button>
          </span>
        </el-dialog>

        <!--编辑分类对话框-->
        <el-dialog title="编辑分类" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false"
          @close="editDialogClosed">
          <!--内容主体-->
          <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
            <el-form-item label="分类名称" prop="categoryName">
              <el-input v-model="editForm.categoryName"></el-input>
            </el-form-item>
          </el-form>
          <!--底部-->
          <span slot="footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editCategory">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getCategoryList, addCategory, deleteCategoryById, updateCategory } from '@/api/category';
export default {
  data() {
    return {
      tableData: [{
        id: '',
        categoryName: '',
        status: '',
        rank: '',
        deleted: '',
        createTime: '',
        updateTime: ''
      }],
      total: 0,
      pagesize: 10,
      currentPage: 1,
      addDialogVisible: false,
      editDialogVisible: false,
      addForm: {
        categoryName: '',
        status: 1,
        rank: 1,
        deleted: 0
      },
      editForm: {},
      formRules: {
        categoryName: [{ required: true, message: '请输入分类名称', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
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
    getList() {
      const params = {
        pageSize: this.pagesize,
        pageNum: this.currentPage,
      }
      getCategoryList(params)
        .then(response => {
          this.tableData = response.data.list
          this.total = response.data.total
        })
    },
    deleteById(id) {
      this.$confirm('此操作将永久删除该文章<strong style="color: red">及其所有评论</strong>，是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: "warning",
        dangerouslyUseHTMLString: true
      }).then(() => {
        deleteCategoryById(id).then(res => {
          this.getList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    showEditDialog(row) {
      //row 中没有对象(blogs是表单不需要的属性)，直接拓展运算符深拷贝一份(拓展运算符不能深拷贝对象，只能拷贝引用)
      //如果直接赋值，则为引用，表格上的数据也会随对话框中数据的修改而实时改变
      this.editForm = { ...row }
      this.editDialogVisible = true
    },
    addDialogClosed() {
      this.$refs.addFormRef.resetFields()
    },
    editDialogClosed() {
      this.editForm = {}
      this.$refs.editFormRef.resetFields()
    },
    addCategory() {
      this.$refs.addFormRef.validate(valid => {
        if (valid) {
          addCategory(this.addForm).then(res => {
            this.addDialogVisible = false
            this.getList();
          })
        }
      })
    },
    editCategory() {
      this.$refs.editFormRef.validate(valid => {
        if (valid) {
          updateCategory(this.editForm).then(response => {
            this.editDialogVisible = false
            this.getList();
          })
        }
      })
    },
  }
}
</script>

<style lang="scss">
.table {
  margin-top: 20px;
}

.el-button+span {
  margin-left: 10px;
}
</style>
