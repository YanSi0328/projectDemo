<template>
	<div>
		欢迎页面 工作台：<br />
		<div class="msg">
			<span>演 示 组：第九组</span><br />
			<span>小组成员：张词、王中文、胡晶晶</span><br />
			<span>演示Demo：尚马金融管理系統</span><br />
		</div>

		<el-container>
			<el-main>
				<div id="mymain">
					<router-view></router-view>
				</div>
				<div id="main" style="width: 600px;height: 500px;"></div>
			</el-main>
		</el-container>
	</div>
</template>

<script>
	import * as echarts from 'echarts';
	export default {
		data() {
			return {
				menus: [],
				updateFromVisible: false,
				formLabelWidth: 120,
				updateform: {
					adminPassword: ''
				},
				tempdata: [54, 28, 44, 10, 10, 20],
				tempdata2: ['私募股权投资', '收益宝', '测试基金产品', '安顺收益【证券】', '中金收益宝【保险】']
			}
		},
		methods: {
			handleCommand(command) {
				if (command == 'logout') {
					this.$axios.get('http://localhost:8080/logout')
						.then(response => {
							console.log(response);
							this.$router.push("/login");
						}).catch(function(error) {
							console.log(error);
						});

				}
			},
			myECharts() {
				// 基于准备好的dom，初始化echarts实例
				var myChart = echarts.init(document.getElementById('main'));

				var option = {
					title: {
						text: '近日销量'
					},
					tooltip: {},
					xAxis: {
						data: this.tempdata2
					},
					yAxis: {},
					series: [{
						name: '销量',
						type: 'bar',
						data: this.tempdata
					}]
				}
				// 绘制图表
				myChart.setOption(option);
			}
		},
		mounted() {
			this.myECharts();
		}
	}
</script>

<style>
	.msg {
		margin-left: 30%;
	}
</style>
