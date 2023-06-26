package Novoe.LoyaltySystem.controller.API;


import Novoe.LoyaltySystem.service.CardService;
import Novoe.LoyaltySystem.service.CompanyService;
import Novoe.LoyaltySystem.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "app", description = "API для запросов от мобильных приложений")
@RestController
@RequestMapping("/app")
public class AppRestController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CardService cardService;

    @Operation(summary = "регистрация", description = "Номер телефона передается без кода страны только 10 цифр " +
            "Input: {\n" +
            "  \"name\": \"Полное имя\",\n" +
            "  \"email\": \"Электронная почта\",\n" +
            "  \"phoneNumber\": \"999999999\",\n" +
            "  \"birthday\": \"1999-01-01T00:00:00.000Z\"\n" +
            "} Output:{token}")
    @ApiResponse(responseCode = "200", description = "Успешная регистрация")
    @ApiResponse(responseCode = "460", description = "Ошибка передачи номера телефона")
    @PostMapping("/create")
     public ResponseEntity<String> create(
            @RequestBody String json) throws JsonProcessingException {
            return customerService.createCustomer(json);
         }


    @Operation(summary = "Выдает список всех компаний.", description = "description")
    @ApiResponse(responseCode = "200", description = "description")
    @GetMapping("/allcompany")
    public ResponseEntity<String> getAllCompany() {
        return ResponseEntity.ok(customerService.getAllCompany());
    }

    @Operation(summary = "Выдает список шаблонов по id компании.", description = "description")
    @ApiResponse(responseCode = "200", description = "description")
    @GetMapping("/card_by_company_id")
    public ResponseEntity<String> cardByCompanyId() {
        return ResponseEntity.ok(cardService.);
    }

//    @Operation(summary = "Выдает список всех карт по id компании.", description = "description")
//    @ApiResponse(responseCode = "200", description = "description")
//    @GetMapping("/cards_by_company_id")
//    public ResponseEntity<String> getCardsByCompanyId() {
//        return ResponseEntity.ok(companyService.cardToCompany());
//    }


}
