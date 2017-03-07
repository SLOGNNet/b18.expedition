package com.bridge18.expedition.dto.v1;

import lombok.Value;
import org.pcollections.PSequence;

@Value
public class PaginatedSequence<T> {
    PSequence<T> drivers;
    int page;
    int pageSize;
    int count;

    public PaginatedSequence(PSequence<T> drivers, int page, int pageSize, int count) {
        this.drivers = drivers;
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
    }

    public boolean isEmpty() {
        return drivers.isEmpty();
    }

    public boolean isFirst() {
        return page == 0;
    }

    public boolean isLast() {
        return count <= (page + 1) * pageSize;
    }

    public boolean isPaged() {
        return count > pageSize;
    }

    public int getPageCount() {
        return ((count - 1) / pageSize) + 1;
    }
}
