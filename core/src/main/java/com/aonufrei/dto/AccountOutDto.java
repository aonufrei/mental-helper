package com.aonufrei.dto;

import com.aonufrei.enums.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountOutDto {

	private String id;

	private AccountRole role;

	private String email;

}
