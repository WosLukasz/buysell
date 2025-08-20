package com.wosarch.buysell.auctions.restclient.admin.synchronization;

import com.wosarch.buysell.auctions.common.model.synchronizations.SynchronizationItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "admin", path = "/synchronizations")
public interface SynchronizationFeignClient {

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<SynchronizationItem> saveSynchronization(@RequestBody SynchronizationItem synchronizationItem);

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    ResponseEntity<List<SynchronizationItem>> findByCodeAndStatus(@RequestParam("code") String code, @RequestParam("status") String status);

}