package Novoe.LoyaltySystem.repository;

import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByPhoneNumber(String phoneNumber);


    //проверить работоспособность
        @Query("SELECT c FROM Customer c JOIN c.cardItems ci JOIN ci.company co WHERE co.id = :companyId")
        List<Customer> findCustomerIdsByCompanyId(@Param("companyId") Long companyId);

}
