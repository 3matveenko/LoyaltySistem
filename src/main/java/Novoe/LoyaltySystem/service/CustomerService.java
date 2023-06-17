package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.Сustomer;
import Novoe.LoyaltySystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CompanyService companyService;

    public List<Сustomer> getCustomersByCompanyId(Long companyId){
       return customerRepository.findAllByCompany(companyService.findById(companyId));
    }
}
