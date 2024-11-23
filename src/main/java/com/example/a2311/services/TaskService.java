package com.example.a2311.services;


import com.alibaba.fastjson.JSONObject;
import com.example.a2311.dao.TaskDao;
import com.example.a2311.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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


    public List<Task> getAll() {
        return taskDao.findAll();
    }


    public String update(String id, String problem, String tasks, String time, String username) {

        // 构建查询条件：查找 ID 为 taskId 的任务
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));

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
