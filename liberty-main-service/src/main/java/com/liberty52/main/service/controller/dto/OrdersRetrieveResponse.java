package com.liberty52.main.service.controller.dto;

import com.liberty52.main.global.util.Utils;
import com.liberty52.main.service.entity.CustomProductOption;
import com.liberty52.main.service.entity.Orders;
import com.liberty52.main.service.entity.payment.Payment;
import com.liberty52.main.service.entity.payment.Payment.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.liberty52.main.global.constants.RepresentImageUrl.LIBERTY52_FRAME_REPRESENTATIVE_URL;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrdersRetrieveResponse {

    private String orderId;
    private String orderDate;
    private String orderStatus;
    private String address;
    private String receiverName;
    private String receiverEmail;
    private String receiverPhoneNumber;
    private String productRepresentUrl;
    private String orderNum;
    private String paymentType;
    private PaymentInfo paymentInfo;
    private List<OrderRetrieveProductResponse> products;


    public OrdersRetrieveResponse(Orders orders) {
        this.orderId = orders.getId();
        this.orderDate = orders.getOrderedAt().format(Utils.DATE_FORMAT_DATE);
        this.orderStatus = orders.getOrderStatus().getKoName();
        this.address = orders.getOrderDestination().getAddress1() + " " + orders.getOrderDestination().getAddress2();
        this.receiverName = orders.getOrderDestination().getReceiverName();
        this.receiverEmail = orders.getOrderDestination().getReceiverEmail();
        this.receiverPhoneNumber = orders.getOrderDestination().getReceiverPhoneNumber();
        orders.getCustomProducts().stream().findFirst().ifPresent(c -> this.productRepresentUrl = c.getProduct().getPictureUrl());
        this.orderNum = orders.getOrderNum();
        Payment payment = orders.getPayment();
        if (payment != null) {
            this.paymentType = payment.getType().getKorName();
            this.paymentInfo = payment.getInfoAsDto();
        }
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


    }

    public OrdersRetrieveResponse(String orderId, String orderDate, String orderStatus,
                                  String address,
                                  String receiverName, String receiverEmail, String receiverPhoneNumber,
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
    }
}
