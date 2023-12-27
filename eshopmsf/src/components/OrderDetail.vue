<script setup>
import { Search } from "@element-plus/icons-vue";
import { More } from "@element-plus/icons-vue";
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header height="150px">
        <h1>订单详细</h1>
        <el-divider></el-divider>
        <div style="display: flex">
          <div>
            <el-input v-model="keyword" :prefix-icon="Search" style="width: 200px" />
            <el-button @click="select" style="position: relative; left: 20px"
              >查询</el-button
            ><el-button @click="back" style="position: relative; left: 20px"
              >返回</el-button
            >
          </div>
          <h1 style="position: absolute; left: 900px">订单编号：{{ orderID }}</h1>
          <h1 style="position: absolute; left: 1250px">时间：{{ rounding(time) }}</h1>
        </div>
        <el-divider></el-divider>
      </el-header>
      <el-main>
        <el-table :data="tableData" empty-text="无数据" border style="width: 100%">
          <el-table-column prop="id" label="商品编号" width="200" />
          <el-table-column prop="name" label="商品名称" width="400" />
          <el-table-column prop="price" label="单价" width="200" :formatter="r2" />
          <el-table-column prop="quantity" label="购买数量" width="200" />
          <el-table-column prop="tprice" label="小计价格" width="200" :formatter="r2" />
          <el-table-column prop="do" label="操作" width="300">
            <template #default="scope">
              <div style="display: flex">
                <el-button size="small" icon="More" disabled />
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
            :total="orderDetailStore.data.orderDetail.length"
            style="position: relative; left: 20px"
            :pre-click="
              (tableData = orderDetailStore.data.orderDetail.slice(
                page * 10 - 10,
                page * 10
              ))
            "
            :next-click="
              (tableData = orderDetailStore.data.orderDetail.slice(
                page * 10 - 10,
                page * 10
              ))
            "
          />
          <el-button @click="backtomyorder" style="position: absolute; left: 1400px"
            >返回</el-button
          >
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import { orderDetailStore } from "../store/orderdetail";
export default {
  name: "Shopping",
  data() {
    return {
      tableData: orderDetailStore.data.orderDetail,
      orderID: orderDetailStore.data.orderid,
      time: orderDetailStore.data.time,
      orderDetailStore,
      keyword: "",
      page: 1,
    };
  },
  methods: {
    backtomyorder() {
      this.$router.push("/menu/myorder");
    },
    rounding(value) {
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
        (data) => !this.keyword || data.name.includes(this.keyword)
      );
    },
    back() {
      this.tableData = orderDetailStore.data.orderDetail.slice(
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
