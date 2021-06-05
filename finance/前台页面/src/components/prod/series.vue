<template>
	<div>
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>产品管理</el-breadcrumb-item>
			<el-breadcrumb-item>产品系列信息</el-breadcrumb-item>
		</el-breadcrumb>

		<el-form :inline="true" ref="opBox">
			<el-form-item label="系列名称" prop="seriesName">
				<el-select v-model="opBox.seriesName" placeholder="请选择系列名称">
					<el-option v-for="ca in clas" :label="ca.val" :value="ca.val"></el-option>
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" @click="queryMsg">查询</el-button>
				<el-button type="success" @click="addFormOpen">添加</el-button>
				<el-button type="warning" :disabled="btnStatus" @click="updateFormOpen">修改</el-button>
				<el-button type="danger" :disabled="btnStatus" @click="remitFormOpen">汇款信息</el-button>
			</el-form-item>
		</el-form>

		<!-- 页面数据表 -->
		<el-table ref="singleTable" :data="tableSeries" highlight-current-row @current-change="handleRowChange" style="width: 100%">
			<el-table-column property="psId" label="产品系列ID"></el-table-column>
			<el-table-column property="pcname" label="产品中文名"></el-table-column>
			<el-table-column property="pename" label="产品英文名ID"></el-table-column>
			<el-table-column property="remitInfo" label="汇款信息概要"></el-table-column>
		</el-table>

		<!-- 分页信息 -->
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.page"
		 :page-sizes="[5, 10]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total">
		</el-pagination>


		<!-- 弹出框：新增 -->
		<el-dialog title="添加" :visible.sync="addFormVisible">
			<el-form ref="addFrom" :model="addForm">
				<el-form-item label="产品渠道" :label-width="formLabelWidth" prop="pCanal">
					<el-select v-model="addForm.pCanal" placeholder="请选择">
						<el-option v-for="ca in canals" :label="ca.val" :value="ca.val"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="产品分类" :label-width="formLabelWidth" prop="psName">
					<el-select v-model="addForm.psName" placeholder="请选择">
						<el-option v-for="ca in clas" :label="ca.val" :value="ca.val"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="中文名称" :label-width="formLabelWidth" prop="psCname">
					<el-input v-model="addForm.pCname" autocomplete="off" placeholder="请输入内容"></el-input>
				</el-form-item>
				<el-form-item label="英文名称" :label-width="formLabelWidth" prop="psEname">
					<el-input v-model="addForm.pEname" autocomplete="off" placeholder="请输入内容"></el-input>
				</el-form-item>
				<!-- 弹出框的操作 -->
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="addFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="addSubmit()">确 定</el-button>
			</div>
		</el-dialog>

		<!-- 弹出框：修改菜单信息 -->
		<el-dialog title="修改菜单" :visible.sync="editFormVisible">
			<el-form ref="editForm" :model="editForm">
				<el-form-item label="产品渠道" :label-width="formLabelWidth" prop="pCanal">
					<el-select v-model="editForm.pCanal" placeholder="请选择">
						<el-option v-for="ca in canals" :label="ca.val" :value="ca.val"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="产品分类" :label-width="formLabelWidth" prop="psName">
					<el-select v-model="editForm.psName" placeholder="请选择">
						<el-option v-for="ca in clas" :label="ca.val" :value="ca.val"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="中文名称" :label-width="formLabelWidth" prop="pCname">
					<el-input v-model="editForm.pCname" autocomplete="off" placeholder="请输入内容"></el-input>
				</el-form-item>
				<el-form-item label="英文名称" :label-width="formLabelWidth" prop="pEname">
					<el-input v-model="editForm.pEname" autocomplete="off" placeholder="请输入内容"></el-input>
				</el-form-item>
				<!-- 弹出框的操作 -->
			</el-form>

			<!-- 弹出框的操作 -->
			<div slot="footer" class="dialog-footer">
				<el-button @click="editFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="editSubmit()">确 定</el-button>
			</div>
		</el-dialog>

		<!-- 弹出框-汇款信息 -->
		<el-dialog title="汇款信息" :visible.sync="remitFormVisible">
			<el-form ref="remitForm" :model="remitForm" label-width="100px">
				<el-form :inline="true">
					<el-form-item>
						<el-col :span="50">
							<el-form-item label="收款银行名称" prop="dueBankName">
								<el-input v-model="remitForm.dueBankName" placeholder="请输入内容"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="50">
							<el-form-item label="银行SWIFT码" prop="swiftCode">
								<el-input v-model="remitForm.swiftCode" placeholder="请输入内容"></el-input>
							</el-form-item>
						</el-col>
					</el-form-item>
				</el-form>

				<el-form :inline="true">
					<el-form-item>
						<el-col :span="50">
							<el-form-item label="收款银行地区" prop="dueBankArea">
								<el-input v-model="remitForm.dueBankArea" placeholder="请输入内容"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="50">
							<el-form-item label="收款银行城市" prop="dueBankCity">
								<el-input v-model="remitForm.dueBankCity" placeholder="请输入内容"></el-input>
							</el-form-item>
						</el-col>
					</el-form-item>
				</el-form>

				<el-form :inline="true">
					<el-form-item>
						<el-col :span="50">
							<el-form-item label="收 款 人 户名" prop="beneficiaryName">
								<el-input v-model="remitForm.beneficiaryName" placeholder="请输入内容"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="50">
							<el-form-item label="收 款 人 账 号" prop="beneficiaryAccount">
								<el-input v-model="remitForm.beneficiaryAccount" placeholder="请输入内容"></el-input>
							</el-form-item>
						</el-col>
					</el-form-item>
				</el-form>


				<el-form-item label="收款人地址" prop="beneficiaryAddress">
					<el-input v-model="remitForm.beneficiaryAddress" placeholder="请输入内容"></el-input>
				</el-form-item>

				<el-form :inline="true">
					<el-form-item>
						<el-col :span="50">
							<el-form-item label=" 汇  款  账  户" prop="remitUser">
								<el-input v-model="remitForm.remitUser" placeholder="请输入内容"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="50">
							<el-form-item label="汇 款 附 言" prop="remitRemark">
								<el-input v-model="remitForm.remitRemark" placeholder="请输入内容"></el-input>
							</el-form-item>
						</el-col>
					</el-form-item>
				</el-form>

				<el-form :inline="true">
					<el-form-item>
						<el-col :span="50">
							<el-form-item label="BANK CODE" prop="bankCode">
								<el-input v-model="remitForm.bankCode" placeholder="请输入内容"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="50">
							<el-form-item label="CNAPS编号" prop="cnapsCode">
								<el-input v-model="remitForm.cnapsCode" placeholder="请输入内容"></el-input>
							</el-form-item>
						</el-col>
					</el-form-item>
				</el-form>

			</el-form>

			<!-- 弹出框的操作 -->
			<div slot="footer" class="dialog-footer">
				<el-button @click="remitFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="remitSubmit()">确 定</el-button>
			</div>
		</el-dialog>

	</div>
</template>

<script>
	export default {
		data() {
			return {
				psId: '',
				btnStatus: false,
				tableSeries: [],
				opBox: {
					seriesName: ''
				},
				addForm: {
					pcanal: '',
					psName: '',
					pCname: '',
					pEname: ''
				},
				editForm: {
					pCanal: '',
					psName: '',
					pCname: '',
					pEname: ''
				},
				remitForm: {
					dueBankName: "", // 收款银行名称
					swiftCode: "", // SWIFT code银行国际代码
					dueBankArea: "", // 收款银行地区
					dueBankCity: "", // 收款银行城市
					beneficiaryName: "", // 收款人户名
					beneficiaryAccount: "", // 收款人账号
					beneficiaryAddress: "", // 收款人地址
					remitUser: "", // 汇款用户
					remitRemark: "", // 汇款附言
					bankCode: "", // 银行代码
					cnapsCode: "", // 人行系统支付行号
				},
				canals: [{
					val: '香港资管'
				}, {
					val: '内地资管'
				}],
				clas: [{
					val: '基金'
				}, {
					val: '保险'
				}, {
					val: '证券'
				}],
				formLabelWidth: '120px',
				addFormVisible: false,
				editFormVisible: false,
				remitFormVisible: false,
				pageInfo: {
					page: 1,
					pageSize: 5,
					total: 0
				},
			}
		},
		methods: {
			handleSizeChange(val) {
				/* 没有显示记录数改变 */
				this.pageInfo.pageSize = val;
				this.query(this.$qs.stringify(this.pageInfo));
				console.log(`每页 ${val} 条`);
			},
			handleCurrentChange(val) {
				console.log(`当前页: ${val}`);
				// 根据页数查询
				this.pageInfo.page = val;
				this.query(this.$qs.stringify(this.pageInfo));
			},
			handleRowChange(val) { //点击行事件
				this.btnStatus = false;
				console.log(`当前行信息: ${val}`);
				// 注意： handleRowChange的默认机制
				// 每次更新页面数据后仍然会触发该方法，但由于此时未选中任何的行信息，此时会触发一个 当前行信息未定义的错误
				// 可以理解为 handleRowChange 的默认多触发，第一次进入界面时不会触发，但是点击当前行后会触发，因为此时数据刷新了。
				if (val != null) {
					this.psId = val.psId;
					// 会有一个错误：psId未定义，但是数据操作却成功了

					// 需要提供一个接口根据产品系列id查询
					this.$axios.get("prodSer/queryById?psId=" + this.psId)
						.then(reVal => {
							var vals = reVal.data.returnData;
							console.log("vals " + vals);
							this.editForm.pCanal = vals.pcanal;
							this.editForm.psName = vals.psName;
							this.editForm.pCname = vals.pcname;
							this.editForm.pEname = vals.pename;
						})
						.catch(err => {
							console.log("psId 查询失败");
						})
				}
			},
			query(params) {
				// 发送请求
				this.$axios.get("prodSer/query?" + params)
					.then(reVal => {
						this.tableSeries = reVal.data.returnData;
						console.log(reVal.data.returnData);
						this.pageInfo = reVal.data.pageInfo;

						// 更改按钮状态
						this.$nextTick(function() {
							this.btnStatus = true;
						})
					})
					.catch(err => {
						console.log(err);
					})
			},
			queryMsg() {
				console.log("查询");
				this.pageInfo.page = 1;
				var par = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.opBox);
				console.log(par);
				console.log("000" + this.opBox.seriesName);
				this.query(par);
			},
			addFormOpen() {
				this.addFormVisible = true;
				console.log("添加");
			},
			addSubmit() {
				console.log("提交");
				var par = this.$qs.stringify(this.addForm);
				console.log(par);
				// 发送请求
				this.$axios.get("prodSer/add?" + par).then(reVal => {
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					console.log("0000" + err);
				})
				this.$refs['addFrom'].resetFields();
				this.addFormVisible = false;
				this.query("");
			},
			updateFormOpen() {
				this.editFormVisible = true;
				console.log("修改");
			},
			editSubmit() {
				console.log("修改提交信息");
				var par = this.$qs.stringify(this.editForm) + "&psId=" + this.psId;

				console.log(par);
				// 发送修改请求
				this.$axios.get("prodSer/update?" + par).then(reVal => {
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					console.log("0000" + err);
				})
				// 数据更新
				this.editFormVisible = false;
				this.query("");
			},
			remitFormOpen() {
				this.remitFormVisible = true;
			},
			remitSubmit() {
				console.log("提交汇款信息");
				var par = this.$qs.stringify(this.remitForm) + "&psId=" + this.psId;
				// 发送请求
				this.$axios.get("prodSer/addRemit?" + par).then(reVal => {
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					console.log("0000" + err);
				})
				// 数据更新
				this.remitFormVisible = false;
				this.query("");
			}
		},
		mounted() {
			// 调用查询方法
			this.query("");
		},
		watch: {
			// 监控数据 在数据发生改变时，可以进行自己的操作
			tableSeries: function() {
				console.log("tableSeries 数据更新了");
				this.$nextTick(function() {
					// 数据更新，控制按钮状态
					this.btnStatus = true;
				})
			}
		}
	}
</script>

<style>
</style>
