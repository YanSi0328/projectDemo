<template>
	<div>
		<el-container>
			<el-header height="100px">
				<span class="headerColor">
					尚 马 金 融 管 理 系 統
				</span>
				<el-dropdown @command="handleCommand">
					<el-button type="primary">
						用户操作<i class="el-icon-arrow-down el-icon--right"></i>
					</el-button>
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item>
							<el-button @click="updateFromVisible = true" width="40%">修改密码</el-button>
							<el-dropdown-item>
								<el-button @click="exit" width="40%">退出</el-button>
							</el-dropdown-item>
						</el-dropdown-item>
						<!-- <el-dropdown-item command="logout">退出</el-dropdown-item> -->
						<div slot="footer" class="dialog-footer">
							<el-button @click="cancelform()">取 消</el-button>
							<el-button type="primary"  @click="submitForm()">确 定</el-button>
						</div>
					</el-dropdown-menu>
				</el-dropdown>
			</el-header>

			<el-container>
				<el-aside width="200px">
					<el-row>
						<el-col :span="24">
							<el-menu class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" background-color="#545c64"
							 text-color="#fff" active-text-color="#ffd04b" unique-opened :router="true">
								<el-submenu :index="menu.id.toString()" :key="menu.id" v-for="menu in menus">
									<template slot="title">
										<i :class="menu.icon"></i>
										<span>{{menu.name}}</span>
									</template>
									<el-menu-item-group v-for="(sub,index) in menu.submenu" :key="sub.id">
										<el-menu-item :index="sub.address">{{sub.name}}</el-menu-item>
									</el-menu-item-group>
								</el-submenu>
							</el-menu>
						</el-col>
					</el-row>
				</el-aside>

				<el-main>
					<div id="mymain">
						<router-view></router-view>
					</div>
					<div id="main" style="width: 600px;height: 500px;"></div>
				</el-main>
			</el-container>
		</el-container>

		<el-dialog title="修改密码" :visible.sync="updateFromVisible">
			<el-form :model="updateform">
				<el-form-item label="新密码" :label-width="formLabelWidth">
					<el-input v-model="updateform.adminPassword" autocomplete="off"></el-input>
				</el-form-item>

			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="updateFromVisible = false">取 消</el-button>
				<el-button type="primary" @click="updateFromSubmit">确 定</el-button>
			</div>
		</el-dialog>


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
				tempdata2: ['仙侠', '言情', '言情2', '言情3', '言情4']
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
						text: '近30日活动报名人数'
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
			},
			handleOpen(key, keyPath) {
				console.log(key, keyPath);
			},
			handleClose(key, keyPath) {
				console.log(key, keyPath);
			},
			openUpdateForm() {
				this.updateFormVisable = true;
				if (this.currentRow) {
					this.updateform = JSON.parse(JSON.stringify(this.currentRow));
				}

			},
			updateFromSubmit() {
				console.log(this.updateform)
				this.$axios.post("updatePwd", this.$qs.stringify(this.updateform))
					.then(resp => {
						console.log(resp + "111111");
						this.$message.success(resp.data.returnMsg);
						this.updateFormVisable = false;
						// this.myQuery();
						this.$router.push('/login');
					}).catch(err => {
						console.log(err + "失败");
					})
			},
			exit() {
				this.$axios.post("logout")
					.then(resp => {
						console.log("成功");
						this.$router.push('/login');
						// this.myQuery();
					}).catch(err => {
						console.log("失败");
					})
			},
			myECharts() {
				// 基于准备好的dom，初始化echarts实例
				// var myChart = echarts.init(document.getElementById('main'));
				var myChart = echarts.init(document.getElementById("main"))

				var option = {
					title: {
						text: '近30日活动报名人数'
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
			},
			queryMenu() {
				this.$axios.get('/menu/roleMenu')
					.then(response => {
						console.log(response);

						this.menus = response.data.extData;
					}).catch(function(error) {
						console.log(error);
					});
			}
		},

		created() {

		},
		mounted() {
			// this.myECharts();
			this.queryMenu();
		}
	}
</script>

<style>
	html,
	body {
		height: 100%;
		margin: 0px;
	}

	.el-header,
	.el-footer {
		background-color: #B3C0D1;
		color: #333;
		text-align: center;
		line-height: 100px;
	}

	.el-dropdown {
		float: right;
	}

	.el-aside {
		background-color: #D3DCE6;
		color: #333;
		text-align: left;
		line-height: 200px;
	}

	.el-main {
		background-color: #E9EEF3;
		color: #333;
	}

	.el-container:nth-child(5) .el-aside,
	.el-container:nth-child(6) .el-aside {
		line-height: 260px;
	}

	.el-container:nth-child(7) .el-aside {
		line-height: 320px;
	}

	#base {
		height: 100%;
	}

	.el-container {
		height: 100%;
	}

	a {
		text-decoration: none;
		color: white;
	}

	.el-submenu {
		text-align: left;
	}

	ul.el-menu-vertical-demo.el-menu {
		border-right: none;
	}
	.headerColor{
		color: #003366;
		font-size: 50px;
	}
</style>
