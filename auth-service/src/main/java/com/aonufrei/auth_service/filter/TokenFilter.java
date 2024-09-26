package com.aonufrei.auth_service.filter;

import com.aonufrei.auth_service.service.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

	private final String TOKEN_PREFIX = "Bearer ";

	private final AccountService accountService;

	public TokenFilter(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		var token = req.getHeader("Authorization");
		if (token != null && token.startsWith(TOKEN_PREFIX)) {
			token = token.substring(TOKEN_PREFIX.length());
			var details = accountService.getSecurityDetails(token);
			var upAuth = new UsernamePasswordAuthenticationToken(
					details.getUsername(),
					details.getPassword(),
					details.getAuthorities()
			);
			upAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
			SecurityContextHolder.getContext().setAuthentication(upAuth);
		}
		chain.doFilter(req, res);
	}
}
