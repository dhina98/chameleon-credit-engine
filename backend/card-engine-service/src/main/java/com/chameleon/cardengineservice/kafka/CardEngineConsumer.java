package com.chameleon.cardengineservice.kafka;

import com.chameleon.cardengineservice.Repository.CustomerCardRepository;
import com.chameleon.cardengineservice.dto.CategoryChangeEvent;
import com.chameleon.cardengineservice.dto.NotificationEvent;
import com.chameleon.cardengineservice.model.CustomerCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class CardEngineConsumer {

    private final CustomerCardRepository customerCardRepository;

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @KafkaListener(topics = "category-change-events", groupId ="card-engine-group")
    public void consumeCategoryChangeEvent(CategoryChangeEvent event){
        log.info("Received category change event for customer: {}, newCategory: {}",
                event.getCustomerId(),  event.getNewCategory());
        CustomerCard card = customerCardRepository.findByCustomerId(event.getCustomerId()).
                orElse(CustomerCard.builder()
                        .customerId(event.getCustomerId())
                        .cardId("CARD-"+event.getCustomerId()).build());
        card.setActiveCategory(event.getNewCategory());
        card.setLastUpdated(LocalDateTime.now());
        card.setKnnConfidence(event.getConfidence());
        card.setPreviousCategory(event.getOldCategory());
        customerCardRepository.save(card);

        String emoji = switch(event.getNewCategory()){
            case "FOOD" -> "🍕";
            case "TRAVEL" -> "✈️";
            case "ENTERTAINMENT" -> "🎬";
            case "SHOPPING" -> "🛍️";
            case "FUEL" -> "⛽";
            case "UTILITIES" -> "⚡";
            default -> "💳";
        };

        kafkaTemplate.send("notification-events",event.getCustomerId(), NotificationEvent.builder()
                .customerId(event.getCustomerId())
                .message(("Your Chameleon Card is now a " + event.getNewCategory()
                          + " Card " + emoji + "! Enhanced rewards activated."))
                .notifiedAt(LocalDateTime.now())
                .newCategory(event.getNewCategory())
                .previousCategory(event.getOldCategory())
                .build());
    }
}
