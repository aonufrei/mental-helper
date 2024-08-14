package com.aonufrei.auth_service.rest;

import com.aonufrei.auth_service.service.AccountService;
import com.aonufrei.dto.AccountOutDto;
import com.aonufrei.dto.ChangePasswordRequestDto;
import com.aonufrei.dto.LoginDto;
import com.aonufrei.dto.RegisterDto;
import com.aonufrei.enums.AccountRole;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthRestController {

	private final AccountService accountService;

	public AuthRestController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("login")
	public String login(@RequestParam AccountRole role, @Validated @RequestBody LoginDto loginDto) {
		return accountService.login(role, loginDto);
	}

	@PostMapping("register")
	public AccountOutDto register(@RequestParam AccountRole role, @Validated @RequestBody RegisterDto registerDto) {
		return accountService.register(role, registerDto);
	}

	@GetMapping("identify")
	public ResponseEntity<AccountOutDto> identifyUser(HttpServletRequest servletRequest) {
		String token = servletRequest.getHeader("Authorization");
		if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		return ResponseEntity.ok(accountService.identifyUser(token));
	}

	@PutMapping("change-password")
	public ResponseEntity<Boolean> changePassword(@Validated @RequestBody ChangePasswordRequestDto payload) {
		accountService.changePassword(payload);
		return ResponseEntity.ok(true);
	}

	@DeleteMapping
	public ResponseEntity<Boolean> deleteAccount(@RequestParam String accountId) {
		accountService.deleteAccount(accountId);
		return ResponseEntity.ok(true);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> validationErrorHandler(RuntimeException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

}
