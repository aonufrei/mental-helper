package com.aonufrei.dto;

import com.aonufrei.enums.AccountRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginOutDto {

	private String token;

	@JsonProperty("account_id")
	private String accountId;

	private AccountRole role;

}
