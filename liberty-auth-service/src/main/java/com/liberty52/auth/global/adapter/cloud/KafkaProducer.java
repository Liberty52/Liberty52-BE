package com.liberty52.auth.global.adapter.cloud;

public interface KafkaProducer {

    void publishMemberDeletedEvent(String authId);


}
