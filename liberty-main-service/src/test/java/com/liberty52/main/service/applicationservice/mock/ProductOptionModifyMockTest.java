package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.main.service.applicationservice.impl.ProductOptionModifyServiceImpl;
import com.liberty52.main.service.controller.dto.ProductOptionModifyRequestDto;
import com.liberty52.main.service.entity.ProductOption;
import com.liberty52.main.service.repository.ProductOptionRepository;
import com.liberty52.main.service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atMostOnce;

@ExtendWith(MockitoExtension.class)
public class ProductOptionModifyMockTest {

    @InjectMocks
    ProductOptionModifyServiceImpl productOptionModifyService;

    @Mock
    ProductOptionRepository productOptionRepository;

    @Mock
    ProductRepository productRepository;

    @Test
    void 옵션수정() {
        //given 조건
        given(productOptionRepository.findById("testId"))
                .willReturn(Optional.ofNullable(ProductOption.builder().name("test").onSale(true).require(true).build()));
        //given: springBootTest와는 달리 DB와의 연결이 안되기 때문에 Repository의 반환 객체를 설정해 줘야한다


        //when 테스트하려는 메소드
        ProductOptionModifyRequestDto dto = ProductOptionModifyRequestDto.create("test", false, false);
        productOptionModifyService.modifyProductOptionByAdmin(ADMIN, "testId", dto);
        //실제 테스트를 하려는 메소드 실행

        //then 결과 체크
        Mockito.verify(productOptionRepository, atMostOnce()).findById("testId");
        //위의 메소드는 void이기 때문에 리턴을 받지 않아 대신 안의 메소드 실행여부를 체크한다
        //리턴을 받는 경우 Assertions.assertEquals();로 체크

        Assertions.assertThrows(ResourceNotFoundException.class, () -> productOptionModifyService.modifyProductOptionByAdmin(ADMIN, "error", dto));
        //예외 테스트

    }

    @Test
    void 옵션삭제상태() {
        //given 조건
        given(productOptionRepository.findById("testId"))
                .willReturn(Optional.ofNullable(ProductOption.builder().name("test").onSale(true).require(true).build()));
        //given: springBootTest와는 달리 DB와의 연결이 안되기 때문에 Repository의 반환 객체를 설정해 줘야한다


        //when 테스트하려는 메소드
        productOptionModifyService.modifyProductOptionOnSailStateByAdmin(ADMIN, "testId");
        //실제 테스트를 하려는 메소드 실행

        //then 결과 체크
        Mockito.verify(productOptionRepository, atMostOnce()).findById("testId");
        //위의 메소드는 void이기 때문에 리턴을 받지 않아 대신 안의 메소드 실행여부를 체크한다
        //리턴을 받는 경우 Assertions.assertEquals();로 체크


        Assertions.assertThrows(ResourceNotFoundException.class, () -> productOptionModifyService.modifyProductOptionOnSailStateByAdmin(ADMIN, "error"));
        //예외 테스트

    }

}
