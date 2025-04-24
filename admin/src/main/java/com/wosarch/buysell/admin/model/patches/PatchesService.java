package com.wosarch.buysell.admin.model.patches;

import java.util.List;

public interface PatchesService {

    List<String> getPatchesIdsByStatus(PatchStatus status);

    PatchItem save(PatchItem patchItem);
}
