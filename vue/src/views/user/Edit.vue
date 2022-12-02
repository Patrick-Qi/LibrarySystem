<template>
    <div style="width : 70%">
    <h2 style="text-align: center;">编辑用户</h2>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm"  label-width="120px" >
    <el-form-item label="会员卡号">
    <el-input v-model="form.username" disabled></el-input>
    </el-form-item>
    <el-form-item label="姓名" prop ="name">
    <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
    </el-form-item>
    <el-form-item label="年龄">
    <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
    </el-form-item>
    <el-form-item label="性别">
    
    <el-radio v-model="form.sex" label="男">男</el-radio>
    <el-radio v-model="form.sex" label="女">女</el-radio>
    <el-radio v-model="form.sex" label="未知">未知</el-radio>
    </el-form-item>
    <el-form-item label="联系方式">
    <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
    </el-form-item>
    <el-form-item label="地址">
    <el-input v-model="form.address" placeholder="请输入地址"></el-input>
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
    name: 'EditUser',
    data() {
        return {
            form: {},
            rules: {
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
            }
        }
    },
    created() {
        const id = this.$route.query.id
        request.get("/user/" + id).then(res => {
            this.form = res.data
        })
    },
    methods: {
        save(){
            request.put('/user/update' ,this.form).then(res => {
                if(res.code === '200'){
                    this.$notify.success('更新成功')
                    this.$router.push("/user")
                } else{
                    this.$notify.error(res.msg)
                }
            })
        }
    }
}

</script>