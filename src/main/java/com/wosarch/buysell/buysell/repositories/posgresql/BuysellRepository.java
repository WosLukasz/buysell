package com.wosarch.buysell.buysell.repositories.posgresql;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class BuysellRepository {

    Logger logger = LoggerFactory.getLogger(BuysellRepository.class);

    @Autowired
    @Qualifier("buySellEntityManagerFactory")
    protected EntityManager entityManager;

}