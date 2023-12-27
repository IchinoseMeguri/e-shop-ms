<template>
  <div id="picture">
    <title>在线商店登录</title>
    <div class="common-layout">
      <el-container>
        <el-header
          ><h1 align="center" style="color: white">在线商店购物系统</h1></el-header
        >

        <el-main>
          <div align="center" style="position: relative; top: 20px">
            <el-card
              align="center"
              style="width: 300px; background-color: rgba(255, 255, 255, 0.8)"
            >
              <h1 align="center">登录</h1>
              <div>
                <el-row>
                  <p>用户名</p>
                  <el-input v-model="name" maxlength="20" placeholder="用户名" />
                </el-row>

                <p></p>
                <el-row>
                  <p>密码</p>
                  <el-input
                    v-model="pwd"
                    type="password"
                    maxlength="20"
                    placeholder="密码"
                    show-password
                  />
                </el-row>
              </div>
              <p></p>
              <div align="center">
                <el-button type="primary" @click="login">登录</el-button>
                <el-button @click="register">注册</el-button>
              </div>
            </el-card>
            <p></p>
          </div>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
import { requestService } from "../request/request.js";
import { ElMessage } from "element-plus";
import { store } from "../store/userinfo.js";
import { commodityStore } from "../store/commodity";
import { myOrderStore } from "../store/myorder";
import { userStore } from "../store/userlist";
import { orderStore } from "../store/order";

export default {
  name: "Login",
  data() {
    return {
      name: "",
      pwd: "",
    };
  },
  methods: {
    login() {
      var this_vue = this;
      let name_ = this.name;
      requestService({
        url: "/login?name=" + this.name + "&pwd=" + this.pwd,
        method: "get",
      })
        .then(function (response) {
          let res = response.data;
          if (res == 1) {
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
              ElMessage.success("登陆成功");
              console.log(store.data);
              requestService({
                url: "/getuserorder?userid=" + store.data.id,
                method: "get",
              }).then(function (response) {
                console.log(response.data);
                let res = response.data;
                myOrderStore.data.order = [];
                res.forEach(function (element) {
                  myOrderStore.data.order.push({
                    id: element.id,
                    name: store.data.name,
                    time: element.time,
                    price: element.price,
                  });
                });
                requestService({
                  url: "/getcommoditylist",
                  method: "get",
                }).then(function (response) {
                  console.log(response.data);
                  let res = response.data;
                  commodityStore.data.commodity = [];
                  res.forEach(function (element) {
                    commodityStore.data.commodity.push({
                      id: element.id,
                      name: element.name,
                      price: element.price,
                      quantity: element.quantity,
                      number: 0,
                    });
                  });
                  this_vue.$router.push("/menu/shopping");
                  if (store.data.type == "管理员") {
                    requestService({
                      url: "/admin/getuserlist",
                      method: "get",
                    }).then(function (response) {
                      let res = response.data;
                      console.log(res);
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
                      console.log(userStore.data.user);
                      let users = userStore.data.user;
                      requestService({
                        url: "/admin/getorderlist",
                        method: "get",
                      }).then(function (response) {
                        let res = response.data;
                        console.log(res);
                        orderStore.data.order = [];
                        let user = {};
                        res.forEach(function (element) {
                          user = users.find((item) => item.id == element.userId);
                          if (typeof user == "undefined") user = { name: "Undefined" };
                          orderStore.data.order.push({
                            id: element.id,
                            name: user.name,
                            time: element.time,
                            price: element.price,
                          });
                        });
                      });
                    });
                  }
                });
              });
            });
          } else ElMessage.error("登陆失败");
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    register() {
      this.$router.push("/register");
    },
  },
};
</script>

<style scoped>
.read-the-docs {
  color: #888;
}
#picture {
  background: url("../assets/bkg.jpg");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100%;
}
</style>
