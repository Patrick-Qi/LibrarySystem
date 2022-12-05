<template>
<div>

<div style="margin-down:20px">
    <el-input style="width: 240px" placeholder="请输入图书名称" v-model="params.name"></el-input>
    <el-input style="width: 240px" placeholder="请输入图书isbn号码" v-model="params.book_isbn"></el-input>
    <el-button style="margin-left: 5px;" type="primary" @click.native="load"><i class="el-icon-search"></i>搜索</el-button>
    <el-button style="margin-left: 5px;" type="warning" @click.native="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>
  
    <el-table :data="tableData" stripe row-key="id" default-expand-all>
    <el-table-column prop="id" label="ID" width="80"></el-table-column>
    <el-table-column prop="name" label="图书名称" ></el-table-column>
    <el-table-column prop="description" label="描述"></el-table-column>
    <el-table-column prop="publishDate" label="出版日期"></el-table-column>
    <el-table-column prop="author" label="作者"></el-table-column>
    <el-table-column prop="publisher" label="出版社"></el-table-column>
    <el-table-column prop="category" label="类别"></el-table-column>
    <el-table-column prop="bookIsbn" label="isbn编号"></el-table-column>
    <el-table-column prop="cover" label="封面"></el-table-column>
    <el-table-column prop="createtime" label="创建时间"></el-table-column>
    <el-table-column prop="updatetime" label="最后更新时间"></el-table-column>

    <el-table-column label="操作" width="500">
    <template v-slot="scope">
    <el-button type="primary" @click.native="$router.push('/editBook?id=' + scope.row.id)">编辑</el-button>
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
import Cookies from 'js-cookie'
export default {
  name: 'Book',
  data() {
    return {
      tableData: [],
      total: 0,
      rules: {
          name: [
            { required: true, message: '请输入分类名称', trigger: 'blur' },
            { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
          ],
            },
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        bookIsbn: null,
      },
    
    admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
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
      request.get(  '/book/page', {
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
        bookIsbn: '',
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
        request.delete( '/book/delete/'+id).then(res => {
            if(res.code === '200'){
                this.$notify.success('删除成功')
                this.load()
            }else{
                this.$notify.error(res.msg)
            }
        })

    },
    
  }
}

</script>

<style scoped>

</style>