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
        <el-table-column prop="name" label="书名" />
        <el-table-column prop="price" label="单价" />
        <el-table-column prop="author" label="作者" sortable />
        <el-table-column prop="createTime" label="发表时间" />
        <el-table-column label="封面">
          <template #default="scope">
            <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.cover"
              :fit="fit"
            ></el-image>
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
        <el-form-item label="书名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="单价">
          <el-input v-model="form.price"></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="form.author"></el-input>
        </el-form-item>
        <el-form-item label="发表时间">
          <el-date-picker
            v-model="form.createTime"
            type="date"
            placeholder="Select date"
            style="width: 70%"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-i label="封面">
          <el-upload
            class="upload-demo"
            action="http://localhost:8081/files/upload"
            :on-success="fileUploadSuccess"
            list-type="picture"
            :limit="1"
          >
            <el-button size="small" type="primary">Click to upload</el-button>
            <template #tip>
              <div class="el-upload__tip">
                jpg/png files with a size less than 500kb
              </div>
            </template>
          </el-upload>
        </el-form-i>
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
import { ElMessage } from "element-plus";

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
    fileUploadSuccess(res) {
      console.log(res);
      this.form.cover = res.data;
    },
    handleAdd() {
      this.flag = 2;
      this.form = {};
      this.dialogVisible = true;
    },

    load() {
      request
        .get("/book", {
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
        request.put("/book", this.form).then((res) => {
          if (res.code == 0) {
            this.load();
            this.dialogVisible = false;
          } else {
            ElMessage.error(res.msg);
          }
        });
      } else {
        request.post("/book", this.form).then((res) => {
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
        .delete("/book", {
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