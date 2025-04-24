package com.wosarch.buysell.auctions.common.model.elasticsearch;

import co.elastic.clients.elasticsearch._types.SortOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ElasticSearchSearchRequest {
    private Integer offset;

    @NonNull
    private Integer pageSize;

    private String sortBy;

    private SortOrder sortOrder;
}
