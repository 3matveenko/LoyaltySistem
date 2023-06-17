package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.TypeOfCard;
import Novoe.LoyaltySystem.repository.TypeOfCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfCardService {

    @Autowired
    TypeOfCardRepository typeOfCardRepository;

    public List<TypeOfCard> allTypeOfCard(){
        return typeOfCardRepository.findAll();
    }

    public TypeOfCard getTypeOfCardById(long id){
        return typeOfCardRepository.findById(id).orElseThrow();
    }
}
