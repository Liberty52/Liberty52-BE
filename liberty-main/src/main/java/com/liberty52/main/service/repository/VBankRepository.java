package com.liberty52.main.service.repository;

import com.liberty52.main.service.entity.payment.BankType;
import com.liberty52.main.service.entity.payment.VBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VBankRepository extends JpaRepository<VBank, String> {

    boolean existsByBankAndAccountAndHolder(BankType bankType, String account, String holder);

    @Query("SELECT count(v.id) FROM VBank as v")
    Long countAll();

}
