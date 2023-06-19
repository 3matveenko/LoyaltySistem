package Novoe.LoyaltySystem.controller.API;


import Novoe.LoyaltySystem.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "app", description = "API для запросов от мобильных приложений")
@RestController
@RequestMapping("/app")
public class AppRestController {

    @Autowired
    CustomerService customerService;
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


    @GetMapping("/old")
    public ResponseEntity<String> get(
            @RequestHeader("Authorization") String headerValue){
        if ("Bearer qwerty".equals(headerValue)){
            System.out.println(headerValue);
            return ResponseEntity.ok("Ok");
        } else {
            System.out.println(headerValue);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
    }
}
