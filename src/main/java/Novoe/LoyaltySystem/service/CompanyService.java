package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.Card;
import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public Company getCompanyById(Long companyId) throws NotFoundException {
        Optional<Company> company = companyRepository.findById(companyId);
        if(company.isPresent()){
            return company.get();
        } else {
            throw new NotFoundException("not found");
        }
    }
    public long getCount(){
       return companyRepository.count();
    }


    public List<Company> getAllCompany(){
    return companyRepository.findAll();
    }

    public void create(Company company){
        String token = UUID.randomUUID().toString();
        company.setToken(token);
        companyRepository.save(company);
    }

    public void update(Company company){
        Company company1 = companyRepository.findById(company.getId()).orElseThrow(); //в company нет карточек. Для того, чтобы измененная компания была с карточками, ищем в базе связку с карточками
        company.setCards(company1.getCards());
        companyRepository.save(company);
    }

    public void delete(Long id){
        companyRepository.delete(companyRepository.findById(id).orElseThrow());
    }

    public Company findById(Long id){
       return companyRepository.findById(id).orElseThrow();
    }

    public void save(Company company){
        companyRepository.save(company);
    }

    public List<Card> cardToCompany(Long id){
        Company company = companyRepository.findById(id).orElseThrow();
        return company.getCards();
    }
}
