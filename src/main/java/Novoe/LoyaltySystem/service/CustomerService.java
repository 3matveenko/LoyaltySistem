package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.exception.ForbiddenException;
import Novoe.LoyaltySystem.model.CardItem;
import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.model.Customer;
import Novoe.LoyaltySystem.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CompanyService companyService;

    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }
    public Customer findCustomerById(Long customerId) throws NotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            return customer.get();
        } else {
             throw new NotFoundException("not found");
        }
    }
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public List<Customer> getCustomersByCompanyId(Long companyId){
        return customerRepository.findCustomerIdsByCompanyId(companyId);
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

    public String getAllCompany(){
        List<Company> allCompany = companyService.getAllCompany();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(allCompany);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void updateCustomer(Customer customer){
        customer.setCardItems(findCustomerById(customer.getId()).getCardItems());
        customer.setBirthday(findCustomerById(customer.getId()).getBirthday());
        customerRepository.save(customer);
    }

    public Long getUserIdByToken(String token) throws ForbiddenException {
        Optional<Customer> customer = customerRepository.findCustomerByToken(token);
       if(customer.isPresent()){
          return customer.get().getId();
       } else {
           throw new ForbiddenException("Forbidden");
       }
    }

    /**
     * метод проверяет не привязана ли карта с таким шаблоном к этому клиенту
     */
    public boolean findCardByCustomerId(Long cardId, Long customerId){
        for (CardItem carditem: findCustomerById(customerId).getCardItems()) {
            if(carditem.getCard().getId()==cardId){
                return false;
            }
        }
        return true;
    }
}
