package com.aonufrei.auth_service.service;

import com.aonufrei.auth_service.dto.AccountSecurityDetails;
import com.aonufrei.auth_service.model.Account;
import com.aonufrei.auth_service.repo.AccountRepo;
import com.aonufrei.dto.*;
import com.aonufrei.enums.AccountRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class AccountService {

	private static final Logger log = LoggerFactory.getLogger(AccountService.class);

	private static final Duration TOKEN_EXPIRATION_PERIOD = Duration.ofDays(30);
	private static final String TOKEN_ISSUER = "MentalHelper";
	private static final String BEARER_PREFIX = "Bearer ";

	private final AccountRepo accountRepo;
	private final PasswordEncoder passwordEncoder;
	private final Algorithm algorithm;
	private final ModelMapper modelMapper;

	public AccountService(AccountRepo accountRepo, PasswordEncoder passwordEncoder, Algorithm algorithm, ModelMapper modelMapper) {
		this.accountRepo = accountRepo;
		this.passwordEncoder = passwordEncoder;
		this.algorithm = algorithm;
		this.modelMapper = modelMapper;
	}

	public LoginOutDto login(LoginDto loginDto) {
		return accountRepo.findByEmail(loginDto.getEmail())
				.filter(it -> passwordEncoder.matches(loginDto.getPassword(), it.getPassword()))
				.map(a -> new LoginOutDto(BEARER_PREFIX + createToken(a.getId()), a.getId(), a.getRole()))
				.orElseThrow(() -> new RuntimeException("Failed to authenticate"));
	}

	public Optional<Account> identifyUserModel(String token) {
		if (StringUtils.isBlank(token)) {
			throw new RuntimeException("Invalid token provided");
		}
		var accountId = getAccountIdFromToken(token);
		return accountRepo.findById(accountId);
	}

	public AccountOutDto identifyUser(String token) {
		return identifyUserModel(token)
				.map(this::toOutDto)
				.orElseThrow(() -> new RuntimeException("Account not found"));
	}

	public AccountOutDto register(@NotNull AccountRole role, RegisterDto dto) {
		validatePasswords(dto.getPassword(), dto.getReplyPassword());
		if (accountRepo.findByEmail(dto.getEmail()).isPresent()) {
			throw new RuntimeException("Account with provided email already exist");
		}
		var model = toModel(new AccountInDto(role, dto.getEmail(), passwordEncoder.encode(dto.getPassword())));
		model.setId(UUID.randomUUID().toString());
		var saved = accountRepo.save(model);
		return toOutDto(saved);
	}

	public UserDetails getSecurityDetails(String token) throws UsernameNotFoundException {
		return identifyUserModel(token).map(model -> new AccountSecurityDetails(
				model.getId(),
				model.getEmail(),
				model.getRole(),
				model.getPassword())
		).orElseThrow(() -> new UsernameNotFoundException("Invalid token provided"));
	}

	public void changePassword(ChangePasswordRequestDto request) {
		validatePasswords(request.getPassword(), request.getReplyPassword());
		var account = accountRepo.findById(request.getAccountId())
				.orElseThrow(() -> new RuntimeException("Account not found"));
		account.setPassword(passwordEncoder.encode(request.getPassword()));
		accountRepo.save(account);
	}

	public void deleteAccount(String id) {
		accountRepo.deleteById(id);
	}

	public String createToken(String accountId) {
		var now = LocalDateTime.now();
		return JWT.create()
				.withIssuer(TOKEN_ISSUER)
				.withSubject("auth")
				.withClaim("accountId", accountId)
				.withExpiresAt(now.plus(TOKEN_EXPIRATION_PERIOD).toInstant(ZoneOffset.UTC))
				.sign(algorithm);
	}

	public String getAccountIdFromToken(String token) {
		try {
			var verifier = JWT.require(algorithm)
					.withIssuer(TOKEN_ISSUER)
					.build();
			var decodedJWT = verifier.verify(token);
			return decodedJWT.getClaim("accountId").asString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException("Failed to decode token", e);
		}
	}

	public void validatePasswords(String password, String replyPassword) {
		if (!Objects.equals(password, replyPassword)) {
			throw new RuntimeException("Passwords are not equal");
		}
	}

	public Account toModel(AccountInDto accountInDto) {
		return modelMapper.map(accountInDto, Account.class);
	}

	public AccountOutDto toOutDto(Account model) {
		return modelMapper.map(model, AccountOutDto.class);
	}

	public static AccountSecurityDetails getCurrentAuth() {
		var authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
		return authentication.map(Authentication::getPrincipal)
				.map(p -> p instanceof AccountSecurityDetails ? (AccountSecurityDetails) p : null)
				.orElseThrow(() -> new RuntimeException("No users authorized"));
	}


}
