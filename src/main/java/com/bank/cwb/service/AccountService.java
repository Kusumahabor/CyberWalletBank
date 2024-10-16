package com.bank.cwb.service;

import com.bank.cwb.model.Account;
import com.bank.cwb.model.Transaction;
import com.bank.cwb.repository.AccountRepository;
import com.bank.cwb.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findAccountByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("Username or Password not found");
            }

        Account account1 = new Account();
        account1.setUsername(account.getUsername());
        account1.setPassword(passwordEncoder.encode(account.getPassword()));
        account1.setBalance(account.getBalance());
        account1.setTransactions(account.getTransactions());
        account1.setAuthorities(authorities());

        return new Account(
                account.getUsername(),
                account.getPassword(),
                account.getBalance(),
                account.getTransactions(),
                authorities());
    }

    public Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

    public Account registerAccount(String username, String password) {
        if (accountRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Account already exists");
        }

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    public void deposit(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction(
                amount,
                "Deposit",
                LocalDateTime.now(),
                account
        );
        transactionRepository.save(transaction);
    }

    public void withdraw(Account account, BigDecimal amount) {
        if(account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        Transaction transaction = new Transaction(
                amount,
                "Withdrawal",
                LocalDateTime.now(),
                account
        );
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Account account) {
        return transactionRepository.findByAccountId(account.getId());
    }

    public void transferAmount(Account fromAccount, String toUsername, BigDecimal amount) {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        Account toAccount = accountRepository.findByUsername(toUsername)
                .orElseThrow(() -> new RuntimeException("Recipient account not found"));

        // Deduct from sender's account
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountRepository.save(fromAccount);

        // Add to recipient's account
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(toAccount);

        // Create transaction records for both accounts
        Transaction debitTransaction = new Transaction(
                amount,
                "Transfer Out to " + toAccount.getUsername(),
                LocalDateTime.now(),
                fromAccount
        );
        transactionRepository.save(debitTransaction);

        Transaction creditTransaction = new Transaction(
                amount,
                "Transfer In from " + fromAccount.getUsername(),
                LocalDateTime.now(),
                toAccount
        );
        transactionRepository.save(creditTransaction);
    }

    public List<Transaction> getTransactionHistory(Account account) {
        return transactionRepository.findByAccountId(account.getId());
    }
}