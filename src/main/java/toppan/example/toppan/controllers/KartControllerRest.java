package toppan.example.toppan.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import toppan.example.toppan.service.ExceptionService;
import toppan.example.toppan.service.NameFileDoc;
import toppan.example.toppan.service.StringEquals;

@RestController
@RequiredArgsConstructor
public class KartControllerRest {

    private final ExceptionService service;

    @GetMapping
    public ResponseEntity<String> nethod(){
        // обработка исклчения
        //  передать на вюшку сообщение
        try {
            service.methodThrowsException();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e){
            return new ResponseEntity<>("Потрібна оплата", HttpStatus.PAYMENT_REQUIRED);
        }
    }

}
