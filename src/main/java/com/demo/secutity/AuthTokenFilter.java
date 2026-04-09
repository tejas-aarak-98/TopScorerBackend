package com.demo.secutity;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter{

	@Autowired
	private UtilsJwt utilsJwt;
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	
	try {
			
			System.out.println("Auth Token Called");
			
			String jwt = getJwtToken(request);
			if(jwt != null && utilsJwt.validateJwt(jwt)  && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				String userName = utilsJwt.getUserNameFromToken(jwt);
				Claims claims = utilsJwt.getAllClaims(jwt);
				List<String> roles = claims.get("roles",List.class);
				List<GrantedAuthority> authorities = roles.stream().map(role -> (GrantedAuthority)new SimpleGrantedAuthority(role)).toList();
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, null, authorities);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
			
		} catch (Exception e) {
			
			System.out.println("Token is null");
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	private String getJwtToken(HttpServletRequest request) {
		
		String jwt = utilsJwt.getJwtFromHeader(request);
		return jwt;
		
	}

}
