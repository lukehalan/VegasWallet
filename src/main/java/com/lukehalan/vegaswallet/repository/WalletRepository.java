package com.lukehalan.vegaswallet.repository;

import com.lukehalan.vegaswallet.model.Player;
import com.lukehalan.vegaswallet.model.Wallet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Optional<Wallet> findByPlayerId(Player playerId);
}
