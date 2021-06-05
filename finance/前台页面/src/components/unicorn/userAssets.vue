<template>
	<div>
		<span>独角兽>用户资产</span>

		<el-form :inline="true" :model="queryForm" class="demo-form-inline">
			<el-form-item label="姓名">
				<el-input v-model="queryForm.clientName" placeholder="请输入姓名"></el-input>
			</el-form-item>
			<el-form-item label="电话">
				<el-input v-model="queryForm.clientPhone" placeholder="请输入姓名"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="queryData">查询</el-button>
			</el-form-item>
		</el-form>

		<el-table :data="tableData" style="width: 100%">
			<el-table-column prop="clientId" label="客户编号" width="180">
			</el-table-column>
			<el-table-column prop="clientName" label="客户名称" width="180">
			</el-table-column>
			<el-table-column prop="clientPhone" label="客户手机号" width="180">
			</el-table-column>
			<el-table-column prop="enterpriseName" label="企业名称" width="180">
			</el-table-column>
			<el-table-column prop="stockAmount" label="数量" width="180">
			</el-table-column>
			<el-table-column prop="enterpriseItprice" label="成本价格">
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
				pageInfo: {
					page: 1,
					pageSize: 2,
					total: 8
				},
				queryForm: {
					clientName: '',
					clientPhone: ''
				}
			}

		},
		methods: {
			handleSizeChange(val) {
				console.log({
					val
				});
				this.pageInfo.page = 1;
				this.pageInfo.pageSize = val;
				let myparm = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);

				this.query(myparm);
			},
			handleCurrentChange(val) {
				console.log(val);
				this.pageInfo.page = val;
				let myparm = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);
				console.log(myparm);
				this.query(myparm);
			},
			queryData() {
				this.page = 1;
				console.log(this.$qs.stringify(this.queryForm));
				let myparm = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.queryForm);
				this.query(myparm);
			},
			query(parm) {
				this.$axios.get("client/query?" + parm)
					.then(returnVal => {
						console.log(returnVal);
						this.tableData = returnVal.data.returnData;
						this.pageInfo = returnVal.data.pageInfo;

					})
					.catch(err => {
						console.log(err);
					});
			}

		},
		mounted() {
			this.query("");
		}
	}
</script>

<style>

</style>
