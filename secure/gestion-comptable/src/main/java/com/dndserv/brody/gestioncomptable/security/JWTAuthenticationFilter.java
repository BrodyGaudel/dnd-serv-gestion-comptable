package com.dndserv.brody.gestioncomptable.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dndserv.brody.gestioncomptable.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{


	private final AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			User user;
			user = new ObjectMapper().readValue(request.getInputStream(), User.class);
			return authenticationManager.
					authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(),user.getPassword()));
		} catch (Exception e){
			log.info(e.getMessage());
			return null;
		}


	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											FilterChain chain, Authentication authResult) {
		
		org.springframework.security.core.userdetails.User springUser = 
			(org.springframework.security.core.userdetails.User) authResult.getPrincipal();
		
		List<String> roles = new ArrayList<>();
		springUser.getAuthorities().forEach(au-> roles.add(au.getAuthority()));
		
		String jwt = JWT.create()
				.withSubject(springUser.getUsername())
				.withArrayClaim("roles", roles.toArray(new String[0]))
				.withExpiresAt(new Date(System.currentTimeMillis()+SecParams.EXP_TIME))
				.sign(Algorithm.HMAC256(SecParams.SECRET));
		
		response.addHeader("Authorization", jwt);			  
		
	}
	
}
