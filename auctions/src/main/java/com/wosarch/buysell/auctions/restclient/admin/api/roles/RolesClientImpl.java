package com.wosarch.buysell.auctions.restclient.admin.api.roles;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesClientImpl implements RolesClient {

    @Override
    public List<String> getCurrentUserRights() {
        return List.of();
    }

}
