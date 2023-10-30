package com.liberty52.product.service.controller.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.liberty52.product.service.controller.dto.QOrderRetrieveProductResponse is a Querydsl Projection type for OrderRetrieveProductResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOrderRetrieveProductResponse extends ConstructorExpression<OrderRetrieveProductResponse> {

    private static final long serialVersionUID = -1101816024L;

    public QOrderRetrieveProductResponse(com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Integer> quantity, com.querydsl.core.types.Expression<Long> price) {
        super(OrderRetrieveProductResponse.class, new Class<?>[]{String.class, int.class, long.class}, name, quantity, price);
    }

    public QOrderRetrieveProductResponse(com.querydsl.core.types.Expression<String> customProductId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Integer> quantity, com.querydsl.core.types.Expression<Long> price, com.querydsl.core.types.Expression<String> productUrl, com.querydsl.core.types.Expression<Boolean> hasReview, com.querydsl.core.types.Expression<? extends java.util.List<String>> options) {
        super(OrderRetrieveProductResponse.class, new Class<?>[]{String.class, String.class, int.class, long.class, String.class, boolean.class, java.util.List.class}, customProductId, name, quantity, price, productUrl, hasReview, options);
    }

}

