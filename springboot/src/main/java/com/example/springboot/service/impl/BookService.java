package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.mapper.BookMapper;

import com.example.springboot.service.IBookService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j

public class BookService implements IBookService {
    @Resource
    BookMapper bookMapper;
    
    
    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    @Override
    public PageInfo<Book> page(BaseRequest baseRequest){
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Book> books = bookMapper.listByCondition(baseRequest);
        return new PageInfo<>(books);
    }

    @Override
    public void save(Book obj) {
        bookMapper.save(obj);
    }

    @Override
    public Book getById(Integer id) {
        return bookMapper.getById(id);
    }

    @Override
    public void updateById(Book obj) {
        obj.setUpdatetime(LocalDate.now());
        bookMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }
}
