package com.chameleon.notificationservice.kafka;

import com.chameleon.notificationservice.Repository.NotificationRepository;
import com.chameleon.notificationservice.dto.NotificationEvent;
import com.chameleon.notificationservice.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "notification-events", groupId = "notification-group")
    public void onNotification(NotificationEvent event){

        notificationRepository.save(Notification.builder()
                .customerId(event.getCustomerId())
                .message(event.getMessage())
                .previousCategory(event.getPreviousCategory())
                .newCategory(event.getNewCategory())
                .notifiedAt(event.getNotifiedAt())
                .read(false)
                .build());

    }
}
