package com.example.a2311.dao;

import com.example.a2311.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MongoDbDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Student s) {
        mongoTemplate.save(s);
    }

    public Student get(Query query) {
        return mongoTemplate.findOne(query, Student.class);
    }

    public List<Student> findAll() {
        return mongoTemplate.findAll(Student.class);
    }

    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, Student.class);
    }

    public void delete(Query query) {
        mongoTemplate.remove(query, Student.class);
    }
}
