package com.aonufrei.therapist_service.security;

import com.aonufrei.dto.AccountOutDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Setter
@Getter
public class AccountDetails extends User {

	private AccountOutDto account;

	public AccountDetails(AccountOutDto account) {
		super(account.getEmail(), "", List.of((GrantedAuthority) account.getRole()::name));
		this.account = account;
	}

}
