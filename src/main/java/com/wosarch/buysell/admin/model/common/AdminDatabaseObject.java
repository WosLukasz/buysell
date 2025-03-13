package com.wosarch.buysell.admin.model.common;

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
public class AdminDatabaseObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version = 0L;

    @UtilityClass
    public static class Fields {
        public static final String OBJECT_ID = "id";
        public static final String VERSION = "version";
    }
}