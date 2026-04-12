package com.chameleon.transactionservice.service;

import com.chameleon.transactionservice.dto.TransactionEvent;
import com.chameleon.transactionservice.kafka.TransactionProducer;
import com.chameleon.transactionservice.model.Transaction;
import com.chameleon.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionProducer producer;

    @Transactional
    public Transaction process(Transaction txn){
        txn.setTransactionTime(LocalDateTime.now());
        txn.setStatus("SUCCESS");
        Transaction saved = transactionRepository.save(txn);
        producer.publishTransactionEvent(TransactionEvent.builder()
                .transactionId(saved.getId())
                .customerId(saved.getCustomerId())
                .amount(saved.getAmount())
                .category(saved.getCategory())
                .merchantName(saved.getMerchantName())
                .status(saved.getStatus())
                .transactionTime(saved.getTransactionTime())
                .cardId(saved.getCardId())
                .merchantCode(saved.getMerchantCode())
                .build());
        return saved;
    }

    public List<Transaction> getAll(String customerId){
       return transactionRepository.findByCustomerId(customerId);
    }

    public List<Transaction> last30Days(String customerId){
        return transactionRepository.findByCustomerIdAndTransactionTimeAfter(customerId, LocalDateTime.now().minusDays(30));
    }

}
