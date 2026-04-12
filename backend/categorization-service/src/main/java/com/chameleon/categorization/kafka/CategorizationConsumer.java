package com.chameleon.categorization.kafka;

import com.chameleon.categorization.dto.CategoryChangeEvent;
import com.chameleon.categorization.dto.TransactionEvent;
import com.chameleon.categorization.model.SpendingDataPoint;
import com.chameleon.categorization.repository.SpendingDataPointRepository;
import com.chameleon.categorization.service.KNNClassifierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategorizationConsumer {
    private SpendingDataPointRepository spendingDataPointRepository;
    private KNNClassifierService knnClassifierService;
    private final KafkaTemplate<String, CategoryChangeEvent> kafkaTemplate;

    private final Map<String, String> activeCategoryMap = new HashMap<>();

    @KafkaListener(topics = "transactions-event", groupId = "categorization-group")
    public void onTransaction(TransactionEvent event) {
        spendingDataPointRepository.save(SpendingDataPoint.builder().
                customerId(event.getCustomerId())
                .amount((event.getAmount()))
                .category(event.getCategory())
                .spentAt(event.getTransactionTime())
                .build());

        LocalDateTime from = LocalDateTime.now().minusDays(30);
        List<SpendingDataPoint> last30Days = spendingDataPointRepository.findByCustomerIdAndSpentAtAfter(event.getCustomerId(), from);
        String newCategory = knnClassifierService.classify(last30Days);
        String previousCatgeory = activeCategoryMap.getOrDefault(event.getCustomerId(), "GENERAL");

        if (!newCategory.equals(previousCatgeory)) {
            double getConfidenceLevel = knnClassifierService.calculateConfidence(last30Days, newCategory);
            if (getConfidenceLevel > 70.0) {
                log.info("Category changed: {} -> {} for customer {}",
                        previousCatgeory, newCategory, event.getCustomerId());
                kafkaTemplate.send("catagory-change-events",event.getCustomerId(), CategoryChangeEvent.builder()
                        .customerId(event.getCustomerId())
                        .oldCategory(previousCatgeory)
                        .newCategory(newCategory)
                        .confidence(getConfidenceLevel)
                        .changedAt(LocalDateTime.now())
                        .build());
                activeCategoryMap.put(event.getCustomerId(), newCategory);
            }
        }
    }
}
