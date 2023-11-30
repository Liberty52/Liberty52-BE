package com.liberty52.main.service.applicationservice.impl;

import com.liberty52.common.exception.external.badrequest.BadRequestException;
import com.liberty52.common.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.global.exception.external.forbidden.InvalidRoleException;
import com.liberty52.main.service.controller.dto.AdminProductDeliveryOptionsDto;
import com.liberty52.main.service.repository.ProductRepository;
import com.liberty52.main.service.utils.MockFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductDeliveryOptionServiceImplTest {

    @InjectMocks
    private ProductDeliveryOptionServiceImpl service;

    @Mock
    private ProductRepository productRepository;

    @Nested
    @DisplayName("상품 배송옵션 추가")
    class Creation {
        @Test
        @DisplayName("관리자가 상품의 배송옵션을 추가한다")
        void create() {
            // given
            var product = MockFactory.createProduct("");
            given(productRepository.findById(anyString()))
                    .willReturn(Optional.of(product));
            var role = "ADMIN";
            var productId = product.getId();
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("courier_name")
                    .fee(100_000)
                    .build();
            // when
            var result = service.create(role, productId, dto);
            // then
            assertNotNull(result);
            assertEquals(productId, result.productId());
            assertEquals(dto.courierName(), result.courierName());
            assertEquals(dto.fee(), result.fee());
        }

        @Test
        @DisplayName("일반 유저가 상품의 배송옵션을 추가할 수 없다")
        void create_not_admin() {
            // given
            var role = "USER";
            // when
            // then
            assertThrows(
                    InvalidRoleException.class,
                    () -> service.create(role, "id", null)
            );
        }

        @Test
        @DisplayName("관리자가 택배사 이름 없이 배송옵션을 추가할 수 없다")
        void create_no_courierName() {
            // given
            var role = "ADMIN";
            var productId = "id";
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .fee(100_000)
                    .build();
            // when
            // then
            var ex = assertThrows(
                    BadRequestException.class,
                    () -> service.create(role, productId, dto)
            );
            assertEquals("모든 파라미터를 올바르게 요청해주세요", ex.getErrorMessage());
        }

        @Test
        @DisplayName("관리자가 배송비 없이 배송옵션을 추가할 수 없다")
        void create_no_fee() {
            // given
            var role = "ADMIN";
            var productId = "id";
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("courier_name")
                    .build();
            // when
            // then
            var ex = assertThrows(
                    BadRequestException.class,
                    () -> service.create(role, productId, dto)
            );
            assertEquals("모든 파라미터를 올바르게 요청해주세요", ex.getErrorMessage());
        }

        @Test
        @DisplayName("관리자가 0 미만 배송비의 배송옵션을 추가할 수 없다")
        void create_feeIsLessThanZero() {
            // given
            var role = "ADMIN";
            var productId = "id";
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("courier_name")
                    .fee(-1)
                    .build();
            // when
            // then
            var ex = assertThrows(
                    BadRequestException.class,
                    () -> service.create(role, productId, dto)
            );
            assertEquals("모든 파라미터를 올바르게 요청해주세요", ex.getErrorMessage());
        }

        @Test
        @DisplayName("관리자가 존재하지 않는 상품의 배송옵션을 추가할 수 없다")
        void create_not_found_product() {
            // given
            var product = MockFactory.createProduct("");
            given(productRepository.findById(anyString()))
                    .willReturn(Optional.empty());
            var role = "ADMIN";
            var productId = product.getId();
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("courier_name")
                    .fee(100_000)
                    .build();
            // when
            // then
            assertThrows(
                    ResourceNotFoundException.class,
                    () -> service.create(role, productId, dto)
            );
        }

        @Test
        @DisplayName("관리자가 이미 배송옵션이 존재한 상품에 배송옵션을 추가할 수 없다")
        void create_exist_options() {
            // given
            var product = MockFactory.createProduct("");
            MockFactory.createProductDeliveryOption(product);
            given(productRepository.findById(anyString()))
                    .willReturn(Optional.of(product));
            var role = "ADMIN";
            var productId = product.getId();
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("courier_name")
                    .fee(100_000)
                    .build();
            // when
            // then
            var ex = assertThrows(
                    BadRequestException.class,
                    () -> service.create(role, productId, dto)
            );
            assertEquals("이미 등록되어 있는 배송옵션이 존재합니다", ex.getErrorMessage());
        }
    }

    @Nested
    @DisplayName("상품 배송옵션 조회")
    class Retrieve {
        @Test
        @DisplayName("관리자가 상품의 존재하는 배송옵션을 조회한다")
        void getByProductId() {
            // given
            var product = MockFactory.createProduct("");
            var deliveryOption = MockFactory.createProductDeliveryOption(product);
            given(productRepository.findById(anyString()))
                    .willReturn(Optional.of(product));
            var role = "ADMIN";
            var productId = product.getId();
            // when
            var result = service.getByProductIdForAdmin(role, productId);
            // then
            assertNotNull(result);
            assertEquals(product.getId(), result.productId());
            assertEquals(deliveryOption.getCourierName(), result.courierName());
            assertEquals(deliveryOption.getFee(), result.fee());
        }

        @Test
        @DisplayName("관리자가 상품의 존재하지 않는 배송옵션을 조회한다")
        void getByProductId_noProductDeliveryOption() {
            // given
            var product = MockFactory.createProduct("");
            given(productRepository.findById(anyString()))
                    .willReturn(Optional.of(product));
            var role = "ADMIN";
            var productId = product.getId();
            // when
            var result = service.getByProductIdForAdmin(role, productId);
            // then
            assertNotNull(result);
            assertEquals(product.getId(), result.productId());
            assertNull(result.courierName());
            assertNull(result.fee());
        }

        @Test
        @DisplayName("일반 유저가 관리자 전용 상품 배송옵션을 조회할 수 없다")
        void getByProductIdForAdmin_not_admin() {
            // given
            var role = "USER";
            var productId = "product-id";
            // when
            // then
            assertThrows(
                    InvalidRoleException.class,
                    () -> service.getByProductIdForAdmin(role, productId)
            );
        }

        @Test
        @DisplayName("관리자가 존재하지 않는 상품의 배송옵션을 조회할 수 없다")
        void getByProductIdForAdmin_not_product() {
            // given
            given(productRepository.findById(anyString()))
                    .willReturn(Optional.empty());
            var role = "ADMIN";
            var productId = "product-id";
            // when
            // then
            assertThrows(
                    ResourceNotFoundException.class,
                    () -> service.getByProductIdForAdmin(role, productId)
            );
        }
    }

    @Nested
    @DisplayName("상품 배송옵션 수정")
    class Update {

        @Test
        @DisplayName("관리자가 상품의 배송옵션을 수정한다")
        void update() {
            // given
            var product = MockFactory.createProduct("");
            MockFactory.createProductDeliveryOption(product);
            given(productRepository.findById(anyString()))
                    .willReturn(Optional.of(product));
            var role = "ADMIN";
            var productId = product.getId();
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("new_courier_name")
                    .fee(10_000)
                    .build();
            // when
            var result = service.update(role, productId, dto);
            // then
            assertNotNull(result);
            assertEquals(product.getId(), result.productId());
            assertEquals(dto.courierName(), result.courierName());
            assertEquals(dto.fee(), result.fee());
        }

        @Test
        @DisplayName("일반 유저가 상품의 배송옵션을 수정할 수 없다")
        void update_notAdmin() {
            // given
            var role = "USER";
            var productId = "product-id";
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("new_courier_name")
                    .fee(10_000)
                    .build();
            // when
            // then
            assertThrows(
                    InvalidRoleException.class,
                    () -> service.update(role, productId, dto)
            );
        }

        @Test
        @DisplayName("관리자가 택배사 이름 없이 상품의 배송옵션을 수정할 수 없다")
        void update_noCourierName() {
            // given
            var product = MockFactory.createProduct("");
            var role = "ADMIN";
            var productId = product.getId();
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .fee(10_000)
                    .build();
            // when
            // then
            var ex = assertThrows(
                    BadRequestException.class,
                    () -> service.update(role, productId, dto)
            );
            assertEquals(
                    "모든 파라미터를 올바르게 요청해주세요",
                    ex.getErrorMessage()
            );
        }

        @Test
        @DisplayName("관리자가 배송비 없이 상품의 배송옵션을 수정할 수 없다")
        void update_noFee() {
            // given
            var product = MockFactory.createProduct("");
            var role = "ADMIN";
            var productId = product.getId();
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("new_courier_name")
                    .build();
            // when
            // then
            var ex = assertThrows(
                    BadRequestException.class,
                    () -> service.update(role, productId, dto)
            );
            assertEquals(
                    "모든 파라미터를 올바르게 요청해주세요",
                    ex.getErrorMessage()
            );
        }

        @Test
        @DisplayName("관리자가 0 미만의 배송비로 상품의 배송옵션을 수정할 수 없다")
        void update_feeIsLessThanZero() {
            // given
            var product = MockFactory.createProduct("");
            var role = "ADMIN";
            var productId = product.getId();
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("new_courier_name")
                    .fee(-1)
                    .build();
            // when
            // then
            var ex = assertThrows(
                    BadRequestException.class,
                    () -> service.update(role, productId, dto)
            );
            assertEquals(
                    "모든 파라미터를 올바르게 요청해주세요",
                    ex.getErrorMessage()
            );
        }

        @Test
        @DisplayName("관리자가 존재하지 않는 상품의 배송옵션을 수정할 수 없다")
        void update_noProduct() {
            // given
            var product = MockFactory.createProduct("");
            MockFactory.createProductDeliveryOption(product);
            given(productRepository.findById(anyString()))
                    .willReturn(Optional.empty());
            var role = "ADMIN";
            var productId = product.getId();
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("new_courier_name")
                    .fee(10_000)
                    .build();
            // when
            // then
            assertThrows(
                    ResourceNotFoundException.class,
                    () -> service.update(role, productId, dto)
            );
        }

        @Test
        @DisplayName("관리자가 존재하는 상품의 존재하지 않는 배송옵션을 수정할 수 없다")
        void update_noProductDeliveryOption() {
            // given
            var product = MockFactory.createProduct("");
            given(productRepository.findById(anyString()))
                    .willReturn(Optional.of(product));
            var role = "ADMIN";
            var productId = product.getId();
            var dto = AdminProductDeliveryOptionsDto.Request.builder()
                    .courierName("new_courier_name")
                    .fee(10_000)
                    .build();
            // when
            // then
            assertThrows(
                    ResourceNotFoundException.class,
                    () -> service.update(role, productId, dto)
            );
        }
    }
}
