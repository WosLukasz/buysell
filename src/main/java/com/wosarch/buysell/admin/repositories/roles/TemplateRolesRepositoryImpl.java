package com.wosarch.buysell.admin.repositories.roles;

import com.wosarch.buysell.admin.model.roles.Role;
import com.wosarch.buysell.admin.repositories.AdminRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TemplateRolesRepositoryImpl extends AdminRepository implements TemplateRolesRepository {

    Logger logger = LoggerFactory.getLogger(TemplateRolesRepositoryImpl.class);

    @Override
    public Optional<Role> findByCode(String code) {
        if (Objects.isNull(code)) {
            return Optional.empty();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Role.Fields.CODE), code));

        return Optional.of(entityManager.createQuery(criteriaQuery).getSingleResult());
    }

    @Override
    @Transactional(value = "buySellAdminTransactionManager")
    public void createRole(String code, List<String> rights) {
        Role role = Role.builder()
                .code(code)
                .rights(rights)
                .build();

        entityManager.merge(role);
    }

    @Override
    @Transactional(value = "buySellAdminTransactionManager")
    public void addRightsToRole(String code, List<String> rightsToAdd) {
        Optional<Role> roleOptional = findByCode(code);
        if (roleOptional.isEmpty()) {
            logger.error("No role found for code {}", code);
            throw new RuntimeException(); // to change to 404
        }

        Role role = roleOptional.get();

        if (CollectionUtils.isEmpty(role.getRights())) {
            role.setRights(new ArrayList<>());
        }

        role.getRights().addAll(rightsToAdd);
        entityManager.merge(role);
    }

    @Override
    @Transactional(value = "buySellAdminTransactionManager")
    public void removeRightsFromRole(String code, List<String> rightsToRemove) {
        Optional<Role> roleOptional = findByCode(code);
        if (roleOptional.isEmpty()) {
            logger.error("No role found for code {}", code);
            throw new RuntimeException(); // to change to 404
        }

        Role role = roleOptional.get();

        if (CollectionUtils.isEmpty(role.getRights())) {
            role.setRights(new ArrayList<>());
        }

        role.getRights().removeIf(rightsToRemove::contains);
        entityManager.merge(role);
    }

}
