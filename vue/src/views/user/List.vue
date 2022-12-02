<template>
<div>

<div style="margin-down:20px">
    <el-input style="width: 240px" placeholder="请输入名称" v-model="params.name"></el-input>
    <el-input style="width: 240px; margin-left: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
    <el-button style="margin-left: 5px;" type="primary" @click.native="load"><i class="el-icon-search"></i>搜索</el-button>
    <el-button style="margin-left: 5px;" type="warning" @click.native="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>
  
    <el-table :data="tableData" stripe>
    <el-table-column prop="id" label="ID" width="80"></el-table-column>
    <el-table-column prop="username" label="会员号" ></el-table-column>
    <el-table-column prop="name" label="名称" width="80"></el-table-column>
    <el-table-column prop="age" label="年龄" width="80"></el-table-column>
    <el-table-column prop="sex" label="性别 " width="80"></el-table-column>
    <el-table-column prop="phone" label="电话"></el-table-column>
    <el-table-column prop="address" label="地址" width="180"></el-table-column>
    <el-table-column prop="createtime" label="创建时间"></el-table-column>
    <el-table-column prop="updatetime" label="最后更新时间"></el-table-column>

    <el-table-column label="操作">
    <template v-slot="scope">
    <el-button type="primary" @click.native="$router.push('/editUser?id=' + scope.row.id)">编辑</el-button>
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
  name: 'User',
  data() {
    return {
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
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
      request.get(  '/user/page', {
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
        phone: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
        request.delete( '/user/delete/'+id).then(res => {
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