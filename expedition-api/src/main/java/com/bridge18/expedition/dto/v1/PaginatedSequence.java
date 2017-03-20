package com.bridge18.expedition.dto.v1;

import lombok.Value;
import org.pcollections.PSequence;

@Value
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

    public boolean isEmpty() {
        return values.isEmpty();
    }
}
