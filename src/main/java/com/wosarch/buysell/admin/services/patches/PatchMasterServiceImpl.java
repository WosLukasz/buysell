package com.wosarch.buysell.admin.services.patches;

import com.wosarch.buysell.admin.model.patches.Patch;
import com.wosarch.buysell.admin.model.patches.PatchItem;
import com.wosarch.buysell.admin.model.patches.PatchStatus;
import com.wosarch.buysell.admin.repositories.patches.PatchesRepository;
import com.wosarch.buysell.buysell.api.auctions.AuctionsServiceRestEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class PatchMasterServiceImpl {

    Logger logger = LoggerFactory.getLogger(AuctionsServiceRestEndpoint.class);

    @Autowired
    PatchesRepository patchesRepository;

    @Autowired
    private List<Patch> patches;

    @EventListener(ApplicationReadyEvent.class)
    private void runPatches() {
        //block everything

        try {
            List<String> finishedPatches = patchesRepository.getPatchesIdsByStatus(PatchStatus.SUCCESS);
            patches.stream()
                    .filter(patch -> !finishedPatches.contains(patch.getPatchId()))
                    .sorted(Comparator.comparing(Patch::getPatchId))
                    .forEach(this::runPatch);
            logger.info("Installation finished successfully");
        } catch (Exception e) {
            logger.error("Installation finished with errors", e);
            throw e;
        }

        //unblock everything
    }

    private void runPatch(Patch patch) {
        PatchItem patchItem = savePatchStart(patch.getPatchId());
        try {
            patch.run();
            savePatchSuccess(patchItem);
        } catch (Exception e) {
            savePatchError(patchItem);
            logger.error("Error during patch installation {}", patch.getPatchId(), e);
            throw e;
        }
    }

    private PatchItem savePatchStart(String patchId) {
        PatchItem patchItem = new PatchItem();
        patchItem.setId(patchId);
        patchItem.setStatus(PatchStatus.IN_PROGRESS);

        return patchesRepository.save(patchItem);
    }

    private PatchItem savePatchError(PatchItem patchItem) {
        patchItem.setStatus(PatchStatus.FAILED);
        patchItem.setInstallationDate(new Date());

        return patchesRepository.save(patchItem);
    }

    private PatchItem savePatchSuccess(PatchItem patchItem) {
        patchItem.setStatus(PatchStatus.SUCCESS);
        patchItem.setInstallationDate(new Date());

        return patchesRepository.save(patchItem);
    }
}
