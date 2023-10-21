package com.liberty52.product.global.adapter.courier;

import java.util.Map;

public interface CourierCompanyClient<K, V> {
    Map<K, V> getCourierCompanyList();
}
