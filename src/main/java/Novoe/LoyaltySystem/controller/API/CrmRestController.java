package Novoe.LoyaltySystem.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "crm", description = "API для запросов от 1c")
@RestController
@RequestMapping("/crm")
public class CrmRestController {

    @Operation(summary = "регистрация", description = "Регистрация нового пользователя")
    @ApiResponse(responseCode = "200", description = "Успешная регистрация")
    @ApiResponse(responseCode = "401", description = "Такой клиент существует.")
    @GetMapping("/")
    public ResponseEntity<String> response(
            @Parameter(description = "ФИО", example = "{\n" +
                    "  \"name\": \"Полное имя\",\n" +
                    "  \"email\": \"Электронная почта\",\n" +
                    "  \"phoneNumber\": \"999999999\",\n" +
                    "  \"birthday\": \"1999-01-01T00:00:00.000Z\"\n" +
                    "}") @RequestBody String json
    ){

        return ResponseEntity.ok("Ok");
//             return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
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