package com.chameleon.notificationservice.controller;

import com.chameleon.notificationservice.Repository.NotificationRepository;
import com.chameleon.notificationservice.model.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
//@RequiredArgsConstructor
public class NotificationController {

    private NotificationRepository notificationRepository;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Notification>> get(@PathVariable String customerId){

        return ResponseEntity.ok(notificationRepository.findByCustomerIdOrderByNotifiedAtDesc(customerId));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id){
        notificationRepository.findById(id).
           ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Notification Service is healthy");
    }
}

