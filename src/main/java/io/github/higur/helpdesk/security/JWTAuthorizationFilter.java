package io.github.higur.helpdesk.security;

import io.github.higur.helpdesk.security.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTUtils jwtUtils;

    private CustomUserDetailsService customUserDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(header.substring(7));
            if(usernamePasswordAuthenticationToken != null){
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token){
        if(jwtUtils.validToken(token)){
            String username = jwtUtils.getUsername(token);
            UserDetails details = customUserDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
        }
        return null;
    }
}
