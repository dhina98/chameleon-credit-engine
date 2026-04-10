package com.chameleon.credithistory.kafka;

import com.chameleon.credithistory.dto.TransactionEvent;
import com.chameleon.credithistory.service.CreditHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreditHistoryConsumer {

    @Autowired
    public CreditHistoryService creditHistoryService;

    @KafkaListener(topics = "transaction-events", groupId = "credit-history-service")
    public void consumeTransactionEvent(TransactionEvent transactionEvent){
        log.info("Received transaction event for customer: {}, transactionId: {}",
                transactionEvent.getCustomerId(), transactionEvent.getTransactionId());
        creditHistoryService.saveHistory(transactionEvent);
    }
}
