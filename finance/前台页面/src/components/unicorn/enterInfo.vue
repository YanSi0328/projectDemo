<template>
	<div>
		<span>独角兽>企业信息管理</span>

		<el-form :inline="true" :model="queryForm" class="demo-form-inline">
			<el-form-item label="企业名称">
				<el-input v-model="queryForm.clientName" placeholder="请输入内容"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="queryData">查询</el-button>
				<el-button type="success" @click="addOpenForm()">添加</el-button>
				<el-button type="primary" :disabled="btnStatus" @click="editOpenForm()">修改</el-button>
				<el-button type="primary" :disabled="btnStatus" @click="updateOpenForm()">编辑挂单</el-button>
				<el-button type="primary" :disabled="btnStatus" @click="oldOpenForm()">历史融资</el-button>
			</el-form-item>
		</el-form>
		<!-- 展示 -->
		<el-table :data="tableData" highlight-current-row @current-change="handleRowChange" style="width: 100%">
			<el-table-column prop="enterpriseName" label="企业名称" width="180">
			</el-table-column>
			<el-table-column prop="enterpriseCode" label="交易代码" width="180">
			</el-table-column>
			<el-table-column prop="enterpriseSprice" label="最近成交价格(每股)" width="180">
			</el-table-column>
			<el-table-column prop="enterpriseRate" label="费率" width="180">
			</el-table-column>
			<el-table-column prop="enterpriseId" label="顺序">
			</el-table-column>
		</el-table>
		<!-- 添加 -->
		<el-dialog title="添加" :visible.sync="adddialogVisible">
			<el-form :inline="true" :model="addform" :rules="rules" ref="addform" class="demo-form-inline">
				<el-form-item label="企业名称" prop="enterprise_name">
					<el-input v-model="addform.enterprise_name" placeholder="企业名称"></el-input>
				</el-form-item>
				<el-form-item label="交易代码" prop="enterprise_code">
					<el-input v-model="addform.enterprise_code" placeholder="交易代码"></el-input>
				</el-form-item>
				<el-form-item label="企业logo" prop="enterprise_logo">
					<el-upload class="avatar-uploader" action="http://localhost:8080/finance/" :http-request="myupload"
					 :show-file-list="false" :before-upload="beforeAvatarUpload" name="myfile">
						<img v-if="addform.enterprise_logo" :src="addform.enterprise_logo" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>
				<el-form-item label="APP端logo" prop="enterprise_alogo">
					<el-upload class="avatar-uploader" action="http://localhost:8080/finance/" :http-request="myupload2"
					 :show-file-list="false" :before-upload="beforeAvatarUpload" name="myfile">
						<img v-if="addform.enterprise_alogo" :src="addform.enterprise_alogo" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>
				<el-form-item label="所属行业" prop="enterprise_type">
					<el-select v-model="addform.enterprise_type" placeholder="活动区域">
						<el-option v-for="option in typoptions" :label="option" :value="option"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="成立年份" prop="enterprise_founding">
					<el-date-picker v-model="addform.enterprise_founding" type="date" @change="dateFormat()" placeholder="选择日期">
					</el-date-picker>
				</el-form-item>
				<el-form-item label="ceo" prop="enterprise_ceo">
					<el-input v-model="addform.enterprise_ceo" placeholder="ceo"></el-input>
				</el-form-item>
				<el-form-item label="企业所在地" prop="enterprise_address">
					<el-input v-model="addform.enterprise_address" placeholder="企业所在地"></el-input>
				</el-form-item>
				<el-form-item label="费率(%)" prop="enterprise_rate">
					<el-input v-model="addform.enterprise_rate" placeholder="费率(%)"></el-input>
				</el-form-item>
				<el-form-item label="企业顺序" prop="enterprise_id">
					<el-input v-model="addform.enterprise_id" placeholder="企业顺序"></el-input>
				</el-form-item>
				<el-form-item label="企业介绍" prop="enterprise_desc">
					<el-input type="textarea" v-model="addform.enterprise_desc"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="adddialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="addSubmit()">确 定</el-button>
			</span>
		</el-dialog>
		<!-- 修改 -->
		<el-dialog title="添加" :visible.sync="editdialogVisible">
			<el-form :inline="true" :model="editform" :rules="rules" ref="editform" class="demo-form-inline">
				<el-form-item label="企业名称" prop="enterpriseName">
					<el-input v-model="editform.enterpriseName" placeholder="企业名称"></el-input>
				</el-form-item>
				<el-form-item label="交易代码" prop="enterpriseCode">
					<el-input v-model="editform.enterpriseCode" placeholder="交易代码"></el-input>
				</el-form-item>
				<el-form-item label="企业logo" prop="enterpriseLogo">
					<el-upload class="avatar-uploader" action="http://localhost:8080/finance/upload/image" :http-request="myupload3"
					 :show-file-list="false" :before-upload="beforeAvatarUpload" name="myfile">
						<img v-if="editform.enterpriseLogo" :src="editform.enterpriseLogo" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>
				<el-form-item label="APP端logo" prop="enterpriseAlogo">
					<el-upload class="avatar-uploader" action="http://localhost:8080/finance/" :http-request="myupload4"
					 :show-file-list="false" :before-upload="beforeAvatarUpload" name="myfile">
						<img v-if="editform.enterpriseAlogo" :src="editform.enterpriseAlogo" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>
				<el-form-item label="所属行业" prop="enterpriseType">
					<el-select v-model="editform.enterpriseType" placeholder="活动区域">
						<el-option v-for="option in typoptions" :label="option" :value="option"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="成立年份" prop="enterpriseFounding">
					<el-date-picker v-model="editform.enterpriseFounding" type="date" @change="dateFormat2()" placeholder="选择日期">
					</el-date-picker>
				</el-form-item>
				<el-form-item label="ceo" prop="enterpriseCeo">
					<el-input v-model="editform.enterpriseCeo" placeholder="ceo"></el-input>
				</el-form-item>
				<el-form-item label="企业所在地" prop="enterpriseAddress">
					<el-input v-model="editform.enterpriseAddress" placeholder="企业所在地"></el-input>
				</el-form-item>
				<el-form-item label="费率(%)" prop="enterpriseRate">
					<el-input v-model="editform.enterpriseRate" placeholder="费率(%)"></el-input>
				</el-form-item>
				<el-form-item label="企业顺序" prop="enterpriseId">
					<el-input v-model="editform.enterpriseId" placeholder="企业顺序"></el-input>
				</el-form-item>
				<el-form-item label="企业介绍" prop="enterpriseDesc">
					<el-input type="textarea" v-model="editform.enterpriseDesc"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="editdialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="editSubmit()">确 定</el-button>
			</span>
		</el-dialog>
		<!-- 编辑挂单 -->
		<el-dialog title="编辑挂单" :visible.sync="updatedialogVisible">
			<el-form :inline="true" :model="updateform" :rules="rules" ref="updateform" class="demo-form-inline">
				<el-form-item label="买一" prop="fbuy_price">
					<el-input v-model="updateform.fbuy_price" placeholder="企业名称"></el-input>
				</el-form-item>
				<el-form-item label="买二" prop="sbuy_price">
					<el-input v-model="updateform.sbuy_price" placeholder="交易代码"></el-input>
				</el-form-item>
				<el-form-item label="买三" prop="tbuy_price">
					<el-input v-model="updateform.tbuy_price" placeholder="ceo"></el-input>
				</el-form-item>
				<el-form-item label="卖一" prop="fsell_price">
					<el-input v-model="updateform.fsell_price" placeholder="企业所在地"></el-input>
				</el-form-item>
				<el-form-item label="卖二" prop="ssell_price">
					<el-input v-model="updateform.ssell_price" placeholder="费率(%)"></el-input>
				</el-form-item>
				<el-form-item label="卖三" prop="tsell_price">
					<el-input v-model="updateform.tsell_price" placeholder="企业顺序"></el-input>
				</el-form-item>
				<el-form-item label="企业顺序" prop="tsell_price">
					<el-input v-model="updateform.id" placeholder="企业顺序"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="updatedialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="updateSubmit()">确 定</el-button>
			</span>
		</el-dialog>
		<!-- 历史融资 -->
		<el-dialog title="历史融资" :visible.sync="olddialogVisible">
			<el-table :data="oldtableData" highlight-current-row @current-change="handleRow2Change" style="width: 100%">
				<el-table-column prop="enterpriseName" label="企业名称" width="180">
				</el-table-column>
				<el-table-column prop="investDate" label="投资日期" width="180">
				</el-table-column>
				<el-table-column prop="investRound" label="投资轮" width="180">
				</el-table-column>
				<el-table-column prop="investAmount" label="投资金额(百万)" width="180">
				</el-table-column>
				<el-table-column prop="investValuation" label="投后估值(亿)">
				</el-table-column>
				<el-table-column prop="stockIissue" label="总发发行股数(百万)">
				</el-table-column>
				<el-table-column prop="stockPrice" label="每股价格">
				</el-table-column>
			</el-table>
		</el-dialog>
		<!-- 分页 -->
		<div class="block">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.page"
			 :page-sizes="[3,4,5]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total">
			</el-pagination>
		</div>
	</div>
</template>

<script>
	import moment from 'moment'
	export default {
		data() {
			return {
				tableData: [],
				oldtableData: [],
				pageInfo: {
					page: 3,
					pageSize: 2,
					total: 8
				},
				queryForm: {
					clientName: '',
					clientPhone: ''
				},
				adddialogVisible: false,
				editdialogVisible: false,
				updatedialogVisible: false,
				olddialogVisible: false,
				formInline: {
					user: '',
					region: ''
				},

				addform: {
					enterprise_id: '',
					enterprise_name: '',
					enterprise_address: '',
					enterprise_alogo: '',
					enterprise_ceo: '',
					enterprise_desc: '',
					enterprise_type: '',
					enterprise_founding: '',
					enterprise_code: '',
					enterprise_rate: '',
					enterprise_logo: '',
				},
				editform: {
					enterpriseId: '',
					enterpriseName: '',
					enterpriseAddress: '',
					enterpriseAlogo: '',
					enterpriseCeo: '',
					enterpriseDesc: '',
					enterpriseType: '',
					enterpriseFounding: '',
					enterpriseCode: '',
					enterpriseRate: '',
					enterpriseLogo: '',
				},
				updateform: {
					fbuy_price: '',
					sbuy_price: '',
					tbuy_price: '',
					fsell_price: '',
					ssell_price: '',
					tsell_price: '',
					id: ''
				},
				typoptions: [],
				btnStatus: true,

				rules: {
					enterprise_name: [{
						required: true,
						message: '请输入企业名称',
						trigger: 'blur'
					}],
					enterprise_type: [{
						required: true,
						message: '请输入企业名称',
						trigger: 'blur'
					}],
					enterprise_code: [{
						required: true,
						message: '请输入交易代码',
						trigger: 'change'
					}],
					enterprise_logo: [{
						required: true,
						message: '请上传企业logo',
						trigger: 'change'
					}],
					enterprise_alogo: [{
						required: true,
						message: '请上传app端logo',
						trigger: 'change'
					}],
					enterprise_ceo: [{
						required: true,
						message: '请输入ceo',
						trigger: 'change'
					}],
					enterprise_founding: [{

						required: true,
						message: '请选择成立年份',
						trigger: 'change'
					}],

					enterprise_address: [{
						required: true,
						message: '请输入企业所在的',
						trigger: 'change'
					}],
					enterprise_rate: [{
							// type: 'number',
							required: true,
							message: '请输入费率，必须为数字值'
						}

					],
					enterprise_id: [{
						required: true,
						message: '请输入费率，，必须为数字值'
					}],
					enterprise_desc: [{
						required: true,
						message: '请填写企业介绍',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			// 每页记录数改变时
			handleSizeChange(val) {
				// console.log(val);
				this.pageInfo.page = 1;
				this.pageInfo.pageSize = val;
				// console.log(this.pageInfo);
				let myparm = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);

				this.query(myparm);
			},
			// 页码改变时
			handleCurrentChange(val) {
				// console.log(val);
				this.pageInfo.page = val;
				let myparm = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);
				// console.log(myparm);
				this.query(myparm);
			},
			// 点击历史融资按钮时
			oldOpenForm() {
				this.olddialogVisible = true;
			},
			// 编辑挂单按钮点击时
			updateOpenForm() {
				this.updatedialogVisible = true;
			},
			updateSubmit() {
				this.updatedialogVisible = false;
				let parm = this.$qs.stringify(this.updateform);
				console.log(parm);
				this.$axios.post("trading/edit", parm)
					.then(returnVal => {
						console.log(returnVal);
						if (returnVal.data.code == 300) {
							this.$message.success("提交成功");
						} else {
							this.$message.error("提交失败");
						}
					}).catch(err => {
						console.log(err);
					});
			},
			//被选中的表格,执行查询语句
			handleRowChange(val) {
				this.btnStatus = false;

				// this.$refs['updateform'].resetFields();	
				this.currentRow = val;
				if (val != null) {
					// 查询数据，响应的数据赋值给editform
					console.log("不是null");
					this.$axios.get("enterprise/queryOne?enterprise_name=" + this.currentRow.enterpriseName + "&enterprise_code=" +
							this.currentRow.enterpriseCode)
						.then(returnVal => {
							console.log("不是null");
							console.log(returnVal);
							// 赋值给修改表单
							this.editform = returnVal.data.returnData;
							console.log(this.editform);
						})
						.catch(err => {
							console.log(err);
						});
					// 被选中的表格中的顺序id，赋值给updateform.id
					this.updateform.id = this.currentRow.enterpriseId;
					console.log(this.updateform.id);
					// 备选中表格企业的名字赋值给parm
					let parm2 = this.currentRow.enterpriseName;
					console.log("名字"+parm2);
					this.$axios.post("financing/query?name=" + parm2)
						.then(Val => {
							console.log("这是oldtableData");
							console.log(Val.data.returnData);
							this.oldtableData=Val.data.returnData;
							console.log("这是oldtableData");
							// console.log(this.oldtableData);
						})
						.catch(err => {
							console.log(err);
						});
				}
			},
			// oldtable 被点击时
			handleRow2Change(val) {

			},
			// 点击修改按钮，弹出修改表单并赋值列表选项，且更新数据
			editOpenForm() {

				this.editdialogVisible = true;
				//问题：为何会是原先的数据？   
				// this.$nextTick(function() {
				// 	this.$refs['editform'].resetFields();
				// });
				// 给列表赋值
				this.$axios.get("enterprise/type")
					.then(retrunVal => {

						console.log(retrunVal.data.returnData);
						this.typoptions = retrunVal.data.returnData;
					})
					.catch(error => {
						concole.log(err);
					})
			},
			// 修改提交时
			editSubmit() {

				let parm = this.$qs.stringify(this.editform);

				this.$refs['editform'].validate((valid) => {
					if (valid) {
						this.editdialogVisible = false;
						this.myedit(parm);
					} else {
						console.log('error submit!!');
						return false;
					}
				});
				console.log(this.$qs.stringify(this.editform));

			},
			myedit(param) {
				this.$axios.post("enterprise/edit", param)
					.then(returnVal => {
						console.log(returnVal);
						if (returnVal.data.code == 300) {
							this.$message.success(returnVal.data.returnMsg);
						} else {
							this.$message.error(returnVal.data.returnMsg);
						}
						//更新查询
						let myparm = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);
						this.query(myparm);
					})
					.catch(err => {
						console.log(err + "11111111");
					});
			},
			// 点击添加，弹出添加表单并清空数据
			addOpenForm() {
				this.adddialogVisible = true;
				this.$nextTick(function() {
					this.$refs['addform'].resetFields();
				})
				// 并且给列表赋值
				this.$axios.get("enterprise/type")
					.then(retrunVal => {

						console.log(retrunVal.data.returnData);
						this.typoptions = retrunVal.data.returnData;
					})
					.catch(error => {
						concole.log(err);
					})
			},
			// 添加 提交事件
			addSubmit() {

				let parm = this.$qs.stringify(this.addform);

				this.$refs['addform'].validate((valid) => {
					if (valid) {
						this.adddialogVisible = false;
						this.myadd(parm);
					} else {
						this.$message.error("提交失败");
						return false;
					}
				});
				console.log(this.$qs.stringify(this.addform));

			},
			myadd(param) {
				this.$axios.post("enterprise/add", param)
					.then(returnVal => {
						console.log(returnVal);
						if (returnVal.data.code == 300) {
							this.$message.success(returnVal.data.returnMsg);
						} else {
							this.$message.error(returnVal.data.returnMsg);
						}
						//更新查询
						let myparm = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);
						this.query(myparm);
					})
					.catch(err => {
						this.$message.error(returnVal.data.returnMsg);
					});
			},
			// 上传logo
			myupload(param) {
				let formData = new FormData();
				formData.append("myfile", param.file);
				console.log(formData);
				this.$axios.post("upload2/image", formData, {
						headers: {
							"Content-Type": "multipart/form-data"
						}
					})
					.then(resp => {
						console.log(resp);
						this.addform.enterprise_logo = resp.data.imgsrc;


					})
			},
			// 上传alogo
			myupload2(param) {
				let formData = new FormData();
				formData.append("myfile", param.file);
				this.$axios.post("upload2/image", formData, {
						headers: {
							"Content-Type": "multipart/form-data"
						}
					})
					.then(resp => {
						console.log(resp);
						this.addform.enterprise_alogo = resp.data.imgsrc;
					})
			},
			myupload3(param) {
				let formData = new FormData();
				formData.append("myfile", param.file);
				this.$axios.post("upload2/image", formData, {
						headers: {
							"Content-Type": "multipart/form-data"
						}
					})
					.then(resp => {
						console.log(resp);
						this.addform.enterprise_alogo = resp.data.imgsrc;
					})
			},
			myupload4(param) {
				let formData = new FormData();
				formData.append("myfile", param.file);
				this.$axios.post("upload2/image", formData, {
						headers: {
							"Content-Type": "multipart/form-data"
						}
					})
					.then(resp => {
						console.log(resp);
						this.addform.enterprise_alogo = resp.data.imgsrc;
					})
			},
			//时间格式化
			dateFormat() {
				console.log(this.addform.enterprise_founding);
				let date = this.addform.enterprise_founding;

				console.log(moment(date).format("YYYY-MM-DD"));
				let date2 = moment(date).format("YYYY-MM-DD");
				this.addform.enterprise_founding = date2;

			},
			dateFormat2() {
				console.log(this.editform.enterprise_founding);
				let date = this.editform.enterprise_founding;

				console.log(moment(date).format("YYYY-MM-DD"));
				let date2 = moment(date).format("YYYY-MM-DD");
				this.editform.enterprise_founding = date2;

			},
			//上传限制
			beforeAvatarUpload(file) {
				// const isJPEG = file.type === 'image/jpeg';
				// const isPNG = file.type === 'image/png';
				// const isJPG = file.type === 'image/jpg';
				// const isLt2M = file.size / 1024 / 1024 < 2;
				// console.log(isLt2M);
				// if (!isJPG && !isPNG) {
				// 	this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
				// }
				// if (!isLt2M) {
				// 	this.$message.error('上传头像图片大小不能超过 2MB!');
				// }
				return true;
			},

			// 条件查询
			queryData() {
				this.page = 1;
				// console.log(this.$qs.stringify(this.queryForm));
				let myparm = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);
				this.query(myparm);
			},
			// 页面展示查询
			query(parm) {
				this.$axios.get("enterprise/query?" + parm)
					.then(returnVal => {
						// console.log(returnVal);
						this.tableData = returnVal.data.returnData;
						console.log(returnVal.data.returnData);
						this.pageInfo = returnVal.data.pageInfo;

					})
					.catch(err => {
						console.log(err);
					});
			}

		},
		// 钩子函数
		mounted() {
			this.query("");
		}
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>
