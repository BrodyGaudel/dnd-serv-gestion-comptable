package com.dndserv.brody.gestioncomptable.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig  {

	private static final String URL_REGISTRATION = "/api/v1/registration/**";
	private static final String URL_SALE = "/api/v1/sale/**";
	private static final String URL_PURCHASE = "/api/v1/purchase/**";
	private static final String URL_ENTERPRISE = "/api/v1/enterprise/**";
	private static final String URL_SIGN_UP = "/sendSmsToAuthenticate/**";
	private static final String URL_VALIDATION_PHONE = "/api/v1/user/signUp/**";
	private static final String URL_USER = "/api/v1/user/secure/**";
	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";

 	private final AuthenticationManager authMgr;

	public SecurityConfig(AuthenticationManager authMgr) {
		this.authMgr = authMgr;
	}


	@Bean
	public AuthenticationManager authManager(HttpSecurity http, 
			BCryptPasswordEncoder bCryptPasswordEncoder, 
			UserDetailsService userDetailsService) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(userDetailsService)
	      .passwordEncoder(bCryptPasswordEncoder)
	      .and()
	      .build();
	}
	
	
	 @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 
		  http.csrf().disable();
		  http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		  http.authorizeHttpRequests()
				  .requestMatchers(HttpMethod.POST, URL_REGISTRATION).permitAll()
				  .requestMatchers(HttpMethod.GET, URL_SIGN_UP).permitAll()
				  .requestMatchers(HttpMethod.GET, URL_VALIDATION_PHONE).permitAll()
				  .requestMatchers("/swagger-ui/**", "/javainuse-openapi/**").permitAll()


				  .requestMatchers(HttpMethod.GET, URL_USER).hasAnyAuthority(ADMIN)
				  .requestMatchers(HttpMethod.POST, URL_USER).hasAnyAuthority(ADMIN)
				  .requestMatchers(HttpMethod.PUT, URL_USER).hasAnyAuthority(ADMIN)
				  .requestMatchers(HttpMethod.DELETE, URL_USER).hasAnyAuthority(ADMIN)


				  .requestMatchers(HttpMethod.GET, URL_ENTERPRISE).hasAnyAuthority(ADMIN)
				  .requestMatchers(HttpMethod.POST, URL_ENTERPRISE).hasAnyAuthority(ADMIN)
				  .requestMatchers(HttpMethod.PUT, URL_ENTERPRISE).hasAnyAuthority(ADMIN)
				  .requestMatchers(HttpMethod.DELETE, URL_ENTERPRISE).hasAnyAuthority(ADMIN)

				  .requestMatchers(HttpMethod.GET, URL_SALE).hasAnyAuthority(ADMIN, USER)
				  .requestMatchers(HttpMethod.POST, URL_SALE).hasAnyAuthority(ADMIN, USER)
				  .requestMatchers(HttpMethod.PUT, URL_SALE).hasAnyAuthority(ADMIN, USER)
				  .requestMatchers(HttpMethod.DELETE, URL_SALE).hasAnyAuthority(ADMIN, USER)

				  .requestMatchers(HttpMethod.GET, URL_PURCHASE).hasAnyAuthority(ADMIN, USER)
				  .requestMatchers(HttpMethod.POST, URL_PURCHASE).hasAnyAuthority(ADMIN, USER)
				  .requestMatchers(HttpMethod.PUT, URL_PURCHASE).hasAnyAuthority(ADMIN, USER)
				  .requestMatchers(HttpMethod.DELETE, URL_PURCHASE).hasAnyAuthority(ADMIN, USER)
				  .anyRequest().authenticated();

		  http.addFilter(new  JWTAuthenticationFilter (authMgr)) ;
		  http.addFilterBefore(new JWTAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
		 		
		  return http.build();
   
     }


}
