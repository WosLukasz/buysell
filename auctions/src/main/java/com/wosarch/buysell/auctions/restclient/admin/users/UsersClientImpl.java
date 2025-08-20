package com.wosarch.buysell.auctions.restclient.admin.users;

import com.wosarch.buysell.auctions.buysell.model.common.CommunicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsersClientImpl implements UsersClient {

    Logger logger = LoggerFactory.getLogger(UsersClientImpl.class);

    @Autowired
    private UsersFeignClient usersFeignClient;

    @Override
    public List<String> getCurrentUserRights() {
        try {
            ResponseEntity<List<String>> response = usersFeignClient.getCurrentUserRights();
            if (Objects.isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
                throw new CommunicationException();
            }

            return response.getBody();
        } catch (Exception e) {
            logger.error("Error occurred during getCurrentUserRights ", e);
            throw e;
        }
    }

}
