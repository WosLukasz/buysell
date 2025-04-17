package com.wosarch.buysell.admin.model.dictionaries;

import com.wosarch.buysell.admin.model.common.AdminDatabaseObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Dictionary.ENTITY_NAME)
public class Dictionary extends AdminDatabaseObject {

    @Transient
    public static final String ENTITY_NAME = "dictionaries";

    private String code;

    private String valueType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_id")
    private List<DictionaryElement> elements;


    @UtilityClass
    public static class DictionaryElementValueTypes {
        public static final String STRING = "STRING";
        public static final String BOOLEAN = "BOOLEAN";
    }
}
