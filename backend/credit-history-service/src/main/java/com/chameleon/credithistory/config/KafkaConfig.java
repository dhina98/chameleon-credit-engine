package com.chameleon.credithistory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic transactionEventTopic(){
        return TopicBuilder.name("transactions-event").partitions(3).replicas(1).build();
    }
}
