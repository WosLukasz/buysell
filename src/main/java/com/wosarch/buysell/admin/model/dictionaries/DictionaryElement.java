package com.wosarch.buysell.admin.model.dictionaries;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wosarch.buysell.admin.model.common.AdminDatabaseObject;
import com.wosarch.buysell.common.services.postgres.ObjectConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.Transient;

import java.io.IOException;

@Data
@Builder
@Entity
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
