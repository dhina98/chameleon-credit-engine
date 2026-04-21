package com.chameleon.reward.kafka;

import com.chameleon.reward.Respository.RewardRespository;
import com.chameleon.reward.dto.CategoryChangeEvent;
import com.chameleon.reward.dto.TransactionEvent;
import com.chameleon.reward.model.Reward;
import com.chameleon.reward.service.RewardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class RewardConsumer {

    private final RewardRespository rewardRespository;

    private final RewardService rewardService;

    @KafkaListener( topics = "transaction-events", groupId = "reward-group")
    public void onTransaction(TransactionEvent event) {
        log.info("Received transaction event for customer:{}",event.getCustomerId());
        double pts = 0.0;
        if (event.getCategory().equals("FOOD")) {
            pts = event.getAmount().doubleValue() * 0.05;
        }
        if (event.getCategory().equals("FUEL")) {
            pts = event.getAmount().doubleValue() * 0.15;
        }
        if (event.getCategory().equals("GROECERIES")) {
            pts = event.getAmount().doubleValue() * 0.10;
        }
        Reward rewards = rewardService.getOrCreate(event.getCustomerId(), event.getCategory());
        rewards.setTotalPoints(rewards.getTotalPoints() + pts);
        rewardRespository.save(rewards);
        log.info("Added {} points for {} to {} and Total {}", pts, event.getCustomerId(), event.getCategory(), rewards.getTotalPoints());
    }

    @KafkaListener(topics = "category-change-events", groupId = "reward-category-group")
    public void onCategoryChange(CategoryChangeEvent event) {
        log.info("Received category change event for customer: {}, oldCategory: {}, newCategory: {}",
                event.getCustomerId(), event.getOldCategory(), event.getNewCategory());
        rewardRespository.findByCustomerId(event.getCustomerId()).ifPresent(reward -> {
            reward.setCurrentCategory(event.getNewCategory());
            reward.setLastUpdated(LocalDateTime.now());
            rewardRespository.save(reward);
        });
    }
}
