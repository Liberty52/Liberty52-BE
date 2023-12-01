package com.liberty52.main.service.applicationservice.mock;

import com.liberty52.main.service.applicationservice.impl.OptionDetailModifyServiceImpl;
import com.liberty52.main.service.controller.dto.OptionDetailModifyRequestDto;
import com.liberty52.main.service.entity.OptionDetail;
import com.liberty52.main.service.repository.OptionDetailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.liberty52.main.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OptionDetailModifyServiceMockTest {
    @InjectMocks
    OptionDetailModifyServiceImpl optionDetailModifyService;

    @Mock
    OptionDetailRepository optionDetailRepository;

    @Test
    void 옵션선택지수정() {
        /**Given**/
        //generate testProductOptionDetail Entity
        OptionDetail testOptionDetail = mock(OptionDetail.class);

        //generate testProductOptionDetail Request DTO
        String newName = "newName";
        Integer newPrice = 1000;
        Integer newStock = 100;
        OptionDetailModifyRequestDto testOptionDetailModifyRequestDto = OptionDetailModifyRequestDto.create(newName, newPrice, true, newStock);

        String testOptionDetailID = testOptionDetail.getId();
        when(optionDetailRepository.findById(testOptionDetailID)).thenReturn(Optional.of(testOptionDetail));
        when(testOptionDetail.getName()).thenReturn(newName);
        when(testOptionDetail.getPrice()).thenReturn(newPrice);
        when(testOptionDetail.isOnSale()).thenReturn(true);
        when(testOptionDetail.getStock()).thenReturn(newStock);

        /**When**/
        optionDetailModifyService.modifyOptionDetailByAdmin(ADMIN, testOptionDetailID, testOptionDetailModifyRequestDto);

        /**Then**/
        assertEquals(newName, testOptionDetail.getName());
        assertEquals(newPrice, testOptionDetail.getPrice());
        assertTrue(testOptionDetail.isOnSale());
        assertEquals(newStock, testOptionDetail.getStock());
    }
}
