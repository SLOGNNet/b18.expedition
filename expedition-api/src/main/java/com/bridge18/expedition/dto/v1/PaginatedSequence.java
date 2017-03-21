package com.bridge18.expedition.dto.v1;

import org.pcollections.PSequence;

public class PaginatedSequence<T> {
    PSequence<T> values;
    String pagingState;
    int pageSize;
    int count;

    public PaginatedSequence(PSequence<T> values, String pagingState, int pageSize, int count) {
        this.values = values;
        this.pagingState = pagingState;
        this.pageSize = pageSize;
        this.count = count;
    }

    public PSequence<T> getValues() {
        return values;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCount() {
        return count;
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}
