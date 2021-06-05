import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'

// 导入axios组件  qs组件
import axios from 'axios'
import qs from 'qs'

Vue.config.productionTip = false
// 基础访问路径
axios.defaults.baseURL = "http://localhost:8080/finance/"
// 跨域带 cookie：用于页面跳转后cookie中登录的用户不被清空
axios.defaults.withCredentials = true

Vue.config.productionTip = false

// 响应拦截器 统一处理响应数据
axios.interceptors.response.use(function(resp) {
	if (resp.data.code == 200) {
		// 如果服务器响应码是 200 的话 通过路由跳转到登录组件
		console.log("拦截器输出： 没有登录的用户")
		router.push("/login");
	}
	return resp;
}, function(error) {
	return Promise.reject(error);
});

Vue.prototype.$axios = axios
Vue.prototype.$qs = qs

new Vue({
	router,
	render: h => h(App)
}).$mount('#app')
