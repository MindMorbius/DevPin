package com.example.a2311.entity;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("student")
public class Student {
    private String id;
    private String name;
    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Hobbie getHobbies() {
        return hobbies;
    }

    public void setHobbies(Hobbie hobbies) {
        this.hobbies = hobbies;
    }

    private Integer sex;
    private Integer height;
    private Hobbie hobbies;
}
