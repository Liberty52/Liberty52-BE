package com.liberty52.product.service.controller;

import com.liberty52.product.service.applicationservice.OrderCancelService;
import com.liberty52.product.service.applicationservice.OrderDeliveryService;
import com.liberty52.product.service.applicationservice.OrderRetrieveService;
import com.liberty52.product.service.applicationservice.OrderStatusModifyService;
import com.liberty52.product.service.controller.dto.AdminCourierListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderAdminController.class)
class OrderAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private OrderRetrieveService orderRetrieveService;
    
    @MockBean
    private OrderCancelService orderCancelService;
    
    @MockBean
    private OrderStatusModifyService orderStatusModifyService;

    @MockBean
    private OrderDeliveryService orderDeliveryService;
    
    @Test
    @DisplayName("관리자가 국내 택배사 리스트 100건을 조회한다")
    void getKoreanCourierList() throws Exception {
        // given
        var meta = AdminCourierListDto.MetaResponse.builder()
                .international(false)
                .totalCount(100)
                .build();
        var documents = new ArrayList<AdminCourierListDto.DocumentResponse>();
        for (int i = 1; i <= 100; i++) {
            var doc = AdminCourierListDto.DocumentResponse.builder()
                    .courierCode("code_" + i)
                    .courierName("name_" + i)
                    .build();
            documents.add(doc);
        }
        var response = AdminCourierListDto.Response.builder()
                .meta(meta)
                .documents(documents)
                .build();
        given(orderDeliveryService.getCourierCompanyList(false))
                .willReturn(response);
        // when
        // then
        mockMvc.perform(
                get("/admin/orders/courier-companies")
                        .param("international", "false")
                        .header("LB-Role", "ADMIN")
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.meta.international").value(false),
                jsonPath("$.meta.totalCount").value(100),
                jsonPath("$.documents.size()").value(100)
        );
    }

    @Test
    @DisplayName("일반유저가 국내 택배사 리스트 100건을 조회할 수 없다")
    void getKoreanCourierList_notAdmin() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(
                get("/admin/orders/courier-companies")
                        .param("international", "false")
                        .header("LB-Role", "USER")
        ).andExpectAll(status().isForbidden());
    }
}