<template>
<div class="container">

<div class="register">
    <div class="log">
        <span style="">登录</span>
        <div style="margin-left:170px">
            <span >还没有账号? </span>
        <a href="/regist">立即注册</a>
        </div>
        
    </div>
    <div class="form">
        <div class="item">
            <el-input placeholder="请输入电子邮箱" v-model="form.username" prefix-icon="el-icon-message" style="width: 360px; height:40px;"></el-input>
        </div>
        <div class="item">
            <el-input placeholder="请输入登录密码"  v-model="form.password" show-password prefix-icon="el-icon-lock" style="width: 360px; height:40px;"></el-input>
        </div>

    </div>
    <div class="button">
        <button class="btn" type="submit" @click="handleSubmit">立即登录</button>
    </div>
</div>
<a class="home" href="/">
    <svg t="1710399771596" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2354" width="12" height="12"><path d="M563.197 0H460.803L0.013 486.388s0.9 102.396 51.2 102.396h76.796v332.817c0 100.3 102.397 102.399 102.397 102.399h204.797V665.583c0-25.647 25.6-25.6 25.6-25.6H563.2s25.6 0.852 25.6 25.6V1024h204.796c105.147 0 102.398-102.4 102.398-102.4V563.186h76.799c49.597 0 51.196-76.796 51.196-76.796L563.198 0z" p-id="2355" fill="#ffffff"></path></svg>
      返回首页
</a>
</div>

</template>
<script>
import {getKey} from '@/api/key'
import { setAesKey, getAes } from "@/utils/auth";
import {login} from '@/api/login'
import {AESEncrypt} from '@/api/aes'
export default {
  data() {
    return {
      res: {
      requestData: "",
      },
      form: {
        username: '1752472377@qq.com',
        password: 'dgn147258'
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
      //获取aes密钥
      getKey().then((response) => {
        // console.log(response.data.key)
        //设置到cookie中
        setAesKey(response.data.key);
      });
  },
  methods: {
    handleSubmit() {
      //获取保存在cookie的AES密钥
      let aesKey = getAes();
      //进行参数加密,必须把对象转换json字符串，不然加密不了
      let dataJson = JSON.stringify(this.form);
      //数据进行加密
      this.res.requestData = AESEncrypt(dataJson, aesKey);
      this.$store.dispatch("Login", this.res).then(() => {
        this.$router.go(-1); // 返回上一页
      })
    },
    handleRigister(){
      console.log(111)
    }
    
  }
}
</script><style scoped>
.container{
    position: relative;

    padding-top: 30px;
    width: 100%;
    height: 100vh;
    font-size: 16px;
    line-height: 1.8;
    counter-reset: footnotes;
}
.register{
    margin: auto;
    padding-top: 20px;
    padding-bottom: 20px;
    
    background-color: white;
    width: 410px;
    border-radius: 5px;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
}

.log{
    margin-bottom: 30px;

    margin-left: 25px;
    width: 360px;
    display: flex;
    
    /* text-align: left; */
}
.item{
    margin-bottom: 20px;
}
.home {
    background-color: red;
    border-radius: 5px;
    line-height: 40px;
    padding-left: 30px;
    padding-right: 30px;
    position: absolute;
    top: 30px; /*距离顶部的距离*/
    right: 20px; /* 距离右侧的距离 */
    color: white;
}
.btn{
    width: 360px;
    height: 39.6px;
    background-color: red;
    outline: 0;
    z-index: 0;
    border-radius: 4px;
    font-size: 14px;
  line-height: 16px;
  margin-bottom: 0;
  font-weight: 500;
  text-align: center;
  touch-action: manipulation;
  cursor: pointer;
  border: 1px solid #dcdfe6;
  white-space: nowrap;
  user-select: none;
  color: #fff;
}
</style>