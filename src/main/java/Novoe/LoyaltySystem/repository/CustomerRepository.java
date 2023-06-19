package Novoe.LoyaltySystem.repository;

import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByCompany(Company company);

    Optional<Customer> findCustomerByPhoneNumber(String phoneNumber);

}
