package Novoe.LoyaltySystem.controller.API;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/app")
public class AppRestController {

    @Operation(summary = "Get a greeting", description = "Get a greeting by name")
    @GetMapping("/")
     public ResponseEntity<String> response(){
         //    @RequestHeader("Authorization") String headerValue){
//         if ("Bearer qwerty".equals(headerValue)){
//             System.out.println(headerValue);
//             return ResponseEntity.ok("Ok");
//         } else {
//             System.out.println(headerValue);
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
