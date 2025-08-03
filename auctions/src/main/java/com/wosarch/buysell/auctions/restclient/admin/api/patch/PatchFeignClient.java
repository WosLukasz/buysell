package com.wosarch.buysell.auctions.restclient.admin.api.patch;

import com.wosarch.buysell.auctions.common.model.patches.PatchItem;
import com.wosarch.buysell.auctions.common.model.patches.PatchStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "admin", path = "/admin/patches")
public interface PatchFeignClient {

    @RequestMapping(path = "/signatures-by-status", method = RequestMethod.GET)
    ResponseEntity<List<String>> getPatchesIdsByStatus(@RequestParam("status") PatchStatus status);

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<PatchItem> savePatch(@RequestBody PatchItem patchItem);
}
