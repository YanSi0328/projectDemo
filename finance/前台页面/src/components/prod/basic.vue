<template>
	<div class="mainDiv">
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>产品管理</el-breadcrumb-item>
			<el-breadcrumb-item>产品基础信息</el-breadcrumb-item>
		</el-breadcrumb>

		<el-form :inline="true" ref="opBox">
			<el-form-item>
				<el-input v-model="opBox.selProdName" placeholder="产品名称"></el-input>
			</el-form-item>
			<el-form-item>
				<el-select v-model="opBox.selProdType" placeholder="产品类型">
					<el-option v-for="item in secCategory" :label="item.label" :value="item.label"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-select v-model="opBox.selAuditStatus" placeholder="审核状态">
					<el-option v-for="item in statusList" :label="item.val" :value="item.key"></el-option>
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" @click="queryMsg">查询</el-button>
				<el-button type="success" @click="addFormOpen">新增</el-button>
				<el-button type="warning" :disabled="btnStatus" @click="updateFormOpen">修改</el-button>
				<el-button type="danger" :disabled="btnStatus" @click="netWorthFormOpen">净值管理</el-button>
			</el-form-item>
		</el-form>

		<!-- 页面数据表 -->
		<el-table ref="singleTable" :data="tableBasic" highlight-current-row @current-change="handleRowChange" style="width: 100%">
			<el-table-column property="prodName" label="产品名称"></el-table-column>
			<el-table-column property="prodCategory" label="产品系列"></el-table-column>
			<el-table-column property="prodSecCategory" label="二级分类"></el-table-column>
			<el-table-column property="adminOrgan" label="机构名称"></el-table-column>
			<el-table-column property="openDate" label="开放时间"></el-table-column>
			<el-table-column property="fundCurrency" label="币种"></el-table-column>
			<el-table-column property="reviewStatus" label="审核状态">
				<template v-slot="val">
					<el-tag v-if="val.row.reviewStatus === 1">审核中</el-tag>
					<el-tag v-else-if="val.row.reviewStatus === 2" type="success">已通过</el-tag>
					<el-tag v-else="val.row.reviewStatus === 3" type="info">未通过</el-tag>
				</template>
			</el-table-column>
		</el-table>

		<!-- 分页信息 -->
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.page"
		 :page-sizes="[5, 10]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total">
		</el-pagination>

		<!-- 弹出框：新增 -->
		<el-dialog title="添加" :visible.sync="addFormVisible">
			<!--   -->
			<el-form :inline="true" :model="addProdForm" :rules="rule" ref="addProdForm">
				<el-divider content-position="left" class="backLine">基本信息</el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="产 品 系 列" prop="prodCategory">
							<el-select v-model="addProdForm.prodCategory" placeholder="请选择系列名称">
								<el-option v-for="ca in clas" :label="ca.val" :value="ca.val"></el-option>
							</el-select>
						</el-form-item>
					</el-col>

					<el-col :span="50">
						<el-form-item label="二级类别" prop="prodSecCategory">
							<el-select v-model="addProdForm.prodSecCategory" placeholder="请选择二级类别">
								<el-option v-for="item in secCategory" :label="item.label" :value="item.label">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="产 品 名 称" prop="prodName">
							<el-input v-model="addProdForm.prodName" placeholder="请输入产品名称"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="管理机构" prop="adminOrgan">
							<el-input v-model="addProdForm.adminOrgan" placeholder="请输入管理机构名称"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="年化收益率" prop="annualizedYield">
							<el-input v-model="addProdForm.annualizedYield" placeholder="请输入年化收益率"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="货币类型" prop="fundCurrency">
							<el-select v-model="addProdForm.fundCurrency" placeholder="请选择">
								<el-option v-for="item in currency" :label="item.label" :value="item.label">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-divider content-position="left" class="backLine">认购属性</el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="开放日期" prop="openDate">
							<el-date-picker type="date" placeholder="选择日期" v-model="addProdForm.openDate" style="width: 100%;"></el-date-picker>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="认购周期" prop="subscriptCycle">
							<el-input v-model="addProdForm.subscriptCycle" placeholder="请输入认购周期"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="基金管理费率" prop="relativeManageCost">
							<el-input v-model="addProdForm.relativeManageCost" placeholder="请输入基金管理费率"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="认购费率" prop="subscriptionRate">
							<el-input v-model="addProdForm.subscriptionRate" placeholder="请输入认购费率"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="起投金额" prop="startAmount">
							<el-input v-model="addProdForm.startAmount" placeholder="请输入起投金额"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="认购费收取方式" prop="chargeMode">
							<el-input v-model="addProdForm.chargeMode" placeholder="请输入认购费收取方式"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-divider content-position="left">赎回属性</el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="赎回周期" prop="redeemCycle">
							<el-input v-model="addProdForm.redeemCycle" placeholder="请输入赎回周期"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="赎回起始金额" prop="initRedeemAmount">
							<el-input v-model="addProdForm.initRedeemAmount" placeholder="请输入赎回起始金额"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="赎回费率" prop="redeemRate">
							<el-input v-model="addProdForm.redeemRate" placeholder="请输入赎回费率"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="锁定期" prop="redeemLockUp">
							<el-input v-model="addProdForm.redeemLockUp" placeholder="请输入锁定期"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-divider class="backLine"></el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="审批人" prop="reviewer">
							<el-select v-model="addProdForm.reviewer" placeholder="请选择二级类别">
								<el-option v-for="item in reviewers" :label="item.val" :value="item.val">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>

					<el-col :span="50" class="btnOp">
						<el-button @click="addFormVisible=false">关闭</el-button>
						<el-button type="primary" @click="addSubmit('addProdForm')">保存</el-button>
					</el-col>
				</el-form-item>
			</el-form>
		</el-dialog>

		<!-- 弹出框：修改-->
		<el-dialog title="修改" :visible.sync="updateFormVisible">
			<!--  :rules="rule" -->
			<el-form :inline="true" :model="updateProdForm" :rules="rule" ref="updateProdForm">
				<el-divider content-position="left" class="backLine">基本信息</el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="产 品 系 列" prop="prodCategory">
							<el-select v-model="updateProdForm.prodCategory" placeholder="请选择系列名称">
								<el-option v-for="ca in clas" :label="ca.val" :value="ca.val"></el-option>
							</el-select>
						</el-form-item>
					</el-col>

					<el-col :span="50">
						<el-form-item label="二级类别" prop="prodSecCategory">
							<el-select v-model="updateProdForm.prodSecCategory" placeholder="请选择二级类别">
								<el-option v-for="item in secCategory" :label="item.label" :value="item.label">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="产 品 名 称" prop="prodName">
							<el-input v-model="updateProdForm.prodName" placeholder="请输入产品名称" disabled></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="管理机构" prop="adminOrgan">
							<el-input v-model="updateProdForm.adminOrgan" placeholder="请输入管理机构名称"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="年化收益率" prop="annualizedYield">
							<el-input v-model="updateProdForm.annualizedYield" placeholder="请输入年化收益率"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="货币类型" prop="fundCurrency">
							<el-select v-model="updateProdForm.fundCurrency" placeholder="请选择">
								<el-option v-for="item in currency" :label="item.label" :value="item.label">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-divider content-position="left" class="backLine">认购属性</el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="开放日期" prop="openDate">
							<el-date-picker type="date" placeholder="选择日期" v-model="updateProdForm.openDate" style="width: 100%;"></el-date-picker>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="认购周期" prop="subscriptCycle">
							<el-input v-model="updateProdForm.subscriptCycle" placeholder="请输入认购周期"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="基金管理费率" prop="relativeManageCost">
							<el-input v-model="updateProdForm.relativeManageCost" placeholder="请输入基金管理费率"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="认购费率" prop="subscriptionRate">
							<el-input v-model="updateProdForm.subscriptionRate" placeholder="请输入认购费率"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-form-item>
					<el-col :span="50">
						<el-form-item label="起投金额" prop="startAmount">
							<el-input v-model="updateProdForm.startAmount" placeholder="请输入起投金额"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="认购费收取方式" prop="chargeMode">
							<el-input v-model="updateProdForm.chargeMode" placeholder="请输入认购费收取方式"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-divider content-position="left">赎回属性</el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="赎回周期" prop="redeemCycle">
							<el-input v-model="updateProdForm.redeemCycle" placeholder="请输入赎回周期"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="赎回起始金额" prop="initRedeemAmount">
							<el-input v-model="updateProdForm.initRedeemAmount" placeholder="请输入赎回起始金额"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="赎回费率" prop="redeemRate">
							<el-input v-model="updateProdForm.redeemRate" placeholder="请输入赎回费率"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="50">
						<el-form-item label="锁定期" prop="redeemLockUp">
							<el-input v-model="updateProdForm.redeemLockUp" placeholder="请输入锁定期"></el-input>
						</el-form-item>
					</el-col>
				</el-form-item>

				<el-divider class="backLine"></el-divider>
				<el-form-item>
					<el-col :span="50">
						<el-form-item label="审批人" prop="reviewer">
							<el-select v-model="updateProdForm.reviewer" placeholder="请选择审批人">
								<el-option v-for="item in reviewers" :label="item.val" :value="item.val">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>

					<el-col :span="50" class="btnOp">
						<el-button @click="cancel('updateProdForm')">关闭</el-button>
						<el-button type="primary" @click="updateSubmit()">保存</el-button>
					</el-col>
				</el-form-item>
			</el-form>
		</el-dialog>

		<!-- 弹出框：净值管理 -->
		<el-dialog title="编辑净值" :visible.sync="netWorthFormVisible">
			<el-form :model="netWorthForm" ref="netWorthForm">
				<el-form-item label="产品名称" prop="prodName">
					<el-input placeholder="不可编辑" v-model="netWorthForm.prodName" disabled></el-input>
				</el-form-item>
				<el-form-item label="单位净值" prop="average">
					<el-input placeholder="请输入内容" v-model="netWorthForm.average"></el-input>
				</el-form-item>
				<el-form-item label="净值基准日" prop="baseDate">
					<el-date-picker type="date" placeholder="选择日期" v-model="netWorthForm.baseDate" style="width: 100%;"></el-date-picker>
				</el-form-item>
				<el-form-item label="累计增长率" prop="growthRate">
					<el-input placeholder="请输入内容" v-model="netWorthForm.growthRate"></el-input>
				</el-form-item>
			</el-form>

			<!-- 弹出框的操作 -->
			<div slot="footer" class="dialog-footer">
				<el-button @click="cancel('netWorthForm')">取 消</el-button>
				<el-button type="primary" @click="netWorthSubmit()">确 定</el-button>
			</div>
		</el-dialog>

	</div>
</template>

<script>
	export default {
		data() {
			return {
				pName: '',
				opBox: {
					selProdName: '',
					selProdType: '',
					selAuditStatus: ''
				},
				tableBasic: [],
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
				addProdForm: {
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
				updateProdForm: {
					// 对于表单中显示的是后台返回的数据，不用提前建立表单，只需将对应的字段与服务端返回的字段对应即可
					// prodCategory: '',
					// prodSecCategory: "",
					// prodName: "",
					// adminOrgan: "",
					// annualizedYield: "",
					// fundCurrency: "",
					// opDate: '',
					// subscriptCycle: "",
					// relativeManageCost: "",
					// subscriptionRate: "",
					// startAmount: "",
					// chargeMode: "",
					// redeemCycle: "",
					// initRedeemAmount: "",
					// redeemRate: "",
					// redeemLockUp: "",
					// reviewer: ""
				},
				netWorthForm: {
					prodName: '',
					average: '',
					baseDate: '',
					growthRate: ''
				},
				addFormVisible: false,
				updateFormVisible: false,
				netWorthFormVisible: false,
				btnStatus: true,
				pageInfo: {
					page: 1,
					pageSize: 5,
					total: 0
				},
				reviewers: [{
					val: "zc"
				}, {
					val: "wzw"
				}, {
					val: "hjj"
				}],
				rule: {
					prodCategory: [{
						required: true,
						message: '请输入产品系列',
						trigger: 'blur'
					}],
					prodSecCategory: [{
						required: true,
						message: '请输入二级类别',
						trigger: 'blur'
					}],
					prodName: [{
						required: true,
						message: '请输入产品名称',
						trigger: 'blur'
					}],
					adminOrgan: [{
						required: true,
						message: '请输入管理机构',
						trigger: 'blur'
					}],
					annualizedYield: [{
						required: true,
						message: '请输入年化收益率',
						trigger: 'blur'
					}, {
						type: 'float',
						message: '请精确到两位小数',
						trigger: 'blur'
					}],
					fundCurrency: [{
						required: true,
						message: '请输入币种',
						trigger: 'blur'
					}],
					openDate: [{
						required: true,
						message: '请选择日期',
						trigger: 'blur'
					}],
					subscriptCycle: [{
						required: true,
						message: '请输入认购周期',
						trigger: 'blur'
					}],
					relativeManageCost: [{
						required: true,
						message: '请输入基金管理费率',
						trigger: 'blur'
					}, {
						type: 'float',
						message: '请精确到两位小数',
						trigger: 'blur'
					}],
					subscriptionRate: [{
						required: true,
						message: '请输入认购费率',
						trigger: 'blur'
					}, {
						type: 'float',
						message: '请精确到两位小数',
						trigger: 'blur'
					}],
					startAmount: [{
						required: true,
						message: '请输入起投金额',
						trigger: 'blur'
					}, {
						type: 'float',
						message: '请精确到两位小数',
						trigger: 'blur'
					}],
					chargeMode: [{
						required: true,
						message: '请输入认购费收取方式',
						trigger: 'blur'
					}],
					redeemCycle: [{
						required: true,
						message: '请输入赎回周期',
						trigger: 'blur'
					}, {
						type: 'integer',
						message: '请输入数字类型',
						trigger: 'change'
					}],
					initRedeemAmount: [{
						required: true,
						message: '请输入赎回起始金额',
						trigger: 'blur'
					}],
					redeemRate: [{
						required: true,
						message: '请输入赎回费率',
						trigger: 'blur'
					}, {
						type: 'float',
						message: '请精确到两位小数',
						trigger: 'blur'
					}],
					redeemLockUp: [{
						required: true,
						message: '请输入锁定期',
						trigger: 'blur'
					}, {
						type: 'integer',
						message: '请输入数字类型',
						trigger: 'change'
					}],
					reviewer: [{
						required: true,
						message: '请输入审批人',
						trigger: 'blur'
					}]
				},
				secCategory: [{
					label: '阳光私募'
				}, {
					label: '对冲基金'
				}, {
					label: '香港私募股权'
				}, {
					label: '共同基金'
				}, {
					label: '其他基金'
				}],
				clas: [{
					val: '基金'
				}, {
					val: '保险'
				}, {
					val: '证券'
				}],
				currency: [{
					label: 'CNY'
				}, {
					label: 'USD'
				}]
			}
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
				this.btnStatus = false;
				console.log(`当前行信息: ${val}`);
				console.log(val);
				if (val != null) {
					this.pName = val.prodName;
					this.netWorthForm.prodName = val.prodName;
					this.$axios.get("prodBasic/queryByProdName?prodName=" + this.pName)
						.then(reVal => {
							console.log(reVal.data.returnData);
							this.updateProdForm = reVal.data.returnData;
						})
						.catch(err => {
							console.log("pName 查询失败");
						})
				}
			},
			query(params) {
				// 发送请求
				this.$axios.get("prodBasic/query?" + params)
					.then(reVal => {
						this.tableBasic = reVal.data.returnData;
						console.log(reVal.data);
						console.log(reVal.data.pageInfo);
						if (reVal.data.pageInfo != null) {
							this.pageInfo = reVal.data.pageInfo;
						}
						// 更改按钮状态
						this.$nextTick(function() {
							this.btnStatus = true;
						})
					})
					.catch(err => {
						console.log(err);
					})
					this.$refs['opBox'].resetFields();
			},
			queryMsg() {
				console.log("查询");

				this.pageInfo.page = 1;
				console.log(this.pageInfo.page);
				var par = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.opBox);
				console.log(par);
				this.query(par);
			},
			addFormOpen() {
				this.addFormVisible = true;
				console.log("添加窗口");
			},
			addSubmit() {
				console.log("提交");
				this.addFormVisible = false;
				// 封装数据
				var par = this.$qs.stringify(this.addProdForm);
				console.log(par);
				this.$axios.get("prodBasic/add?" + par).then(reVal => {
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					console.log("0000" + err);
				})
				this.$refs['addProdForm'].resetFields();
				this.query("");
			},
			updateFormOpen() {
				this.updateFormVisible = true;
				console.log("修改");
			},
			updateSubmit() {
				console.log("修改提交信息");
				var par = this.$qs.stringify(this.updateProdForm);
				console.log(par);
				// 发送修改请求
				this.$axios.get("prodBasic/modify?" + par).then(reVal => {
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					this.$message.error("修改提交信息 失败");
				})
				this.$refs['updateProdForm'].resetFields();
				// 数据更新
				this.updateFormVisible = false;
				this.query("");
			},
			netWorthFormOpen() {
				this.netWorthFormVisible = true;
			},
			cancel(param){
				this.netWorthFormVisible = false;
				this.updateFormVisible = false;
				this.$refs[param].resetFields();
			},
			netWorthSubmit() {
				console.log("提交净值");
				var par = this.$qs.stringify(this.netWorthForm);
				console.log(par);
				this.$axios.get("prodEquity/add?" + par).then(reVal => {
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					this.$message.error("提交净值 失败 单位净值为整数");
				})
				this.$refs['netWorthForm'].resetFields();
				this.netWorthFormVisible = false;
				this.query("");
			},
			// 全表单验证
			regForm(formPar) {
				console.log(this.$qs.stringify(this.formPar));
				// 表单校验：当所有的校验规则通过后才进行提交数据
				this.$refs[formPar].validate((valid) => {
					if (valid) {
						console.log("校验通过");
						// 封装表单数据
						let param = this.$qs.stringify(this.formPar);
						console.log(param);
						return param;
					} else {
						alert('提交失败');
						console.log('error submit!!');
						return false;
					}
				});
			}
		},
		mounted() {
			this.query("");
		},
		watch: {
			// 监控数据 在数据发生改变时，可以进行自己的操作
			tableBasic: function() {
				console.log("tableBasic 数据更新了");
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
