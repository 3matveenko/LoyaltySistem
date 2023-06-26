package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.CardItem;
import Novoe.LoyaltySystem.repository.CardItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardItemService {

    @Autowired
    CardItemRepository cardItemRepository;

    public List<CardItem> getall(){
        return cardItemRepository.findAll();
    }

}
