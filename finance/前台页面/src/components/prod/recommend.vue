<template>
	<div>
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
			<el-breadcrumb-item>产品管理</el-breadcrumb-item>
			<el-breadcrumb-item>产品推荐管理</el-breadcrumb-item>
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
				<el-button type="warning" :disabled="btnStatus" @click="editFormOpen">修改</el-button>
				<el-button type="danger" :disabled="btnStatus" @click="relatedSubscript">关联申购</el-button>
			</el-form-item>
		</el-form>

		<!-- 页面数据表 -->
		<el-table ref="singleTable" :data="tableRecom" highlight-current-row @current-change="handleRowChange" style="width: 100%">
			<el-table-column property="prodName" label="产品名称"></el-table-column>
			<el-table-column property="degree" label="推荐度">
				<template v-slot="val">
					<el-tag v-if="val.row.degree === 1">热门推荐</el-tag>
					<el-tag v-else-if="val.row.degree === 2" type="success">普通推荐</el-tag>
					<el-tag v-else="val.row.degree === 3" type="info">下架</el-tag>
				</template>
			</el-table-column>
			<el-table-column property="firstRound" label="是否首发">
				<template v-slot="val">
					<el-tag v-if="val.row.firstRound === 1">是</el-tag>
					<el-tag v-else="val.row.firstRound === 2" type="success">否</el-tag>
				</template>
			</el-table-column>
			<el-table-column property="onlinePurchase" label="线上申购">
				<template v-slot="val">
					<el-tag v-if="val.row.onlinePurchase === 1">是</el-tag>
					<el-tag v-else="val.row.onlinePurchase === 2" type="success">否</el-tag>
				</template>
			</el-table-column>
			<el-table-column property="rank" label="排序"></el-table-column>
		</el-table>

		<!-- 分页信息 -->
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.page"
		 :page-sizes="[5,10]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total">
		</el-pagination>


		<!-- 弹出框：新增 -->
		<el-dialog title="添加" :visible.sync="addFormVisible">
			<el-form ref="addForm" :model="addForm">
				<el-form-item label="推荐产品" :label-width="formLabelWidth" prop="prodName">
					<el-select v-model="addForm.prodName" placeholder="请选择">
						<el-option v-for="item in pNameList" :label="item" :value="item"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="推荐度" :label-width="formLabelWidth" prop="degree">
					<el-select v-model="addForm.degree" placeholder="请选择">
						<el-option v-for="item in recommends" :label="item.val" :value="item.key"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="是否投顾端可见" :label-width="formLabelWidth" prop="investVisible">
					<el-radio v-model="addForm.investVisible" label="1">是</el-radio>
					<el-radio v-model="addForm.investVisible" label="2">否</el-radio>
				</el-form-item>
				<el-form-item label="是否首发" :label-width="formLabelWidth" prop="firstRound">
					<el-radio v-model="addForm.firstRound" label="1">是</el-radio>
					<el-radio v-model="addForm.firstRound" label="2">否</el-radio>
				</el-form-item>
				<el-form-item label="线上申购" :label-width="formLabelWidth" prop="onlinePurchase">
					<el-radio v-model="addForm.onlinePurchase" label="1">是</el-radio>
					<el-radio v-model="addForm.onlinePurchase" label="2">否</el-radio>
				</el-form-item>
				<el-form-item label="推荐理由" :label-width="formLabelWidth" prop="reason">
					<el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="addForm.reason">
					</el-input>
				</el-form-item>
			</el-form>

			<!-- 弹出框的操作 -->
			<div slot="footer" class="dialog-footer">
				<el-button @click="addFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="addSubmit()">确 定</el-button>
			</div>
		</el-dialog>

		<!-- 弹出框：修改 -->
		<el-dialog title="修改" :visible.sync="editFormVisible">
			<el-form ref="editForm" :model="editForm">
				<el-form-item label="推荐产品" :label-width="formLabelWidth" prop="prodName">
					<el-select v-model="editForm.prodName" placeholder="请选择" disabled>
						<el-option v-for="pl in pNameList" :label="pl.val" :value="pl.val"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="推荐度" :label-width="formLabelWidth" prop="degree">
					<el-select v-model="editForm.degree" placeholder="请选择">
						<el-option v-for="item in recommends" :label="item.val" :value="item.key"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="是否投顾端可见" :label-width="formLabelWidth" prop="investVisible">
					<el-radio v-model="editForm.investVisible" label="1">是</el-radio>
					<el-radio v-model="editForm.investVisible" label="2">否</el-radio>
				</el-form-item>
				<el-form-item label="是否首发" :label-width="formLabelWidth" prop="firstRound">
					<el-radio v-model="editForm.firstRound" label="1">是</el-radio>
					<el-radio v-model="editForm.firstRound" label="2">否</el-radio>
				</el-form-item>
				<el-form-item label="线上申购" :label-width="formLabelWidth" prop="onlinePurchase">
					<el-radio v-model="editForm.onlinePurchase" label="1">是</el-radio>
					<el-radio v-model="editForm.onlinePurchase" label="2">否</el-radio>
				</el-form-item>
				<el-form-item label="推荐理由" :label-width="formLabelWidth" prop="reason">
					<el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="editForm.reason">
					</el-input>
				</el-form-item>
			</el-form>

			<!-- 弹出框的操作 -->
			<div slot="footer" class="dialog-footer">
				<el-button @click="editFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="editSubmit()">确 定</el-button>
			</div>
		</el-dialog>


		<!-- 关联申购 -->
		<el-dialog title="关联申购" :visible.sync="relatedSubscriptVisible">
			<el-transfer style="text-align: left; display: inline-block;" :titles="['未关联', '已关联']" :button-texts="['<<', '>>']"
			 v-model="chooseProd" :data="allProd" :props="{key: 'id',label: 'pName'}">


			</el-transfer>

			<!-- 弹出框的操作 -->
			<div slot="footer" class="dialog-footer">
				<el-button @click="relatedSubscriptVisible = false">取 消</el-button>
				<el-button type="primary" @click="relatedSubmit()">确 定</el-button>
			</div>
		</el-dialog>

	</div>
</template>

<script>
	export default {
		data() {
			return {
				allProd: [],
				chooseProd: [],
				pId: '',
				opBox: {
					seriesName: ''
				},
				addForm: {
					prodName: '',
					degree: '',
					investVisible: "1",
					firstRound: '1',
					onlinePurchase: "1",
					reason: ''
				},
				editForm: {
					prodName: '',
					degree: '',
					investVisible: "",
					firstRound: '',
					onlinePurchase: "",
					reason: ''
				},
				clas: [{
					val: '基金'
				}, {
					val: '保险'
				}, {
					val: '证券'
				}],
				recommends: [{
					key: '1',
					val: '热门推荐'
				}, {
					key: '2',
					val: '普通推荐'
				}, {
					key: '3',
					val: '下架'
				}],
				btnStatus: false,
				tableRecom: [],
				pNameList: [],
				radio: '1',
				num: 1,
				formLabelWidth: '120px',
				addFormVisible: false,
				editFormVisible: false,
				relatedSubscriptVisible: false,
				pageInfo: {
					page: 1,
					pageSize: 5,
					total: 0
				},
			};
		},
		methods: {
			handleChange(value) {
				console.log("101010");
				console.log(value);
			},
			handleSizeChange(val) {
				/* 没有显示记录数改变 */
				console.log(`每页 ${val} 条`);
				console.log(val);
				this.pageInfo.pageSize = val;
				var par = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.opBox);
				this.query(par);
			},
			handleCurrentChange(val) {
				console.log(`当前页: ${val}`);
				this.pageInfo.page = val;
				var par = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.opBox);
				this.query(par);
			},
			handleRowChange(val) { //点击行事件
				console.log(`当前行信息: ${val}`);
				console.log(val);
				if (val != null) {
					this.editForm.prodName = val.prodName;
					this.pId = val.id;
					this.queryProdId(val.prodCategory);
				}
				this.btnStatus = false;
			},
			queryProdId(param) {
				this.$axios.get("prodRe/related?pCategory=" + param)
					.then(reVal => {
						console.log("reVal.data.returnData");
						this.allProd = reVal.data.returnData;
					})
					.catch(err => {
						this.$notify.error({
							title: '错误',
							message: 'queryProdId 请求发送失败'
						});
					})
			},
			queryProdName() {
				// 发送请求
				this.$axios.get("prodRe/queryProdName")
					.then(reVal => {
						this.pNameList = reVal.data.returnData;
					})
					.catch(err => {
						this.$notify.error({
							title: '错误',
							message: '查询接口 请求发送失败'
						});
					})
			},
			query(params) {
				// 发送请求
				this.$axios.get("prodRe/query?" + params)
					.then(reVal => {
						this.tableRecom = reVal.data.returnData;
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
				console.log("查询");
				this.pageInfo.page = 1;
				var par = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.opBox);
				console.log(par);
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
				this.$axios.get("prodRe/add?" + par).then(reVal => {
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					this.$notify.error({
						title: '错误',
						message: '新增接口 请求发送失败'
					});
				})
				this.$refs['addForm'].resetFields();
				this.query("");
				this.addFormVisible = false;
			},
			editFormOpen() {
				this.editFormVisible = true;
				console.log("修改");
			},
			editSubmit() {
				console.log("修改提交");
				// 封装数据
				var par = this.$qs.stringify(this.editForm);
				console.log(par);
				this.$axios.get("prodRe/modify?" + par).then(reVal => {
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					this.$message.error("修改提交信息 失败");
				})
				// 数据更新
				this.editFormVisible = false;
				this.query("");
			},
			relatedSubscript() {
				this.relatedSubscriptVisible = true;
			},
			relatedSubmit() {
				console.log("提交关联申购");
				var par = this.chooseProd.toString();
				// var par = this.$qs.stringify();
				console.log(par);
				this.$axios.get("prodRe/modifyRelated?pidStr=" + par + "&pId=" + this.pId).then(reVal => {
					if (reVal.data.code == 300) {
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					this.$message.error("提交关联申购 失败");
				})
				this.relatedSubscriptVisible = false;
			}
		},
		mounted() {
			this.query("");
			this.queryProdName();
		},
		watch: {
			// 监控数据 在数据发生改变时，可以进行自己的操作
			tableRecom: function() {
				console.log("tableRecom 数据更新了");
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
