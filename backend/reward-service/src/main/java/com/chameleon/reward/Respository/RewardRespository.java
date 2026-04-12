package com.chameleon.reward.Respository;


import com.chameleon.reward.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RewardRespository extends JpaRepository<Reward, String> {
    Optional<Reward> findByCustomerId(String customerId);
}
