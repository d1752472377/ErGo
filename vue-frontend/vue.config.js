const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: 'localhost', // 项目运行的ip
    port: 8888, // 项目运行的端口号
    client: {
      overlay: false
    }
  }
})
