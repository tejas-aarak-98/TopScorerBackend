package com.demo.secutity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.entity.User;
import com.demo.repo.UserRepo;

@Service
public class CustomDetailsService implements UserDetailsService{

	 @Autowired
	    private UserRepo userRepo;

	   
	    public UserDetails loadUserByUsername(String email)
	            throws UsernameNotFoundException {

	        User user = userRepo.findByEmail(email);

	        if (user == null) {
	            throw new UsernameNotFoundException("User not found");
	        }

	        return new org.springframework.security.core.userdetails.User(
	                user.getEmail(),
	                user.getPassword(),
	                List.of(new SimpleGrantedAuthority(user.getRole()))
	        );
	    }
	
}
