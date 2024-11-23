package com.example.a2311.controller;

import com.example.a2311.services.MongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 参考https://blog.csdn.net/u014135369/article/details/112357997

@RestController
@RequestMapping("/mongodb2")
public class MongoDbController2 {
    @Autowired
    private MongoDbService mongoDbService;


    // string 数据类型演示
    // 添加数据到mongodb
    //增：
    @PostMapping("/saveBook")
    public String saveBook(String b_name) {
        mongoDbService.saveBooks(b_name);
        return "save成功";
    }
//    //删：
//    @RequestMapping("/deleteBooks")
//    public void delete(){
//        mongoDbService.deleteBooks();
//    }
//
//    //改：
//    @RequestMapping("/updateBooks")
//    public void update(){
//        mongoDbService.updateBooks();
//    }
//
//    //查（条件查询）：
//    @RequestMapping("/getBooks")
//    public books getBooks(){return mongoDbService.getBooks();}
//
//    //查询全部：
//    @RequestMapping("/findAllBooks")
//    public List<books> findAll(){
//        return mongoDbService.findAllBooks();
//    }

}