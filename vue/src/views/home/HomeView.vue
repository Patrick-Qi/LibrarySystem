<template>
  <div class="item">
  <div >管理员：{{admin.username}}</div>
  <div>手机号： {{admin.phone}} </div>
  <div>邮箱： {{admin.email}} </div>
    
  </div>
</template>

<script>
// @ is an alias to /src
import request from "@/utils/request";
import Cookies from 'js-cookie'
export default {
  name: 'HomeView',
  data() {
      return {
        admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
      }
    },
    
}
</script>

<style>
.item {
  margin: 10px 0;
  color: #666
}
</style>