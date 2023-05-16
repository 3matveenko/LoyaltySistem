package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public long getCount(){
       return companyRepository.count();
    }

    public List<Company> allCompany(){
    return companyRepository.findAll();
    }
}
