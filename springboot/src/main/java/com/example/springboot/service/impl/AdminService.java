package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.service.IAdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {
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
        Date date=new Date();
        //obj.setUsername(DateUtil.format(date,  "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
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
        LoginDTO loginDTO = null;
        try{
            Admin admin=adminMapper.getByUsernameAdnPassword(request);
            loginDTO = new LoginDTO();
            BeanUtils.copyProperties(admin, loginDTO);
            return loginDTO;
        }catch (Exception e)
        {
            log.error("登录出现异常",e);
            return null;
        }

    }
}
