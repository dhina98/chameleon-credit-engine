package com.chameleon.credithistory.service;

import com.chameleon.credithistory.dto.TransactionEvent;
import com.chameleon.credithistory.model.CreditHistory;
import com.chameleon.credithistory.repository.CreditHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditHistoryService {

    @Autowired
    private CreditHistoryRepository creditHistoryRepository;

    public CreditHistory saveHistory(TransactionEvent transactionEvent){
        CreditHistory creditHistory = CreditHistory.builder()
                .merchantName(transactionEvent.getMerchantName())
                .merchantCode(transactionEvent.getMerchantCode())
                .amount(transactionEvent.getAmount())
                .id(transactionEvent.getTransactionId())
                .customerId(transactionEvent.getCustomerId())
                .category(transactionEvent.getCategory())
                .transactionTime(transactionEvent.getTransactionTime())
                .status(transactionEvent.getStatus())
                .cardId(transactionEvent.getCardId())
                .build();

        CreditHistory saved = creditHistoryRepository.save(creditHistory);
        log.info("CreditHistory saved successfully to DB for customer: {}", saved.getCustomerId());
        return saved;
    }

    public List<CreditHistory> getAllByCustomer(String customerId){
        return creditHistoryRepository.findByCustomerId(customerId);
    }

    public List<CreditHistory> getLastNDays(String customerId, int days){
        LocalDateTime from = LocalDateTime.now().minusDays(days);
        return creditHistoryRepository.findByCustomerIdAndTransactionTimeAfter(customerId, from);
    }

    public List<CreditHistory> getByCategory(String customerId,String category){
        return creditHistoryRepository.findByCustomerIdAndCategory(customerId, category);
    }

    public CreditHistory getById(Long id){
        return creditHistoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Credit history not found for id: " + id));
    }
}
