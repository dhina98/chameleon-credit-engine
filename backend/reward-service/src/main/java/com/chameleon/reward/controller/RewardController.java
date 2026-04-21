package com.chameleon.reward.controller;

import com.chameleon.reward.model.Reward;
import com.chameleon.reward.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @GetMapping("/{customerId}")
    public ResponseEntity<Reward> getReward(@PathVariable("customerId") String customerId){
        return rewardService.getByCustomerId(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{customerId}/customerId/{category}/category")
    public ResponseEntity<Reward> getByCategory(@PathVariable("customerId") String customerId, String category){

        Reward reward = rewardService.getOrCreate(customerId,category);
        return ResponseEntity.ok(reward);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health(){
        return ResponseEntity.ok("Reward service is UP");
    }
}
