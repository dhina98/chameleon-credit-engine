package com.chameleon.transactionservice.kafka;

import com.chameleon.transactionservice.dto.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionProducer {

    private static final String TOPIC = "transaction-events";

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public void publishTransactionEvent(TransactionEvent event) {
        log.info("Publishing transaction event for customer: {}, transactionId: {}",
                event.getCustomerId(), event.getTransactionId());
        kafkaTemplate.send(TOPIC, event.getCustomerId(), event);
    }

}
