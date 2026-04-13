package com.chameleon.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEvent {

    private String customerId;
    private String message;
    private LocalDateTime notifiedAt;
    private String newCategory;
    private String previousCategory;
}
