<template>
  <div class="common-layout">
    <el-container>
      <el-header height="150px">
        <h1>购买商品</h1>
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
          <el-table-column prop="id" label="商品编号" width="200" />
          <el-table-column prop="name" label="商品名称" width="400" />
          <el-table-column prop="price" label="单价" width="200" :formatter="rounding" />
          <el-table-column prop="quantity" label="库存数量" width="200" />
          <el-table-column prop="number" label="购买数量" width="100" />
          <el-table-column prop="do" label="操作" width="400">
            <template #default="scope">
              <div style="display: flex">
                <el-button
                  size="small"
                  @click="handleMinus(scope.$index, scope.row)"
                  icon="Minus"
                  :disabled="isMinusDisabled(scope.$index, scope.row)"
                />
                <el-button
                  size="small"
                  type="danger"
                  @click="handlePlus(scope.$index, scope.row)"
                  icon="Plus"
                  :disabled="isPlusDisabled(scope.$index, scope.row)"
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
            :total="commodityStore.data.commodity.length"
            style="position: relative; left: 20px"
            :pre-click="
              (tableData = commodityStore.data.commodity.slice(page * 10 - 10, page * 10))
            "
            :next-click="
              (tableData = commodityStore.data.commodity.slice(page * 10 - 10, page * 10))
            "
          />
          <h1
            style="
              position: absolute;
              left: 1200px;
              font-size: 25px;
              bottom: 10px;
              color: red;
            "
          >
            ¥{{ total.toFixed(2) }}
          </h1>
          <el-button
            v-loading.fullscreen.lock="fullscreenLoading"
            type="success"
            @click="ok"
            style="position: absolute; left: 1400px"
            >结算</el-button
          >
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script setup>
import { Search } from "@element-plus/icons-vue";
</script>

<script>
import { commodityStore } from "../store/commodity.js";
import { cartStore } from "../store/cart.js";
import { store } from "../store/userinfo.js";
import { Plus, Minus } from "@element-plus/icons-vue";
import { requestService } from "../request/request.js";
import { ElMessage, ElLoading } from "element-plus";
import { myOrderStore } from "../store/myorder";
import { userStore } from "../store/userlist";
import { orderStore } from "../store/order";
export default {
  name: "Shopping",
  data() {
    return {
      commodityStore,
      cartStore,
      tableData: commodityStore.data.commodity.slice(0, 10),
      total: cartStore.data.price,
      keyword: "",
      fullscreenLoading: false,
      page: 1,
    };
  },
  methods: {
    rounding(row, column) {
      return parseFloat(row[column.property]).toFixed(2);
    },
    handleMinus(index, row) {
      commodityStore.data.commodity[index].number--;
      cartStore.data.price -= commodityStore.data.commodity[index].price;
      this.total = cartStore.data.price;
      if (commodityStore.data.commodity[index].number == 0)
        cartStore.data.item.splice(
          cartStore.data.item.indexOf(commodityStore.data.commodity[index]),
          1
        );
    },
    handlePlus(index, row) {
      if (commodityStore.data.commodity[index].number == 0)
        cartStore.data.item.push(commodityStore.data.commodity[index]);
      commodityStore.data.commodity[index].number++;
      cartStore.data.price += commodityStore.data.commodity[index].price;
      this.total = cartStore.data.price;
    },
    isMinusDisabled(index, row) {
      return commodityStore.data.commodity[index].number == 0 ? true : false;
    },
    isPlusDisabled(index, row) {
      return commodityStore.data.commodity[index].number ==
        commodityStore.data.commodity[index].quantity
        ? true
        : false;
    },
    ok() {
      this.fullscreenLoading = true;
      setTimeout(() => {
        this.fullscreenLoading = false;

        let orderDetailList = "";
        cartStore.data.item.forEach(function (element) {
          orderDetailList +=
            element.id +
            "&" +
            element.number +
            "&" +
            element.number * element.price +
            "&";
        });
        requestService({
          url: "/createorder",
          method: "get",
          params: {
            userid: store.data.id,
            orderDetailList: orderDetailList,
          },
        }).then(function (response) {
          let res = response.data;
          console.log(res);
          if (res == 0) ElMessage.error("结算失败");
          else {
            ElMessage.success("结算成功，生成订单");
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
              });
            });
          }
        });
      }, 1500);
    },
    select() {
      this.tableData = this.tableData.filter(
        (data) => !this.keyword || data.name.includes(this.keyword)
      );
    },
    back() {
      this.tableData = commodityStore.data.commodity.slice.slice(
        this.page * 10 - 10,
        this.page * 10
      );
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
