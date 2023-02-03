package toppan.example.toppan.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PaymentRequiredException extends ResponseStatusException {


    public PaymentRequiredException(HttpStatus status, String reason) {
        super(status, reason);
    }
}

