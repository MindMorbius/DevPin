package com.example.a2311.controller;

import com.example.a2311.entity.Student;
import com.example.a2311.services.MongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 参考https://blog.csdn.net/u014135369/article/details/112357997

@RestController
@RequestMapping("/mongodb")
public class MongoDbController {
    @Autowired
    private MongoDbService mongoDbService;
    @RequestMapping("/save")
    public void save(){
        mongoDbService.save();
    }

    // string 数据类型演示
    // 添加数据到mongodb
    @PostMapping("/saveStudent")
        public String saveStudent(String name,int age, int sex, int height, String hobby) {
        mongoDbService.saveStudent(name,age, sex,height,hobby);
        return "save成功";
    }

    @RequestMapping("/get")
    public Student get(){
        return mongoDbService.get();
    }
    @RequestMapping("/findAll")
    public List<Student> findAll(){
        return mongoDbService.findAll();
    }

    @RequestMapping("/update")
    public void update(){
        mongoDbService.update();
    }
    @RequestMapping("/delete")
    public void delete(){
        mongoDbService.delete();
    }
}