<template>
    <div style="width : 70%">
    <h2 style="text-align: center;">新增管理员</h2>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="120px" >
    <el-form-item label="用户名" prop="username">
    <el-input v-model="form.username" placeholder="请输入姓名"></el-input>
    </el-form-item>
    <el-form-item label="联系方式" prop="phone">
    <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
    <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
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
    name: 'EditAdmin',
    data() {
        return {
            form: {},
            rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
            }
        }
    },
    created() {
        const id = this.$route.query.id
        request.get("/admin/" + id).then(res => {
            this.form = res.data
        })
    },
    methods: {
        save(){
            request.put('/admin/update' ,this.form).then(res => {
                if(res.code === '200'){
                    this.$notify.success('更新成功')
                    this.$router.push("/admin")
                } else{
                    this.$notify.error(res.msg)
                }
            })
        }
    }
}

</script>