<template>
  <div style="position: relative; left: 20px">
    <h1>个人信息</h1>
    <el-divider></el-divider>
    <div>
      <el-row>
        <p>用户名</p>
        <el-input
          v-model="username"
          maxlength="20"
          placeholder="用户名"
          disabled
        ></el-input>
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
        type="danger"
        @click="changepwd = true"
        style="position: relative; left: 340px; top: 20px"
        >修改密码</el-button
      >
      <el-button
        type="primary"
        style="position: relative; left: 340px; top: 20px"
        @click="ok"
        >确定</el-button
      >
    </div>
  </div>
  <el-dialog v-model="changepwd" title="修改密码" width="30%" :before-close="handleClose">
    <p></p>
    <el-row>
      <p>原密码</p>
      <el-input
        v-model="pwd"
        type="password"
        maxlength="20"
        placeholder="原密码"
        show-password
      />
    </el-row>

    <p></p>
    <el-row>
      <p>新密码</p>
      <el-input
        v-model="newpwd"
        type="password"
        maxlength="20"
        placeholder="新密码"
        show-password
      />
    </el-row>

    <p></p>
    <el-row>
      <p>确认新密码</p>
      <el-input
        v-model="newpwd2"
        type="password"
        maxlength="20"
        placeholder="确认新密码"
        show-password
      />
    </el-row>
    <p></p>
    <el-button
      type="primary"
      style="position: relative; left: 440px; top: 20px"
      @click="pwdok()"
      >确定</el-button
    >
    <p></p>
  </el-dialog>
</template>

<script>
import { store } from "../store/userinfo.js";
import { ElMessage } from "element-plus";
import { requestService } from "../request/request.js";

export default {
  name: "UserInfo",
  data() {
    return {
      store,
      username: store.data.name,
      phone: store.data.phone,
      address: store.data.address,
      pwd: "",
      newpwd: "",
      newpwd2: "",
      changepwd: false,
    };
  },
  methods: {
    ok() {
      if (this.phone == "") ElMessage.error("手机号不能为空");
      else if (this.address == "") ElMessage.error("地址不能为空");
      else {
        let userid = store.data.id;
        let name_ = store.data.name;
        requestService({
          url:
            "/updateuserinfo?id=" +
            userid +
            "&phone=" +
            this.phone +
            "&address=" +
            this.address,
          method: "get",
        }).then(function (response) {
          let res = response.data;
          if (res == 0) ElMessage.error("修改失败");
          else {
            requestService({
              url: "/getuser?name=" + name_,
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
              ElMessage.success("修改成功");
            });
          }
        });
      }
    },
    pwdok() {
      if (this.newpwd == "") ElMessage.error("密码不能为空");
      else if (this.newpwd != this.newpwd2) ElMessage.error("密码与确认密码不一致");
      else {
        let userid = store.data.id;
        let name_ = store.data.name;
        var this_vue = this;
        requestService({
          url: "/changepwd?id=" + userid + "&pwd=" + this.pwd + "&newpwd=" + this.newpwd,
          method: "get",
        }).then(function (response) {
          let res = response.data;
          if (res == -1) ElMessage.error("原密码不匹配，修改失败");
          else if (res == 0) ElMessage.error("修改失败");
          else {
            ElMessage.success("修改成功");
            console.log("store");
            this_vue.changepwd = false;
            console.log(this_vue.changepwd);
            requestService({
              url: "/getuser?name=" + name_,
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
        });
      }
    },
  },
};
</script>

<style scoped>
.read-the-docs {
  color: #888;
}
.el-input {
  width: 400px;
  position: absolute;
  left: 100px;
  top: 10px;
}
</style>
