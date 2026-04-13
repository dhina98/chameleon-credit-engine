package com.chameleon.cardengineservice.Controller;

import com.chameleon.cardengineservice.Repository.CustomerCardRepository;
import com.chameleon.cardengineservice.model.CustomerCard;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/cards")
public class CardEngineController {

    private CustomerCardRepository customerCardRepository;

    @GetMapping("/customerId")
    public ResponseEntity<CustomerCard> getAll(@PathVariable String customerId) {
        return customerCardRepository.findByCustomerId(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health(){
        return ResponseEntity.ok("Card Engine Service is UP");
    }
}
