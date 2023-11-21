package com.liberty52.auth.user.web.dto;

import com.liberty52.auth.user.entity.Auth;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class CustomerInfoListResponseDto {
    private List<CustomerInfoResponseDto> infoList;
    private long totalCount;
    private int numberOfElements;
    private int pageNumber;
    private boolean hasPrev;
    private boolean hasNext;
    private boolean first;
    private boolean last;

    public static CustomerInfoListResponseDto of(Page<Auth> page) {
        return builder()
                .infoList(page.getContent().stream().map(CustomerInfoResponseDto::of).toList())
                .totalCount(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .pageNumber(page.getNumber())
                .hasPrev(page.hasPrevious())
                .hasNext(page.hasNext())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }

    public static CustomerInfoListResponseDto of(List<CustomerInfoResponseDto> list, int totalCount, int pageNumber, int size) {
        return builder()
                .infoList(list)
                .totalCount(totalCount)
                .numberOfElements(list.size())
                .pageNumber(pageNumber)
                .hasPrev(pageNumber != 0)
                .hasNext(pageNumber * size < totalCount)
                .first(pageNumber == 0)
                .last(pageNumber * size >= totalCount)
                .build();
    }
}
