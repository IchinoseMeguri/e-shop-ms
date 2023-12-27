<script setup>
import { Search } from "@element-plus/icons-vue";
import { More } from "@element-plus/icons-vue";
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header height="150px">
        <h1>我的订单</h1>
        <el-divider></el-divider>
        <div>
          <el-input v-model="keyword" :prefix-icon="Search" style="width: 200px" />
          <el-button @click="select" style="position: relative; left: 20px"
            >查询</el-button
          ><el-button @click="back" style="position: relative; left: 20px"
            >返回</el-button
          >
        </div>
        <el-divider></el-divider>
      </el-header>
      <el-main>
        <el-table :data="tableData" empty-text="无数据" border style="width: 100%">
          <el-table-column prop="id" label="订单编号" width="200" />
          <el-table-column prop="name" label="用户名" width="400" />
          <el-table-column prop="time" label="时间" width="400" :formatter="rounding" />
          <el-table-column prop="price" label="总价格" width="200" :formatter="r2" />
          <el-table-column prop="do" label="操作" width="300">
            <template #default="scope">
              <div style="display: flex">
                <el-button
                  size="small"
                  @click="handleDetail(scope.$index, scope.row)"
                  icon="More"
                />
              </div>
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
            :total="myOrderStore.data.order.length"
            style="position: relative; left: 20px"
            :pre-click="
              (tableData = myOrderStore.data.order.slice(page * 10 - 10, page * 10))
            "
            :next-click="
              (tableData = myOrderStore.data.order.slice(page * 10 - 10, page * 10))
            "
          />
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import { orderStore } from "../store/order.js";
import { myOrderStore } from "../store/myorder";
import { orderDetailStore } from "../store/orderdetail";
import { requestService } from "../request/request";
import { commodityStore } from "../store/commodity";
export default {
  name: "Shopping",
  data() {
    return {
      orderStore,
      myOrderStore,
      commodityStore,
      tableData: myOrderStore.data.order,
      keyword: "",
      page: 1,
    };
  },
  methods: {
    handleDetail(index, row) {
      var this_vue = this;
      let orderid = row.id;
      let ordertime = row.time;
      let commodity = {};
      requestService({
        url: "/getorderdetail?orderid=" + orderid,
        method: "get",
      }).then(function (response) {
        console.log(response.data);
        let res = response.data;
        orderDetailStore.data.orderid = orderid;
        orderDetailStore.data.time = ordertime;
        orderDetailStore.data.orderDetail = [];
        res.forEach(function (element) {
          commodity = commodityStore.data.commodity.find(
            (item) => item.id == element.commodityId
          );
          if (typeof commodity == "undefined")
            commodity = {
              name: "Undefined",
              price: element.price / element.quantity,
            };
          console.log(commodity);
          orderDetailStore.data.orderDetail.push({
            id: element.commodityId,
            name: commodity.name,
            price: commodity.price,
            quantity: element.quantity,
            tprice: element.price,
          });
        });
        this_vue.$router.push("/menu/orderdetail");
      });
    },
    rounding(row, column) {
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
    r2(row, column) {
      return parseFloat(row[column.property]).toFixed(2);
    },
    select() {
      this.tableData = this.tableData.filter(
        (data) =>
          !this.keyword ||
          data.name.includes(this.keyword) ||
          String(data.id).includes(this.keyword) ||
          String(data.time).includes(this.keyword) ||
          String(data.price).includes(this.keyword)
      );
    },
    back() {
      this.tableData = myOrderStore.data.order.slice(this.page * 10 - 10, this.page * 10);
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
