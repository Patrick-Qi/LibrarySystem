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
    name: 'AddAdmin',
    data() {

        var checkPhone = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('号码不能为空'));
        }
          if (!/^[0-9]+$/.test(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (!/^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$/.test(value)) {
              callback(new Error('请输入正确的手机号码！'));
            }
            else callback()
          }
      };
        return {
            form: {},
            rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          phone: [
            { validator: checkPhone , trigger: 'blur'}
          ],
          
            }
        }
    },
    methods: {
        save(){
            this.$refs['ruleForm'].validate((valid)=>{
            if (valid) {
                request.post('/admin/save' ,this.form).then(res => {
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
            
        }
    }
}

</script>