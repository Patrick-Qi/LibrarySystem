package com.example.springboot.mapper;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

//    @Select("select * from user")
    List<Admin> list();
    List<Admin> listByCondition(BaseRequest baseRequest);
    void save(Admin admin);
    Admin getById(Integer id);
    void updateById(Admin admin);
    void deleteById(Integer id);

    Admin getByUsernameAdnPassword(LoginRequest request);
}
