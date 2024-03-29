package com.liberty52.main.service.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liberty52.main.global.util.Utils;
import com.liberty52.main.service.entity.Orders;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminCanceledOrderListResponse {

    private List<AdminCanceledOrderContent> orders;
    private Long currentPage;
    private Long startPage;
    private Long lastPage;
    private Long totalLastPage;

    private AdminCanceledOrderListResponse(List<Orders> entities,
                                           Map<String, AuthClientDataResponse> customerInfos,
                                           Long currentPage, Long startPage, Long lastPage, Long totalLastPage) {
        this.orders = entities.stream()
                .map(AdminCanceledOrderContent::of)
                .peek(orderContent -> orderContent.setCustomerNames(customerInfos.get(orderContent.getCustomerId()).getAuthorName()))
                .toList();
        this.currentPage = currentPage;
        this.startPage = startPage;
        this.lastPage = lastPage;
        this.totalLastPage = totalLastPage;
    }

    public static AdminCanceledOrderListResponse empty() {
        return new AdminCanceledOrderListResponse(List.of(), 0L, 0L, 0L, 0L);
    }

    public static AdminCanceledOrderListResponse of(List<Orders> entities,
                                                    Map<String, AuthClientDataResponse> customerInfos,
                                                    Long currentPage, Long startPage, Long lastPage, Long totalLastPage) {
        return new AdminCanceledOrderListResponse(entities, customerInfos, currentPage, startPage, lastPage, totalLastPage);
    }

    @Getter
    public static class AdminCanceledOrderContent {
        private String orderId;
        private String orderNumber;
        private String productName;
        private String orderDate;
        @JsonIgnore
        private String customerId;
        private String customerName;
        private String orderStatus;
        private String reqAt;
        private String canceledAt;
        private String approvedAdminName;

        public static AdminCanceledOrderContent of(Orders entity) {
            AdminCanceledOrderContent response = new AdminCanceledOrderContent();
            response.orderId = entity.getId();
            response.orderNumber = entity.getOrderNum();
            response.productName = getProductName(entity);
            response.orderDate = entity.getOrderedAt().format(Utils.DATE_FORMAT_DATE);
            response.customerId = entity.getAuthId();
            response.orderStatus = entity.getOrderStatus().getKoName();
            response.reqAt = entity.getCanceledOrders().getReqAt().format(Utils.DATE_FORMAT_DATETIME);
            response.canceledAt = entity.getCanceledOrders().getCanceledAt() != null ?
                    entity.getCanceledOrders().getCanceledAt().format(Utils.DATE_FORMAT_DATETIME) : "대기중";
            response.approvedAdminName = entity.getCanceledOrders().getApprovedAdminName();
            return response;
        }

        private static String getProductName(Orders entity) {
            StringBuilder sb = new StringBuilder();
            if (entity.getCustomProducts().isEmpty())
                return "";
            sb.append(entity.getCustomProducts().get(0).getProduct().getName());
            if (entity.getCustomProducts().size() > 1) {
                sb.append(" 외 ").append(entity.getTotalQuantity()).append("건");
            }
            return sb.toString();
        }

        private void setCustomerNames(String customerName) {
            this.customerName = customerName;
        }
    }
}
