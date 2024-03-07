# ErGo

## 简介

Spring Boot + Vue前后端分离系统
个人学习，长期维护

## 后端

技术栈

| 技术          | 说明                       |
| ------------- | -------------------------- |
| SpringBoot    | Spring应用简化集成开发框架 |
| mybatis       | 数据库orm框架              |
| mybatis-plus  | 数据库orm框架              |
| elasticsearch | 近实时文本搜索             |
| redis         | 内存数据存储               |
| oss           | 对象存储                   |
| jwt           | jwt登录                    |
| lombok        | Java语言增强库             |
| knife4        | API文档生成工具            |

## 前端

核心框架：Vue2.x、Vue Router、Vuex

### 后台 UI

后台管理界面基于 [vue-admin-template](https://github.com/PanJiaChen/vue-admin-template) 进行二次修改，使用了 Element UI 组件库。

```shell
npm install
npm run dev
```

```
#访问地址
  - Local:   http://localhost:8081/
  - Network: http://192.168.1.2:8081/
```

首页图片

![首页图片](https://p.sda1.dev/16/29e5109d4bb6e203f9cde610391ce168/后台首页.png)

修改账户

![](https://p.sda1.dev/16/1fa121dc3eaad15226e2f1874fba6d72/后台修改账户.png)

分类管理

![](https://p.sda1.dev/16/577380359ba27d2c8201ded1722b4b10/后台分类.png)

标签管理

![](https://p.sda1.dev/16/d7dbba717d97c9e28f080c4cd4549aab/后台标签.png)

文章管理

![](https://p.sda1.dev/16/23f0dde3266d5e06ef997dade39fdc7f/后台文章.png)

用户管理

![](https://p.sda1.dev/16/af436fef9adb55784159c6996757a69e/后台用户.png)

评论管理

![](https://p.sda1.dev/16/b38bfce0d6fcb86b577b1d42c8b3331d/后台评论.png)



### 前台 UI

vue2.x

[Element UI](https://github.com/ElemeFE/element)

#### 安装步骤

**Clone：**

```shell
# GitHub
git clone https://github.com/d1752472377/ErGo.git
```

**Install：**

```
cd ErGo/vue-frontend
pnpm install
pnpm run serve
```

地址：http://localhost:8888/

首页截图

![前端截图](https://p.sda1.dev/16/060fc1cf004b9a873955944e24f86fd1/Snipaste_2024-03-08_01-10-28.png)

发布文章

![](https://p.sda1.dev/16/52af92cf2e766bd7136f8140738c8d1e/Snipaste_2024-03-08_01-15-33.png)



## 开发环境

| 工具          | 版本      |
| ------------- | --------- |
| jdk           | 17        |
| maven         | 3.8.1     |
| mysql         | 5.7+/8.0+ |
| redis         | 3.2.100   |
| elasticsearch | 8.0.0+    |

