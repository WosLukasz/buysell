package com.wosarch.buysell.auctions.restclient.admin.api.patch;

import com.wosarch.buysell.auctions.common.model.patches.PatchItem;
import com.wosarch.buysell.auctions.common.model.patches.PatchStatus;

import java.util.List;

public interface PatchClient {

    List<String> getPatchesIdsByStatus(PatchStatus status)

    PatchItem savePatch(PatchItem patchItem);

}
