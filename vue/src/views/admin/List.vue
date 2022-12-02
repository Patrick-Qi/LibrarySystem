<template>
<div>

<div style="margin-down:20px">
    <el-input style="width: 240px" placeholder="请输入用户名" v-model="params.username"></el-input>
    <el-input style="width: 240px; margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
    <el-input style="width: 240px; margin-left: 5px" placeholder="请输入邮箱" v-model="params.email"></el-input>
    <el-button style="margin-left: 5px;" type="primary" @click.native="load"><i class="el-icon-search"></i>搜索</el-button>
    <el-button style="margin-left: 5px;" type="warning" @click.native="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>
  
    <el-table :data="tableData" stripe>
    <el-table-column prop="id" label="ID" width="80"></el-table-column>
    <el-table-column prop="username" label="用户名" ></el-table-column>
    <el-table-column prop="phone" label="电话"></el-table-column>
    <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
    <el-table-column prop="createtime" label="创建时间"></el-table-column>
    <el-table-column prop="updatetime" label="最后更新时间"></el-table-column>

    <el-table-column label="操作">
    <template v-slot="scope">
    <el-button type="primary" @click.native="$router.push('/editAdmin?id=' + scope.row.id)">编辑</el-button>
    <el-popconfirm title="确定删除？" @confirm="del(scope.row.id)" style="margin-left:5px">
    <el-button type="danger" slot="reference" >删除</el-button>
    </el-popconfirm>
    </template>
    </el-table-column>
    
    </el-table>


    <div style="margin-top: 20px">
      <el-pagination
      background
      :current-page="params.pageNum"
      :page-size="params.pageSize"
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
      :total="total">
      
      </el-pagination>
    </div>

</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'Admin',
  data() {
    return {
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        username: '',
        phone: ''
      }
    }
  },
  created() {
    this.load();
  },
  methods:{
    load() {
      // fetch( 'http://localhost:9090/user/list').then(res => res.json()).then(res => {
      //   console.log(res)
      //   this.tableData = res
      // })
      request.get(  '/admin/page', {
        params: this.params
      }).then(res => {
        if(res.code === '200') {}
        {this.tableData = res.data.list
        this.total = res.data.total}
      })
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: '',
        email: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
        request.delete( '/admin/delete/'+id).then(res => {
            if(res.code === '200'){
                this.$notify.success('删除成功')
                this.load()
            }else{
                this.$notify.error(res.msg)
            }
        })

    }
  }
}

</script>

<style scoped>

</style>