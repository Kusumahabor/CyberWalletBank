//package com.bank.cwb;
//
//import com.bank.cwb.model.Account;
//import com.bank.cwb.model.Transaction;
//import com.bank.cwb.model.User;
//import com.bank.cwb.repository.AccountRepository;
//import com.bank.cwb.repository.TransactionRepository;
//import com.bank.cwb.repository.UserRepository;
//import com.bank.cwb.service.AccountService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//
//class AccountServiceTest {
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private AccountService accountService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        User user = new User();
//        user.setUsername("kusumapraveena");
//        user.setPassword(passwordEncoder.encode("1234"));
//
//        Account account = new Account();
//        account.setUser(user);
//    }
//
//    // 1. Test query method in AccountRepository
//    @Test
//    void testFindAccountByUsername() {
//        String username = "testUser";
//        User user = new User();
//        user.setUsername(username);
//        Account account = new Account(user, BigDecimal.ZERO, null);
//
//        when(accountRepository.findByUser_Username(username)).thenReturn(Optional.of(account));
//
//        Account result = accountService.findAccountByUsername(username);
//
//        assertNotNull(result);
//        assertEquals(username, result.getUser().getUsername());
//        verify(accountRepository).findByUser_Username(username);
//    }
//
//    // 2. Test method in AccountService
//    @Test
//    void testRegisterUser() {
//        User userForm = new User();
//        userForm.setUsername("newUser");
//        userForm.setPassword("password");
//        userForm.setEmail("newuser@example.com");
//
//        when(userRepository.findByUsername(userForm.getUsername())).thenReturn(Optional.empty());
//        when(userRepository.findByEmail(userForm.getEmail())).thenReturn(Optional.empty());
//        when(passwordEncoder.encode(userForm.getPassword())).thenReturn("encodedPassword");
//        when(userRepository.save(any(User.class))).thenReturn(userForm);
//
//        User result = accountService.registerUser(userForm);
//
//        assertNotNull(result);
//        assertEquals(userForm.getUsername(), result.getUsername());
//        verify(userRepository).save(any(User.class));
//        verify(accountRepository).save(any(Account.class));
//    }
//
//    // 3. Parameterized test for deposit method
//    @ParameterizedTest
//    @CsvSource({
//            "100.00, 50.00, 150.00",
//            "0.00, 100.00, 100.00",
//            "1000.00, 250.00, 1250.00"
//    })
//    void testDeposit(BigDecimal initialBalance, BigDecimal depositAmount, BigDecimal expectedBalance) {
//        User user = new User();
//        Account account = new Account(user, initialBalance, null);
//
//        accountService.deposit(account, depositAmount);
//
//        assertEquals(expectedBalance, account.getBalance());
//        verify(accountRepository).save(account);
//        verify(transactionRepository).save(any(Transaction.class));
//    }
//
//    // Additional test for withdraw method
//    @Test
//    void testWithdraw() {
//        User user = new User();
//        Account account = new Account(user, new BigDecimal("500.00"), null);
//        BigDecimal withdrawAmount = new BigDecimal("200.00");
//
//        accountService.withdraw(account, withdrawAmount);
//
//        assertEquals(new BigDecimal("300.00"), account.getBalance());
//        verify(accountRepository).save(account);
//        verify(transactionRepository).save(any(Transaction.class));
//    }
//
//    // Test for insufficient funds during withdrawal
//    @Test
//    void testWithdrawInsufficientFunds() {
//        User user = new User();
//        Account account = new Account(user, new BigDecimal("100.00"), null);
//        BigDecimal withdrawAmount = new BigDecimal("200.00");
//
//        assertThrows(RuntimeException.class, () -> accountService.withdraw(account, withdrawAmount));
//    }
//
//    // Test for getTransactions method
//    @Test
//    void testGetTransactions() {
//        User user = new User();
//        Account account = new Account(user, BigDecimal.ZERO, null);
//        account.setId(1L);
//
//        List<Transaction> transactions = Arrays.asList(
//                new Transaction(new BigDecimal("100.00"), "Deposit", LocalDateTime.now(), account),
//                new Transaction(new BigDecimal("50.00"), "Withdrawal", LocalDateTime.now(), account)
//        );
//
//        when(transactionRepository.findByAccount_Id(account.getId())).thenReturn(transactions);
//
//        List<Transaction> result = accountService.getTransactions(account);
//
//        assertEquals(2, result.size());
//        verify(transactionRepository).findByAccount_Id(account.getId());
//    }
//
//    // Test for transferAmount method
//    @Test
//    void testTransferAmount() {
//        User fromUser = new User();
//        fromUser.setUsername("fromUser");
//        Account fromAccount = new Account(fromUser, new BigDecimal("500.00"), null);
//
//        User toUser = new User();
//        toUser.setUsername("toUser");
//        Account toAccount = new Account(toUser, new BigDecimal("200.00"), null);
//
//        when(accountRepository.findByUser_Username("toUser")).thenReturn(Optional.of(toAccount));
//
//        BigDecimal transferAmount = new BigDecimal("100.00");
//        accountService.transferAmount(fromAccount, "toUser", transferAmount);
//
//        assertEquals(new BigDecimal("400.00"), fromAccount.getBalance());
//        assertEquals(new BigDecimal("300.00"), toAccount.getBalance());
//        verify(accountRepository, times(2)).save(any(Account.class));
//        verify(transactionRepository, times(2)).save(any(Transaction.class));
//    }
//}
