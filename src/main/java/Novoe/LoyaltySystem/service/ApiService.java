package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.AbService;
import Novoe.LoyaltySystem.model.Company;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService extends AbService<ApiService> {


    public String toJson(List<?> objects){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
    public String toJson(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }


//    private AbService abService;
//
//    public ApiService(AbService abService) {
//        this.abService = abService;
//    }
//
//    public Object fromJson(String json, String ClassName) throws JsonProcessingException, ClassNotFoundException {
//       return abService.fJson(json, ClassName);
//    }
//
//    public Object toJson() throws JsonProcessingException, ClassNotFoundException {
//        Company myObject = new Company();
//        return abService.tJson(myObject);
 //   }
//    public List<Object> toJsonn() throws JsonProcessingException, ClassNotFoundException {
//        Company myObject = new Company();
//        return abService.tJson(myObject);
//    }


}
