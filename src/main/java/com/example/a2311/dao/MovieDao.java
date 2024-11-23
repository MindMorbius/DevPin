package com.example.a2311.dao;

import com.example.a2311.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Movie movie) {
        mongoTemplate.save(movie);
    }

    public Movie get(Query query) {
        return mongoTemplate.findOne(query, Movie.class);
    }

    public List<Movie> findAll() {
        return mongoTemplate.findAll(Movie.class);
    }

    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, Movie.class);
    }

    public void delete(Query query) {
        mongoTemplate.remove(query, Movie.class);
    }


}
