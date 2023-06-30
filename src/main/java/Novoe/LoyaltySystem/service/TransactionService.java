package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public String makeTransaktion(String jsonText) throws Exception {

        try {
            JSONObject jsonObject = new JSONObject(jsonText);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject item = itemsArray.getJSONObject(i);
                int amount = item.getInt("amount");
                String expression = "* 0.9";
                int newAmount = (int) (amount * 0.9);
                item.put("amount", newAmount);
            }
            return jsonObject.toString();
        } catch (Exception e) {

            throw new Exception(e);
        }
    }
}
