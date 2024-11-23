package com.example.a2311.services;

import com.alibaba.fastjson.JSONObject;
import com.example.a2311.dao.UserDao;
import com.example.a2311.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String register(String username, String password, String email) {

        Criteria criteriaName = Criteria.where("username").is(username);
        Criteria orCriteria = new Criteria();
        orCriteria.orOperator(criteriaName);
        Query query = new Query(orCriteria);
        User u = userDao.get(query);

        if (u != null) {
            return "该用户已被注册";
        } else {

            try {

                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] byteDigest = md.digest();

                userDao.save(new User(username, byteToStr(byteDigest), email, "普通用户"));
                return "注册成功";

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

        }

    }


    public String login(String username, String password) {
        Criteria criteriaName = Criteria.where("username").is(username);
        Criteria orCriteria = new Criteria();
        orCriteria.orOperator(criteriaName);
        Query query = new Query(orCriteria);
        User u = userDao.get(query);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] byteDigest = md.digest();

            if (u != null && u.getPassword().contentEquals(byteToStr(byteDigest))) {

                //生成token保存到redis并返回  ///redis hash
                String token = UUID.randomUUID().toString();
                HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
                hashOps.put("login:", username, token);

                ///redis 过期时间
                int expireTime = 30 * 60;
                stringRedisTemplate.expire("login:" + username, expireTime, TimeUnit.SECONDS);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result", "登录成功");
                jsonObject.put("token", token);

                return jsonObject.toJSONString();
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result", "登录失败");
                return jsonObject.toJSONString();
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String logout(String username) {

        // 清除 Redis 中的 token
        HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
        long num = hashOps.delete("login:", username);

        JSONObject jsonObject = new JSONObject();
        if (num > 0) {
            jsonObject.put("result", "登出成功");
        } else {
            jsonObject.put("result", "登出失败");
        }
        return jsonObject.toJSONString();

    }

    private String byteToStr(byte[] byteDigest) {
        int i;
        StringBuffer buf = new StringBuffer("");
        //遍历byteDigest
        //加密逻辑，可以debug自行了解一下加密逻辑
        for (int offset = 0; offset < byteDigest.length; offset++) {
            i = byteDigest[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            buf.append(Integer.toHexString(i));
        }
        return new String(buf);
    }

}


