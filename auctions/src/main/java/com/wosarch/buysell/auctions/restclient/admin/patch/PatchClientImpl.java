package com.wosarch.buysell.auctions.restclient.admin.patch;

import com.wosarch.buysell.auctions.buysell.model.common.CommunicationException;
import com.wosarch.buysell.auctions.common.model.patches.PatchItem;
import com.wosarch.buysell.auctions.common.model.patches.PatchStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PatchClientImpl implements PatchClient {

    Logger logger = LoggerFactory.getLogger(PatchClientImpl.class);

    @Autowired
    private PatchFeignClient patchFeignClient;

    public List<String> getPatchesIdsByStatus(PatchStatus status) {
        try {
            ResponseEntity<List<String>> response = patchFeignClient.getPatchesIdsByStatus(status);
            if (Objects.isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
                throw new CommunicationException();
            }

            return response.getBody();
        } catch (Exception e) {
            logger.error("Error occurred during getPatchesIdsByStatus ", e);
            throw e;
        }
    }

    public PatchItem savePatch(PatchItem patchItem) {
        try {
            ResponseEntity<PatchItem> response = patchFeignClient.savePatch(patchItem);
            if (Objects.isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
                throw new CommunicationException();
            }

            return response.getBody();
        } catch (Exception e) {
            logger.error("Error occurred during savePatch ", e);
            throw e;
        }
    }
}
