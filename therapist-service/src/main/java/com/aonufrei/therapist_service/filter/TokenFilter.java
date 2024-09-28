package com.aonufrei.therapist_service.filter;

import com.aonufrei.enums.AccountRole;
import com.aonufrei.therapist_service.security.AccountDetails;
import com.aonufrei.therapist_service.security.AuthUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

	private final AuthUtils authUtils;

	public TokenFilter(AuthUtils authUtils) {
		this.authUtils = authUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		var authHeader = req.getHeader("Authorization");
		String TOKEN_PREFIX = "Bearer ";
		if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
			final String token = authHeader.substring(TOKEN_PREFIX.length());
			var details = authUtils.defineAccount(token);
			if (details.getRole() != AccountRole.THERAPIST) {
				throw new AccessDeniedException("Access Denied");
			}
			var accountDetails = new AccountDetails(details);
			var authenticationToken = new UsernamePasswordAuthenticationToken(
					accountDetails, null, accountDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		chain.doFilter(req, res);
	}
}
