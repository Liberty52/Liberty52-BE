package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.main.global.adapter.courier.api.smartcourier.SmartCourierCompanyClient;
import com.liberty52.main.global.adapter.courier.api.smartcourier.dto.SmartCourierCompanyListDto;
import com.liberty52.main.global.exception.external.forbidden.ForbiddenException;
import com.liberty52.main.global.exception.external.internalservererror.InternalServerErrorException;
import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.controller.dto.AdminAddOrderDeliveryDto;
import com.liberty52.main.service.repository.OrdersRepository;
import com.liberty52.main.service.utils.MockFactory;
import com.liberty52.main.service.utils.MockUser;
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
    private SmartCourierCompanyClient client;

    @Mock
    private OrdersRepository ordersRepository;

    @Test
    @DisplayName("관리자가 국내 택배사 100건을 조회한다")
    void getKoreanCourierCompanyList() {
        // given
        var size = 100;
        var companyList = new ArrayList<SmartCourierCompanyListDto.CompanyResponse>();
        for (int i = 0; i < size; i++) {
            companyList.add(new SmartCourierCompanyListDto.CompanyResponse(
                    false, "code_k_" + i, "name_k_" + i
            ));
        }
        for (int i = 0; i < 200; i++) {
            companyList.add(new SmartCourierCompanyListDto.CompanyResponse(
                    true, "code_g_" + i, "name_g_" + i
            ));
        }
        Collections.shuffle(companyList);
        var clientResponse = new SmartCourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(clientResponse.asMap());
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
        var companyList = new ArrayList<SmartCourierCompanyListDto.CompanyResponse>();
        for (int i = 0; i < size; i++) {
            companyList.add(new SmartCourierCompanyListDto.CompanyResponse(
                    true, "code_g_" + i, "name_g_" + i
            ));
        }
        for (int i = 0; i < 200; i++) {
            companyList.add(new SmartCourierCompanyListDto.CompanyResponse(
                    false, "code_k_" + i, "name_k_" + i
            ));
        }
        Collections.shuffle(companyList);
        var clientResponse = new SmartCourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(clientResponse.asMap());
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
        var clientResponse = new SmartCourierCompanyListDto(null);
        given(client.getCourierCompanyList())
                .willReturn(clientResponse.asMap());
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
        var clientResponse = new SmartCourierCompanyListDto(new ArrayList<>());
        given(client.getCourierCompanyList())
                .willReturn(clientResponse.asMap());
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

        var companyList = new ArrayList<SmartCourierCompanyListDto.CompanyResponse>();
        companyList.add(new SmartCourierCompanyListDto.CompanyResponse(
                false, "01", "courier"
        ));
        var courierCompanyList = new SmartCourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(courierCompanyList.asMap());

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
    void 주문의_주문배송정보가_이미_존재할_경우_주문배송정보를_수정한다() {
        // given
        var request = AdminAddOrderDeliveryDto.Request.builder()
                .courierCompanyCode("01")
                .courierCompanyName("courier")
                .trackingNumber("1234567890")
                .build();

        var companyList = new ArrayList<SmartCourierCompanyListDto.CompanyResponse>();
        companyList.add(new SmartCourierCompanyListDto.CompanyResponse(
                false, "01", "courier"
        ));
        var courierCompanyList = new SmartCourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(courierCompanyList.asMap());

        var order = MockFactory.createOrder("1");
        MockFactory.orderDelivery("04", "o_name", "o_tn", order);
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
                .willReturn(SmartCourierCompanyListDto.builder().build().asMap());
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
                .willReturn(SmartCourierCompanyListDto.builder()
                        .companies(new ArrayList<>())
                        .build().asMap());
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

        var companyList = new ArrayList<SmartCourierCompanyListDto.CompanyResponse>();
        companyList.add(new SmartCourierCompanyListDto.CompanyResponse(
                false, "01", "courier"
        ));
        var courierCompanyList = new SmartCourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(courierCompanyList.asMap());
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

        var companyList = new ArrayList<SmartCourierCompanyListDto.CompanyResponse>();
        companyList.add(new SmartCourierCompanyListDto.CompanyResponse(
                false, "01", "courier"
        ));
        var courierCompanyList = new SmartCourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(courierCompanyList.asMap());
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

        var companyList = new ArrayList<SmartCourierCompanyListDto.CompanyResponse>();
        companyList.add(new SmartCourierCompanyListDto.CompanyResponse(
                false, "01", "courier"
        ));
        var courierCompanyList = new SmartCourierCompanyListDto(companyList);
        given(client.getCourierCompanyList())
                .willReturn(courierCompanyList.asMap());

        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.empty());

        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.add("1", request)
        );
    }

    @Test
    void 유저가_자신의_주문에_대한_실시간배송정보를_조회한다() {
        // given
        var user = MockUser.user();
        var order = MockFactory.createOrder(user.getUserId());
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));

        MockFactory.orderDelivery("code", "name", "1234567890", order);

        given(client.getDeliveryInfoRedirectUrl(anyString(), anyString()))
                .willReturn("Response from Courier Client");
        // when
        var result = service.getRealTimeDeliveryInfoRedirectUrl(user, order.getId(), "code", "1234567890");
        // then
        assertEquals("Response from Courier Client", result);
    }

    @Test
    void 유저가_존재하지_않는_주문의_실시간배송정보를_조회할_수_없다() {
        // given
        var user = MockUser.user();
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getRealTimeDeliveryInfoRedirectUrl(user, "order", "code", "number")
        );
    }

    @Test
    void 유저가_자신의_주문이_아닌_주문에_대한_실시간배송정보를_조회할_수_없다() {
        // given
        var user = MockUser.user();
        var order = MockFactory.createOrder("not_yours");
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));
        // when
        // then
        assertThrows(
                ForbiddenException.class,
                () -> service.getRealTimeDeliveryInfoRedirectUrl(user, "order", "code", "number")
        );
    }

    @Test
    void 유저가_주문배송정보가_존재하지_않는_주문에_대한_실시간배송정보를_조회할_수_없다() {
        // given
        var user = MockUser.user();
        var order = MockFactory.createOrder(user.getUserId());
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));
        // when
        // then
        var ex = assertThrows(
                ResourceNotFoundException.class,
                () -> service.getRealTimeDeliveryInfoRedirectUrl(user, "order", "code", "number")
        );
        assertEquals("orderDelivery is not exist.", ex.getErrorMessage());
    }

    @Test
    void 유저가_자신의_주문에_대한_실시간배송정보를_잘못된_택배사코드로_조회할_수_없다() {
        // given
        var user = MockUser.user();
        var order = MockFactory.createOrder(user.getUserId());
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));
        MockFactory.orderDelivery("code", "name", "number", order);
        // when
        // then
        var ex = assertThrows(
                BadRequestException.class,
                () -> service.getRealTimeDeliveryInfoRedirectUrl(user, "order", "wrong", "number")
        );
        assertEquals("배송정보가 일치하지 않습니다", ex.getErrorMessage());
    }

    @Test
    void 관리자가_유저의_주문에_대한_실시간배송정보를_조회한다() {
        // given
        var user = MockUser.admin();
        var order = MockFactory.createOrder("user-id");
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));
        MockFactory.orderDelivery("code", "name", "number", order);

        given(client.getDeliveryInfoRedirectUrl(anyString(), anyString()))
                .willReturn("Response from Courier Client");
        // when
        var result = service.getRealTimeDeliveryInfoRedirectUrl(user, order.getId(), "code", "number");
        // then
        assertEquals("Response from Courier Client", result);
    }

    @Test
    void 관리자가_존재하지_않는_주문에_대한_실시간배송정보를_조회할_수_없다() {
        // given
        var user = MockUser.admin();
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getRealTimeDeliveryInfoRedirectUrl(user, "order", "code", "number")
        );
    }

    @Test
    void 관리자가_주문의_배송정보가_존재하지_않는_경우_실시간배송정보를_조회할_수_없다() {
        // given
        var user = MockUser.admin();
        var order = MockFactory.createOrder("user-id");
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getRealTimeDeliveryInfoRedirectUrl(user, order.getId(), "code", "number")
        );
    }

    @Test
    void 관리자가_잘못된_택배사코드로_실시간배송정보를_조회할_수_없다() {
        // given
        var user = MockUser.admin();
        var order = MockFactory.createOrder("user-id");
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));
        MockFactory.orderDelivery("code", "name", "number", order);
        // when
        // then
        var ex = assertThrows(
                BadRequestException.class,
                () -> service.getRealTimeDeliveryInfoRedirectUrl(user, order.getId(), "wrong", "number")
        );
        assertEquals("배송정보가 일치하지 않습니다", ex.getErrorMessage());
    }

    @Test
    void 관리자가_잘못된_운송장번호로_실시간배송정보를_조회할_수_없다() {
        // given
        var user = MockUser.admin();
        var order = MockFactory.createOrder("user-id");
        given(ordersRepository.findById(anyString()))
                .willReturn(Optional.of(order));
        MockFactory.orderDelivery("code", "name", "number", order);
        // when
        // then
        var ex = assertThrows(
                BadRequestException.class,
                () -> service.getRealTimeDeliveryInfoRedirectUrl(user, order.getId(), "code", "wrong")
        );
        assertEquals("배송정보가 일치하지 않습니다", ex.getErrorMessage());
    }

    @Test
    void 비회원_유저가_자신의_주문에_대한_실시간배송정보를_조회한다() {
        // given
        var guest = MockUser.guest();
        var order = MockFactory.createOrder(guest.getUserId());
        given(ordersRepository.findByOrderNum(anyString()))
                .willReturn(Optional.of(order));
        MockFactory.orderDelivery("code", "name", "1234567890", order);

        given(client.getDeliveryInfoRedirectUrl(anyString(), anyString()))
                .willReturn("Response from Courier Client");
        // when
        var result = service.getGuestRealTimeDeliveryInfoRedirectUrl(guest, order.getOrderNum(), "code", "1234567890");
        // then
        assertEquals("Response from Courier Client", result);
    }

    @Test
    void 비회원_유저가_존재하지_않는_주문의_실시간배송정보를_조회할_수_없다() {
        // given
        var guest = MockUser.guest();
        given(ordersRepository.findByOrderNum(anyString()))
                .willReturn(Optional.empty());
        // when
        // then
        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getGuestRealTimeDeliveryInfoRedirectUrl(guest, "order", "code", "number")
        );
    }

    @Test
    void 비회원_유저가_자신의_주문이_아닌_주문에_대한_실시간배송정보를_조회할_수_없다() {
        // given
        var guest = MockUser.guest();
        var order = MockFactory.createOrder("not_yours");
        given(ordersRepository.findByOrderNum(anyString()))
                .willReturn(Optional.of(order));
        // when
        // then
        assertThrows(
                ForbiddenException.class,
                () -> service.getGuestRealTimeDeliveryInfoRedirectUrl(guest, "order", "code", "number")
        );
    }

    @Test
    void 비회원_유저가_주문배송정보가_존재하지_않는_주문에_대한_실시간배송정보를_조회할_수_없다() {
        // given
        var guest = MockUser.guest();
        var order = MockFactory.createOrder(guest.getUserId());
        given(ordersRepository.findByOrderNum(anyString()))
                .willReturn(Optional.of(order));
        // when
        // then
        var ex = assertThrows(
                ResourceNotFoundException.class,
                () -> service.getGuestRealTimeDeliveryInfoRedirectUrl(guest, "order", "code", "number")
        );
        assertEquals("orderDelivery is not exist.", ex.getErrorMessage());
    }

    @Test
    void 비회원_유저가_자신의_주문에_대한_실시간배송정보를_잘못된_택배사코드로_조회할_수_없다() {
        // given
        var guest = MockUser.guest();
        var order = MockFactory.createOrder(guest.getUserId());
        given(ordersRepository.findByOrderNum(anyString()))
                .willReturn(Optional.of(order));
        MockFactory.orderDelivery("code", "name", "number", order);
        // when
        // then
        var ex = assertThrows(
                BadRequestException.class,
                () -> service.getGuestRealTimeDeliveryInfoRedirectUrl(guest, "order", "wrong", "number")
        );
        assertEquals("배송정보가 일치하지 않습니다", ex.getErrorMessage());
    }
}
