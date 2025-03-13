package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.auctions.search.AuctionsSearchRequest;
import com.wosarch.buysell.buysell.model.common.DatabaseObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = UserFavourites.ENTITY_NAME)
public class UserFavourites extends DatabaseObject {

    @Transient
    public static final String ENTITY_NAME = "usersFavourites";

    private String userId;

    private List<String> auctions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "usersFavourite_id")
    private List<AuctionsSearchRequest> filters; // to implement after searching auctions implementation
}