package com.aonufrei.auth_service.dto;

import com.aonufrei.enums.AccountRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record AccountSecurityDetails(String id, String username, AccountRole role,
                                     String password) implements UserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of((GrantedAuthority) role::name);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
