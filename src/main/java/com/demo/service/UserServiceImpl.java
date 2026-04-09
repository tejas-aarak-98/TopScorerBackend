package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.entity.User;
import com.demo.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;

	   @Autowired
	    private PasswordEncoder passwordEncoder;
	
	public User saveUser(User user) {
	
		 user.setPassword(passwordEncoder.encode(user.getPassword()));
		 user.setRole("ROLE_" + user.getRole());

		return userRepo.save(user);
	}

	
	public String loginUser(String email, String password) {
		
		  User user = userRepo.findByEmail(email);
		
		  return (user != null && passwordEncoder.matches(password, user.getPassword()))
		            ? "Login Successful"
		            : "Invalid Email or Password";
	}
	
}
