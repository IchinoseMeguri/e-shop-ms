<template>
  <div class="user" style="position: relative; left: 20px; top: 20px">
    <el-icon size="50px" color="#ffffff">
      <UserFilled />
    </el-icon>
    <h3 style="position: relative; left: 20px; color: white">{{ store.data.name }}</h3>
  </div>
  <el-divider />
  <el-menu
    router
    :default-active="defaultActive"
    @select="handleSelect"
    active-text-color="#ffd04b"
    background-color="#545c64"
    class="el-menu-vertical-demo"
    text-color="#fff"
    @open="handleOpen"
    @close="handleClose"
  >
    <div>
      <el-menu-item index="/menu/shopping">
        <span>购买商品</span>
      </el-menu-item>
      <el-menu-item index="/menu/shoppingcart">
        <span>购物车</span>
      </el-menu-item>
      <el-menu-item index="/menu/userinfo">
        <span>个人信息</span>
      </el-menu-item>
      <el-menu-item index="/menu/myorder">
        <span>我的订单</span>
      </el-menu-item>
      <el-menu-item index="/">
        <span>退出</span>
      </el-menu-item>
    </div>
    <el-divider />
    <div v-show="store.data.type == '管理员'">
      <el-menu-item index="/menu/commodity">
        <span>商品管理</span>
      </el-menu-item>
      <el-menu-item index="/menu/user">
        <span>用户管理</span>
      </el-menu-item>
      <el-menu-item index="/menu/order"> <span>订单管理</span> </el-menu-item
      ><el-divider />
    </div>
  </el-menu>
</template>

<script>
import { store } from "../store/userinfo.js";
export default {
  name: "Menu",
  data() {
    return {
      store,
      defaultActive: "/menu/shopping",
    };
  },
  mounted() {
    this.defaultActive = sessionStorage.getItem("keyPath") || "/menu/shopping";
  },
  watch: {
    defaultActive(newValue) {
      if (newValue != this.$route.path) {
        this.defaultActive = this.$route.path;
      }
    },
  },
  methods: {
    handleSelect(keyPath) {
      sessionStorage.setItem("keyPath", keyPath);
    },
  },
};
</script>

<style scoped>
.read-the-docs {
  color: #888;
}
.user {
  display: inline-flex;
}
.el-menu-item {
  justify-content: center;
  font-size: large;
}
</style>
