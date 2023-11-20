package com.liberty52.main.global.adapter.courier;

import java.util.Map;

public interface CourierCompanyClient {
    <K, V> Map<K, V> getCourierCompanyList();

    String getDeliveryInfoRedirectUrl(String courierCode, String trackingNumber);
}
