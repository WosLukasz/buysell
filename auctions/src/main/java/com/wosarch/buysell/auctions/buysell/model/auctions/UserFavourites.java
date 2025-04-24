package com.wosarch.buysell.auctions.buysell.model.auctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.search.AuctionsSearchRequest;
import com.wosarch.buysell.auctions.buysell.model.common.MongoObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(UserFavourites.COLLECTION_NAME)
public class UserFavourites extends MongoObject {

    @Transient
    public static final String COLLECTION_NAME = "usersFavourites";

    private String userId;

    private List<String> auctions;

    private List<AuctionsSearchRequest> filters; // to implement after searching auctions implementation
}