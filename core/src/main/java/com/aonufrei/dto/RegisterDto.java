package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	@JsonProperty("reply_password")
	private String replyPassword;

}
