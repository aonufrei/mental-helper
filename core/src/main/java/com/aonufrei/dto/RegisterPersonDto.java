package com.aonufrei.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPersonDto<T> {

	@NotNull
	private RegisterDto account;

	@NotNull
	private T person;

}
