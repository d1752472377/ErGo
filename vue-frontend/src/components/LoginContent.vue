<template>
<div class="login-container">
    <div class="login-form">
      <h2>登录</h2>
      <Form ref="form" :model="form" :rules="rules">
        <FormItem prop="username">
         <Input type="text" v-model="form.username" placeholder="用户名">
            <Icon type="ios-person-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <FormItem prop="password">
         <Input type="password" v-model="form.password" placeholder="密码">
            <Icon type="ios-lock-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <FormItem>
         <Button type="primary" @click="handleSubmit">登录</Button>
        </FormItem>
      </Form>
    </div>
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
  beforeCreate() {
    
    if (getAes() == undefined) {
      //获取aes密钥
      getKey().then((response) => {
        // console.log(response.data.key)
        //设置到cookie中
        setAesKey(response.key);
      });
    }
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
        this.$router.go(-1);
        // console.log('666')
      })
      
      // login(this.res).then(response=>{
      //   this.$router.go(-1);
      //   // this.reload();
      //   console.log(response.data.data.userInfo)
      // })
    }
  }
}
</script><style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f0f0;
}

.login-form {
  width: 400px;
  padding: 30px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.login-form h2 {
  text-align: center;
  margin-bottom: 20px;
}
</style>@/api/aes