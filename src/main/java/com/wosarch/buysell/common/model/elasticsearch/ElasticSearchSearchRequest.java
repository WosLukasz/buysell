package com.wosarch.buysell.common.model.elasticsearch;

import co.elastic.clients.elasticsearch._types.SortOrder;
import com.wosarch.buysell.buysell.model.common.DatabaseObject;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@MappedSuperclass
public class ElasticSearchSearchRequest extends DatabaseObject {

    @Column(name = "pageOffset")
    private Integer offset;

    @NonNull
    private Integer pageSize;

    private String sortBy;

    private SortOrder sortOrder;
}
