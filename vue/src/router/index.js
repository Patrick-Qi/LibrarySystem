import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'

Vue.use(VueRouter)

const routes = [
  //login route
  {
    path: '/login',
    name: 'Login',
    component: ()=> import('@/views/login/Login.vue')
  },
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/home',
    children: [
            //user route
            {
              path: 'home',
              name: 'Home',
              component: ()=> import('@/views/home/HomeView.vue'),
            },
        {
          path: 'user',
          name: 'User',
          component: ()=> import('@/views/user/List.vue'),
        },
        {
          path: 'addUser',
          name: 'AddUser',
          component: ()=> import('@/views/user/Add.vue'),
        },
        {
          path: 'editUser',
          name: 'EditUser',
          component: ()=> import('@/views/user/Edit.vue'),
        },
        //admin route
        {
          path: 'admin',
          name: 'Admin',
          component: ()=> import('@/views/admin/List.vue'),
        },
        {
          path: 'addAdmin',
          name: 'AddAdmin',
          component: ()=> import('@/views/admin/Add.vue'),
        },
        {
          path: 'editAdmin',
          name: 'EditAdmin',
          component: ()=> import('@/views/admin/Edit.vue'),
        }
    ]
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
