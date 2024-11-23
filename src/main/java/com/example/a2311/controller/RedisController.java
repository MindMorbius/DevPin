package com.example.a2311.controller;

import com.example.a2311.entity.Student;
import com.example.a2311.services.MongoDbService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class RedisController {
    @Autowired
    private MongoDbService mongoDbService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // string 数据类型演示
    // 添加数据到redis
    @PostMapping("/redis/addstring")
    public String addToRedis(String name, String value) {

        // 操作Redis中的string类型的数据,先获取ValueOperation
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

        // 添加数据到redis
        valueOperations.set(name, value);
        return "向redis添加string类型的数据";
    }


    @GetMapping("/redis/getk")
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Object v = valueOperations.get(key);
        return "key是" + key + ",它的值是:" + v;
    }

    // 从redis获取数据
    @GetMapping("/redis/getstudent")
    public String getStudent(String key) throws JsonProcessingException {
        HashOperations hashOperations = stringRedisTemplate.opsForHash();
        Object v = hashOperations.entries(key);
        // redis中没有数据, 从mongoDb中获取
        if (((Map<?, ?>) v).isEmpty()) {
            {
                Student stu = mongoDbService.getStudentByName(key);
                ObjectMapper mapper = new ObjectMapper();
                v = mapper.writeValueAsString(stu);

                // v 不为空的情况下，写入redis
                if (stu != null) {

                    hashOperations.put(key, "name",stu.getName());
                    hashOperations.put(key, "id",stu.getId());
                    hashOperations.put(key, "age",Integer.toString(stu.getAge()));
                    hashOperations.put(key, "sex",Integer.toString(stu.getSex()));
                } else {
                    return "没有这个学生" + key ;
                }
            }
        } else {
            return "key是从redis中获取的，key是" + key + ",它的值是:" + v;
        }
        return "key是从mongodb中获取的，key是" + key + ",它的值是:" + v;
    }

    // 往list中添加数据
    @PostMapping("/redis/addlist")
    public String addListData(String key , String value) {
        ListOperations listOperations = stringRedisTemplate.opsForList();
        listOperations.leftPush(key, value);
        return "添加数据成功key是:"+key+",value是:"+value;

    }

    // 从list中获取数据
    @GetMapping("/redis/getlist")
    public String getListData(String key, int start , int end) {
        ListOperations listOperations = stringRedisTemplate.opsForList();
        Object value = listOperations.range(key,start,end);
        return "list:" + key +",从" +start + "到"+ end + ",它的值是:" + value;
    }

}
