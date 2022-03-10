package com.lukehalan.vegaswallet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference(value = "wallet_player")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player playerId;

    @Column(name = "balance",nullable = false)
    private BigDecimal balance;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update",nullable = false)
    private Date lastUpdate;

    @JsonManagedReference(value = "transaction_wallet")
    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)
    private List<TransactionHistory> transactions;

    public Wallet(Player player, BigDecimal balance) {
        this.balance = balance;
        this.playerId = player;
    }
}
