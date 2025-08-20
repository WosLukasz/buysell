package com.wosarch.buysell.auctions.restclient.admin.patch;

import com.wosarch.buysell.auctions.common.model.patches.PatchItem;
import com.wosarch.buysell.auctions.common.model.patches.PatchStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatchFeignFallback implements PatchFeignClient {

    @Override
    public ResponseEntity<List<String>> getPatchesIdsByStatus(PatchStatus status) {
        return null;
    }

    @Override
    public ResponseEntity<PatchItem> savePatch(PatchItem patchItem) {
        return null;
    }
}