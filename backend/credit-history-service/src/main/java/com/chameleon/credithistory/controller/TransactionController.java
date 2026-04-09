package com.chameleon.credithistory.controller;

import com.chameleon.credithistory.entity.Transaction;
import com.chameleon.credithistory.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
//@CrossOrigin(origins = "*") will learn later
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/{userId}")
    public List<Transaction> getTransactionById(@PathVariable Long userId){
        return transactionRepository.findByUserId(userId);
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @GetMapping("/all")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
