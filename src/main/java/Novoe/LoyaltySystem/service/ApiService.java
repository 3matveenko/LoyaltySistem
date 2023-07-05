package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.AbService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService extends AbService<ApiService> {


    public String toJson(List<?> objects){
        try {
            return (new ObjectMapper()).writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String toJson(Object object){

        try {
            return  (new ObjectMapper()).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }





}
