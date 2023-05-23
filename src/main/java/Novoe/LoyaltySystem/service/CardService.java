package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.Card;
import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.repository.CardRepository;
import Novoe.LoyaltySystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public String createCard(MultipartFile file, String name, String typeOfDiscount,Long idCompany) throws IOException{
        String imagename = UUID.randomUUID().toString();
        String fileName = imagename+".jpeg";
        String destinationPath = Paths.get("src/main/resources/static/dist/img/cards").toString();
        Path targetPath = Paths.get(destinationPath, fileName);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        Card card = new Card();
        card.setName(name);
        card.setImage("/dist/img/cards/"+imagename+".jpeg");
        card.setTypeOfDiscount(typeOfDiscount);
        cardRepository.save(card);
        Company company = companyRepository.findById(idCompany).orElseThrow();
        List<Card> cards = company.getCards();
        cards.add(card);
        company.setCards(cards);
        companyRepository.save(company);
        return "ok";
    }
}
