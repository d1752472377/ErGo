<template>
  <div>
    <el-card class="box-card">
      <div class="select">
        <el-select v-model="value" clearable placeholder="请选择页面" style="width:400px" @change="handleSelect">
          <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.title"
            :value="item.id">
          </el-option>
        </el-select>
      </div>
      <div class="table">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="id" label="序号" width="80">
          </el-table-column>
          <el-table-column prop="userName" label="昵称" width="80">
          </el-table-column>
          <el-table-column prop="photo" label="头像" width="85">
            <template slot-scope="scope">
              <img :src="scope.row.photo" width="60px" height="60px">
            </template>
          </el-table-column>
          <el-table-column prop="content" label="评论内容" width="180">
          </el-table-column>
          <el-table-column prop="title" label="文章标题" width="180">
          </el-table-column>
          <el-table-column prop="createTime" label="时间">
            <template v-slot="scope">{{ scope.row.createTime | formatDate }}</template>
          </el-table-column>
          <el-table-column prop="operate" label="操作" width="100">
            <template v-slot="scope">
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
    </el-card>
  </div>
</template>

<script>
import {getListByPage, deleteCommentById,getCommentByArticleId} from '@/api/comment'
import { getAllArticleTitle } from '../../api/article'

export default {
  data() {
    return {
      total: 0,
      pagesize: 10,
      currentPage: 1,
      tableData: [],
      formRules: {
        categoryName: [{ required: true, message: '请输入标签名称', trigger: 'blur' }]
      },
      options:[],
      value:[]
    }
  },
  created(){
    this.getList()
    this.articleIdAndTitle()
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
    articleIdAndTitle(){
      getAllArticleTitle().then(res=>{
        this.options = res.data
        console.log(this.options)
      })
    },
    getList(){
      const params ={
        pagesize:this.pagesize,
        currentPage:this.currentPage,
      }
      getListByPage(params).then(res=>{

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


    deleteById(id) {
      this.$confirm('此操作将永久删除<strong style="color: red">该评论</strong>，是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: "warning",
        dangerouslyUseHTMLString: true
      }).then(() => {
        deleteCommentById(id).then(res => {
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
    handleSelect(val){
      const params={
        articleId:val,
        pageSize:this.pagesize,
        pageNum:this.currentPage
      }
      if(params.articleId != ''){
        getCommentByArticleId(params).then(res=>{
        this.tableData = res.data.commentList
        this.total = res.data.allComment
      })
      }else{
        this.getList()
      }

    },
  }
}
</script>

<style lang="scss">
.table {
  margin-top: 20px;
}
</style>
