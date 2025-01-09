import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import '@/assets/css/global.css';
import '@/assets/css/iconfont/iconfont.css';

import axios from 'axios'
axios.defaults.baseURL=global_variable.baseURL;  //设置一个类似base_url的请求路径
global.axios=axios;  //设置一个全局axios便于调用
axios.defaults.headers.post['Content-Type'] = 'application/json';

Vue.prototype.Global=global_variable


import '@/assets/css/echartsTest.css'
import dataV from '@jiaminghi/data-view'
Vue.use(dataV);

import request from "@/utils/request";
import global_variable from "@/assets/global_variable";


Vue.use(ElementUI);

Vue.config.productionTip = true

Vue.prototype.$request=request


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
