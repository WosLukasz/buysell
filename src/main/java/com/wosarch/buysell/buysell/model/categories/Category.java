package com.wosarch.buysell.buysell.model.categories;

import com.wosarch.buysell.buysell.model.common.MongoObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(Category.COLLECTION_NAME)
public class Category extends MongoObject {

    @Transient
    public static final String SEQUENCE_NAME = "categories";
    @Transient
    public static final String COLLECTION_NAME = "categories";

    private String id;
    private String parentId;
    private String code;
}
