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

暂时没上传

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



![前端截图](/picture/前端截图.png)

地址：http://localhost:8888/

![前端首页](/picture/前端首页.png)

## 开发环境

| 工具          | 版本      |
| ------------- | --------- |
| jdk           | 17        |
| maven         | 3.8.1     |
| mysql         | 5.7+/8.0+ |
| redis         | 3.2.100   |
| elasticsearch | 8.0.0+    |

