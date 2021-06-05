<template>
	<el-card class="box-card mydiv">
		<div slot="header" class="clearfix">
			<span>登录</span>
		</div>
		<div class="text item">
			<el-form ref="myform" :model="myform" :rules="myroles" label-width="80px" hide-required-asterisk>
				<el-form-item label="用户名" prop="adminName">
					<el-input v-model="myform.adminName" suffix-icon="el-icon-user"></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="adminPassword">
					<el-input v-model="myform.adminPassword" suffix-icon="el-icon-lock" type="password" show-password>
					</el-input>
				</el-form-item>

				<el-form-item>
					<el-button type="primary" @click="submitForm('myform')">登录</el-button>
					<el-button @click="resetForm('myform')">重置</el-button>

				</el-form-item>
			</el-form>
		</div>
	</el-card>


</template>

<script>
	export default {
		data() {
			return {
				myform: {
					adminName: '',
					adminPassword: ''
				},
				myroles: {
					adminName: [{
							required: true,
							message: '',
							trigger: 'blur'
						},
						{
							min: 2,
							max: 5,
							message: '长度在 2到 5个字符',
							trigger: 'blur'
						}
					],
					adminPassword: [{
							required: true,
							message: '请输入密码',
							trigger: 'blur'
						},
						{
							min: 3,
							max: 8,
							message: '长度在 3 到 8 个字符',
							trigger: 'blur'
						}
					],
				}
			}
		},
		methods: {
			submitForm(formName) {
				this.$refs[formName].validate((val) => {
					if (val) {

						this.$axios.post('login', this.$qs.stringify(this.myform))
							.then(returnval=> {
								console.log(this.$qs.stringify(this.myform));
								console.log(returnval);
								if (returnval.data.code == 202) {
									this.$router.push('/main');
								} else {
									this.$message.error(returnval.data.returnMsg);
								}
							}).catch(error => {
								console.log(error);
								this.$message.error("请联系管理员!");
							});

					} else {
						console.log('信息错误!');
						return false;
					}
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			}
		}
	}
</script>

<style scoped="scoped">
	.el-input {
		width: 90%;
	}

	.text {
		font-size: 14px;
	}

	.item {
		margin-bottom: 18px;
	}

	.clearfix:before,
	.clearfix:after {
		display: table;
		content: "";
	}

	.clearfix:after {
		clear: both
	}

	.box-card {
		width: 480px;
	}

	.mydiv {
		margin: 200px auto;
	}

	.wrongmsg {
		color: #F56C6C;
		font-size: 12px;
		padding-left: 20px;
	}
</style>
