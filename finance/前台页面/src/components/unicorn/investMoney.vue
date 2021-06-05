<template>
	<div>
		<div>独角兽管理>充值提现管理</div>
		<div>交易类型（1：提现&emsp;2：充值）</div>
		<div>交易状态（1：已汇款给用户&emsp;2：已到PTN账户）</div>
		<el-form :inline="true" :model="queryForm" class="demo-form-inline">
			<el-form-item label="姓名">
				<el-input v-model="queryForm.clientName" placeholder="请输入内容"></el-input>
			</el-form-item>
			<el-form-item label="电话">
				<el-input v-model="queryForm.clientPhone" placeholder="请输入内容"></el-input>
			</el-form-item>
			<el-form-item label="交易类型">
				<el-select v-model="queryForm.tradType" placeholder="请输入内容">
					<el-option label=" " :value="0"></el-option>
					<el-option v-for="option in typeoptions1" :label="option" :value="option"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="交易状态">
				<el-select v-model="queryForm.statue" placeholder="请输入内容">
					<el-option label=" " :value="0"></el-option>
					<el-option v-for="option in typeoptions2" :label="option" :value="option"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="queryData">查询</el-button>
			</el-form-item>
		</el-form>

		<el-table :data="tableData" highlight-current-row @current-change="handleRowChange" style="width: 100%">
			<el-table-column prop="clientId" label="客户编号" width="180">
			</el-table-column>
			<el-table-column prop="clientName" label="客户名称" width="180">
			</el-table-column>
			<el-table-column prop="clientPhone" label="客户手机号" width="180">
			</el-table-column>
			<el-table-column prop="tradType" label="交易类型" width="180">
			</el-table-column>
			<el-table-column prop="money" label="金额(千)">
			</el-table-column>
			<el-table-column prop="startTime" label="请求时间">
			</el-table-column>
			<el-table-column prop="completionTime" label="处理完成时间">
			</el-table-column>
			<el-table-column prop="transactionNumber" label="银行交易编号">
			</el-table-column>
			<el-table-column prop="statue" label="状态">
			</el-table-column>
		</el-table>

		<div class="block">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.page"
			 :page-sizes="[3,4,5]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total">
			</el-pagination>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				tableData: [],
				queryForm: {
					clientName: '',
					clientPhone: '',
					tradType: '',
					statue: ''
				},
				pageInfo: {
					page: 6,
					pageSie: 10,
					total: 99
				},
				typeoptions1: [],
				typeoptions2: []

			}
		},
		methods: {
			// 页码发生改变时
			handleCurrentChange(val) {
				this.pageInfo.page = val;
				let param = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);
				this.query(param);
			},
			// 每页记录数发生改变时
			handleSizeChange(val) {
				this.pageInfo.page = 1;
				this.pageInfo.pageSize = val;
				let param = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);
				this.query(param);
			},
			// 被选中的表格
			handleRowChange(val) {},
			// 条件查询
			queryData() {
				this.pageInfo.page = 1;
				let param = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);
				this.query(param);
			},
			// 基础查询
			query(parm) {
				this.$axios.get("invest/query?" + parm)
					.then(returnVal => {
						console.log(returnVal);
						if (returnVal.data != null) {
							this.tableData = returnVal.data.returnData;
							this.pageInfo = returnVal.data.pageInfo;
						}
					})
					.catch(err => {
						this.$notify.error({
							title: '错误',
							message: ' 请求发送失败'
						});

					})
			},
			// 类型列表 查询
			queryType() {
				this.$axios.get("invest/type")
					.then(returnVal => {
						console.log(returnVal);
						this.typeoptions1 = returnVal.data.returnData;
					})
					.catch(err => {
						this.$notify.error({
							title: '错误',
							message: ' 请求发送失败'
						});
					})
			},
			// 状态列表查询
			queryStatue() {
				this.$axios.get("invest/statue")
					.then(returnVal => {
						console.log(returnVal);
						this.typeoptions2 = returnVal.data.returnData;
					})
					.catch(err => {
						this.$notify.error({
							title: '错误',
							message: ' 请求发送失败'
						});
					})
			}
		},
		mounted() {
			this.query("");
			this.queryStatue();
			this.queryType();
		}

	}
</script>

<style>
</style>
