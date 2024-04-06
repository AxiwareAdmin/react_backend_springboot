package com.accurate.erp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.accurate.erp.helper.JwtUtil;
import com.accurate.erp.model.invoice.UserDO;
import com.accurate.erp.security.service.CustomUserDetailsService;

@Component
public class CustomValidationFilter extends OncePerRequestFilter{

	@Autowired
	JwtUtil jwtUtil;
	
    private static final ThreadLocal<String> REGISTER_ID_THREAD_LOCAL = new ThreadLocal<>();

	
	@Autowired
	CustomUserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		   response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
	        response.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type");
	        response.setHeader("Access-Control-Max-Age", "3600");
		// TODO Auto-generated method stub
		String authHeader=request.getHeader("Authorization");
		
		String username=null;
		String jwt=null;
		
		try {
		
		 /*if (request.getRequestURI().equals("/login") || request.getRequestURI().equals("/validate")) {
	            // If it's the login route or validate route, skip the filter and continue with the chain
	            chain.doFilter(request, response);
	            return;
	        }*/
		
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			jwt=authHeader.substring(7);
			
			username=jwtUtil.extractUsername(jwt);
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
            	
            	UserDO userDO=(UserDO)userDetails;
            	
            	REGISTER_ID_THREAD_LOCAL.set(userDO.getRegisterId());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else {
                // If authentication fails, send an error response
                response.setStatus(HttpStatus.UNAUTHORIZED.value()); // Set HTTP 401 Unauthorized status
                response.getWriter().write("{\"error\":\"Invalid credentials\"}"); // Response message
                response.getWriter().flush();
                return;
            }
        }
		chain.doFilter(request, response);
		}
		catch(Exception e) {
			System.out.println("exception in CustomValidationFilter::doFilterInternal()::"+e);
			
			if(request.getAttribute("msgFlag")!=null) return;
			 response.setStatus(HttpStatus.UNAUTHORIZED.value()); // Set HTTP 401 Unauthorized status
             response.getWriter().write("{\"error\":\"JWT token is manipulated\"}"); // Response message
             response.getWriter().flush();
             return;
		}
		
		
	}
	
	 public static String getCurrentRegisterId() {
	        // Access the registerId stored in the ThreadLocal variable
	        return REGISTER_ID_THREAD_LOCAL.get();
	    }

}
