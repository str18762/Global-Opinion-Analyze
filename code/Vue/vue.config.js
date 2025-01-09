const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: process.env.VUE_APP_SERVER_PORT || 8080,
    open: true,
    proxy: {
      '/api': {
        target: process.env.VUE_APP_SERVER_HOST,
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  },
  chainWebpack: config => {
    config.plugin('html')
        .tap(args => {
          args[0].title="基于高影响力人物追踪的国际舆论分析系统";
          return args;
        })
  },
})
