<script setup>
import { Search } from "@element-plus/icons-vue";
import { Plus, Edit, Delete, RefreshRight } from "@element-plus/icons-vue";
import { requestService } from "../request/request";
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header height="150px">
        <h1>用户管理</h1>
        <el-divider></el-divider>
        <div style="display: flex">
          <div>
            <el-input
              v-model="keyword"
              :prefix-icon="Search"
              style="width: 200px; position: relative; left: 0px; top: 0px"
            />
            <el-button @click="select" style="position: relative; left: 20px"
              >查询</el-button
            ><el-button @click="back" style="position: relative; left: 20px"
              >返回</el-button
            >
          </div>
          <el-button
            type="primary"
            :icon="Plus"
            style="position: relative; left: 40px"
            @click="
              add = true;
              this.userid = 0;
              this.type = '';
              this.name = '';
              this.phone = '';
              this.address = '';
            "
            >添加新用户</el-button
          >
        </div>
        <el-divider></el-divider>
      </el-header>
      <el-main>
        <el-table :data="tableData" empty-text="无数据" border style="width: 100%">
          <el-table-column prop="id" label="用户编号" width="100" />
          <el-table-column prop="type" label="用户类型" width="200" />
          <el-table-column prop="name" label="用户名" width="200" />
          <el-table-column prop="phone" label="手机号" width="200" />
          <el-table-column prop="address" label="地址" width="500" />
          <el-table-column label="操作" width="300">
            <template #default="scope">
              <el-button
                size="small"
                @click="
                  change = true;
                  handleEdit(scope.$index, scope.row);
                "
                icon="Edit"
              />
              <el-button
                size="small"
                type="primary"
                @click="
                  changepwd = true;
                  handlePwd(scope.$index, scope.row);
                "
                icon="RefreshRight"
              />
              <el-button
                size="small"
                type="danger"
                @click="
                  confirm = true;
                  handleDelete(scope.$index, scope.row);
                "
                icon="Delete"
              />
            </template>
          </el-table-column>
        </el-table>
        <el-divider></el-divider>
      </el-main>
      <el-footer style="position: relative; bottom: 20px">
        <div style="display: flex">
          <el-pagination
            v-model:current-page="page"
            layout="prev, pager, next"
            :total="userStore.data.user.length"
            style="position: relative; left: 20px"
            :pre-click="
              (tableData = userStore.data.user.slice(page * 10 - 10, page * 10))
            "
            :next-click="
              (tableData = userStore.data.user.slice(page * 10 - 10, page * 10))
            "
          />
        </div>
      </el-footer>
    </el-container>
  </div>
  <el-dialog v-model="confirm" title="提示" width="30%" :before-close="handleClose">
    <p>确认要删除该用户吗？</p>
    <p></p>
    <el-button
      type="primary"
      style="position: relative; left: 400px; top: 20px"
      @click="confirmdelete"
      >确认</el-button
    >
    <el-button style="position: relative; left: 400px; top: 20px" @click="cancel"
      >取消</el-button
    >
    <p></p>
  </el-dialog>
  <el-dialog v-model="changepwd" title="提示" width="30%" :before-close="handleClose">
    <p>确认要重置该用户的密码吗？</p>
    <p></p>
    <el-button
      type="primary"
      style="position: relative; left: 400px; top: 20px"
      @click="confirmpwd"
      >确认</el-button
    >
    <el-button style="position: relative; left: 400px; top: 20px" @click="cancel"
      >取消</el-button
    >
    <p></p>
  </el-dialog>
  <el-dialog v-model="change" title="修改信息" width="30%" :before-close="handleClose">
    <p></p>
    <el-row>
      <p>用户类型</p>
      <el-select
        v-model="type"
        placeholder="用户类型"
        style="width: 400px; position: absolute; left: 100px; top: 10px"
      >
        <el-option
          v-for="item in types"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-row>

    <p></p>
    <el-row>
      <p>用户名</p>
      <el-input v-model="name" maxlength="20" placeholder="用户名" />
    </el-row>

    <p></p>
    <el-row>
      <p>手机号</p>
      <el-input v-model="phone" maxlength="20" placeholder="手机号" />
    </el-row>

    <p></p>
    <el-row>
      <p>地址</p>
      <el-input v-model="address" maxlength="255" placeholder="地址" />
    </el-row>
    <p></p>
    <el-button
      type="primary"
      style="position: relative; left: 440px; top: 20px"
      @click="confirmchange"
      >确定</el-button
    >
    <p></p>
  </el-dialog>
  <el-dialog v-model="add" title="添加用户" width="30%" :before-close="handleClose">
    <p></p>
    <el-row>
      <p>用户类型</p>
      <el-select
        v-model="type"
        placeholder="用户类型"
        style="width: 400px; position: absolute; left: 100px; top: 10px"
      >
        <el-option
          v-for="item in types"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-row>

    <p></p>
    <el-row>
      <p>用户名</p>
      <el-input v-model="name" maxlength="20" placeholder="用户名" />
    </el-row>

    <p></p>
    <el-row>
      <p>手机号</p>
      <el-input v-model="phone" maxlength="20" placeholder="手机号" />
    </el-row>

    <p></p>
    <el-row>
      <p>地址</p>
      <el-input v-model="address" maxlength="255" placeholder="地址" />
    </el-row>
    <p></p>
    <el-button
      type="primary"
      style="position: relative; left: 440px; top: 20px"
      @click="confirmadd"
      >确定</el-button
    >
    <p></p>
  </el-dialog>
</template>

<script>
import { userStore } from "../store/userlist";
import { requestService } from "../request/request";
import { ElMessage } from "element-plus";
import { store } from "../store/userinfo";
export default {
  name: "User",
  data() {
    return {
      userStore,
      tableData: userStore.data.user,
      confirm: false,
      change: false,
      changepwd: false,
      add: false,
      type: "",
      name: "",
      phone: "",
      address: "",
      types: [
        { value: "管理员", label: "管理员" },
        { value: "用户", label: "用户" },
      ],
      userid: 0,
      keyword: "",
      page: 1,
    };
  },
  methods: {
    select() {
      this.tableData = this.tableData.filter(
        (data) => !this.keyword || data.name.includes(this.keyword)
      );
    },
    back() {
      this.tableData = userStore.data.user.slice(this.page * 10 - 10, this.page * 10);
    },
    cancel() {
      this.confirm = false;
      this.changepwd = false;
    },
    handleDelete(index, row) {
      this.userid = row.id;
    },
    handleEdit(index, row) {
      this.userid = row.id;
      this.type = row.type;
      this.name = row.name;
      this.phone = row.phone;
      this.address = row.address;
    },
    handlePwd(index, row) {
      this.userid = row.id;
    },
    confirmdelete() {
      var this_vue = this;
      requestService({
        url: "/admin/deleteuser?id=" + this.userid,
        method: "get",
      }).then(function (response) {
        console.log(response.data);
        let res = response.data;
        if (res == 0) ElMessage.error("删除失败");
        else {
          ElMessage.success("删除成功");
          requestService({
            url: "/admin/getuserlist",
            method: "get",
          }).then(function (response) {
            console.log(response.data);
            let res = response.data;
            userStore.data.user = [];
            res.forEach(function (element) {
              userStore.data.user.push({
                id: element.id,
                type: element.type,
                name: element.name,
                phone: element.phone,
                address: element.address,
              });
            });
            this_vue.tableData = userStore.data.user.slice(
              this_vue.page * 10 - 10,
              this_vue.page * 10
            );
            this_vue.confirm = false;
            console.log(this_vue.confirm);
          });
        }
      });
    },
    confirmadd() {
      var this_vue = this;
      requestService({
        url:
          "/admin/adduser?type=" +
          this.type +
          "&name=" +
          this.name +
          "&phone=" +
          this.phone +
          "&address=" +
          this.address,
        method: "get",
      }).then(function (response) {
        console.log(response.data);
        let res = response.data;
        if (res == -1) ElMessage.error("存在同名用户，添加失败");
        else if (res == 0) ElMessage.error("添加失败");
        else {
          ElMessage.success("添加成功");
          requestService({
            url: "/admin/getuserlist",
            method: "get",
          }).then(function (response) {
            console.log(response.data);
            let res = response.data;
            userStore.data.user = [];
            res.forEach(function (element) {
              userStore.data.user.push({
                id: element.id,
                type: element.type,
                name: element.name,
                phone: element.phone,
                address: element.address,
              });
              this_vue.tableData = userStore.data.user.slice(
                this_vue.page * 10 - 10,
                this_vue.page * 10
              );
              this_vue.add = false;
              console.log(this_vue.add);
            });
          });
        }
      });
    },
    confirmchange() {
      var this_vue = this;
      requestService({
        url:
          "/admin/updateuserinfo?id=" +
          this.userid +
          "&type=" +
          this.type +
          "&name=" +
          this.name +
          "&phone=" +
          this.phone +
          "&address=" +
          this.address,
        method: "get",
      }).then(function (response) {
        console.log(response.data);
        let res = response.data;
        if (res == -1) ElMessage.error("存在同名用户，修改失败");
        else if (res == 0) ElMessage.error("修改失败");
        else {
          ElMessage.success("修改成功");
          requestService({
            url: "/admin/getuserlist",
            method: "get",
          }).then(function (response) {
            console.log(response.data);
            let res = response.data;
            userStore.data.user = [];
            res.forEach(function (element) {
              userStore.data.user.push({
                id: element.id,
                type: element.type,
                name: element.name,
                phone: element.phone,
                address: element.address,
              });
            });
            this_vue.tableData = userStore.data.user.slice(
              this_vue.page * 10 - 10,
              this_vue.page * 10
            );
            this_vue.change = false;
            console.log(this_vue.change);
          });
        }
      });
    },
    confirmpwd() {
      var this_vue = this;
      requestService({
        url: "/admin/resetpwd?id=" + this.userid,
        method: "get",
      }).then(function (response) {
        let res = response.data;
        console.log(res);
        if (res == 0) ElMessage.error("重置失败");
        else {
          ElMessage.success("重置成功");
          if ((this_vue.id = store.data.id)) {
            requestService({
              url: "/getuser?name=" + store.data.name,
              method: "get",
            }).then(function (response) {
              console.log(response.data);
              let res = response.data;
              store.data = {
                id: res.id,
                type: res.type,
                name: res.name,
                pwd: res.pwd,
                phone: res.phone,
                address: res.address,
              };
            });
          }
          this_vue.changepwd = false;
        }
      });
    },
  },
};
</script>

<style scoped>
.read-the-docs {
  color: #888;
}

.common-layout {
  position: fixed;
}
.el-input {
  width: 400px;
  position: absolute;
  left: 100px;
  top: 10px;
}
</style>
