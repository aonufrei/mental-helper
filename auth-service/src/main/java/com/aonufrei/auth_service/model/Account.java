package com.aonufrei.auth_service.model;

import com.aonufrei.enums.AccountRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

	@Id
	private String id;

	private AccountRole role;

	@Column(unique = true)
	private String email;

	private String password;

}
