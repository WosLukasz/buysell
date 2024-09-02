package com.wosarch.buysell.admin.model.patches;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(com.wosarch.buysell.admin.model.patches.PatchItem.COLLECTION_NAME)
public class PatchItem {
    @Transient
    public static final String COLLECTION_NAME = "patches";

    private String id;

    private PatchStatus status;

    private Date installationDate;

    @UtilityClass
    public static class Fields {
        public static final String ID = "id";
        public static final String STATUS = "status";
        public static final String INSTALLATION_DATE = "installationDate";
    }
}