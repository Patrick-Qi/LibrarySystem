<template>
<div>

<div style="margin-down:20px">
    <el-input style="width: 240px" placeholder="请输入分类" v-model="params.name"></el-input>
    <el-button style="margin-left: 5px;" type="primary" @click.native="load"><i class="el-icon-search"></i>搜索</el-button>
    <el-button style="margin-left: 5px;" type="warning" @click.native="reset"><i class="el-icon-refresh"></i>重置</el-button>
    </div>
  
    <el-table :data="tableData" stripe row-key="id" default-expand-all>
    <el-table-column prop="id" label="ID" width="80"></el-table-column>
    <el-table-column prop="name" label="名称" ></el-table-column>
    <el-table-column prop="remark" label="备注"></el-table-column>
    <el-table-column prop="createtime" label="创建时间"></el-table-column>
    <el-table-column prop="updatetime" label="最后更新时间"></el-table-column>

    <el-table-column label="操作" width="500">
    <template v-slot="scope">
    <el-button type="primary" @click.native="handleAdd(scope.row)" v-if="!scope.row.pid">添加二级分类</el-button>
    <el-button type="primary" @click.native="$router.push('/editCategory?id=' + scope.row.id)">编辑</el-button>
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
<el-dialog title="添加二级分类" :visible.sync="dialogFormVisible" width="30%">
  <el-form :model="form" label-width="100px" ref="ruleForm" :rules="rules">
    <el-form-item label="分类名称" prop="name">
      <el-input v-model="form.name" autocomplete="off" ></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="form.remark" autocomplete="off" ></el-input>
    </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
    </div>
    </el-dialog>
</div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie'
export default {
  name: 'Category',
  data() {
    return {
      tableData: [],
      dialogFormVisible : false,
      total: 0,
      form: {},
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
        pid: null,
      },
    
    category: Cookies.get('category') ? JSON.parse(Cookies.get('category')) : {},
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
      request.get(  '/category/page', {
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
        request.delete( '/category/delete/'+id).then(res => {
            if(res.code === '200'){
                this.$notify.success('删除成功')
                this.load()
            }else{
                this.$notify.error(res.msg)
            }
        })

    },
    handleAdd(row) {
      this.form.pid = row.id
      console.log(row.id)
      this.dialogFormVisible = true
    },

     save(){
            this.$refs['ruleForm'].validate((valid)=>{
            if (valid) {
                //console.log(this.form.pid)
                request.post('/category/save' ,this.form).then(res => {
                    if(res.code === '200'){
                        this.$notify.success('新增二级分类成功')
                        this.$refs['ruleForm'].resetFields();
                        this.dialogFormVisible=false
                        this.load()
                    } else{
                        this.$notify.error(res.msg)
                        this.dialogFormVisible=false
                    }
                })
            } else {
            console.log('error submit!!');
                return false;
            }
            })
            
        }
    
  }
}

</script>

<style scoped>

</style>