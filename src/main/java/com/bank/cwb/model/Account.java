package com.bank.cwb.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    public Account(User user, BigDecimal balance, List<Transaction> transactions) {
        this.user = user;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Account(User user, BigDecimal balance) {
        this.user = user;
        this.balance = balance;
    }
}
