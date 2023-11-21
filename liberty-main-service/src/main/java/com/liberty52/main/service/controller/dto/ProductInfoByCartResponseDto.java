package com.liberty52.main.service.controller.dto;

import com.liberty52.main.service.entity.Product;
import com.liberty52.main.service.entity.ProductOption;
import com.liberty52.main.service.entity.license.LicenseOption;
import com.liberty52.main.service.entity.license.LicenseOptionDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoByCartResponseDto {
    String productId;
    boolean isCustom;
    List<ProductOptionInfoByCartResponseDto> productOptionList;
    LicenseOptionByCartResponseDto licenseOption;

    public ProductInfoByCartResponseDto(Product product) {
        productId = product.getId();
        isCustom = product.isCustom();
        productOptionList = product.getProductOptions().stream().filter(ProductOption::isOnSale).map(ProductOptionInfoByCartResponseDto::new).collect(Collectors.toList());
        if(!product.isCustom()) {
            licenseOption =  new LicenseOptionByCartResponseDto(product.getLicenseOption());
        }
    }

    @Data
    public static class LicenseOptionByCartResponseDto {
        String licenseOptionId;
        String optionName;
        List<LicenseOptionDetailByCartResponseDto> licenseOptionDetailList;

        public LicenseOptionByCartResponseDto(LicenseOption licenseOption) {
            LocalDate today = LocalDate.now();
            this.licenseOptionId = licenseOption.getId();
            this.optionName = licenseOption.getName();
            this.licenseOptionDetailList = licenseOption.getLicenseOptionDetails().stream().filter(LicenseOptionDetail::getOnSale)
                    .filter(licenseOptionDetail -> licenseOptionDetail.getStartDate().isBefore(today))
                    .filter(licenseOptionDetail -> licenseOptionDetail.getEndDate().isAfter(today))
                    .map(LicenseOptionDetailByCartResponseDto::new)
                    .toList();
        }

        @Data
        public static class LicenseOptionDetailByCartResponseDto {
            String licenseOptionDetailId;
            String artName;
            String artistName;
            Integer stock;
            String artUrl;
            Integer price;


            public LicenseOptionDetailByCartResponseDto(LicenseOptionDetail licenseOptionDetail) {
                licenseOptionDetailId = licenseOptionDetail.getId();
                artName = licenseOptionDetail.getArtName();
                artistName = licenseOptionDetail.getArtistName();
                stock = licenseOptionDetail.getStock();
                price = licenseOptionDetail.getPrice();
                artUrl = licenseOptionDetail.getArtUrl();
            }
        }
    }
}
