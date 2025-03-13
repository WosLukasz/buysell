package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.common.ContactInformation;
import com.wosarch.buysell.buysell.model.common.DatabaseObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = SellerProfile.ENTITY_NAME)
public class SellerProfile extends DatabaseObject {

    public static final String ENTITY_NAME = "vendors";

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<Auction> auctions;

    @NotNull
    private String firstname;

    @NotNull
    private String name;

    @NotNull
    private String location;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ContactInformation> contactInformation;

}
