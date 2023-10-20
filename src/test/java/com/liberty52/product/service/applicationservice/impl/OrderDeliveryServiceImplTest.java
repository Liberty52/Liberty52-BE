package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.smartcourier.api.CourierCompanyClient;
import com.liberty52.smartcourier.api.dto.CourierCompanyListDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderDeliveryServiceImplTest {

    @InjectMocks
    private OrderDeliveryServiceImpl service;

    @Mock
    private CourierCompanyClient client;

    @Test
    @DisplayName("관리자가 국내 택배사 100건을 조회한다")
    void getKoreanCourierCompanyList() {
        // given
        var size = 100;
        var companyList = new ArrayList<CourierCompanyListDto.CompanyResponse>();
        for (int i = 0; i < size; i++) {
            companyList.add(new CourierCompanyListDto.CompanyResponse(
                    false, "code_k_"+i, "name_k_"+i
            ));
        }
        for (int i = 0; i < 200; i++) {
            companyList.add(new CourierCompanyListDto.CompanyResponse(
                    true, "code_g_"+i, "name_g_"+i
            ));
        }
        Collections.shuffle(companyList);
        var clientResponse = new CourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(clientResponse);
        // when
        var result = service.getCourierCompanyList(false);
        // then
        assertFalse(result.meta().international());
        assertEquals(size, result.meta().totalCount());
        assertEquals(size, result.documents().size());
        result.documents().forEach(it -> {
            assertTrue(it.courierCode().startsWith("code_k_"));
            assertTrue(it.courierName().startsWith("name_k_"));
        });
    }

    @Test
    @DisplayName("관리자가 국제 택배사 150건을 조회한다")
    void getInternationalCourierCompanyList() {
        // given
        var size = 150;
        var companyList = new ArrayList<CourierCompanyListDto.CompanyResponse>();
        for (int i = 0; i < size; i++) {
            companyList.add(new CourierCompanyListDto.CompanyResponse(
                    true, "code_g_"+i, "name_g_"+i
            ));
        }
        for (int i = 0; i < 200; i++) {
            companyList.add(new CourierCompanyListDto.CompanyResponse(
                    false, "code_k_"+i, "name_k_"+i
            ));
        }
        Collections.shuffle(companyList);
        var clientResponse = new CourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(clientResponse);
        // when
        var result = service.getCourierCompanyList(true);
        // then
        assertTrue(result.meta().international());
        assertEquals(size, result.meta().totalCount());
        assertEquals(size, result.documents().size());
        result.documents().forEach(it -> {
            assertTrue(it.courierCode().startsWith("code_g_"));
            assertTrue(it.courierName().startsWith("name_g_"));
        });
    }

    @Test
    @DisplayName("관리자가 국내 택배사 리스트를 조회할 때 외부API의 응답이 null일 경우 조회할 수 없다")
    void getKoreanCourierCompanyList_when_clientResultIsNull() {
        // given
        given(client.getCourierCompanyList())
                .willReturn(null);
        // when
        // then
        assertEquals(
                "택배사 리스트 조회가 실패하였습니다. 관리자에게 문의해주세요",
                assertThrows(
                        InternalServerErrorException.class,
                        () -> service.getCourierCompanyList(false)
                ).getErrorMessage()
        );
    }

    @Test
    @DisplayName("관리자가 국제 택배사 리스트를 조회할 때 외부API의 응답 중 택배사 리스트가 null일 경우 조회할 수 없다")
    void getKoreanCourierCompanyList_when_companiesOfClientResultIsNull() {
        // given
        var clientResponse = new CourierCompanyListDto(null);
        given(client.getCourierCompanyList())
                .willReturn(clientResponse);
        // when
        // then
        assertEquals(
                "택배사 리스트 조회가 실패하였습니다. 관리자에게 문의해주세요",
                assertThrows(
                        InternalServerErrorException.class,
                        () -> service.getCourierCompanyList(false)
                ).getErrorMessage()
        );
    }

    @Test
    @DisplayName("관리자가 국내 택배사 리스트를 조회할 때 외부API의 응답 중 택배사 리스트가 비어있을 경우 조회할 수 없다")
    void getKoreanCourierCompanyList_when_companiesOfClientResultIsEmpty() {
        // given
        var clientResponse = new CourierCompanyListDto(new ArrayList<>());
        given(client.getCourierCompanyList())
                .willReturn(clientResponse);
        // when
        // then
        assertEquals(
                "택배사 리스트 조회가 실패하였습니다. 관리자에게 문의해주세요",
                assertThrows(
                        InternalServerErrorException.class,
                        () -> service.getCourierCompanyList(false)
                ).getErrorMessage()
        );
    }
}
