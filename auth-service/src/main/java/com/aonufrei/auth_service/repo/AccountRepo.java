package com.aonufrei.auth_service.repo;

import com.aonufrei.auth_service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, String> {

	Optional<Account> findByEmail(String email);
}
