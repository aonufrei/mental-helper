package com.aonufrei.auth_service.rest;

import com.aonufrei.auth_service.service.AccountService;
import com.aonufrei.dto.*;
import com.aonufrei.enums.AccountRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/v1/auth")
public class AuthRestController {

	private final Logger log = LoggerFactory.getLogger(AuthRestController.class);
	private final AccountService accountService;

	public AuthRestController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("login")
	public LoginOutDto login(@Validated @RequestBody LoginDto loginDto) {
		return accountService.login(loginDto);
	}

	@PostMapping("register")
	public AccountOutDto register(@RequestParam AccountRole role, @Validated @RequestBody RegisterDto registerDto) {
		return accountService.register(role, registerDto);
	}

	@PutMapping("change-password")
	public ResponseEntity<Boolean> changePassword(@Validated @RequestBody ChangePasswordRequestDto payload) {
		var auth = AccountService.getCurrentAuth();
		if (Objects.equals(payload.getAccountId(), auth.id())) {
			return ResponseEntity.status(404).body(false);
		}
		accountService.changePassword(payload);
		return ResponseEntity.ok(true);
	}

	@DeleteMapping
	public ResponseEntity<Boolean> deleteAccount(@RequestParam String accountId) {
		var auth = AccountService.getCurrentAuth();
		if (Objects.equals(accountId, auth.id())) {
			return ResponseEntity.status(404).body(false);
		}
		accountService.deleteAccount(accountId);
		return ResponseEntity.ok(true);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> validationErrorHandler(RuntimeException e) {
		log.error("An error occurred while processing request", e);
		return ResponseEntity.badRequest().body(e.getMessage());
	}

}
