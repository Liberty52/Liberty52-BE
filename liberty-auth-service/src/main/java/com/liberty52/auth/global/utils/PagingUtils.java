package com.liberty52.auth.global.utils;

import com.liberty52.auth.global.exception.external.badrequest.PageNumberOutOfRangeException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class PagingUtils {

    public static final String START_PAGE = "startPage";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String LAST_PAGE = "lastPage";
    public static final String TOTAL_PAGE = "totalPage";

    public static final String CREATED_AT = "createdAt";


    public static Map<String, Long> getPageInfo(long totalPages, long pageNumber) {

        long currentPage = pageNumber + 1; // 0부터 시작하는 페이지 번호를 1부터 시작하는 번호로 변환
        long startPage = currentPage % 10 == 0 ? (currentPage / 10 - 1) * 10 + 1
                : (currentPage / 10) * 10 + 1;
        long lastPage = Math.min(totalPages,
                10L * (currentPage % 10 == 0 ? currentPage / 10 : currentPage / 10 + 1));
        Map<String, Long> returnMap = new HashMap<>();
        returnMap.put(START_PAGE, startPage);
        returnMap.put(CURRENT_PAGE, currentPage);
        returnMap.put(LAST_PAGE, lastPage);
        returnMap.put(TOTAL_PAGE, totalPages);
        return returnMap;
    }

    public static void validatePageNumber(int pageNumber, int totalPages) {
        if (pageNumber < 0 || pageNumber >= totalPages) {
            throw new PageNumberOutOfRangeException();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class PageInfo {
        private Long startPage;
        private Long currentPage;
        private Long lastPage;
        private Long totalPage;
        private Long totalCount;

        public static PageInfo of(Page<?> page) {
            long totalPages = page.getTotalPages();
            long pageNumber = page.getPageable().getPageNumber();
            long totalCount = page.getTotalElements();
            return PageInfo.of(totalPages, pageNumber, totalCount);
        }

        public static PageInfo of(Long totalPage, Long pageNum, Long totalCount) {
            validatePageNumber(Math.toIntExact(pageNum), Math.toIntExact(totalPage));

            Long currentPage = pageNum + 1;
            Long startPage = currentPage % 10 == 0 ?
                    (currentPage / 10 - 1) * 10 + 1 : (currentPage / 10) * 10 + 1;
            Long lastPage = Math.min(
                    totalPage,
                    10L * (currentPage % 10 == 0 ? currentPage / 10 : currentPage / 10 + 1)
            );
            return PageInfo.of(startPage, currentPage, lastPage, totalPage, totalCount);
        }

        private static PageInfo of(Long startPage, Long currentPage, Long lastPage, Long totalPage, Long totalCount) {
            return new PageInfo(startPage, currentPage, lastPage, totalPage, totalCount);
        }

    }

}
