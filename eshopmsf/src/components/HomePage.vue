<script setup>
import Header from "./Header.vue";
import Menu from "./Menu.vue";
</script>
<template>
  <div id="picture">
    <div class="common-layout">
      <el-container direction="vertical">
        <el-header height="200px"><Header /></el-header>
        <el-container>
          <el-aside width="250px" style="background-color: #545c64"><Menu /></el-aside>
          <el-main style="background-color: #fafafa"><router-view></router-view></el-main>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomePage",
  data() {
    return {};
  },
};
</script>

<style scoped>
.read-the-docs {
  color: #888;
}
.common-layout {
  height: calc(100vh);
}
.el-container {
  height: 100%;
}
#picture {
  background: url("../assets/bkg.jpg");
  width: 100%;
  height: 200px;
  position: fixed;
  background-size: 100%;
}
</style>
