package com.liberty52.main.service.utils;

import com.liberty52.main.service.entity.OrderStatus;

public class MockConstants {

    public static final String MOCK_AUTH_ID = "IAmAuthId";

    public static final String MOCK_ORDER_ID = "MOCK_ORDER_ID";
    public static final String MOCK_ADDRESS = "MOCK_ADDRESS";
    public static final String MOCK_RECEIVER_NAME = "MOCK_RECEIVER_NAME";
    public static final String MOCK_RECEIVER_EMAIL = "MOCK_RECEIVER_EMAIL";
    public static final String MOCK_RECEIVER_PHONE_NUMBER = "MOCK_RECEIVER_PHONE_NUMBER";
    public static final String MOCK_PRODUCT_REPRESENT_URL = "MOCK_PRODUCT_REPRESENT_URL";

    public static final String MOCK_LICENSE_ART_URL = "MOCK_LICENSE_ART_URL";
    public static final String MOCK_LICENSE_ART_NAME = "MOCK_LICENSE_ART_NAME";
    public static final String MOCK_LICENSE_ARTIST_NAME = "MOCK_LICENSE_ARTIST_NAME";

    public static final String MOCK_PRODUCT_NAME = "MOCK_PRODUCT_NAME";
    public static final int MOCK_QUANTITY = 1;
    public static final Long MOCK_PRICE = 10000000L;
    public static final int MOCK_LIST_SIZE = 3;
    public static final OrderStatus MOCK_ORDER_STATUS_ORDERED = OrderStatus.ORDERED;

    public static final long MOCK_TOTAL_PRODUCT_PRICE = MOCK_PRICE * MOCK_LIST_SIZE;
    public static final int MOCK_DELIVERY_FEE = 0;
    public static final long MOCK_TOTAL_PRICE = MOCK_TOTAL_PRODUCT_PRICE + MOCK_DELIVERY_FEE;

    public static final String MOCK_AUTHOR_NAME = "MOCK_AUTHOR_NAME";
    public static final String MOCK_AUTHOR_PROFILE_URL = "MOCK_AUTHOR_PROFILE_URL";

    public static final String MOCK_CUSTOM_PRODUCT_ID = "MOCK_CUSTOM_PRODUCT_ID";

}
