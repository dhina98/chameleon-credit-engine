package com.chameleon.categorization.Controller;

import com.chameleon.categorization.model.SpendingDataPoint;
import com.chameleon.categorization.repository.SpendingDataPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorizationController {

    @Autowired
    private SpendingDataPointRepository spendingDataPointRepository;

    @GetMapping("/{customerId}/current")
    public ResponseEntity<List<SpendingDataPoint>> getCurrent(@PathVariable("customerId") String customerId) {
        LocalDateTime from = LocalDateTime.now().minusDays(30);
        List<SpendingDataPoint> getLastMonth = spendingDataPointRepository.findByCustomerIdAndSpentAtAfter(customerId, from);
        return ResponseEntity.ok(getLastMonth);
    }

    @GetMapping("/{customerId}/spending-points")
    public ResponseEntity<List<SpendingDataPoint>> getSpendingPoints(@PathVariable("customerId") String customerId) {
        List<SpendingDataPoint> points = spendingDataPointRepository.findByCustomerId(customerId);
        return ResponseEntity.ok(points);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Categorization Service is UP");
    }

}
