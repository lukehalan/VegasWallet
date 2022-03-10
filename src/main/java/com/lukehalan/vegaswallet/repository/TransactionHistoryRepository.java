package com.lukehalan.vegaswallet.repository;

import com.lukehalan.vegaswallet.model.TransactionHistory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
    Optional<TransactionHistory> findByTransactionId(String transactionId);
    List<TransactionHistory> findByPlayerId(Integer playerId);
}
