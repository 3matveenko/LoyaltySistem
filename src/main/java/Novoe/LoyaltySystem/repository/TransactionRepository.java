package Novoe.LoyaltySystem.repository;

import Novoe.LoyaltySystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
