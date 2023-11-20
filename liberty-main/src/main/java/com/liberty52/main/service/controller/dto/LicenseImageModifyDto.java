package com.liberty52.main.service.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class LicenseImageModifyDto {
    @NotBlank
    private String artistName;
    @NotBlank
    private String artName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Min(0)
    private Integer stock;

    public static LicenseImageModifyDto createForTest(String artistName, String artName, LocalDate startDate,
                                                      LocalDate endDate, Integer stock) {
        LicenseImageModifyDto dto = new LicenseImageModifyDto();
        dto.artistName = artistName;
        dto.artName = artName;
        dto.startDate = startDate;
        dto.endDate = endDate;
        dto.stock = stock;
        return dto;
    }
}
