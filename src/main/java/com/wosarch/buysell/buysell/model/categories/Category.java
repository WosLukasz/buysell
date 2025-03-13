package com.wosarch.buysell.buysell.model.categories;

import com.wosarch.buysell.buysell.model.common.DatabaseObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Category.ENTITY_NAME)
public class Category extends DatabaseObject {

    @Transient
    public static final String ENTITY_NAME = "categories";

    private Long parentId;

    private String code;
}
