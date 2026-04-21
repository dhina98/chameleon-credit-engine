package com.chameleon.transactionservice.controller;

import com.chameleon.transactionservice.model.Transaction;
import com.chameleon.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction txn){
        return ResponseEntity.ok(transactionService.process(txn));
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<List<Transaction>> getAll(@PathVariable("customerId") String customerId){
        return ResponseEntity.ok(transactionService.getAll(customerId));
    }

    @GetMapping("/{customerId}/last30Days")
    public ResponseEntity<List<Transaction>> getLast30Days(@PathVariable("customerId")String customerId){
        return ResponseEntity.ok(transactionService.last30Days(customerId));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health(){
        return ResponseEntity.ok("Transaction Service is UP");
    }
}
