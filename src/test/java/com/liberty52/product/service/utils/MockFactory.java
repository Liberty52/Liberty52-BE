package com.liberty52.product.service.utils;

import com.liberty52.product.service.controller.dto.OrderDetailRetrieveResponse;
import com.liberty52.product.service.controller.dto.OrderRetrieveProductResponse;
import com.liberty52.product.service.controller.dto.OrdersRetrieveResponse;
import com.liberty52.product.service.controller.dto.ReviewRetrieveResponse;
import com.liberty52.product.service.entity.*;
import com.liberty52.product.service.entity.payment.BankType;
import com.liberty52.product.service.entity.payment.VBank;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.liberty52.product.service.utils.MockConstants.*;

public class MockFactory {
    public static CustomProduct createCustomProduct(String imageUrl, int quantity, String authId) {
        return CustomProduct.create(imageUrl, quantity, authId);
    }

    public static CustomProduct createCustomProduct(String imageUrl, int quantity, String authId, Product product) {
        var cp = CustomProduct.create(imageUrl, quantity, authId);
        cp.associateWithProduct(product);
        return cp;
    }

    public static CustomProduct createCustomProduct(String imageUrl, int quantity, String authId, Product product, Orders order) {
        var cp = CustomProduct.create(imageUrl, quantity, authId);
        cp.associateWithProduct(product);
        cp.associateWithOrder(order);
        return cp;
    }

    public static CustomProductOption createCustomProductOption(CustomProduct cp, OptionDetail od) {
        var cpo = CustomProductOption.create();
        cpo.associate(cp);
        cpo.associate(od);
        return cpo;
    }

    public static Product createProduct(String name, ProductState state, Long price, boolean isPremium) {
        return Product.create(name, state, price, isPremium);
    }

    public static Product createProduct(String name) {
        return createProduct(name, ProductState.ON_SALE, 10_000_000L, true);
    }

    public static Product createProduct(String name, Long price) {
        return createProduct(name, ProductState.ON_SALE, price,true);
    }

    public static ProductOption createProductOption(String name, boolean require) {
        return ProductOption.create(name, require, true);
    }

    public static OptionDetail createOptionDetail(String name, Integer price, Boolean onSale, Integer stock, ProductOption po) {
        var od =  OptionDetail.create(name, price, onSale, stock);
        od.associate(po);
        return od;
    }

    public static OptionDetail createOptionDetail(String name, Integer price) {
        return OptionDetail.create(name, price, true, 100);
    }

    public static OptionDetail createOptionDetail(String name, Integer price, Integer stock) {
        return createOptionDetail(name, price, true, stock, MockFactory.createProductOption("po", false));
    }

    public static OptionDetail createOptionDetail(String name, Integer price, Integer stock, ProductOption po) {
        return createOptionDetail(name, price, true, stock, po);
    }

    public static CustomProductOption createProductCartOption() {
        return CustomProductOption.create();
    }

    public static Cart createCart(String authId) {
        return Cart.create(authId);
    }

    public static Orders createOrder(String authId) {
        OrderDestination orderDestination = OrderDestination.create("", "", "", "", "", "");
        return Orders.create(authId, orderDestination);
    }

    public static OrderDelivery orderDelivery(
            String id,
            String code,
            String name,
            String trackingNumber,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Orders order
    ) {
        var od = OrderDelivery.builder()
                .courierCompanyCode(code)
                .courierCompanyName(name)
                .trackingNumber(trackingNumber)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        od.associate(order);
        try {
            var oId = od.getClass().getDeclaredField("id");
            oId.setAccessible(true);
            oId.set(od, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return od;
    }

    public static OrderDelivery orderDelivery(
            String id,
            String code,
            String name,
            String trackingNumber,
            Orders order
    ) {
        var now = LocalDateTime.now();
        return orderDelivery(id, code, name, trackingNumber, now, now, order);
    }

    public static OrderDelivery orderDelivery(
            String code,
            String name,
            String trackingNumber,
            Orders order
    ) {
        return orderDelivery("orderDeliveryId", code, name, trackingNumber, order);
    }

    public static OrderDelivery orderDelivery(Orders order) {
        return orderDelivery("orderDeliveryId", "code", "name", "1234567890", order);
    }

    public static List<OrdersRetrieveResponse> createMockOrderRetrieveResponseList(){
        List<OrdersRetrieveResponse> list = new ArrayList<>();
        for (int i = 0; i <MOCK_LIST_SIZE; i++) {
            list.add(createMockOrderRetrieveResponse());
        }
        return list;
    }

    public static OrdersRetrieveResponse createMockOrderRetrieveResponse(){
        return new OrdersRetrieveResponse(MOCK_ORDER_ID, LocalDate.now().toString(),
                MOCK_ORDER_STATUS_ORDERED.name(),MOCK_ADDRESS,MOCK_RECEIVER_NAME,MOCK_RECEIVER_EMAIL
        ,MOCK_RECEIVER_PHONE_NUMBER, createMockOrderRetrieveProductResponseList());
    }
    public static List<OrderRetrieveProductResponse> createMockOrderRetrieveProductResponseList(){
        List<OrderRetrieveProductResponse> list = new ArrayList<>();
        for (int i = 0; i < MOCK_LIST_SIZE; i++) {
            list.add(createMockOrderRetrieveProductResponse());
        }
        return list;
    }
    public static OrderRetrieveProductResponse createMockOrderRetrieveProductResponse(){
        return new OrderRetrieveProductResponse(
                MOCK_CUSTOM_PRODUCT_ID,
                MOCK_PRODUCT_NAME
                ,MOCK_QUANTITY
                ,MOCK_PRICE,
                MOCK_PRODUCT_REPRESENT_URL,
                false,
                null);
    }

    public static OrderDetailRetrieveResponse createMockOrderDetailRetrieveResponse(){
        return new OrderDetailRetrieveResponse(MOCK_ORDER_ID,LocalDate.now().toString(),
                MOCK_ORDER_STATUS_ORDERED.name(),MOCK_ADDRESS,MOCK_RECEIVER_NAME,MOCK_RECEIVER_EMAIL
                ,MOCK_RECEIVER_PHONE_NUMBER,
                MOCK_DELIVERY_FEE,createMockOrderRetrieveProductResponseList());
    }

    public static ReviewRetrieveResponse createMockReviewRetrieveResponse(){
        return new ReviewRetrieveResponse(List.of(createMockReview()), 1,1,1,1, MOCK_AUTH_ID);
    }

    public static Review createMockReview(){
        Review review = Review.create(3, "good");
        review.associate(createCustomProduct(MOCK_PRODUCT_REPRESENT_URL, 1, MOCK_AUTH_ID));

        ReviewImage.create(review,MOCK_PRODUCT_REPRESENT_URL);

        for(int i = 0; i<3; i++){
            Reply reply = Reply.create("맛있따"+i,MOCK_AUTH_ID);
            reply.associate(review);
        }

        return review;
    }

    public static VBank mockVBank() throws Exception {
        VBank vBank = VBank.of(BankType.HANA, "m_account", "m_holder");
        Field id = vBank.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(vBank, "m_vBankId");
        id.setAccessible(false);
        return vBank;
    }

    public static DeliveryOption mockDeliveryOptionOnlyFee(int fee) throws Exception {
        DeliveryOption option = DeliveryOption.feeOf(fee);
        Field id = option.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(option, 1L);
        id.setAccessible(false);
        return option;
    }


}
