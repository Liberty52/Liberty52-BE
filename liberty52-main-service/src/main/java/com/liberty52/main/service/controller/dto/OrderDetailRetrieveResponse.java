package com.liberty52.main.service.controller.dto;

import com.liberty52.main.global.util.Utils;
import com.liberty52.main.service.entity.CustomProductOption;
import com.liberty52.main.service.entity.OrderDestination;
import com.liberty52.main.service.entity.Orders;
import com.liberty52.main.service.entity.payment.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.liberty52.main.global.constants.RepresentImageUrl.LIBERTY52_FRAME_REPRESENTATIVE_URL;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailRetrieveResponse {

    private String orderId;
    private String orderDate;
    private String orderStatus;
    private String address;
    private String receiverEmail;
    private String receiverName;
    private String receiverPhoneNumber;
    private String productRepresentUrl;
    private long totalProductPrice;
    private int deliveryFee;
    private long totalPrice;
    private String orderNum;
    private String paymentType;
    private Payment.PaymentInfo paymentInfo;
    private List<OrderRetrieveProductResponse> products;
    private String customerName;
    private OrderDeliveryDto orderDelivery;

    public OrderDetailRetrieveResponse(Orders orders) {
        this.orderId = orders.getId();
        this.orderDate = orders.getOrderedAt().format(Utils.DATE_FORMAT_DATE);
        this.orderStatus = orders.getOrderStatus().getKoName();
        OrderDestination destination = orders.getOrderDestination();
        this.address = destination.getAddress1() + " " + destination.getAddress2();
        this.receiverName = destination.getReceiverName();
        this.receiverEmail = destination.getReceiverEmail();
        this.receiverPhoneNumber = destination.getReceiverPhoneNumber();
        orders.getCustomProducts().stream().findFirst().ifPresent(c -> this.productRepresentUrl = c.getProduct().getPictureUrl());

        this.orderNum = orders.getOrderNum();

        this.products = orders.getCustomProducts().stream().map(c -> {
            Long price = (c.getCustomLicenseOption() != null && c.getCustomLicenseOption().getLicenseOptionDetail() != null)
                ? c.getCustomLicenseOption().getLicenseOptionDetail().getPrice()
                : 0L;

            String artUrl = (c.getCustomLicenseOption() != null && c.getCustomLicenseOption().getLicenseOptionDetail() != null)
                ? c.getCustomLicenseOption().getLicenseOptionDetail().getArtUrl()
                : "";

            String artName = (c.getCustomLicenseOption() != null && c.getCustomLicenseOption().getLicenseOptionDetail() != null)
                ? c.getCustomLicenseOption().getLicenseOptionDetail().getArtName()
                : "";

            String artistName = (c.getCustomLicenseOption() != null && c.getCustomLicenseOption().getLicenseOptionDetail() != null)
                ? c.getCustomLicenseOption().getLicenseOptionDetail().getArtistName()
                : "";

            return new OrderRetrieveProductResponse(c.getId(), c.getProduct().getName(), c.getQuantity(),
                c.getProduct().getPrice() + c.getOptions().stream().mapToLong(CustomProductOption::getPrice).sum() + price,
                c.getUserCustomPictureUrl(), c.getReview() != null, c.getOptions().stream().map(CustomProductOption::getDetailName).toList(),
                c.getProduct().isCustom(), artUrl, artName, artistName);
        }).toList();


        this.deliveryFee = orders.getDeliveryPrice();
        this.totalPrice = orders.getAmount();
        this.totalProductPrice = totalPrice - deliveryFee;
        Payment payment = orders.getPayment();
        if (payment != null) {
            this.paymentType = payment.getType().getKorName();
            this.paymentInfo = payment.getInfoAsDto();
        }
        this.orderDelivery = OrderDeliveryDto.of(orders.getOrderDelivery());
    }

    public OrderDetailRetrieveResponse(String orderId, String orderDate, String orderStatus,
                                       String address,
                                       String receiverName, String receiverEmail, String receiverPhoneNumber,
                                       int deliveryFee,
                                       List<OrderRetrieveProductResponse> products) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.receiverName = receiverName;
        this.receiverEmail = receiverEmail;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.productRepresentUrl = LIBERTY52_FRAME_REPRESENTATIVE_URL;
        this.products = products;
        this.deliveryFee = deliveryFee;
        this.products.forEach(p ->
                this.totalProductPrice += p.getPrice());
        this.totalPrice = totalProductPrice + deliveryFee;
    }

    public static OrderDetailRetrieveResponse of(Orders entity, String customerName) {
        OrderDetailRetrieveResponse response = new OrderDetailRetrieveResponse(entity);
        response.customerName = customerName;
        response.orderDelivery = OrderDeliveryDto.of(entity.getOrderDelivery());
        return response;
    }

}
