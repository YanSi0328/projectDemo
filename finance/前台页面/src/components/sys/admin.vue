<!-- 系统人员管理 -->
<template>
	<div>
		<el-breadcrumb separator-class="el-icon-arrow-right">
			<el-breadcrumb-item :to="{}">管理中心</el-breadcrumb-item>
			<el-breadcrumb-item>人员管理</el-breadcrumb-item>
		</el-breadcrumb>
		<br />

		<el-form ref="queryForm" :inline="true" :model="queryForm" class="demo-form-inline">
			<el-form-item label="用户id" prop="adminId">
				<el-input v-model="queryForm.adminId" placeholder="用户id"></el-input>
			</el-form-item>
			<el-form-item label="用户名" prop="adminName">
				<el-input v-model="queryForm.adminName" placeholder="用户名"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="myQuery">查询</el-button>
				<el-button @click="resetForm('queryForm')">重置</el-button>
				<el-button type="success" @click="openAddForm">新增</el-button>
				<el-button type="warning" :disabled="disBtn" @click="openEditForm">修改</el-button>
				<el-button type="danger" :disabled="disBtn" @click="openDelForm">删除</el-button>
				<el-button type="info" :disabled="disBtn" @click="openAuthForm">授权</el-button>
			</el-form-item>
		</el-form>
		<el-table ref="singleTable" :data="tableData" highlight-current-row @current-change="handleRowchange" style="width: 100%">
			<el-table-column property="adminId" label="用户id" width="100">
			</el-table-column>
			<el-table-column property="adminName" label="用户姓名" width="150">
			</el-table-column>
			<el-table-column property="adminStatus" label="用户状态" width="150"></el-table-column>
			<el-table-column property="roleId" label="角色" width="150">
				<template slot-scope="scope">
					<el-tag :type="scope.row.roleId == '1' ? 'primary' : 'danger'" disable-transitions>
						{{scope.row.roleId==1?'超级管理员':'二级管理员'}}
					</el-tag>
				</template>

			</el-table-column>

			</el-table-column>
			<el-table-column property="adminCreateTime" label="注册时间" :formatter="dateFormat" width="180">
			</el-table-column>
			<el-table-column property="adminUpdateTime" label="最后登录时间" :formatter="dateFormat" width="180">
			</el-table-column>
			<el-table-column property="adminImg" label="头像" width="300">
				<template slot-scope="scope">
					<img :src="scope.row.adminImg" />
				</template>
			</el-table-column>

		</el-table>

		<div class="block">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageinfo.page"
			:page-sizes="[3, 5, 10, 20]" :page-size="pageinfo.pageSize" layout="total, sizes, prev, pager, next, jumper" 
			:total="pageinfo.total">
			</el-pagination>
		</div>
		<!--添加-->
		<el-dialog title="添加" :visible.sync="addFormVisable" width="40%">
			<el-form ref="addForm" :model="addform" label-width="80px" hide-required-asterisk>
				<el-form-item label="用户id" prop="adminId">
					<el-input v-model="addform.adminId"></el-input>
				</el-form-item>
				<el-form-item label="用户名" prop="adminName">
					<el-input v-model="addform.adminName"></el-input>
				</el-form-item>

				<el-form-item label="状态" prop="adminStatus">
					<el-input v-model="editform.adminStatus" type="textarea"></el-input>
				</el-form-item>
				<el-form-item label="角色" prop="roleId">
					<el-select v-model="addform.roleId" placeholder="请选择">
						<el-option label="超级管理员" :value="1">
						</el-option>
						<el-option label="二级管理员" :value="2">
						</el-option>
					</el-select>

				</el-form-item>
				<el-form-item label="头像" prop="adminImg">
					<el-upload class="avatar-uploader" action="" :http-request="myupload" :show-file-list="false" :before-upload="beforeAvatarUpload"
					 name="myfile">
						<img v-if="addform.adminImg" :src="addform.adminImg" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>

				</el-form-item>
				<!-- 弹出框的操作 -->
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click="addFromSubmit()">提交</el-button>
			</div>
		</el-dialog>

		</el-form>
		</el-dialog>
		<!--修改-->
		<el-dialog title="修改" :visible.sync="editFormVisable" width="40%">
			<el-form ref="editForm" :model="editform" label-width="80px" hide-required-asterisk>
				<el-form-item label="用户id" prop="adminId">
					<el-input v-model="editform.adminId" disabled="true" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="用户名" prop="adminName">
					<el-input v-model="editform.adminName"></el-input>
				</el-form-item>

				<el-form-item label="状态" prop="adminStatus">
					<el-input v-model="editform.adminStatus"></el-input>
				</el-form-item>

				<el-form-item label="角色" prop="roleId">
					<el-select v-model="editform.roleId" placeholder="请选择">
						<el-option label="超级管理员" :value="1">
						</el-option>
						<el-option label="二级管理员" :value="2">
						</el-option>
					</el-select>

				</el-form-item>
				<el-form-item label="头像" prop="adminImg">
					<el-upload class="avatar-uploader" action="" :http-request="myupload2" :show-file-list="false" :before-upload="beforeAvatarUpload"
					 name="myfile">
						<img v-if="editform.adminImg" :src="editform.adminImg" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>

				</el-form-item>
				<!-- 弹出框的操作 -->
				<el-form-item>
					<el-button type="primary" @click="editFromSubmit">提交</el-button>
					<el-button @click="resetForm('editForm')">取消</el-button>

				</el-form-item>
			</el-form>
		</el-dialog>

		<el-dialog title="授权" :visible.sync="authFormVisable" width="40%">
			<el-form ref="authForm" :model="authform" label-width="80px" hide-required-asterisk>
				<el-form-item label="用户id" prop="adminId">
					<el-input v-model="authform.adminId" readonly></el-input>
				</el-form-item>

				<el-tree :data="authdata" show-checkbox ref="authtree" node-key="id" default-expand-all :default-checked-keys="currntAuth"
				 :props="defaultProps">
				</el-tree>

				<el-form-item>
					<el-button type="primary" @click="authFromSubmit">提交</el-button>
					<el-button @click="authFormVisable=false">取消</el-button>

				</el-form-item>
			</el-form>
		</el-dialog>
	</div>
</template>

<script>
	/* {"pageinfo":{"page":1,"pagesize":3},"returnCode":10000,"returnData":[{"createUid":9,"createUname":"测试1","isValid":0,"loginTime":1469444784000,"passWord":"e10adc3949ba59abbe56e057f20f883e","regTime":1469173099000,"remark":"","uid":3,"userName":"wangshaocheng"}],"returnMsg":"操作成功"} 
		{"createUid":9,"createUname":"测试1","headImg":"logo/user1.png","isValid":0,"loginTime":1469444784000,"passWord":"e10adc3949ba59abbe56e057f20f883e","regTime":1469173099000,"remark":"","uid":3,"userName":"wangshaocheng"}	*/
	import moment from 'moment'
	export default {
		data() {
			return {
				//  权限数组 roles
				currntAuth: [],
				authform: {
					adminId: ''
				},
				// 菜单 father
				authdata: [],
				defaultProps: {
					//  children ： 指定要展开的子菜单数组 属性名
					//  label   ：  指定菜单树要显示的名字
					children: 'submenu',
					label: 'name'
				},
				disBtn: true,
				imageUrl: '',
				addFormVisable: false,
				editFormVisable: false,
				authFormVisable: false,
				addform: {
					adminId: '',
					adminName: '',
					adminStatus: '',
					roleId: '',
					adminImg: ''
				},
				editform: {
					adminId: '',
					adminName: '',
					adminStatus: '',
					roleId: '',
					adminImg: ''
				},
				pageinfo: {
					page: 1,
					pageSize: 10,
					total: 10
				},
				queryForm: {
					adminId: '',
					adminName: ''
				},
				tableData: [],
				currentRow: null
			}
		},

		methods: {
			authFromSubmit() {
				this.authFormVisable = false;
				//获取id数组 转成字符串
				let id = this.$refs.authtree.getCheckedKeys().join(",");
				console.log(id);
				//拼接成键值对参数
				let param = "adminId=" + this.authform.adminId + "&menuId=" + id;
				console.log(param);
				this.$axios.post("admin/edituserauth", param)
					.then(reVal => {
						console.log(reVal);
						this.$message.success(reVal.data.returnMsg);
						this.myQuery();
					}).catch(err => {
						console.log(err);
					})
			},
			resetAuthTree() {
				this.$refs.authtree.setCheckedKeys(this.currntAuth);
			},
			//点击授权
			openAuthForm() {
				this.authFormVisable = true;
				console.log("获取1111");
				if (this.currentRow) {
					this.authform = JSON.parse(JSON.stringify(this.currentRow));
				}
				console.log("获取2");
				var that = this;
				this.$axios.post("admin/getauth", "adminId=" + this.currentRow.adminId)
					.then(reVal => {
						this.authdata = reVal.data.returnData;
						console.log(reVal.data)
						console.log("输出了223566");
						// this.currntAuth=reVal.data.extData;
						this.currntAuth = reVal.data.extData.split(",");
						console.log(this.currntAuth);
						console.log("输出了权限数组");
						//console.log(resp.data.extData.split(","));
					}).catch(err => {
						console.log(err);
					})

			},
			//点击删除
			openDelForm() {

				console.log("删除");
				// 1 获得待删除的菜单id
				var delAdminId = this.currentRow.adminId;
				// 2 发送请求
				this.$axios.post("admin/del", "adminId=" + delAdminId).then(reVal => {
					// 3 根据返回的信息进行响应
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg);
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					console.log(err);
				})
				// 4 刷新数据

				this.myQuery();
			},

			//点击提交修改
			openEditForm() {
				this.editFormVisable = true;
				if (this.currentRow) {
					this.editform = JSON.parse(JSON.stringify(this.currentRow));
				}
			},
			//提交修改保存
			editFromSubmit() {
				this.editFormVisable = false;
				console.log(this.editform)
				this.$axios.post("admin/edit", this.$qs.stringify(this.editform))
					.then(reVal => {
						console.log(reVal);
						this.$message.success(reVal.data.returnMsg);
						// this.editFormVisable = false;
						this.myQuery();
					}).catch(err => {
						console.log(err);
					})
				this.myQuery();
			},

			//点击新增
			openAddForm() {
				this.addFormVisable = true;
				this.$nextTick(function() {
					this.resetForm('addForm');
				})
			},
			//提交新增
			addFromSubmit() {
				// 关闭新增菜单对话框
				this.addFormVisible = false;
				// 提交新增菜单信息到后台
				// 封装参数
				var addAdmin = this.$qs.stringify(this.addform);
				console.log("新增 " + addAdmin);
				// 向后台发送数据
				this.$axios.get("admin/add?" + addAdmin).then(reVal => {
					console.log(reVal);
					// 处理返回信息
					if (reVal.data.code == 300) {
						// success
						this.$message.success(reVal.data.returnMsg + "新增人员重启系统后有效");
					} else if (reVal.data.code == 400) {
						this.$message.error(reVal.data.returnMsg);
					}
				}).catch(err => {
					this.$message.error("信息录入不完整");
				})

				// 更新数据
				this.myQuery();
			},
			//变更当前行
			handleRowchange(val) {
				this.currentRow = val;
				this.disBtn = false;
				console.log(this.currentRow);
			},
			dateFormat(row, column) {
				var date = row[column.property];
				if (date === undefined) {
					return ''
				};
				return moment(date).format("YYYY-MM-DD HH:mm:ss")
			},
			myQuery() {
				console.log("查询");
				// 处理页码信息
				//this.pageinfo.page = 1;
				// 拼接查询参数
				this.$axios.get("admin/query?" + this.$qs.stringify(this.queryForm) + "&" + this.$qs.stringify(this
						.pageinfo))
					.then(reVal => {
						console.log(reVal.data);
						this.tableData = reVal.data.returnData;
						this.pageinfo = reVal.data.pageInfo;
					}).catch(function(err) {
						console.log(err)
					})
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			},
			handleSizeChange(val) {
				console.log(`每页 ${val} 条`);
				this.pageinfo.page = 1;
				this.pageinfo.pageSize = val;
				this.myQuery();
			},
			handleCurrentChange(val) {
				console.log(`当前页: ${val}`);
				this.pageinfo.page = val;
				this.myQuery();
			},
			//上传成功
			myupload(param) {
				let formData = new FormData();
				formData.append("myfile", param.file);
				this.$axios.post("upload", formData, {
						headers: {
							"Content-Type": "multipart/form-data"
						}
					})
					.then(reVal => {
						console.log(reVal);
						this.addform.headImg = reVal.data.imgsrc;
					})
			},
			myupload2(param) {
				let formData = new FormData();
				formData.append("myfile", param.file);
				this.$axios.post("upload", formData, {
						headers: {
							"Content-Type": "multipart/form-data"
						}
					})
					.then(reVal => {
						console.log(reVal);

						this.editform.adminImg = reVal.data.imgsrc;
					})
			},
			//上传限制
			beforeAvatarUpload(file) {
				const isJPG = file.type === 'image/jpeg';
				const isPNG = file.type === 'image/png';
				const isLt2M = file.size / 1024 / 1024 < 2;

				if (!isJPG && !isPNG) {
					this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
				}
				if (!isLt2M) {
					this.$message.error('上传头像图片大小不能超过 2MB!');
				}
				return (isJPG || isPNG) && isLt2M;
			}
		},
		getMenuSelect() {
			this.$axios.get("menu/getmenuselect")
				.then(returnval => {
					//把获取的一级菜单 赋值给菜单选项的遍历数组
					this.menuOptions = returnval.data.returnData;
				})
				.catch(err => {

				})
		},
		mounted() {
			console.log(1111)
			this.myQuery();
		},
		watch: {
			tableData: function() {
				console.log("数据刷新了");
				this.$nextTick(function() {
					this.disBtn = true;
				})

			}
		}
	}
</script>

<style>
	img {
		width: 50px;
		height: 50px;
	}

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
