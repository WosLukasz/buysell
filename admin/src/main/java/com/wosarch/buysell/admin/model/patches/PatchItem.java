package com.wosarch.buysell.admin.model.patches;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PatchItem.ENTITY_NAME)
public class PatchItem {

    @Transient
    public static final String ENTITY_NAME = "patches";

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private PatchStatus status;

    @Column(name="installationdate")
    private Date installationDate;

    @UtilityClass
    public static class Fields {
        public static final String STATUS = "status";
        public static final String INSTALLATION_DATE = "installationDate";
    }
}