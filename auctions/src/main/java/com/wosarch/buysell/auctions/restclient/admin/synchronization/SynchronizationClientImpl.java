package com.wosarch.buysell.auctions.restclient.admin.synchronization;

import com.wosarch.buysell.auctions.buysell.model.common.CommunicationException;
import com.wosarch.buysell.auctions.common.model.synchronizations.SynchronizationItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SynchronizationClientImpl implements SynchronizationClient {

    Logger logger = LoggerFactory.getLogger(SynchronizationClientImpl.class);

    @Autowired
    private SynchronizationFeignClient synchronizationFeignClient;

    @Override
    public SynchronizationItem saveSynchronization(SynchronizationItem synchronizationItem) {
        try {
            ResponseEntity<SynchronizationItem> response = synchronizationFeignClient.saveSynchronization(synchronizationItem);
            if (Objects.isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
                throw new CommunicationException();
            }

            return response.getBody();
        } catch (Exception e) {
            logger.error("Error occurred during saveSynchronization ", e);
            throw e;
        }
    }

    @Override
    public List<SynchronizationItem> findByCodeAndStatus(String code, String status) {
        try {
            ResponseEntity<List<SynchronizationItem>> response = synchronizationFeignClient.findByCodeAndStatus(code, status);
            if (Objects.isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
                throw new CommunicationException();
            }

            return response.getBody();
        } catch (Exception e) {
            logger.error("Error occurred during findByCodeAndStatus ", e);
            throw e;
        }
    }
}
