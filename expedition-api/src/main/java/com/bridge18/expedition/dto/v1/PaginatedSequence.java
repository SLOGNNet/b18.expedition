package com.bridge18.expedition.dto.v1;

import org.pcollections.PSequence;

public class PaginatedSequence<T> {
    PSequence<T> values;
    int page;
    int pageSize;
    int count;

    public PaginatedSequence(PSequence<T> values, int page, int pageSize, int count) {
        this.values = values;
        this.page = page;
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