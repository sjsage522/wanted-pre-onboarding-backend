package com.junseok.wantedpreonboardingbackend.global.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResponseDto<T> implements Serializable {

    private final List<T> value;
    private final long count;
    private final long page;
    private final long size;
    private final long total;

    @Builder
    public PageResponseDto(List<T> value, long count, long page, long size, long total) {
        this.value = value;
        this.count = count;
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public PageResponseDto(Page<T> page) {
        this.value = page.getContent();
        this.count = page.getNumberOfElements();
        this.page = page.getPageable().getPageNumber();
        this.size = page.getPageable().getPageSize();
        this.total = page.getTotalElements();
    }
}
