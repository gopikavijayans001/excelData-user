package com.example.userdataexcel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.userdataexcel.dto.ResponseStructure;
import com.example.userdataexcel.entity.User;
import com.example.userdataexcel.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/users")
	public ResponseEntity<ResponseStructure<List<User>>> userSignup(@RequestParam("file") MultipartFile file) {
		if (userService.hasCsvFormat(file)) {

			return userService.saveUser(file);
		}
		return new ResponseEntity<ResponseStructure<List<User>>>(HttpStatus.BAD_REQUEST);
	}
}
