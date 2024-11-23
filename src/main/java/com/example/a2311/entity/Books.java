package com.example.a2311.entity;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("book")
public class Books {
    private String id;
    private String name;
    private Integer age;
    private Integer sex;
    private Integer height;
    private Hobbie hobbies;
}
