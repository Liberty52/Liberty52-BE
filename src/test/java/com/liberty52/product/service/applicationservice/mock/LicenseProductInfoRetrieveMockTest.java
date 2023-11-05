package com.liberty52.product.service.applicationservice.mock;

import static com.liberty52.product.global.constants.RoleConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.liberty52.product.service.controller.dto.LicenseOptionResponseDto;
import com.liberty52.product.service.entity.OptionDetail;
import com.liberty52.product.service.entity.ProductOption;
import com.liberty52.product.service.entity.ProductState;
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
		LocalDate startDate = LocalDate.of(2023,1,1);
		LocalDate endDate = startDate.plusDays(10);
		LicenseOptionDetail licenseOptionDetail1 = LicenseOptionDetail.create("testArtName1", "testArtistName1", 100,
			true, "testArtUrl1",1000,startDate,endDate);
		LicenseOptionDetail licenseOptionDetail2 = LicenseOptionDetail.create("testArtName2", "testArtistName2", 100,
			true, "testArtUrl2",1000,startDate,endDate);

		Product mockProduct = mock(Product.class);
		when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

		List<LicenseOptionDetail> licenseOptionDetails = Arrays.asList(licenseOptionDetail1, licenseOptionDetail2);

		LicenseOption licenseOption1 = mock(LicenseOption.class);
		when(licenseOption1.getLicenseOptionDetails()).thenReturn(licenseOptionDetails);
		when(licenseOption1.getId()).thenReturn("testOption1");

		when(mockProduct.getLicenseOption()).thenReturn(licenseOption1);

		// When
		LicenseOptionInfoResponseDto result = licenseProductInfoRetrieveService.retrieveLicenseProductOptionInfoListByAdmin(
			ADMIN, productId, onSale);

		// Then
		assertEquals("testOption1", result.getLicenseOptionId());
		assertEquals("testArtName1", result.getLicenseOptionDetailList().get(0).getArtName());
		assertEquals(startDate, result.getLicenseOptionDetailList().get(0).getStartDate());
		assertEquals(endDate, result.getLicenseOptionDetailList().get(0).getEndDate());
		assertEquals("testArtName2", result.getLicenseOptionDetailList().get(1).getArtName());
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

	@Test
	void retrieveLicenseProductOptionInfoTest() {
		// Given
		String productId = "testProductId";
		boolean onSale = true;
		LocalDate startDate = LocalDate.of(2023,1,1);
		LocalDate endDate = startDate.plusDays(10);

		Product product = Product.create("Liberty 52_Frame", ProductState.ON_SALE, 100L,true);

		ProductOption option1 = ProductOption.create("거치 방식", true, true);
		option1.associate(product);
		OptionDetail detailEasel = OptionDetail.create("이젤 거치형", 100,true,100);
		detailEasel.associate(option1);
		OptionDetail detailWall = OptionDetail.create("벽걸이형", 100,true,100);
		detailWall.associate(option1);

		LicenseOption licenseOption = LicenseOption.create("라이센스");
		licenseOption.associate(product);
		LicenseOptionDetail licenseOptionDetail1 = LicenseOptionDetail.create("testArtName1", "testArtistName1", 100,
				true, "testArtUrl1",1000,startDate,endDate);
		licenseOptionDetail1.associate(licenseOption);
		LicenseOptionDetail licenseOptionDetail2 = LicenseOptionDetail.create("testArtName2", "testArtistName2", 100,
				true, "testArtUrl2",1000,startDate,endDate);
		licenseOptionDetail2.associate(licenseOption);

		when(productRepository.findById(productId)).thenReturn(Optional.of(product));

		// When
		LicenseOptionResponseDto result = licenseProductInfoRetrieveService.retrieveLicenseProductOptionInfo(productId);

		// Then
		assertEquals("라이센스", result.getName());
		assertEquals(licenseOption.getLicenseOptionDetails().size(), result.getOptionItems().size());

		assertEquals(licenseOptionDetail1.getId(), result.getOptionItems().get(0).getId());
		assertEquals(licenseOptionDetail1.getArtistName(), result.getOptionItems().get(0).getArtistName());
		assertEquals(licenseOptionDetail1.getArtName(), result.getOptionItems().get(0).getArtName());
		assertEquals(licenseOptionDetail1.getPrice(), result.getOptionItems().get(0).getPrice());
		assertEquals(licenseOptionDetail1.getStock(), result.getOptionItems().get(0).getStock());
		assertEquals(licenseOptionDetail1.getArtUrl(), result.getOptionItems().get(0).getArtUrl());

		assertEquals(licenseOptionDetail2.getId(), result.getOptionItems().get(1).getId());
		assertEquals(licenseOptionDetail2.getArtistName(), result.getOptionItems().get(1).getArtistName());
		assertEquals(licenseOptionDetail2.getArtName(), result.getOptionItems().get(1).getArtName());
		assertEquals(licenseOptionDetail2.getPrice(), result.getOptionItems().get(1).getPrice());
		assertEquals(licenseOptionDetail2.getStock(), result.getOptionItems().get(1).getStock());
		assertEquals(licenseOptionDetail2.getArtUrl(), result.getOptionItems().get(1).getArtUrl());
	}

}
