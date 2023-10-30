package com.liberty52.product.service.applicationservice.mock;

import static com.liberty52.product.global.constants.RoleConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liberty52.product.global.exception.external.notfound.ResourceNotFoundException;
import com.liberty52.product.service.applicationservice.impl.LicenseProductInfoRetrieveServiceImpl;
import com.liberty52.product.service.controller.dto.LicenseOptionInfoResponseDto;
import com.liberty52.product.service.entity.Product;
import com.liberty52.product.service.entity.license.LicenseOption;
import com.liberty52.product.service.entity.license.LicenseOptionDetail;
import com.liberty52.product.service.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class LicenseProductInfoRetrieveMockTest {

	@InjectMocks
	LicenseProductInfoRetrieveServiceImpl licenseProductInfoRetrieveService;

	@Mock
	ProductRepository productRepository;

	@Test
	void retrieveLicenseProductOptionInfoListByAdminTest() {
		// Given
		String productId = "testProductId";
		boolean onSale = true;

		LicenseOptionDetail licenseOptionDetail1 = LicenseOptionDetail.create("testArtName1", "testArtistName1", 100,
			true, "testArtUrl1");
		LicenseOptionDetail licenseOptionDetail2 = LicenseOptionDetail.create("testArtName2", "testArtistName2", 100,
			true, "testArtUrl2");

		Product mockProduct = mock(Product.class);
		when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

		List<LicenseOptionDetail> licenseOptionDetails = Arrays.asList(licenseOptionDetail1, licenseOptionDetail2);

		LicenseOption licenseOption1 = mock(LicenseOption.class);
		when(licenseOption1.getLicenseOptionDetails()).thenReturn(licenseOptionDetails);
		when(licenseOption1.getId()).thenReturn("testOption1");
		when(licenseOption1.getRequire()).thenReturn(true);
		when(licenseOption1.getOnSale()).thenReturn(true);
		List<LicenseOption> licenseOptions = List.of(licenseOption1);

		when(mockProduct.getLicenseOptions()).thenReturn(licenseOptions);

		// When
		List<LicenseOptionInfoResponseDto> result = licenseProductInfoRetrieveService.retrieveLicenseProductOptionInfoListByAdmin(
			ADMIN, productId, onSale);

		// Then
		assertEquals(1, result.size());
		assertEquals("testOption1", result.get(0).getLicenseOptionId());
		assertEquals("testArtName1", result.get(0).getLicenseOptionDetailList().get(0).getArtName());
		assertEquals("testArtName2", result.get(0).getLicenseOptionDetailList().get(1).getArtName());
	}

	@Test
	void retrieveLicenseProductOptionInfoListByAdmin_When_Product_Not_Found_Test() {
		// Given
		String productId = "testProductId";
		boolean onSale = true;

		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		// When
		// Then
		assertThrows(ResourceNotFoundException.class,
			() -> licenseProductInfoRetrieveService.retrieveLicenseProductOptionInfoListByAdmin(ADMIN, productId,
				onSale));
	}
}
