package com.wosarch.buysell.buysell.model.common;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.UtilityClass;
import jakarta.persistence.Id;
import org.springframework.data.annotation.Version;

import java.io.Serializable;

@Data
@MappedSuperclass
public class DatabaseObject extends Auditable implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Version
    @Column(nullable = false) // name = "version", columnDefinition = "integer DEFAULT 0",
    private Long version = 0L;

    @UtilityClass
    public static class Fields {
        public static final String OBJECT_ID = "id";
        public static final String VERSION = "version";
    }
}
