package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.OrderRetrieveService;
import com.liberty52.product.service.controller.dto.OrdersRetrieveResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderRetrieveController {

    private final OrderRetrieveService orderRetrieveService;

    @GetMapping("/product/orders")
    public ResponseEntity<List<OrdersRetrieveResponse>> retrieveOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        return ResponseEntity.ok(orderRetrieveService.retrieveOrders(authorization));
    }

}
