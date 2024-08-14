package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {

	@NotBlank
	@JsonProperty("account_id")
	private String accountId;

	@NotBlank
	private String password;

	@JsonProperty("reply_password")
	@NotBlank
	private String replyPassword;

}
