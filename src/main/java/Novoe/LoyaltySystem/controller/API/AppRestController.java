package Novoe.LoyaltySystem.controller.API;

import Novoe.LoyaltySystem.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

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

    @Autowired
    ApiService apiService;

    @Autowired
    CardItemService cardItemService;

    @Operation(summary = "регистрация", description = "Номер телефона передается без кода страны только 10 цифр " +
            "Input: {\n" +
            "  \"name\": \"Полное имя\",\n" +
            "  \"email\": \"Электронная почта\",\n" +
            "  \"phoneNumber\": \"9999999999\",\n" +
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
    @ApiResponse(responseCode = "403", description = "Не верный токен")
    @GetMapping("/allcompany")
    public ResponseEntity<String> getAllCompany(
            @RequestHeader("Authorization") String token) {
        try {
            customerService.getUserIdByToken(token);
            return ResponseEntity.ok(apiService.toJson(companyService.getAllCompany()));
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Invalid token");
        }

    }

    @Operation(summary = "Выдает список шаблонов карт по id компании.", description = "" +
            "Output: [{\"id\":1,\"name\":\"name\",\"image\":null,\"description\":\"fff\",\"status\":true,\"typeOfCard\":{\"id\":1,\"name\":\"Скидочная\"},\"typeOfDiscount\":\"1\"}]")
    @ApiResponse(responseCode = "200", description = "description")
    @ApiResponse(responseCode = "403", description = "Не верный токен")
    @GetMapping("/card_by_company_id/{companyId}")
    public ResponseEntity<String> cardByCompanyId(
            @RequestHeader("Authorization") String token,
            @PathVariable Long companyId) {
        try {
            customerService.getUserIdByToken(token);
            return ResponseEntity.ok(apiService.toJson(companyService.getCardByCompanyId(companyId)));
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Invalid token");
        }
    }

    @Operation(summary = "Создает карту по id шаблона.", description = "")
    @ApiResponse(responseCode = "200", description = "description")
    @ApiResponse(responseCode = "460", description = "Нет шаблона с таким id")
    @ApiResponse(responseCode = "403", description = "Не верный токен")
    @GetMapping("/create_card/{cardId}")
    public ResponseEntity<String> createCard(
            @RequestHeader("Authorization") String token,
            @PathVariable Long cardId) {
        try {
            Long customerId = customerService.getUserIdByToken(token);
            return ResponseEntity.ok(apiService.toJson(cardItemService.createCardItemId(cardId,customerId)));
        } catch (Exception e) {
            if(e instanceof NotFoundException){
                return ResponseEntity.status(403).body("Invalid token");
            } else {
                return ResponseEntity.status(460).body("Invalid id");
            }

        }
    }


}

//    @Operation(summary = "Выдает список всех карт по id компании.", description = "description")
//    @ApiResponse(responseCode = "200", description = "description")
//    @GetMapping("/cards_by_company_id")
//    public ResponseEntity<String> getCardsByCompanyId() {
//        return ResponseEntity.ok(companyService.cardToCompany());
//    }



