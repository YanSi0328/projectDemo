import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/login.vue'
import Main from '../components/main.vue'
import Default from '../components/default.vue'
import Test from '../components/test.vue'

// 系统
import Menu from '../components/sys/menu.vue'
import Admin from '../components/sys/admin.vue'

// 独角兽
import EnterInfo from '../components/unicorn/enterInfo.vue'
import InvestMoney from '../components/unicorn/investMoney.vue'
import UserAssets from '../components/unicorn/userAssets.vue'

// 产品相关
import Approval from '../components/prod/approval.vue'
import Basic from '../components/prod/basic.vue'
import Recommend from '../components/prod/recommend.vue'
import Series from '../components/prod/series.vue'


Vue.use(VueRouter)

const routes = [{
	path: '/',
	redirect: '/login'
}, {
	path: '/login',
	component: Login
}, {
	path: '/main',
	redirect: '/default',
	component: Main,
	children: [{
		path: '/default',
		component: Default
	}, {
		path: '/admin',
		component: Admin
	}, {
		path: '/menu',
		component: Menu
	}, {
		path: '/approval',
		component: Approval
	}, {
		path: '/basic',
		component: Basic
	}, {
		path: '/recommend',
		component: Recommend
	}, {
		path: '/series',
		component: Series
	}, {
		path: '/enterInfo',
		component: EnterInfo
	}, {
		path: '/investMoney',
		component: InvestMoney
	}, {
		path: '/userAssets',
		component: UserAssets
	}]
},{
	path:'/test',
	component: Test
}]

const router = new VueRouter({
	routes
})

export default router
