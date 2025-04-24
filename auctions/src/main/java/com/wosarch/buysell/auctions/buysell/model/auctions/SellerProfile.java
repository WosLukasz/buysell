package com.wosarch.buysell.auctions.buysell.model.auctions;

import com.wosarch.buysell.auctions.buysell.model.common.ContactInformation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SellerProfile implements Serializable {

    @NotNull
    private String firstname;
    @NotNull
    private String name;
    @NotNull
    private String location;
    @NotNull
    private List<ContactInformation> contactInformation;

}
