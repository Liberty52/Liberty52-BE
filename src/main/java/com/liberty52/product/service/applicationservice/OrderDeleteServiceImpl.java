package com.liberty52.product.service.applicationservice;

import com.liberty52.product.service.entity.OrderStatus;
import com.liberty52.product.service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderDeleteServiceImpl implements OrderDeleteService {

    private final OrdersRepository ordersRepository;

    /** 매일 23시 50분부터 59분까지 5분 간격으로 어제의 Ready 상태인 Order 삭제 */
    @Override
    @Async
    @Scheduled(cron = "0 50/5 23 * * *", zone = "Asia/Seoul")
    public void deleteOrderOfReadyByScheduled() {
        OrderStatus ready = OrderStatus.READY;
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime now = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        Long count = ordersRepository.countAllByOrderStatusAndOrderDateLessThan(ready, today);
        log.info("ORDER SCHEDULED: Now({}), There are {} Ready status Order of yesterday", now, count);
        ordersRepository.deleteAllByOrderStatusAndOrderDateLessThan(ready, today);
        log.info("ORDER SCHEDULED: Now({}), Delete {} Ready status Order of yesterday", now, count);
    }
}
