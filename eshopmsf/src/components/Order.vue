<script setup>
import { Search } from "@element-plus/icons-vue";
import { Plus, Edit, Delete } from "@element-plus/icons-vue";
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header height="150px">
        <h1>订单管理</h1>
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
            @click="ElMessage('开发中')"
            >添加新订单</el-button
          >
        </div>
        <el-divider></el-divider>
      </el-header>
      <el-main>
        <el-table :data="tableData" empty-text="无数据" border style="width: 100%">
          <el-table-column prop="id" label="订单编号" width="200" />
          <el-table-column prop="name" label="购买用户" width="400" />
          <el-table-column prop="time" label="时间" width="400" :formatter="r2" />
          <el-table-column
            prop="price"
            label="总价格"
            width="200"
            :formatter="rounding"
          />
          <el-table-column label="操作" width="300">
            <template #default="scope">
              <el-button
                size="small"
                @click="handleEdit(scope.$index, scope.row)"
                icon="Edit"
              />
              <el-button
                type="primary"
                size="small"
                @click="handlePlus(scope.$index, scope.row)"
                icon="Plus"
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
            :total="orderStore.data.order.length"
            style="position: relative; left: 20px"
            :pre-click="
              (tableData = orderStore.data.order.slice(page * 10 - 10, page * 10))
            "
            :next-click="
              (tableData = orderStore.data.order.slice(page * 10 - 10, page * 10))
            "
          />
        </div>
      </el-footer>
    </el-container>
  </div>
  <el-dialog v-model="confirm" title="提示" width="30%" :before-close="handleClose">
    <p>确认要删除该订单吗？</p>
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

  <el-dialog v-model="addorder" title="添加订单" width="30%" :before-close="handleClose">
    <p></p>
    <el-row>
      <p>订单用户</p>
      <el-select
        v-model="orderuser"
        placeholder="订单用户"
        style="width: 400px; position: absolute; left: 100px; top: 10px"
      >
        <el-option
          v-for="item in userStore.data.user"
          :key="item.id"
          :label="item.name"
          :value="item.name"
        />
      </el-select>
    </el-row>

    <p></p>
    <el-row>
      <p>时间</p>
      <el-input v-model="time" maxlength="20" placeholder="时间" />
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
import { orderStore } from "../store/order";
import { orderDetailStore } from "../store/orderdetail";
import { ElMessage } from "element-plus";
import { userStore } from "../store/userlist";
import { requestService } from "../request/request";
import { store } from "../store/userinfo";
import { myOrderStore } from "../store/myorder";
export default {
  name: "Shopping",
  data() {
    return {
      orderStore,
      tableData: orderStore.data.order.slice(0, 10),
      page: 1,
      keyword: "",
      confirm: false,
      orderid: 0,
      addorder: false,
    };
  },
  methods: {
    select() {
      this.tableData = orderStore.data.order.filter(
        (data) => !this.keyword || data.name.includes(this.keyword)
      );
    },
    back() {
      this.tableData = orderStore.data.order.slice(this.page * 10 - 10, this.page * 10);
    },
    rounding(row, column) {
      return parseFloat(row[column.property]).toFixed(2);
    },
    r2(row, column) {
      let value = row[column.property];
      let str = "" + value[0] + "-";
      if (value[1] < 10) str += "0";
      str += value[1] + "-";
      if (value[2] < 10) str += "0";
      str += value[2] + " ";
      if (value[3] < 10) str += "0";
      str += value[3] + ":";
      if (value[4] < 10) str += "0";
      str += value[4] + ":";
      if (value[5] < 10) str += "0";
      str += value[5];
      return str;
    },
    handleDelete(index, row) {
      this.orderid = row.id;
    },
    handleEdit(index, row) {
      ElMessage("开发中");
    },
    handlePlus(index, row) {
      ElMessage("开发中");
    },
    cancel() {
      this.confirm = false;
    },
    confirmdelete() {
      var this_vue = this;
      requestService({
        url: "/admin/deleteorder?id=" + this.orderid,
        method: "get",
      }).then(function (response) {
        console.log(response.data);
        let res = response.data;
        if (res == 0) ElMessage.error("删除失败");
        else {
          ElMessage.success("删除成功");
          requestService({
            url: "/admin/getorderlist",
            method: "get",
          }).then(function (response) {
            let res = response.data;
            console.log(res);
            orderStore.data.order = [];
            let user = {};
            res.forEach(function (element) {
              user = userStore.data.user.find((item) => item.id == element.userId);
              if (typeof user == "undefined") user = { name: "Undefined" };
              orderStore.data.order.push({
                id: element.id,
                name: user.name,
                time: element.time,
                price: element.price,
              });
            });
            this_vue.tableData = orderStore.data.order.slice(
              this_vue.page * 10 - 10,
              this_vue.page * 10
            );
            this_vue.confirm = false;
            console.log(this_vue.confirm);
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
            });
          });
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
</style>
