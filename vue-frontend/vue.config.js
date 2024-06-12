const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: 'localhost', // 项目运行的ip
    port: 8090, // 项目运行的端口号
    client: {
      overlay: false
    },
  //   proxy: {
  //     "/api":{
  //       target:'http://localhost:8080/',
  //       changeOrigin: true,  
  //       pathRewrite: {                  //路径重置
  //         '^/api': ''
  //     }
  //     }
    // }
  }
})
