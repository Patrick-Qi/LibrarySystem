import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'
import Cookies from 'js-cookie'

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
        },
        //category route
        {
          path: 'category',
          name: 'Category',
          component: ()=> import('@/views/category/List.vue'),
        },
        {
          path: 'addCategory',
          name: 'AddCategory',
          component: ()=> import('@/views/category/Add.vue'),
        },
        {
          path: 'editCategory',
          name: 'EditCategory',
          component: ()=> import('@/views/category/Edit.vue'),
        },

        //book route
        {
          path: 'book',
          name: 'Book',
          component: ()=> import('@/views/book/List.vue'),
        },
        {
          path: 'addBook',
          name: 'AddBook',
          component: ()=> import('@/views/book/Add.vue'),
        },
        {
          path: 'editBook',
          name: 'EditBook',
          component: ()=> import('@/views/book/Edit.vue'),
        }

    ]
  },
  {
    path: "*",
    component:()=>import('@/views/404.vue')
  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {
  if (to.path === '/login') next()
  const admin = Cookies.get("admin")
  if (!admin && to.path !== '/login') return next("/login")
  next()
})

export default router
