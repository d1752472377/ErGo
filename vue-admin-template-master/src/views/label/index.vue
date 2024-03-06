<template>
  <div>
    <el-card class="box-card">

      <div class="top">
        <el-button type="primary" size="small" icon="el-icon-plus" @click="addDialogVisible = true">新增标签</el-button>
      </div>
      <div class="table">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="id" label="序号" width="180">
          </el-table-column>
          <el-table-column prop="tagName" label="名称" width="180">
          </el-table-column>
          <el-table-column prop="createTime" label="时间">
            <template v-slot="scope">{{ scope.row.createTime | formatDate }}</template>
          </el-table-column>
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
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
          :page-sizes="[10, 20, 30, 40]" :page-size="pagesize" layout="total, sizes, prev, pager, next, jumper"
          :total="total" background>
        </el-pagination>

        <!--添加标签对话框-->
        <el-dialog title="添加标签" width="50%" :visible.sync="addDialogVisible" :close-on-click-modal="false"
          @close="addDialogClosed">
          <!--内容主体-->
          <el-form :model="addForm" :rules="formRules" ref="addFormRef" label-width="80px">
            <el-form-item label="标签名称" prop="tagName">
              <el-input v-model="addForm.tagName"></el-input>
            </el-form-item>
          </el-form>
          <!--底部-->
          <span slot="footer">
            <el-button @click="addDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addTag">确 定</el-button>
          </span>
        </el-dialog>

        <el-dialog title="编辑标签" width="50%" :visible.sync="editDialogVisible" :close-on-click-modal="false"
          @close="editDialogClosed">
          <!--内容主体-->
          <el-form :model="editForm" :rules="formRules" ref="editFormRef" label-width="80px">
            <el-form-item label="标签名称" prop="tagName">
              <el-input v-model="editForm.tagName"></el-input>
            </el-form-item>
          </el-form>
          <!--底部-->
          <span slot="footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editTag">确 定</el-button>
          </span>
        </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { getTagList, updateTag ,addTag, deleteTagById} from '@/api/tag'

export default {
  data() {
    return {
      total: 0,
      pagesize: 10,
      currentPage: 1,
      tableData: [],
      editDialogVisible: false,
      addDialogVisible: false,
      editForm: {},
      formRules: {
        categoryName: [{ required: true, message: '请输入标签名称', trigger: 'blur' }]
      },
      addForm: {
        categoryName: '',
        status: 1,
        rank: 1,
        deleted: 0
      },
    }
  },
  created(){
    this.getList()
  },
  filters:{
    formatDate: function(value,args) {
            var dt = new Date(value);
            if(args == 'yyyy-M-d') {// yyyy-M-d
            let year = dt.getFullYear();
            let month = dt.getMonth() + 1;
            let date = dt.getDate();
            return `${year}-${month}-${date}`;
        } else if(args == 'yyyy-M-d H:m:s'){// yyyy-M-d H:m:s
            let year = dt.getFullYear();
            let month = dt.getMonth() + 1;
            let date = dt.getDate();
            let hour = dt.getHours();
            let minute = dt.getMinutes();
            let second = dt.getSeconds();
            return `${year}-${month}-${date} ${hour}:${minute}:${second}`;
        } else if(args == 'yyyy-MM-dd') {// yyyy-MM-dd
            let year = dt.getFullYear();
            let month = (dt.getMonth() + 1).toString().padStart(2,'0');
            let date = dt.getDate().toString().padStart(2,'0');
            return `${year}-${month}-${date}`;
        } else {// yyyy-MM-dd HH:mm:ss
            let year = dt.getFullYear();
            let month = (dt.getMonth() + 1).toString().padStart(2,'0');
            let date = dt.getDate().toString().padStart(2,'0');
            let hour = dt.getHours().toString().padStart(2,'0');
            let minute = dt.getMinutes().toString().padStart(2,'0');
            let second = dt.getSeconds().toString().padStart(2,'0');
            return `${year}-${month}-${date} ${hour}:${minute}:${second}`;
        }
        }
  },
  methods:{
    getList(){
      const params ={
        pagesize:this.pagesize,
        currentPage:this.currentPage,
      }
      getTagList(params).then(res=>{
        console.log(res.data)
        this.tableData = res.data.list
        this.total = res.data.Total
      })
    },
    handleSizeChange(val) {
      this.pagesize = val
      this.getList()
    },
    //监听页码改变事件
    handleCurrentChange(val) {
      this.currentPage = val
      this.getList()
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
    editTag() {
      this.$refs.editFormRef.validate(valid => {
        if (valid) {
          updateTag(this.editForm).then(response => {
            this.editDialogVisible = false
            this.getList();
          })
        }
      })
    },
    deleteById(id) {
      this.$confirm('此操作将永久删除<strong style="color: red">该标签</strong>，是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: "warning",
        dangerouslyUseHTMLString: true
      }).then(() => {
        deleteTagById(id).then(res => {
          this.getList()
          this.$message.success("删除成功")
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    addTag() {
      this.$refs.addFormRef.validate(valid => {
        if (valid) {
          addTag(this.addForm).then(res => {
            this.addDialogVisible = false
            this.getList();
            this.$message.success("添加成功")
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
</style>
