package com.project.BlogApplication.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter{
	 @Autowired
	private UserDetailsService userDetailsService;
	 
	 @Autowired
	 private JwtTokenHelper jwtTokenHelper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {	
		
//      try {
//      Thread.sleep(500);
//  } catch (InterruptedException e) {
//      throw new RuntimeException(e);
//  }
  //Authorization

  String requestHeader = request.getHeader("Authorization");
  //Bearer 2352345235sdfrsfgsdfsdf
//logger.info(" Header :  {}", requestHeader);
  String username = null;
  String token = null;
  if (requestHeader != null && requestHeader.startsWith("Bearer")) {
      //looking good
      token = requestHeader.substring(7);
      try {

          username = this.jwtTokenHelper.getUsernameFromToken(token);

      } catch (IllegalArgumentException e) {
          System.out.println("Unable to get iwt token ");
      } catch (ExpiredJwtException e) {
         System.out.println("Jwt token has Expierd");
      } catch (MalformedJwtException e) {
          System.out.println("Invalid jwt");
      } 

  } else {
     System.out.println("Invalid Header Value !! ");
  }


  //
  if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


      //fetch user detail from username
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      Boolean validateToken = this.jwtTokenHelper.validateToken(token, userDetails);
      if (validateToken) {

          //set the authentication
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);


      } else {
         System.out.println("Invalid jwt token");
      }


  }

  filterChain.doFilter(request, response);


}
		
	}


