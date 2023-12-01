package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.OptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionDetailRepository extends JpaRepository<OptionDetail, String> {
    Optional<OptionDetail> findByIdAndProductOption_Id(String id, String ProductOptionId);

    Optional<OptionDetail> findByName(String name);
}
