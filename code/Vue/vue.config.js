const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 7000
  },
  chainWebpack: config => {
    config.plugin('html')
        .tap(args => {
          args[0].title="基于高影响力人物追踪的国际舆论分析系统";
          return args;
        })
  }
})
