package com.demo.secutity;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;


@Component
public class UtilsJwt {

	
    private String jwtKey = "YS1zdHJpbmctc2VjcmV0LWF0LWxlYXN0LTI1Ni1iaXRzLWxvbmc=";
    private int expiration = 172800000 ;
    
	
	public String getJwtFromHeader(HttpServletRequest request) {
		
		String jwt = request.getHeader("Authorization");
		if(jwt == null || !jwt.startsWith("Bearer ")) return null;
		return jwt.substring(7);
		
	}
    
    public String getTokenFromStorage(UserDetails userDetails) {
    	

        return  Jwts.builder()
        		.subject(userDetails.getUsername())
        		.claim("roles", userDetails.getAuthorities().stream().map(e -> e.getAuthority()).toList())
        		.issuedAt(new Date())
        		.expiration(new Date(new Date().getTime() + expiration))
        		.signWith(key())
        		.compact();
    }

	public Key key() {
		
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
	}
	


public boolean validateJwt(String jwt) {
		
		try {
			Jwts.parser().verifyWith((SecretKey)key()).build().parseSignedClaims(jwt);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public String getUserNameFromToken(String jwt) {
		
		return Jwts.parser().verifyWith((SecretKey)key()).build().parseSignedClaims(jwt).getPayload().getSubject();
		
	}
	
	public Claims getAllClaims(String jwt) {
		
		return Jwts.parser().verifyWith((SecretKey)key()).build().parseSignedClaims(jwt).getPayload();
		
	}
	
}
