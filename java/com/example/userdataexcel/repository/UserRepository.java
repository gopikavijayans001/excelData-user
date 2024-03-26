package com.example.userdataexcel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.userdataexcel.entity.User;

public interface UserRepository extends MongoRepository<User,String>{

}
