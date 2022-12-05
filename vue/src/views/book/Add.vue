<template>
    <div style="width : 70%">
    <h2 style="text-align: center;">新增图书</h2>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="120px" >
    <el-form-item label="名称" prop="name">
    <el-input v-model="form.name" placeholder="请输入名称"></el-input>
    </el-form-item>
    <el-form-item label="描述" prop="description">
    <el-input style="width:400px" type = "textarea" v-model="form.description" placeholder="请输入图书描述"></el-input>
    </el-form-item>
    <el-form-item label="出版日期" prop="publishDate">
     <el-date-picker
      v-model="form.publishDate"
      type="date"
      value-format="yyyy-MM-dd"
      placeholder="请选择出版日期">
    </el-date-picker>
    </el-form-item>
    <el-form-item label="作者" prop="author">
    <el-input v-model="form.author" placeholder="请输入作者"></el-input>
    </el-form-item>
    <el-form-item label="出版社" prop="publisher">
    <el-input v-model="form.publisher" placeholder="请输入出版社"></el-input>
    </el-form-item>
    <el-form-item label="分类" prop="category">
    <el-cascader
    style="width: 200px"
    :props="{ value: 'name', label: 'name'}"
    v-model="form.category"
    :options="categories"
    @change="handleChange"></el-cascader>
    </el-form-item>
    <el-form-item label="isbn编号" prop="bookIsbn">
    <el-input v-model="form.bookIsbn" placeholder="请输入ISBN编号"></el-input>
    </el-form-item>
    <el-form-item label="封面" prop="cover">
    <el-input v-model="form.cover" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <div style="text-align: center ; margin-top: 30px">
    <el-button type="primary" @click.native="save" size="medium">提交</el-button>
    
    </div>
    </div>
</template>

<script>
import request from "@/utils/request";
export default {
    name: 'AddBook',
    data() {

        return {
            form: {},
            categories: [],
            rules: {
          name: [
            { required: true, message: '请输入图书名称', trigger: 'blur' },
            { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
          ],
          
            }
        }
    },
    created() {
        request.get('/category/tree').then(res =>{
            this.categories = res.data
        })
    },
    methods: {
        save(){
            this.$refs['ruleForm'].validate((valid)=>{
            if (valid) {
                console.log(this.form.name)
                request.post('/book/save' ,this.form).then(res => {
                    if(res.code === '200'){
                        this.$notify.success('新增成功')

                        this.$refs['ruleForm'].resetFields();
                    } else{
                        this.$notify.error(res.msg)
                    }
                })
            } else {
            console.log('error submit!!');
                return false;
            }
            })
            
        },
        handleChange() {

        }
    }
}

</script>