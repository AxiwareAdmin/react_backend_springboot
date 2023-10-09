package com.accurate.erp.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.accurate.erp.filter.CustomValidationFilter;
import com.accurate.erp.security.service.CustomUserDetailsService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
public class CustomWebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	CustomValidationFilter validationFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
//		http.csrf().disable().authorizeRequests()
		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers("/login").permitAll()
		.antMatchers("/validate").permitAll()
		.anyRequest().authenticated().and().
		exceptionHandling().and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(validationFilter, UsernamePasswordAuthenticationFilter.class);
//		super.configure(http);
	}

	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration=new CorsConfiguration();
		
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("GET","POST"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of("Authorization","Cache-Control","Content-Type"));
		
		final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
		
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
		
		
	}

	@Bean
	public PasswordEncoder getPassEncoder() {
		return NoOpPasswordEncoder.getInstance(); 
	}
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	/*
	 * @Bean public InMemoryUserDetailsManager userDetailsService() { UserDetails
	 * user1 = User.withDefaultPasswordEncoder() .username("nadim")
	 * .password("nadim") .roles("USER") .build(); UserDetails user2 =
	 * User.withDefaultPasswordEncoder() .username("sadim") .password("sadim")
	 * .roles("ADMIN") .build(); return new InMemoryUserDetailsManager(user1,user2);
	 * }
	 * 
	 * 
	 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
	 * Exception {
	 * 
	 * http .authorizeHttpRequests() .requestMatchers("/customers").hasRole("ADMIN")
	 * .requestMatchers("/**").hasAnyRole("ADMIN","USER") .and() .formLogin();
	 * 
	 * 
	 * //below configuration is when we don't want authorization
	 * 
	 * http.authorizeHttpRequests() .anyRequest().authenticated() .and()
	 * .formLogin();
	 * 
	 * return http.build(); }
	 */

}