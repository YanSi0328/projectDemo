<!-- 系统菜单管理 -->
<template>
	<div>
		<el-form :inline="true" ref="opBox" :model="opBox">
			<el-form-item label="菜单名称" prop="menuName">
				<el-input v-model="opBox.menuName" placeholder="菜单名称"></el-input>
			</el-form-item>
			<el-form-item label="上级菜单" prop="pId">
				<el-select v-model="opBox.pId" placeholder="上级菜单">
					<el-option label="无" :value="0"></el-option>
					<el-option v-for="pMenu in pMenus" :label="pMenu.name" :value="pMenu.id"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="queryMsg">查询</el-button>
				<el-button type="infor" @click="resetMsg">重置</el-button>
				<el-button type="success" @click="addFormOpen">添加</el-button>
				<el-button type="warning" :disabled="btnStatus" @click="updateFormOpen">修改</el-button>
				<el-button type="danger" :disabled="btnStatus" @click="delMsgById">删除</el-button>
			</el-form-item>
		</el-form>

		<!-- 页面数据表 -->
		<el-table ref="singleTable" :data="tableMenu" highlight-current-row @current-change="handleRowChange" style="width: 100%">
			<el-table-column property="id" label="菜单编号ID"></el-table-column>
			<el-table-column property="name" label="菜单名称"></el-table-column>
			<el-table-column property="pid" label="父菜单ID"></el-table-column>
			<el-table-column property="pname" label="父菜单名称"></el-table-column>
			<el-table-column property="address" label="访问地址"></el-table-column>
			<el-table-column property="icon" label="图标">
				<template slot-scope="picture">
			        <i :class="picture.row.icon"></i>
			   </template></el-table-column>
		</el-table>

		<!-- 分页信息 -->
		<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.page"
		 :page-sizes="[10, pageInfo.total]" :page-size="pageInfo.pageSize" layout="total, sizes, prev, pager, next, jumper"
		 :total="pageInfo.total">
		</el-pagination>

		<!-- 弹出框：新增菜单信息 -->
		<el-dialog title="添加菜单" :visible.sync="addFormVisible">
			<el-form ref="addFrom" :model="addForm">
				<el-form-item label="菜单编号" :label-width="formLabelWidth" prop="id">
					<el-input v-model="addForm.id" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="菜单名称" :label-width="formLabelWidth" prop="name">
					<el-input v-model="addForm.name" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="菜单访问地址" :label-width="formLabelWidth" prop="address">
					<el-input v-model="addForm.address" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="上级菜单" :label-width="formLabelWidth" prop="pid">
					<el-select v-model="addForm.pid" placeholder="请选择上级菜单">
						<el-option label="无" :value="0"></el-option>
						<el-option v-for="pMenu in pMenus" :label="pMenu.name" :value="pMenu.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="菜单图标" :label-width="formLabelWidth" prop="icon">
					<el-input v-model="addForm.icon" autocomplete="off"></el-input>
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
			<el-form ref="editFrom" :model="editFrom">
				<el-form-item label="菜单编号" :label-width="formLabelWidth" prop="id">
					<el-input v-model="editFrom.id" :disabled="true" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="菜单名称" :label-width="formLabelWidth" prop="name">
					<el-input v-model="editFrom.name" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="菜单访问地址" :label-width="formLabelWidth" prop="address">
					<el-input v-model="editFrom.address" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="上级菜单" :label-width="formLabelWidth" prop="pid">
					<el-select v-model="editFrom.pid" placeholder="请选择上级菜单">
						<el-option label="无" :value="0"></el-option>
						<el-option v-for="pMenu in pMenus" :label="pMenu.name" :value="pMenu.id"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="菜单图标" :label-width="formLabelWidth" prop="icon">
					<el-input v-model="editFrom.icon" autocomplete="off"></el-input>
				</el-form-item>

				<!-- 弹出框的操作 -->
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="editFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="editSubmit()">确 定</el-button>
			</div>
		</el-dialog>

	</div>
</template>

<script>
	export default {
		data() {
			return {
				tableMenu: [],
				pMenus: [], // 查询时 下拉列表的选项 对应的一级菜单数据
				btnStatus: true,
				currentRowMsg: null,
				// 条件查询
				opBox: {
					menuName: '',
					pId: ''
				},
				addForm: {
					id: '',
					name: '',
					address: '',
					pid: '',
					icon: ''
				},
				editFrom: {
					id: '',
					name: '',
					address: '',
					pid: '',
					icon: ''
				},
				formLabelWidth: '120px',
				addFormVisible: false,
				editFormVisible: false,
				pageInfo: {
					page: 1,
					pageSize: 10,
					total: 0
				}
			}
		},
		methods: {
			handleSizeChange(val) {
				/* 没有显示记录数改变 */
				console.log(`每页 ${val} 条`);
				// 更改页面数据显示的条数
				this.pageInfo.pageSize = val;
				// 重新发送请求
				this.queryAll(this.$qs.stringify(this.pageInfo));
			},
			handleCurrentChange(val) {
				console.log(`当前页: ${val}`);
				// 请求第二页数据
				this.pageInfo.page = val;
				this.queryAll(this.$qs.stringify(this.pageInfo));
			},
			handleRowChange(val) { //点击行事件
				this.btnStatus = false;
				// console.log(val);
				// 拿到当前行信息
				this.currentRowMsg = val;
				// 注意：此时拿到得只是数据的引用并不是真实的数据，此时对数据进行修改会影响到原始数据
				if (this.currentRowMsg != null) {
					// 采用深克隆的方式将当前行数据装入修改框中对应位置
					this.editFrom = JSON.parse(JSON.stringify(this.currentRowMsg));
				}
				console.log(this.editFrom);
			},
			queryAll(params) {
				// 在vue中不要通过function定义函数，直接在methods中定义
				// 调用  this.函数名
				this.$axios.get("menu/query?" + params)
					.then(reVal => {
						this.tableMenu = reVal.data.returnData;
						this.pageInfo = reVal.data.pageInfo;

						// 更改按钮状态
						this.$nextTick(function() {
							this.btnStatus = true;
						})
					})
					.catch(err => {
						this.$message.error("操作有误");
					})
			},
			queryMsg() {
				console.log("查询");
				// 处理页码信息
				this.pageInfo.page = 1;
				// 拼接查询参数
				var par = this.$qs.stringify(this.pageInfo) + "&" + this.$qs.stringify(this.opBox);
				this.queryAll(par);
				console.log(par);
			},
			resetMsg() {
				console.log("重置");
				this.$refs['opBox'].resetFields();
			},
			addFormOpen() {
				console.log("新增");
				// 打开新增菜单窗口
				this.addFormVisible = true;
			},
			addSubmit() {
				// 提交新增菜单信息到后台
				// 1 封装参数
				var addMenu = this.$qs.stringify(this.addForm);
				console.log("新增 " + addMenu);
				// 2 向后台发送数据
				this.$axios.get("menu/add?" + addMenu).then(reVal => {
					console.log(reVal);
					// 3 处理返回信息
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg + "新增菜单重启系统后有效");
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					this.$message.error("输入信息有误");
				})

				// 4 关闭新增菜单对话框
				this.addFormVisible = false;
				// 5 更新数据
				this.getParMenu();
				// this.$qs.stringify(this.pageinfo)
				this.queryAll(this.$qs.stringify(this.pageInfo));
			},
			updateFormOpen() {
				console.log("修改");
				// 打开修改菜单窗口
				this.editFormVisible = true;
			},
			editSubmit() {
				// 1 封装参数
				var par = this.$qs.stringify(this.editFrom);
				// 2 发送请求
				this.$axios.post("menu/modify", par).then(reVal => {
					// 3 根据返回的信息进行响应
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					this.$message.error("输入信息有误");
				})
				// 4 刷新数据
				this.queryAll(this.$qs.stringify(this.pageInfo));
				this.getParMenu();
				// 5 关闭修改框
				this.editFormVisible = false;
			},
			delMsgById() {

				console.log("删除");
				// 1 获得待删除的菜单id
				var delId = this.currentRowMsg.id;
				// 2 发送请求
				this.$axios.post("menu/delById", "id=" + delId).then(reVal => {
					// 3 根据返回的信息进行响应
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					this.$message.error("操作有误");
				})
				// 4 刷新数据
				this.queryAll(this.$qs.stringify(this.pageInfo));
				this.getParMenu();
			},
			getParMenu() {
				console.log("获取父菜单");
				this.$axios.get("menu/queryPMenu").then(reVal => {
					// console.log(reVal);
					this.pMenus = reVal.data.returnData;
				}).catch(err => {
					this.$message.error("操作有误");
				})
			}
		},
		mounted() {
			// 调用查询方法
			this.queryAll("");
			this.getParMenu();
		},
		watch: {
			// 监控数据 在数据发生改变时，可以进行自己的操作
			tableMenu: function() {
				console.log("tableMenu 数据更新了");
				this.$nextTick(function() {
					// 数据更新后，控制按钮状态
					this.btnStatus = true;
				})
			}
		}

	}
</script>

<style>
	.el-main {
		line-height: 30px;
	}

	.el-select {
		width: 100%;
	}
</style>
