package com.demo.service;

import com.demo.entity.User;

public interface UserService {

	User saveUser(User user);

	String loginUser(String email, String password);
}
