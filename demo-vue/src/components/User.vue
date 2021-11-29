<template>
  <div style="width: 100%">
    <div class="div1">
      <el-button type="primary" @click="handleAdd()">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <div class="div1">
      <el-input
        v-model="input"
        placeholder="Please input"
        style="margin-right: 5px; width: 25%"
      />
      <el-button type="primary" @click="load()">查询</el-button>
    </div>
    <div class="duing">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="id" label="id" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="nickName" label="昵称" />
        <el-table-column prop="age" label="年龄" sortable />
        <el-table-column prop="sex" label="性别">
          <template #default="scope">
            <span v-if="scope.row.sex == 1">男</span>
            <span v-else>女</span>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="地址" />
        <el-table-column label="角色">
          <template #default="scope">
            <span v-if="scope.row.role == 1">管理员</span>
            <span v-else>普通用户</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.row)"
              >编辑</el-button
            >
            <el-popconfirm
              title="Are you sure to delete this?"
              @confirm="handleDelete(scope.row)"
            >
              <template #reference>
                <el-button size="mini" type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="height: 50px"
        v-model:currentPage="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
    <el-dialog v-model="dialogVisible" title="Tips" width="30%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickName"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-select v-model="form.age" placeholder="please select your age">
            <el-option
              v-for="i in list"
              :key="i"
              :value="i"
              :label="i"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="1">男</el-radio>
          <el-radio v-model="form.sex" label="2">女</el-radio>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="edit()">Confirm</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<style >
.div1 {
  display: flex;
  margin-bottom: 10px;
}

.el-table__header {
  line-height: 3vh;
  width: 100%;
}
</style>

<script>
import request from "@/utils/request";
import { ElMessage } from 'element-plus'

export default {
  data() {
    return {
      input: "",
      flag: 1,
      list: [],
      pageNum: 1,
      pageSize: 5,
      tableData: [],
      total: 0,
      dialogVisible: false,
      form: {},
    };
  },
  created() {
    for (let i = 1; i < 100; i++) {
      this.list[i - 1] = i;
    }
    this.load();
  },
  methods: {
    handleAdd() {
      this.flag = 2;
      this.form = {};
      this.dialogVisible = true;
    },

    load() {
      request
        .get("/user", {
          params: {
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            search: this.input,
          },
        })
        .then((res) => {
          console.log(res);
          this.tableData = res.data.records;
          this.total = res.data.total;
        });
    },
    handleEdit(row) {
      this.flag = 1;
      this.form = row;
      this.dialogVisible = true;
    },
    edit() {
      if (this.flag == 1) {
        request.put("/user", this.form).then((res) => {
          if (res.code == 0) {
            this.load();
            this.dialogVisible = false;
          } else {
            ElMessage.error(res.msg);
          }
        });
      } else {
        request.post("/user", this.form).then((res) => {
          console.log(res);
          if (res.code == 0) {
            this.load();
            this.dialogVisible = false;
          } else {
            ElMessage.error(res.msg);
          }
        });
      }
    },
    handleDelete(row) {
      request
        .delete("/user", {
          params: { id: row.id },
        })
        .then((res) => {
          this.load();
        });
    },

    handleSizeChange(val) {
      console.log("handleSizeChange");
      this.pageSize = val;
      console.log(this.pageSize);
      this.load();
    },
    handleCurrentChange(val) {
      console.log("handleCurrentChange");
      this.pageNum = val;
      console.log(this.pageNum);
      this.load();
    },
  },
};
</script>