<template>
        <div class="container">

<div class="register">
    <div class="log">
        <span style="">注册</span>
        <div style="margin-left:170px">
            <span >已经有账号? </span>
        <a href="/login">立即登录</a>
        </div>
        
    </div>
    <div class="form">
        <div class="item">
            <el-input placeholder="请输入电子邮箱" v-model="email" prefix-icon="el-icon-message" style="width: 360px; height:40px;"></el-input>
        </div>
        <div class="item">
            <el-input placeholder="请输入登录密码"  v-model="password" show-password prefix-icon="el-icon-lock" style="width: 360px; height:40px;"></el-input>
        </div>
        <div class="item">
            <el-input placeholder="请确认登录密码" v-model="password1" show-password prefix-icon="el-icon-lock" style="width: 360px; height:40px;"></el-input>
        </div>

    </div>
    <div class="button">
        <button class="btn" type="submit" @click="sendRegist">提交注册</button>
    </div>
</div>
<a class="home" href="/">
    <svg t="1710399771596" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2354" width="12" height="12"><path d="M563.197 0H460.803L0.013 486.388s0.9 102.396 51.2 102.396h76.796v332.817c0 100.3 102.397 102.399 102.397 102.399h204.797V665.583c0-25.647 25.6-25.6 25.6-25.6H563.2s25.6 0.852 25.6 25.6V1024h204.796c105.147 0 102.398-102.4 102.398-102.4V563.186h76.799c49.597 0 51.196-76.796 51.196-76.796L563.198 0z" p-id="2355" fill="#ffffff"></path></svg>
      返回首页
</a>
</div>

  
</template>

<script>
import { register } from '@/api/register'

export default {
    data(){
        return{
            email:'',
            password:'',
            password1:'',
            pic:'',
            userName:'',
            picpath:[
                "https://p.sda1.dev/16/7e700d50cded6934ce6f12f7bcff109d/00000001.jpg",
                "https://p.sda1.dev/16/a697b4f2e49bd8c8aec4ced77a31d513/00000002.jpg",
                "https://p.sda1.dev/16/622161189f55b934b9a610ab34b6b4d5/00000003.jpg",
                "https://p.sda1.dev/16/b43d1cde40d973a49fff1c036115da2d/00000006.jpg",
                "https://p.sda1.dev/16/0f0f4e49eb5942dce7a53b77094c8f8c/00000007.jpg",
                "https://p.sda1.dev/16/707b043d9b87850b8b596debececf17f/00000009.jpg",
                "https://p.sda1.dev/16/18ac32b8660ec83bf3436277658a703d/00000010.jpg",
                "https://p.sda1.dev/16/3683d8800e025776a2052072555c06eb/00000012.jpg",
                "https://p.sda1.dev/16/72ae3471c0891d58fba0e3039160be8c/00000013.jpg",
                "https://p.sda1.dev/16/eed70935ac4d68758df10b79cfeceb3a/00000015.jpg",
            ],
            userpath:[

            ]
        }
    },
    created(){
        let index = Math.round(Math.random()*10)
        this.pic = this.picpath[index]

        //随机用户名
        let nameArr = [
            [1, 2, 3, 4, 5, 6, 7, 8, 9, 0],
            ["a", "b", "c", "d", "e", "f", "g", "h", "i", "g", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"]
        ]
        let name = 'User';
        for (var i = 0; i < 9; i++) {
        // 随机生成index
        let index = Math.floor(Math.random() * 2);
        let zm = nameArr[index][Math.floor(Math.random() * nameArr[index].length)];
        // 如果随机出的是英文字母
        if (index === 1) {
            // 则百分之50的概率变为大写
            if (Math.floor(Math.random() * 2) === 1) {
                zm = zm.toUpperCase();
            }
        }
        // 拼接进名字变量中
        name += zm;
    }
    this.userName = name

    },
    methods:{
        sendRegist(){
            const data={
                photo:this.pic,
                userName:this.userName,
                email:this.email,
                password:this.password,

            }
            register(data).then(res=>{
                this.$router.go(-1); // 返回上一页
            })
            console.log(data)
        }
    }
}
</script>

<style scoped>

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