package com.wosarch.buysell.auctions.restclient.admin.users;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "admin", path = "/users")
public interface UsersFeignClient {

    @RequestMapping(method = RequestMethod.GET, path = "/current/rights")
    ResponseEntity<List<String>> getCurrentUserRights();
}