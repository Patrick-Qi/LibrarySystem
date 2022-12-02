<template>
    <div style="width : 70%">
    <h2 style="text-align: center;">新增用户</h2>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="120px" >
    <el-form-item label="姓名" prop="name">
    <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
    </el-form-item>
    <el-form-item label="年龄" prop="age">
    <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
    </el-form-item>
    <el-form-item label="性别">
    <el-radio v-model="form.sex" label="男">男</el-radio>
    <el-radio v-model="form.sex" label="女">女</el-radio>
    <el-radio v-model="form.sex" label="未知">未知</el-radio>
    </el-form-item>
    <el-form-item label="联系方式" prop="phone">
    <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="address">
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
    name: 'AddUser',
    data() {
        var checkAge = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('年龄不能为空'));
        }
          if (!/^[0-9]+$/.test(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (value > 120 || value <=0) {
              callback(new Error('请输入合理的年龄！'));
            }
            else callback()
          }
      };

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
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
          ],
          age: [
            { validator: checkAge, trigger: 'blur'}
          ],
          phone: [
            { validator: checkPhone , trigger: 'blur'}
          ],
          address: [
            {}
          ]
            }
        }
    },
    methods: {
        save(){
            this.$refs['ruleForm'].validate((valid)=>{
            if (valid) {
                request.post('/user/save' ,this.form).then(res => {
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