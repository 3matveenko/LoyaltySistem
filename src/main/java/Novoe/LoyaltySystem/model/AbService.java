package Novoe.LoyaltySystem.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public abstract class AbService<T> {

//    public String tJson(T object) throws JsonProcessingException {
//        return (new ObjectMapper()).writeValueAsString(object);
//    }

    public String tJson(T object) throws JsonProcessingException {
        return (new ObjectMapper()).writeValueAsString(object);
    }

    public T fJson(String json, String className) throws JsonProcessingException, ClassNotFoundException {

        return (T) (new ObjectMapper()).readValue(json, Class.forName(className));
    }

}
