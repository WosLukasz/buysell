package com.wosarch.buysell.admin.repositories;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository {

    @Autowired
    @Qualifier("buySellAdminEntityManagerFactory")
    protected EntityManager entityManager;
}
