package com.liberty52.product.service.applicationservice.mock;


import com.liberty52.product.global.adapter.s3.S3UploaderApi;
import com.liberty52.product.global.exception.external.forbidden.InvalidRoleException;
import com.liberty52.product.service.applicationservice.impl.ProductModifyServiceImpl;
import com.liberty52.product.service.controller.dto.ProductModifyRequestDto;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.ProductState;
import com.liberty52.product.service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.liberty52.product.global.constants.RoleConstants.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductModifyServiceMockTest {

    @InjectMocks
    private ProductModifyServiceImpl productModifyService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private S3UploaderApi s3Uploader;

    @Mock
    private ModelMapper modelMapper;

    private final String productId = "testProductId";

    @Test
    void 제품수정_성공(){
        /**Given**/
        //testProduct(existed)
        Product testProduct = Product.builder()
                .name("previousName")
                .productState(ProductState.NOT_SALE)
                .price(0L)
                .isCustom(false)
                .build();
        //assume that testProduct has previous image
        testProduct.setProductPictureUrl("previousMockImageUrl");

        //new image & data
        MultipartFile newMockImage = mock(MultipartFile.class);
        ProductModifyRequestDto data = ProductModifyRequestDto.builder()
                .name("newName")
                .productState(ProductState.ON_SALE)
                .price(100L)
                .isCustom(true)
                .build();

        //request dto -> entity
        Product expectedProduct = Product.builder()
                                .name(data.getName())
                                .productState(data.getProductState())
                                .price(data.getPrice())
                                .isCustom(data.getIsCustom())
                                .build();
        expectedProduct.setProductPictureUrl("newMockImageUrl");

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productModifyService.modifyProductByAdmin(ADMIN, productId, data, newMockImage)).thenReturn(expectedProduct);
        when(s3Uploader.upload(newMockImage)).thenReturn("newMockImageUrl");

        /**When**/
        Product modifiedProduct = productModifyService.modifyProductByAdmin(ADMIN, productId, data, newMockImage);

        /**Then**/
        assertEquals(modifiedProduct.getPictureUrl(),"newMockImageUrl");
        assertEquals(modifiedProduct.getName(),"newName");
        assertEquals(modifiedProduct.getProductState(),ProductState.ON_SALE);
        assertEquals(modifiedProduct.getPrice(),100L);
        assertEquals(modifiedProduct.isCustom(),true);
    }

    @Test
    void 제품추가_잘못된권한() {
        //given
        MultipartFile productTestImage = mock(MultipartFile.class);
        ProductModifyRequestDto testDto = ProductModifyRequestDto.builder()
                .name("testName")
                .price(100L)
                .productState(ProductState.ON_SALE)
                .isCustom(true)
                .build();

        //when & then
        Assertions.assertThrows(InvalidRoleException.class, () -> productModifyService.modifyProductByAdmin("Normal", productId, testDto, productTestImage));
    }


}
