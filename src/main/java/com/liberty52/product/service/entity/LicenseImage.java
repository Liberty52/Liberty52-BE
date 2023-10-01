package com.liberty52.product.service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "license_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LicenseImage {
	@Id
	private String id = UUID.randomUUID().toString();
	@Column(nullable = false)
	private String artistName;
	@Column(nullable = false)
	private String workName;
	@Column(nullable = false)
	private LocalDateTime startDate;
	@Column(nullable = false)
	private LocalDateTime endDate;
	@Column(nullable = false)
	private String url;
	@Column(nullable = false)
	private Integer stock;

	@Builder
	private LicenseImage(String artistName, String workName, LocalDateTime startDate, LocalDateTime endDate, String url,
		Integer stock) {
		this.artistName = artistName;
		this.workName = workName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.url = url;
		this.stock = stock;
	}
}
