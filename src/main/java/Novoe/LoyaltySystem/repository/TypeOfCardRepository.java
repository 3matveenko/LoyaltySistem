package Novoe.LoyaltySystem.repository;

import Novoe.LoyaltySystem.model.TypeOfCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface TypeOfCardRepository extends JpaRepository<TypeOfCard, Long> {
}
