package com.example.a2311.services;

import com.example.a2311.dao.MongoDbDao;
import com.example.a2311.entity.Hobbie;
import com.example.a2311.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoDbService {
    @Autowired
    private MongoDbDao mongoDbDao;
    public void save() {
        Student s=new Student();
        s.setName("wangwu");
        s.setAge(17);
        s.setSex(1);
        s.setHeight(182);
        Hobbie h=new Hobbie();
        h.setHname("swing");
        s.setHobbies(h);
        mongoDbDao.save(s);
    }

    public void saveBooks(String name) {

        System.out.println(111);

    }
    public void saveSir(String name) {

        System.out.println(111);

    }

    public void saveStudent(String name,int age, int sex, int height, String hobby) {
        Student s=new Student();
        s.setName(name);
        s.setAge(age);
        s.setSex(sex);
        s.setHeight(height);
        Hobbie h=new Hobbie();
        h.setHname(hobby);
        s.setHobbies(h);
        mongoDbDao.save(s);
    }
    public Student get() {
        //and查询
        /* Criteria criteriaName=Criteria.where("name").is("lisi");
        Criteria criteriaAage=Criteria.where("age").is(17);
        Criteria andCriteria = new Criteria();
        andCriteria.andOperator(criteriaName,criteriaAage);
        Query query=new Query(andCriteria);*/
        //or查询,注意age采用的是gt, 既大于16，大于等于采用gte,小于采用lt,小于等于采用lte
        Criteria criteriaName=Criteria.where("name").is("lisi");
        Criteria criteriaAage=Criteria.where("age").gt(16);
        Criteria orCriteria = new Criteria();
        orCriteria.orOperator(criteriaName,criteriaAage);


        Query query=new Query(orCriteria);

        return  mongoDbDao.get(query);
    }

    public Student getStudentByName(String param) {
        Criteria criteriaName=Criteria.where("name").is(param);
        Criteria orCriteria = new Criteria();
        orCriteria.orOperator(criteriaName);
        Query query=new Query(orCriteria);
        return  mongoDbDao.get(query);
    }

    public List<Student> findAll() {
        return mongoDbDao.findAll();
    }
    public void update() {
        Query query=new Query(Criteria.where("name").is("zhangsan"));
        Update update=new Update();
        update.set("age",30);
        update.set("height",188);
        update.set("hobbies.hname","basketball");
        mongoDbDao.update(query,update);
    }
    public void delete() {
        Query query=new Query(Criteria.where("name").is("zhangsan"));
        mongoDbDao.delete(query);
    }
}