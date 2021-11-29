<template>
<div>
<el-menu
      default-active="/user"
        class="el-menu-vertical-demo"
        router=true
      >
        <el-menu-item index="/user" route="/user">
          <span >用户管理</span>
        </el-menu-item>
        <el-sub-menu index="2">
          <template #title>
            <span>其他</span>
          </template>
          <el-menu-item index="/book" route="/book">书籍管理</el-menu-item>
        </el-sub-menu>     
      </el-menu>
</div>
      
</template>
<script>
import { defineComponent } from 'vue'
import {
  Menu as IconMenu
} from '@element-plus/icons'

export default defineComponent({
    name:"Aside",
  components: {
    IconMenu,
  }
})
</script>
<style scoped>
.el-menu-vertical-demo{
       width: 200px;
       height:100%;
    }
</style>