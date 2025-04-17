package com.wosarch.buysell.admin.model.dictionaries;

import com.wosarch.buysell.admin.model.common.AdminDatabaseObject;
import com.wosarch.buysell.admin.utils.ObjectConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.Transient;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DictionaryElement.ENTITY_NAME)
public class DictionaryElement extends AdminDatabaseObject {

    @Transient
    public static final String ENTITY_NAME = "dictionariesElements";

    private String code;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "value", columnDefinition = "json")
    @Convert(converter = ObjectConverter.class) // https://www.baeldung.com/hibernate-persist-json-object
    private Object value;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @MapKeyColumn(name="name")
//    @Column(name="value")
//    @CollectionTable(name="dictionary_properties", joinColumns=@JoinColumn(name="property_id"))
//    private Map<String, Object> properties;

}
