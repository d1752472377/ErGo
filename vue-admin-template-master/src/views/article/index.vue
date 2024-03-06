<template>
  <div>
    <el-card class="box-card">
      <div class="top">
        <el-input placeholder="文章标题" v-model="input" clearable @clear="handleClear" style="width: 200px; margin-right: 10px;">
        </el-input>
        <el-button type="primary" icon="el-icon-search" @click="getListByTitle">搜索</el-button>
      </div>
      <div class="table">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="id" label="序号" v-model="tableData.id" width="80"></el-table-column>
          <el-table-column prop="userName" label="作者" v-model="tableData.userName"></el-table-column>
          <el-table-column prop="title" label="标题" v-model="tableData.title" show-overflow-tooltip></el-table-column>
          <el-table-column prop="categoryName" label="分类" v-model="tableData.categoryName"></el-table-column>
          <el-table-column prop="toppingStat" label="置顶">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.toppingStat" active-toppingStat="1" :inactive-toppingStat="0"
                @change="blogTopChanged(scope.row)"></el-switch>
            </template>
          </el-table-column>
          <el-table-column prop="creamStat" label="推荐" v-model="tableData.creamStat">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.creamStat" :true-value="1" :false-value="0"
                @change="blogRecommendChanged(scope.row)"></el-switch>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="可见性">
            <!-- v-model="tableData.status" -->
            <template v-slot="scope">
              <span v-if="scope.row.status === 0">未发布</span>
              <span v-else-if="scope.row.status === 1">已发布</span>
              <span v-else-if="scope.row.status === 2">审核</span>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" v-model="tableData.createTime">
            <template v-slot="scope">{{ scope.row.createTime | formatDate }}</template>
          </el-table-column>
          <el-table-column prop="updateTime" label="最后更新" v-model="tableData.updateTime"></el-table-column>
          <el-table-column prop="operate" label="操作" width="200">
            <template v-slot="scope">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row)">审核</el-button>
              <el-popconfirm title="确定删除吗？" icon="el-icon-delete" iconColor="red" @onConfirm="deleteBlogById(scope.row.id)">
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
      </div>
    </el-card>

    <!--编辑可见性状态对话框-->
    <el-dialog title="博客可见性" width="30%" :visible.sync="editDialogVisible">
      <!--内容主体-->
      <el-form label-width="50px" @submit.native.prevent>
        <el-form-item>
          <el-radio-group v-model="editForm.status">
            <el-radio :label="0">未发布</el-radio>
            <el-radio :label="1">已发布</el-radio>
            <el-radio :label="2">审核</el-radio>
            <!-- <el-radio :label="3">密码保护</el-radio> -->
          </el-radio-group>
        </el-form-item>

      </el-form>
      <!--底部-->
      <span slot="footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveVisibility">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getArticleList, operate, updateTop, updateRecommend, deleteArticleById, getArticleListByTitle } from '@/api/article';

export default {
  data() {
    return {
      input:'',
      // currentPage4: 3,//默认第几页
      editDialogVisible: false,
      editForm: {},
      currentPage: 1,
      currentIndex: '',
      pagesize: 10,
      total: 0,
      tableData: [{
        id: '',
        userName: '',
        title: '',
        categoryName: '',
        toppingStat: '',
        creamStat: '',
        status: '',
        createTime: '',
        updateTime: '',
      }],
      visForm: {
        appreciation: false,
        recommend: false,
        commentEnabled: false,
        top: false,
        published: false,
        password: '',
      }
    }
  },
  created() {  // Corrected placement of created hook
    this.getList();
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
  methods: {
    handleClear(){
      this.getList()
    },
    getListByTitle(){
      const params ={
      title:this.input,
      pageSize:this.pagesize,
      currentPage:this.currentPage
    }
      getArticleListByTitle(params).then(res=>{
        // console.log(res.data)
        this.total = res.data.total;
        this.tableData = res.data.list
      })
    },

    //切换置顶状态
    blogTopChanged(row) {
      updateTop(row.id, row.toppingStat * 1).then(res => {
        // this.msgSuccess(res.msg);
      })
    },
    //切换推荐状态
    blogRecommendChanged(row) {
      updateRecommend(row.id, row.creamStat * 1).then(res => {
        // this.msgSuccess(res.msg);
      })
    },
    //监听 pageSize 改变事件
    handleSizeChange(val) {
      this.pagesize = val;
      this.getList()
    },
    //监听页码改变的事件
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
    saveVisibility(){
      if(this.editForm.toppingStat == true){
        this.editForm.toppingStat = 1
      }else{
        this.editForm.toppingStat = 0
      }
      if(this.editForm.creamStat == true){
        this.editForm.creamStat = 1
      }else{
        this.editForm.creamStat = 0
      }
      if(this.editForm.officalStat == true){
        this.editForm.officalStat = 1
      }else{
        this.editForm.officalStat = 0
      }
      operate(this.editForm).then(res=>{
        this.getList()
        this.editDialogVisible = false
        this.$message.success("修改成功")
      })
    },
    deleteBlogById(id) {
      this.$confirm('此操作将永久删除该文章<strong style="color: red">及其所有评论</strong>，是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        dangerouslyUseHTMLString: true
      }).then(() => {
        deleteArticleById(id).then(res => {
          // this.msgSuccess(res.msg)
          this.getList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    getList() {
      const params = {
        pageSize: this.pagesize,
        currentPage: this.currentPage,
      };
      console.log("开始发送请求")
      getArticleList(params)
        .then(response => {
          this.total = response.data.total;
          this.tableData = response.data.article
          // this.toppingStat = response.data.article.toppingStat*1
          // this.creamStat = response.data.article.creamStat*1

        })
    },
  },

}
</script>

<style scoped>
.table {
  margin-top: 20px;
}

.el-button+span {
  margin-left: 10px;
}</style>
