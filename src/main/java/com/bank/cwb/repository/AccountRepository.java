package com.bank.cwb.repository;

import com.bank.cwb.model.Account;
import com.bank.cwb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);
    Optional<Account> findByUser_Username(String username);
}
