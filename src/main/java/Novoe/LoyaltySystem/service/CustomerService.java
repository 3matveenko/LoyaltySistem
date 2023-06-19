package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.Customer;
import Novoe.LoyaltySystem.model.enums.ErrorHttp;
import Novoe.LoyaltySystem.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static Novoe.LoyaltySystem.model.enums.ErrorHttp.ERROR_PHONE_NUMBER;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CompanyService companyService;

    public List<Customer> getCustomersByCompanyId(Long companyId){
       return customerRepository.findAllByCompany(companyService.findById(companyId));
    }

    public Optional<Customer> findCustomerByPhone(String phoneNumber){
        return  customerRepository.findCustomerByPhoneNumber(phoneNumber);
    }
    public ResponseEntity<String> createCustomer(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer customer = objectMapper.readValue(json, Customer.class);
        if(customer.getPhoneNumber()!=null&&customer.getPhoneNumber().length()==10){
            String phoneNumber = customer.getPhoneNumber();
            Optional<Customer> optionalCustomer = findCustomerByPhone(phoneNumber);
            if(optionalCustomer.isPresent()){
                return ResponseEntity.ok(optionalCustomer.get().getToken());
            }
            String token = UUID.randomUUID().toString();
            customer.setToken(token);
            customerRepository.save(customer);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(460).body("Invalid phone number");
    }
}
