package com.aonufrei.dto;

import com.aonufrei.enums.AccountRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInDto {

	@NotBlank
	private AccountRole role;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

}
