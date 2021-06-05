<template>
	<div>
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>产品管理</el-breadcrumb-item>
			<el-breadcrumb-item>产品审核</el-breadcrumb-item>
		</el-breadcrumb>


		<el-form :inline="true" ref="opBox">
			<el-form-item>
				<el-input v-model="opBox.prodName" placeholder="产品名称"></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="opBox.auditStatus" placeholder="审核状态">
					<el-option v-for="item in statusList" :label="item.val" :value="item.key"></el-option>
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" @click="queryMsg">查询</el-button>
				<el-tooltip effect="dark" content="123L" placement="top">
					<el-button type="success" :disabled="btnStatus" @click="operateFormOpen">操作</el-button>
				</el-tooltip>
			</el-form-item>
		</el-form>

		<!-- 页面数据表 -->
		<el-table ref="singleTable" :data="tableAuditor" highlight-current-row @current-change="handleRowChange" style="width: 100%">
			<el-table-column property="prodName" label="产品名称"></el-table-column>
			<el-table-column property="prodSecCategory" label="业务类型"></el-table-column>
			<el-table-column property="creator" label="创建人"></el-table-column>
			<el-table-column property="createTime" label="首次创建时间"></el-table-column>
			<el-table-column property="updateTime" label="最新修改时间"></el-table-column>
		</el-table>

		<!-- 分页信息 -->
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.page"
		 :page-sizes="[5, 10]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total">
		</el-pagination>

		<!-- 弹出框：操作 ：仅可以进行操作不可以编辑信息-->
		<el-dialog title="审核" :visible.sync="opFormVisible">
			<el-form :inline="true" :model="prodForm" ref="prodForm" disabled>
				<el-divider content-position="left" class="backLine">基本信息</el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="产 品 系 列" prop="prodCategory">
							<el-input v-model="prodForm.prodCategory" placeholder="请输入产品系列"></el-input>
						</el-form-item>
					</el-col>

					<el-col :span="50">
						<el-form-item label="二级类别" prop="prodSecCategory">
							<el-input v-model="prodForm.prodSecCategory" placeholder="二级类别"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="产 品 名 称" prop="prodName">
							<el-input v-model="prodForm.prodName" placeholder="请输入产品名称"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="管理机构" prop="adminOrgan">
							<el-input v-model="prodForm.adminOrgan" placeholder="请输入管理机构名称"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="年化收益率" prop="annualizedYield">
							<el-input v-model="prodForm.annualizedYield" placeholder="请输入年化收益率"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="货币类型" prop="fundCurrency">
							<el-input v-model="prodForm.fundCurrency" placeholder="货币类型"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-divider content-position="left" class="backLine">认购属性</el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="开放日期" prop="opDate">
							<el-date-picker type="date" placeholder="选择日期" v-model="prodForm.opDate" style="width: 100%;"></el-date-picker>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="认购周期" prop="subscriptCycle">
							<el-input v-model="prodForm.subscriptCycle" placeholder="请输入认购周期"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="基金管理费率" prop="relativeManageCost">
							<el-input v-model="prodForm.relativeManageCost" placeholder="请输入基金管理费率"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="认购费率" prop="subscriptionRate">
							<el-input v-model="prodForm.subscriptionRate" placeholder="请输入认购费率"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="起投金额" prop="startAmount">
							<el-input v-model="prodForm.startAmount" placeholder="请输入起投金额"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="认购费收取方式" prop="chargeMode">
							<el-input v-model="prodForm.chargeMode" placeholder="请输入认购费收取方式"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-divider content-position="left">赎回属性</el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="赎回周期" prop="redeemCycle">
							<el-input v-model="prodForm.redeemCycle" placeholder="请输入赎回周期"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="赎回起始金额" prop="initRedeemAmount">
							<el-input v-model="prodForm.initRedeemAmount" placeholder="请输入赎回起始金额"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="赎回费率" prop="redeemRate">
							<el-input v-model="prodForm.redeemRate" placeholder="请输入赎回费率"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="锁定期" prop="redeemLockUp">
							<el-input v-model="prodForm.redeemLockUp" placeholder="请输入锁定期"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-divider class="backLine"></el-divider>
				<el-form-item>
					<el-col :span="50">
						<!-- 谁登录此系统审批人就是谁 -->
						<el-form-item label="审批人" prop="reviewer">
							<el-input v-model="prodForm.reviewer" placeholder="请输入审批人"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
			</el-form>

			<!-- 弹出框的操作 -->
			<div slot="footer" class="dialog-footer">
				<el-button @click="opFormVisible = false">关闭</el-button>
				<el-button type="primary" @click="opCancel('3')">驳回</el-button>
				<el-button type="primary" @click="opSubmit('2')">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				pName: '',
				tableAuditor: [],
				opFormVisible: false,
				btnStatus: true,
				opBox: {
					prodName: '',
					auditStatus: ''
				},
				prodForm: {
					prodCategory: '',
					prodSecCategory: "",
					prodName: "",
					adminOrgan: "",
					annualizedYield: "",
					fundCurrency: "",
					openDate: '',
					subscriptCycle: "",
					relativeManageCost: "",
					subscriptionRate: "",
					startAmount: "",
					chargeMode: "",
					redeemCycle: "",
					initRedeemAmount: "",
					redeemRate: "",
					redeemLockUp: "",
					reviewer: ""
				},
				statusList: [{
					key: "1",
					val: "审核中"
				}, {
					key: "2",
					val: "已通过"
				}, {
					key: "3",
					val: "未通过"
				}],
				pageInfo: {
					page: 1,
					pageSize: 5,
					total: 0
				},
			};
		},
		methods: {
			handleSizeChange(val) {
				/* 没有显示记录数改变 */
				console.log(`每页 ${val} 条`);
				this.pageInfo.pageSize = val;
				this.query(this.$qs.stringify(this.pageInfo));
			},
			handleCurrentChange(val) {
				console.log(`当前页: ${val}`);
				this.pageInfo.page = val;
				this.query(this.$qs.stringify(this.pageInfo) + this.$qs.stringify(this.opBox));
			},
			handleRowChange(val) { //点击行事件
				// this.btnStatus = false;
				console.log("当前行信息:");
				console.log(val);
				if (val != null) {
					this.pName = val.prodName;
					// 1. 发送请求，确定是否可以审核产品信息
					this.$axios.get("prodAuditor/getReviewProd?prodName=" + this.pName)
						.then(reVal => {
							console.log(reVal.data);
							if (reVal.data.code == 400) {
								// 不可以审核
								this.$notify.error({
									title: '错误',
									message: reVal.data.returnData
								});
							} else {
								// 可以进行审核，修改按钮状态，并将详细的产品信息赋值到修改框
								this.btnStatus = false;
								this.prodForm = reVal.data.returnData;
								console.log("可以审核");
							}
						})
						.catch(err => {
							this.$notify.error({
								title: '错误',
								message: '审核产品接口 请求发送失败'
							});
						})
				}
			},
			query(params) {
				// 发送请求
				this.$axios.get("prodAuditor/query?" + params)
					.then(reVal => {
						this.tableAuditor = reVal.data.returnData;
						if (reVal.data.pageInfo != null) {
							this.pageInfo = reVal.data.pageInfo;
						}
					})
					.catch(err => {
						this.$notify.error({
							title: '错误',
							message: '查询接口 请求发送失败'
						});
					})
			},
			queryMsg() {
				console.log("查询信息");
				this.pageInfo.page = 1;
				var par = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.opBox);
				this.query(par);
			},
			operateFormOpen() {
				// 打开操作框
				this.opFormVisible = true;
			},
			opCancel(num) {
				console.log("审核不通过 " + num);
				this.opFormVisible = false;
				// this.auditStatus = 3;
				// console.log(this.pName);
				var par = "prodName=" + this.pName + "&auditStatus=" + num;
				// console.log(par);
				this.changeStatus(par);
				this.query("");
			},
			opSubmit(num) {
				console.log("审核通过 " + num);
				this.opFormVisible = false;
				// this.auditStatus = 2;
				// 这个地方为什么不能使用 qs
				var par = "prodName=" + this.pName + "&auditStatus=" + num;
				console.log(par);
				this.changeStatus(par);
				this.query("");
			},
			changeStatus(params) {
				this.$axios.get("prodAuditor/modifyStatus?" + params)
					.then(reVal => {
						console.log("000000");
						console.log(reVal.data.returnData);
						if (reVal.data.code == 300) {
							// success
							this.$message.success(reVal.data.returnMsg);
						} else if (reVal.data.code == 400) {
							this.$message.error(reVal.data.returnMsg);
						}
					})
					.catch(err => {
						this.$notify.error({
							title: '错误',
							message: '修改审核状态 请求发送失败'
						});
					})
			}
		},
		mounted() {
			this.query("");
		},
		watch: {
			// 监控数据 在数据发生改变时，可以进行自己的操作
			tableAuditor: function() {
				console.log("tableAuditor 数据更新了");
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
