package Novoe.LoyaltySystem.repository;

import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.model.Push;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PushRepository extends JpaRepository<Push, Long> {

    List<Push> findPushByCompany(Company company);

}
