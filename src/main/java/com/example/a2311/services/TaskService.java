package com.example.a2311.services;


import com.alibaba.fastjson.JSONObject;
import com.example.a2311.dao.TaskDao;
import com.example.a2311.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    public String add(String problem, String tasks, String time, String username) {

        Task task = new Task();
        task.setUsername(username);
        task.setTasks(tasks);
        task.setTime(time);
        task.setProblem(problem);
        task.setPub(false);

        taskDao.save(task);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "分享成功");
        return jsonObject.toJSONString();
    }

    public String delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        taskDao.delete(query);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "删除成功");
        return jsonObject.toJSONString();
    }


    public List<Task> getPubAll() {
        return taskDao.findAll();
    }

    public List<Task> getMeAndPubAll(String username) {

        List<Task> meAll = taskDao.findMeAll(username);
        List<Task> notMePubAll = taskDao.findNotMePubAll(username);
        List<Task> res = new ArrayList<>();
        res.addAll(meAll);
        res.addAll(notMePubAll);
        return res;
    }

    public String updatePub(String id, String username, boolean pub) {
        // 构建查询条件：查找 ID 为 taskId 的任务
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

        // 查询数据库，获取任务信息
        Task task = taskDao.findOne(id, username);

        // 如果任务不存在
        if (task == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "任务不存在");
            return jsonObject.toJSONString();
        }

        // 构建更新内容
        Update update = new Update();
        update.set("pub", pub);  // 仅更新 pub 字段

        // 执行更新操作
        taskDao.update(query, update);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "更新成功");
        return jsonObject.toJSONString();
    }


    public String update(String id, String problem, String tasks, String time, String username) {

        // 构建查询条件：查找 ID 为 taskId 且 username 匹配的任务
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("username").is(username));

        // 查询数据库，获取任务信息
        Task task = taskDao.findOne(id, username);

        // 如果任务不存在或用户名不匹配
        if (task == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "任务不存在或用户名不匹配");
            return jsonObject.toJSONString();
        }

        // 构建更新内容
        Update update = new Update();
        update.set("problem", problem);
        update.set("tasks", tasks);
        update.set("time", time);

        // 执行更新操作
        taskDao.update(query, update);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "更新成功");
        return jsonObject.toJSONString();
    }
}
