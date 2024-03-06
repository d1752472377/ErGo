<template>
  <div class="update">
    <el-card style="width:800px;margin-top:20px;margin-left:20px">
      <span>修改当前用户</span>
      <div class="item">
        <span>账户：</span>
        <el-input v-model="name" placeholder="请输入内容" style="width:600px"></el-input>
      </div>
      <div class="item">
        <span>密码：</span>
        <el-input placeholder="请输入密码" v-model="password" show-password style="width:600px" ></el-input>
      </div>
      <div class="item">
        <el-button type="primary" @click="handleUpdate">确认修改</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { updatePassword } from '../../../api/user'
import { Message } from 'element-ui'


export default {
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  data(){
    return{
      username:'',
      password:'',

    }
  },
  methods:{
    handleUpdate(){
      const data = {
        userName: this.name,
        password:this.password
      }
      updatePassword(data).then(res=>{
        Message.success("修改成功")
      })
    }
  }

}
</script>

<style>
.item{
  display: flex;
  margin-top: 20px;
}

</style>
