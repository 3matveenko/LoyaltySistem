package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.Card;
import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.model.TypeOfCard;
import Novoe.LoyaltySystem.repository.CardRepository;
import Novoe.LoyaltySystem.repository.CompanyRepository;
import Novoe.LoyaltySystem.repository.TypeOfCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CompanyService companyService;

    @Autowired
    TypeOfCardService typeOfCardService;

    public long getCount(Long id){
       Company company = companyService.findById(id);
        return company.getCards().size();
    }

    public String createCard(MultipartFile file, String name, String typeOfDiscount,Long idCompany, long idTypeofCard) throws IOException{
        String imagename = UUID.randomUUID().toString();
        String fileName = imagename+".jpeg";
        String destinationPath = Paths.get("src/main/resources/static/dist/img/cards").toString();
        Path targetPath = Paths.get(destinationPath, fileName);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        Card card = new Card();
        card.setName(name);
        card.setImage("/dist/img/cards/"+imagename+".jpeg");
        card.setTypeOfDiscount(typeOfDiscount);
        TypeOfCard typeOfCard = typeOfCardService.getTypeOfCardById(idTypeofCard);
        card.setTypeOfCard(typeOfCard);
        cardRepository.save(card);
        Company company = companyService.findById(idCompany);
        List<Card> cards = company.getCards();
        cards.add(card);
        company.setCards(cards);
        companyService.save(company);
        return "ok";
    }

    public void update(MultipartFile file, String name, String typeOfDiscount, Long idCard, Long idTypeofCard) throws IOException{
        Card card = findByid(idCard);
        if(!"".equals(file.getOriginalFilename())) {
            deleteImage(card);
            String destinationPath = Paths.get("src/main/resources/static/dist/img/cards").toString();
            String imagename = UUID.randomUUID().toString();
            String fileName = imagename+".jpeg";
            Path targetPath = Paths.get(destinationPath, fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            card.setImage("/dist/img/cards/"+imagename+".jpeg");
        }

        card.setName(name);
        card.setTypeOfDiscount(typeOfDiscount);
        TypeOfCard typeOfCard = typeOfCardService.getTypeOfCardById(idTypeofCard);
        card.setTypeOfCard(typeOfCard);
        cardRepository.save(card);
    }

    public Card findByid(Long id){
        return cardRepository.findById(id).orElseThrow();
    }

    public void deleteImage(Card card){
        String pattern = "/dist/img/cards/(.*)";
        String input = card.getImage();

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);

        String variablePart = null;
        if (matcher.find()) {
            variablePart = matcher.group(1);
        }
        String destinationPath = Paths.get("src/main/resources/static/dist/img/cards").toString();

        String oldImage = destinationPath+"/"+variablePart;
        File imageFile = new File(oldImage);
        if (imageFile.exists()) {
            imageFile.delete();
        }
    }
    public void delete(Long cardId, Long companyId){
       Card card = findByid(cardId);
       Company company = companyService.findById(companyId);
       List<Card> cards = company.getCards();
        cards.remove(card);
        companyService.save(company);
       deleteImage(card);
        cardRepository.delete(card);
    }

}
