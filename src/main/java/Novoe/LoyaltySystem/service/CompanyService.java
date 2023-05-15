package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public long getCount(){
       return companyRepository.count();
    }
}
