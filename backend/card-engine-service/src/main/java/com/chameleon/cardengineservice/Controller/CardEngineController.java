package com.chameleon.cardengineservice.Controller;

import com.chameleon.cardengineservice.Repository.CustomerCardRepository;
import com.chameleon.cardengineservice.model.CustomerCard;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "http://localhost:3000")
public class CardEngineController {

    @Autowired
    private CustomerCardRepository customerCardRepository;

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerCard> getAll(@PathVariable("customerId") String customerId) {
        return customerCardRepository.findByCustomerId(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health(){
        return ResponseEntity.ok("Card Engine Service is UP");
    }
}
