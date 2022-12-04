package com.example.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.service.IAdminService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {

    private static final String DEFAULT_PASS = "123";
    private static final String PASS_SALT = "patrick";

    @Autowired
    AdminMapper adminMapper;
    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest){
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(admins);
    }

    @Override
    public void save(Admin obj) {

        if(StrUtil.isBlank(obj.getPassword()))
        {
            obj.setPassword(DEFAULT_PASS);
        }

        //obj.setPassword(securePass(obj.getPassword()));  //加密有bug
        // Date date=new Date();
        // obj.setUsername(DateUtil.format(date,  "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        adminMapper.save(obj);
    }
    @Override
    public Admin getById(Integer id){
        return adminMapper.getById(id);
    }

    @Override
    public void updateById(Admin obj) {
        obj.setUpdatetime(new Date());
        adminMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest request) {

        //request.setPassword(securePass(request.getPassword()));  //存在bug
        Admin admin=adminMapper.getByUsernameAdnPassword(request.getUsername(),request.getPassword());
        if(admin == null){
            throw new ServiceException("用户名或密码错误");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin, loginDTO);
        //生成token并返回
        String token=TokenUtils.genToken(String.valueOf(admin.getId()),admin.getPassword());
        loginDTO.setToken(token);
        return loginDTO;

    }

    @Override
    public void changePass(PasswordRequest request) {

        //request.setNewPass()=securePass(request.getPassword());
        int count=adminMapper.updatePassword(request);
        if(count<=0){
            throw new ServiceException("用户名或密码错误");
        }
    }

    private String securePass(String password){
        String pass=password;
        return SecureUtil.md5(pass+PASS_SALT);
    }
}
