package Novoe.LoyaltySystem.repository;

import Novoe.LoyaltySystem.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Override
    long count();
}