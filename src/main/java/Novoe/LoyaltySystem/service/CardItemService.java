package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.CardItem;
import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.model.Customer;
import Novoe.LoyaltySystem.repository.CardItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CardItemService {

    @Autowired
    CardItemRepository cardItemRepository;

    @Autowired
    CardService cardService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CustomerService customerService;

    public List<CardItem> getall(){
        return cardItemRepository.findAll();
    }

    public Long createCardItemId(Long cardId, Long customerId) throws NotFoundException {
        Optional<Company> company = companyService.getCompanyByCardId(cardId);
        CardItem cardItem = new CardItem();
        cardItem.setCard(cardService.findByid(cardId));
        company.ifPresent(cardItem::setCompany);
        cardItemRepository.save(cardItem);
        Customer customer = customerService.findCustomerById(customerId);
        List<CardItem> cardItems = customer.getCardItems();
        cardItems.add(cardItem);
        customerService.saveCustomer(customer);
        return cardItem.getId();
    }

}
