package Novoe.LoyaltySystem.controller.API;

import Novoe.LoyaltySystem.exception.ForbiddenException;
import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.service.ApiService;
import Novoe.LoyaltySystem.service.CompanyService;
import Novoe.LoyaltySystem.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "crm", description = "API для запросов от 1c")
@RestController
@RequestMapping("/crm")
public class CrmRestController {

    @Autowired
    CompanyService companyService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    ApiService apiService;

    @Operation(summary = "Обрабатывает транзакцию", description = "")
    @ApiResponse(responseCode = "200", description = "Успешная регистрация")
    @ApiResponse(responseCode = "403", description = "Не верный токен.")
    @PostMapping("/transaction")
    public ResponseEntity<JSONObject> response(
            @RequestHeader("Authorization") String token,
            @RequestBody String transaction) throws Exception {
        try {
           Company company = companyService.getCompanyByToken(token);
            return  ResponseEntity.ok(transactionService.makeTransaktion(transaction));

        } catch (ForbiddenException e){
            System.out.println("d");
            return ResponseEntity.ok(transactionService.makeTransaktion(transaction));
        } catch (JsonProcessingException e) {
            System.out.println("g");
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("k");
            throw new RuntimeException(e);
        }
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