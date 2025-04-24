package com.wosarch.buysell.admin.model.synchronizations;

import com.wosarch.buysell.admin.model.common.AdminDatabaseObject;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SynchronizationItem.ENTITY_NAME)
public class SynchronizationItem extends AdminDatabaseObject {

    @Transient
    public static final String ENTITY_NAME = "synchronizations";

    private Date startDate;

    private Date endDate;

    @Enumerated(EnumType.STRING)
    private SynchronizationStatus status;

    private String code;
}
