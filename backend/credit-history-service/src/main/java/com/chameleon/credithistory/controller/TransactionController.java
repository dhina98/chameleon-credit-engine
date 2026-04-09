package com.chameleon.credithistory.controller;

import com.chameleon.credithistory.entity.Transaction;
import com.chameleon.credithistory.model.CreditSummary;
import com.chameleon.credithistory.repository.TransactionRepository;
import com.chameleon.credithistory.service.CreditSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
//@CrossOrigin(origins = "*") will learn later
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CreditSummaryService creditSummaryService;

    @GetMapping("/{customerId}")
    public List<Transaction> getTransactionById(@PathVariable Long customerId){
        return transactionRepository.findBycustomerId(customerId);
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @GetMapping("/all")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @GetMapping("/{customerId}/summary")
    public CreditSummary getCreditSummary(@PathVariable Long customerId){
        return creditSummaryService.getSummary(customerId);
    }
}
