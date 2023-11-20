package com.liberty52.auth.global.adapter.cloud;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerImpl implements
        KafkaProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void publishMemberDeletedEvent(String authId) {
        kafkaTemplate.send("member-deleted", authId);
    }
}
