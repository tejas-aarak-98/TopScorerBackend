package com.demo.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.LoginDto;
import com.demo.entity.User;
import com.demo.secutity.UtilsJwt;
import com.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/topscorer")
public class UserController {
   
	@Autowired
	private UserService  userSer;
	
	@Autowired
	private UtilsJwt utilsJwt;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody User user){

	    userSer.saveUser(user);

	    Map<String,String> response = new HashMap<>();
	    response.put("message","User saved successfully");

	    return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
		
		Authentication authentication;
		System.out.println("Login API Hit");

//		 String response = userSer.loginUser(loginDto.getEmail(), loginDto.getPassword());
		 
		 try {
			 
			 authentication = authenticationManager.authenticate(
					 
					 new UsernamePasswordAuthenticationToken(
							 
							 loginDto.getEmail(),
							 loginDto.getPassword()
							 
							 )
					 
					 );
			
		} catch (Exception e) {
			Map<String, String> error = new HashMap<>();
			error.put("error_msg", "Invalid Credentials");

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}
		 
		    SecurityContextHolder.getContext().setAuthentication(authentication);
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			
			String jwtToken = utilsJwt.getTokenFromStorage(userDetails);
		 
		    Map<String, String> response = new HashMap<>();
		    response.put("jwt_token", jwtToken);

		    return ResponseEntity.ok(response);
		

		
	}
	
	
    @GetMapping("/dashboard")
    public String adminDashboard() {
    	  System.out.println("Dashboard Hit");
        return "Welcome Admin";
    }
}


