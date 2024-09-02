package com.wosarch.buysell.admin.repositories.patches;

import com.wosarch.buysell.admin.model.patches.PatchStatus;

import java.util.List;

public interface TemplatePatchesRepository {

    List<String> getPatchesIdsByStatus(PatchStatus status);

}