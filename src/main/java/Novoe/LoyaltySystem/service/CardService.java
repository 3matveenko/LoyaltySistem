package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.repository.CardRepository;
import Novoe.LoyaltySystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CompanyRepository companyRepository;

    public long getCount(Long id){
       Company company = companyRepository.findById(id).orElseThrow();
        return company.getCards().size();
    }


}
