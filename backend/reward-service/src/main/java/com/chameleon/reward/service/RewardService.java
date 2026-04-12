package com.chameleon.reward.service;

import com.chameleon.reward.Respository.RewardRespository;
import com.chameleon.reward.model.Reward;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RewardService {

    private RewardRespository rewardRespository;

    public Optional<Reward> getByCustomerId(String customerId){
        return rewardRespository.findByCustomerId(customerId);
    }

    public Reward getOrCreate(String customerId, String category){
        return rewardRespository.findByCustomerId(customerId).orElse(Reward.builder()
                .customerId(customerId)
                .totalPoints(0.0)
                .currentCategory(category)
                .build());
    }
}
