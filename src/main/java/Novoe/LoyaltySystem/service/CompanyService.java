package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.Card;
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

    public void create(Company company){
        companyRepository.save(company);
    }

    public void delete(Long id){
        companyRepository.delete(companyRepository.findById(id).orElseThrow());
    }

    public Company findById(Long id){
       return companyRepository.findById(id).orElseThrow();
    }

    public List<Card> CardToCompany(Long id){
        Company company = companyRepository.findById(id).orElseThrow();
        return company.getCards();
    }
}
