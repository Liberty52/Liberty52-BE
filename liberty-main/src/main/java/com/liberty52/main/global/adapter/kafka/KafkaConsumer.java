package com.liberty52.main.global.adapter.kafka;

public interface KafkaConsumer {

    void consumeMemberDeletedEvent(String authId);

}
