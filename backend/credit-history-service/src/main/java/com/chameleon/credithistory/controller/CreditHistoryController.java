package com.chameleon.credithistory.controller;

import com.chameleon.credithistory.model.CreditHistory;
import com.chameleon.credithistory.service.CreditHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-history")
//@CrossOrigin(origins = "*") will learn later
public class CreditHistoryController {

    @Autowired
    private CreditHistoryService creditHistoryService;

    // GET /api/credit-history/{customerId} : All history records for a customer
    @GetMapping("/{customerId}")
    public ResponseEntity<List<CreditHistory>> getAll(@PathVariable String customerId) {
        List<CreditHistory> history = creditHistoryService.getAllByCustomer(customerId);
        if (history.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(history);
    }

    //GET api/credit-history//{customerId}/last30Days : Last 30 days of transactions for a customer
    @GetMapping("/{customerId}/last30Days")
    public ResponseEntity<List<CreditHistory>> getLast30Days(@PathVariable String customerId){
        return ResponseEntity.ok(creditHistoryService.getLastNDays(customerId, 30));
    }

    // fetch for Flexible window
    @GetMapping("/{customerId}/last-days/{days}")
    public ResponseEntity<List<CreditHistory>> getLastNdays(@PathVariable String customerId,@PathVariable int days ) {
        return ResponseEntity.ok(creditHistoryService.getLastNDays(customerId, days));
    }

    // GET /api/credit-history/{customerId}/category/{category} : Filter by spending category e.g. /category/FOOD
    @GetMapping("/{customerId}/category/{category}")
    public ResponseEntity<List<CreditHistory>> getByCategory(
            @PathVariable String customerId, @PathVariable String category) {
        return ResponseEntity.ok(
                creditHistoryService.getByCategory(customerId, category.toUpperCase()));
    }

    // GET /api/credit-history/record/{id} : Single record by database ID
    @GetMapping("/record/{id}")
    public ResponseEntity<CreditHistory> getById(@PathVariable Long id) {
        return ResponseEntity.ok(creditHistoryService.getById(id));
    }

    // GET /api/credit-history/health
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Credit History Service is UP");
    }

    }

