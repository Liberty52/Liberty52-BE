package com.liberty52.product.service.applicationservice.impl;

import com.liberty52.product.global.exception.external.badrequest.BadRequestException;
import com.liberty52.product.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.service.controller.dto.AdminAddOrderDeliveryDto;
import com.liberty52.product.service.repository.OrdersRepository;
import com.liberty52.product.service.utils.MockFactory;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderDeliveryServiceImplTest {

    @InjectMocks
    private OrderDeliveryServiceImpl service;

    @Mock
    private CourierCompanyClient client;

    @Mock
    private OrdersRepository ordersRepository;

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

    @Test
    void 주문의_주문배송정보를_추가한다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyCode("01")
                .courierCompanyName("courier")
                .trackingNumber("1234567890")
                .build();

        var companyList = new ArrayList<CourierCompanyListDto.CompanyResponse>();
        companyList.add(new CourierCompanyListDto.CompanyResponse(
                false, "01", "courier"
        ));
        var courierCompanyList = new CourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(courierCompanyList);

        var order = MockFactory.createOrder("1");
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));

        // when
        var result = service.add(order.getId(), request);

        // then
        assertNotNull(result);
        assertEquals(order.getId(), result.orderId());
        assertEquals(request.courierCompanyCode(), result.courierCompanyCode());
        assertEquals(request.courierCompanyName(), result.courierCompanyName());
        assertEquals(request.trackingNumber(), result.trackingNumber());
    }

    @Test
    void 택배사코드_없이_주문배송정보를_추가할_수_없다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyName("courier")
                .trackingNumber("1234567890")
                .build();
        // when
        // then
        assertEquals(
                "모든 파라미터를 입력해주세요",
                assertThrows(
                        BadRequestException.class,
                        () -> service.add("1", request)
                ).getErrorMessage()
        );
    }

    @Test
    void 택배사이름_없이_주문배송정보를_추가할_수_없다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyCode("01")
                .trackingNumber("1234567890")
                .build();
        // when
        // then
        assertEquals(
                "모든 파라미터를 입력해주세요",
                assertThrows(
                        BadRequestException.class,
                        () -> service.add("1", request)
                ).getErrorMessage()
        );
    }

    @Test
    void 운송장번호_없이_주문배송정보를_추가할_수_없다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyCode("01")
                .courierCompanyName("courier")
                .build();
        // when
        // then
        assertEquals(
                "모든 파라미터를 입력해주세요",
                assertThrows(
                        BadRequestException.class,
                        () -> service.add("1", request)
                ).getErrorMessage()
        );
    }

    @Test
    void 택배사검증에서_반환값이_없는_경우_주문배송정보를_추가할_수_없다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyCode("01")
                .courierCompanyName("courier")
                .trackingNumber("1234567890")
                .build();
        given(client.getCourierCompanyList())
                .willReturn(null);
        // when
        // then
        assertEquals(
                "택배사 검증에 실패하였습니다. 관리자에게 문의해주세요",
                assertThrows(
                        InternalServerErrorException.class,
                        () -> service.add("1", request)
                ).getErrorMessage()
        );
    }

    @Test
    void 택배사검증에서_택배사_리스트가_없는_경우_주문배송정보를_추가할_수_없다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyCode("01")
                .courierCompanyName("courier")
                .trackingNumber("1234567890")
                .build();
        given(client.getCourierCompanyList())
                .willReturn(CourierCompanyListDto.builder().build());
        // when
        // then
        assertEquals(
                "택배사 검증에 실패하였습니다. 관리자에게 문의해주세요",
                assertThrows(
                        InternalServerErrorException.class,
                        () -> service.add("1", request)
                ).getErrorMessage()
        );
    }

    @Test
    void 택배사검증에서_택배사_리스트가_비어있는_경우_주문배송정보를_추가할_수_없다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyCode("01")
                .courierCompanyName("courier")
                .trackingNumber("1234567890")
                .build();
        given(client.getCourierCompanyList())
                .willReturn(CourierCompanyListDto.builder()
                        .companies(new ArrayList<>())
                        .build());
        // when
        // then
        assertEquals(
                "택배사 검증에 실패하였습니다. 관리자에게 문의해주세요",
                assertThrows(
                        InternalServerErrorException.class,
                        () -> service.add("1", request)
                ).getErrorMessage()
        );
    }

    @Test
    void 택배사검증에서_일치하는_택배사코드가_없는_경우_주문배송정보를_추가할_수_없다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyCode("1000")
                .courierCompanyName("courier")
                .trackingNumber("1234567890")
                .build();

        var companyList = new ArrayList<CourierCompanyListDto.CompanyResponse>();
        companyList.add(new CourierCompanyListDto.CompanyResponse(
                false, "01", "courier"
        ));
        var courierCompanyList = new CourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(courierCompanyList);
        // when
        // then
        assertEquals(
                "유효하지 않는 택배사입니다",
                assertThrows(
                        BadRequestException.class,
                        () -> service.add("1", request)
                ).getErrorMessage()
        );
    }

    @Test
    void 택배사검증에서_일치하는_택배사이름이_없는_경우_주문배송정보를_추가할_수_없다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyCode("01")
                .courierCompanyName("wrong")
                .trackingNumber("1234567890")
                .build();

        var companyList = new ArrayList<CourierCompanyListDto.CompanyResponse>();
        companyList.add(new CourierCompanyListDto.CompanyResponse(
                false, "01", "courier"
        ));
        var courierCompanyList = new CourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(courierCompanyList);
        // when
        // then
        assertEquals(
                "유효하지 않는 택배사입니다",
                assertThrows(
                        BadRequestException.class,
                        () -> service.add("1", request)
                ).getErrorMessage()
        );
    }

    @Test
    void 존재하지_않는_주문에_대해서_주문배송정보를_추가할_수_없다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyCode("01")
                .courierCompanyName("courier")
                .trackingNumber("1234567890")
                .build();

        var companyList = new ArrayList<CourierCompanyListDto.CompanyResponse>();
        companyList.add(new CourierCompanyListDto.CompanyResponse(
                false, "01", "courier"
        ));
        var courierCompanyList = new CourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(courierCompanyList);

        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.empty());

        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.add("1", request)
        );
    }
}
