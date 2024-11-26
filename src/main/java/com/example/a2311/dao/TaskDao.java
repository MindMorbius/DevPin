package com.example.a2311.dao;

import com.example.a2311.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TaskDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Task task) {
        mongoTemplate.save(task);
    }

    public Task get(Query query) {
        return mongoTemplate.findOne(query, Task.class);
    }

    // 实现 findOne 方法
    public Task findOne(String id, String username) {
        // 创建查询条件：根据 id 和 username 查询
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("username").is(username));

        // 执行查询，返回匹配的第一个 Task 文档
        return mongoTemplate.findOne(query, Task.class);
    }

    public List<Task> findMeAll(String username) {
        // 创建查询条件：根据 id 和 username 查询
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        // 执行查询，返回匹配的第一个 Task 文档
        return mongoTemplate.find(query, Task.class);
    }

    public List<Task> findNotMePubAll(String username) {
        // 创建查询条件：根据 id 和 username 查询
        Query query = new Query();
        query.addCriteria(Criteria.where("pub").is(true).and("username").ne(username));

        // 执行查询，返回匹配的第一个 Task 文档
        return mongoTemplate.find(query, Task.class);
    }


    public List<Task> findAll() {
        // 创建查询条件：根据 id 和 username 查询
        Query query = new Query();
        query.addCriteria(Criteria.where("pub").is(true));

        // 执行查询，返回匹配的第一个 Task 文档
        return mongoTemplate.find(query, Task.class);
    }

    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, Task.class);
    }

    public void delete(Query query) {
        mongoTemplate.remove(query, Task.class);
    }

}
