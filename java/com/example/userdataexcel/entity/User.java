package com.example.userdataexcel.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Document
@Data
public class User {

	@Id
	private String id;
	private String userName;
	private String email;
	private String password;
	private long mobileNo;
	private String role;
	
}
