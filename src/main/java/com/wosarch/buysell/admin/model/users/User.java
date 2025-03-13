package com.wosarch.buysell.admin.model.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import org.springframework.data.annotation.Transient;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = User.ENTITY_NAME)
public class User {

    @Transient
    public static final String ENTITY_NAME = "users";

    @Id
//    @Column(columnDefinition="character varying (100) not null", length=100, nullable=false)
    private String id;

    private String firstName;

    private String name;

    private String email;
}
