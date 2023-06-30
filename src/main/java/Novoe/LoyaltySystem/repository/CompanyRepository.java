package Novoe.LoyaltySystem.repository;

import Novoe.LoyaltySystem.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c JOIN c.cards card WHERE card.id = :cardId")
    Optional<Company> findByCardId(@Param("cardId") Long cardId);

    Optional<Company> findCompanyByToken(String token);
}
