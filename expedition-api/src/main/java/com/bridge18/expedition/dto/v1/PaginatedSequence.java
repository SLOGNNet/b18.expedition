package com.bridge18.expedition.dto.v1;

import lombok.*;
import org.pcollections.PSequence;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class PaginatedSequence<T> {
    PSequence<T> values;
    int pageSize;
    int count;

    public boolean isEmpty() {
        return values.isEmpty();
    }
}
