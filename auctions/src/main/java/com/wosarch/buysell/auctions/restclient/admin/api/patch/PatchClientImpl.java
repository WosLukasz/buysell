package com.wosarch.buysell.auctions.restclient.admin.api.patch;

import com.wosarch.buysell.auctions.common.model.patches.PatchItem;
import com.wosarch.buysell.auctions.common.model.patches.PatchStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PatchClientImpl implements PatchClient {


    public List<String> getPatchesIdsByStatus(PatchStatus status) {
        return Collections.emptyList();
    }

    public PatchItem savePatch(PatchItem patchItem) {
        return null;
        // put to the queue
    }


}
