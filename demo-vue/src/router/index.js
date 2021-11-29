import { createRouter, createWebHashHistory } from 'vue-router'
import Index from '../views/Index.vue'

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index,
    redirect:'/user',
    children:[
      {
        path:'/user',
        name:'User',
        component:()=>import('../components/User.vue')
      },
      {
        path:'/book',
        name:'Book',
        component:()=>import('../components/Book.vue')
      },
      
    ]
    
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
