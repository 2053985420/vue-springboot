<template>
    <el-container>
      <el-header>
        <div class="headertitle">后台管理</div>
        <div style="flex: 1"></div>
        <div style="width: 100px">
          <el-dropdown>
            <span class="el-dropdown-link">
              张三<el-icon class="el-icon--right"><arrow-down /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item @click="$router.push('/login')"
                  >退出系统</el-dropdown-item
                >
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
          <Aside></Aside>
          <router-view></router-view>
      </el-main>
    </el-container>
</template>
<style scoped>
.el-header {
  color: var(--el-text-color-primary);
  text-align: center;
  line-height: 60px;
  display: flex;
  border-bottom:1px solid #ccc;
}
.el-main {
  color: var(--el-text-color-primary);
  text-align: center;
  /* line-height:calc(100vh - 110px); */
  display: flex;
}
.headertitle {
  width: 150px;
  font-weight: bold;
  color: dodgerblue;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.el-container{
    height:100%;
}
</style>
<script >
import Aside from "../components/Aside.vue";
export default {
  name: "Index",
  components: {
    Aside,
  },
};
</script>