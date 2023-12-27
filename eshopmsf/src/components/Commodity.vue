<script setup>
import { Search } from "@element-plus/icons-vue";
import { Plus, Edit, Delete } from "@element-plus/icons-vue";
</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-header height="150px">
        <h1>商品管理</h1>
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
              addcommodity = true;
              this.commodityid = 0;
              this.name = '';
              this.price = '';
              this.quantity = '';
            "
            >添加新商品</el-button
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
          <el-table-column label="操作" width="500">
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
            :total="commodityStore.data.commodity.length"
            style="position: relative; left: 20px"
            :pre-click="
              (tableData = commodityStore.data.commodity.slice(page * 10 - 10, page * 10))
            "
            :next-click="
              (tableData = commodityStore.data.commodity.slice(page * 10 - 10, page * 10))
            "
          />
        </div>
      </el-footer>
    </el-container>
  </div>
  <el-dialog v-model="confirm" title="提示" width="30%" :before-close="handleClose">
    <p>确认要删除该商品吗？</p>
    <p></p>
    <el-button
      type="primary"
      style="position: relative; left: 400px; top: 20px"
      @click="confirmdelete()"
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
      <p>商品名称</p>
      <el-input v-model="name" maxlength="20" placeholder="商品名称" />
    </el-row>

    <p></p>
    <el-row>
      <p>单价</p>
      <el-input v-model="price" maxlength="20" placeholder="单价" />
    </el-row>

    <p></p>
    <el-row>
      <p>库存数量</p>
      <el-input v-model="quantity" maxlength="20" placeholder="库存数量" />
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
  <el-dialog
    v-model="addcommodity"
    title="添加商品"
    width="30%"
    :before-close="handleClose"
  >
    <p></p>
    <el-row>
      <p>商品名称</p>
      <el-input v-model="name" maxlength="20" placeholder="商品名称" />
    </el-row>

    <p></p>
    <el-row>
      <p>单价</p>
      <el-input v-model="price" maxlength="20" placeholder="单价" />
    </el-row>

    <p></p>
    <el-row>
      <p>库存数量</p>
      <el-input v-model="quantity" maxlength="20" placeholder="库存数量" />
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
import { commodityStore } from "../store/commodity";
import { requestService } from "../request/request";
import { ElMessage } from "element-plus";
export default {
  name: "Commodity",
  data() {
    return {
      keyword: "",
      commodityStore,
      tableData: commodityStore.data.commodity,
      name: "",
      price: "",
      quantity: "",
      commodityid: 0,
      confirm: false,
      change: false,
      addcommodity: false,
      page: 1,
    };
  },
  methods: {
    handleDelete(index, row) {
      this.commodityid = row.id;
    },
    handleEdit(index, row) {
      this.commodityid = row.id;
      this.name = row.name;
      this.price = row.price;
      this.quantity = row.quantity;
    },
    confirmdelete() {
      var this_vue = this;
      requestService({
        url: "/admin/deletecommodity?id=" + this.commodityid,
        method: "get",
      }).then(function (response) {
        console.log(response.data);
        let res = response.data;
        if (res == 0) ElMessage.error("删除失败");
        else {
          ElMessage.success("删除成功");
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
            this_vue.tableData = commodityStore.data.commodity.slice(
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
          "/admin/addcommodity?name=" +
          this.name +
          "&price=" +
          this.price +
          "&quantity=" +
          this.quantity,
        method: "get",
      }).then(function (response) {
        console.log(response.data);
        let res = response.data;
        if (res == -1) ElMessage.error("存在同名商品，添加失败");
        else if (res == 0) ElMessage.error("添加失败");
        else {
          ElMessage.success("添加成功");
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
              this_vue.tableData = commodityStore.data.commodity.slice(
                this_vue.page * 10 - 10,
                this_vue.page * 10
              );
              this_vue.addcommodity = false;
              console.log(this_vue.addcommodity);
            });
          });
        }
      });
    },
    confirmchange() {
      var this_vue = this;
      requestService({
        url:
          "/admin/updatecommodity?id=" +
          this.commodityid +
          "&name=" +
          this.name +
          "&price=" +
          this.price +
          "&quantity=" +
          this.quantity,
        method: "get",
      }).then(function (response) {
        console.log(response.data);
        let res = response.data;
        if (res == -1) ElMessage.error("存在同名商品，修改失败");
        else if (res == 0) ElMessage.error("修改失败");
        else {
          ElMessage.success("修改成功");
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
            this_vue.tableData = commodityStore.data.commodity.slice(
              this_vue.page * 10 - 10,
              this_vue.page * 10
            );
            this_vue.change = false;
            console.log(this_vue.change);
          });
        }
      });
    },
    select() {
      this.tableData = this.tableData.filter(
        (data) => !this.keyword || data.name.includes(this.keyword)
      );
    },
    back() {
      this.tableData = commodityStore.data.commodity.slice(
        this.page * 10 - 10,
        this.page * 10
      );
    },
    cancel() {
      this.confirm = false;
    },
    rounding(row, column) {
      return parseFloat(row[column.property]).toFixed(2);
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
